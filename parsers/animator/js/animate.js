function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    var name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function getDivisions(){
    return 4;
}

function deg(angle){
    return angle / (Math.PI / 180)
}

function rad(angle){
    return angle * (Math.PI / 180)
}

//TODO make param
function isDebugLines(){
    return false;
}

//TODO make param
function isRelativeAngles(){
    return true;
}

//given a point, an angle, and a distance, returns the point dist away along angle. Uses radians.
function plr(point, angle, dist){
    return eplr(point, angle, dist, dist);
}

//elliptical form of plr.
function eplr(point, angle, hdist, vdist){
    return [point[0] + Math.cos(angle) * hdist, point[1] + Math.sin(angle) * vdist];
}

//given two points, return r and theta from one to the other. Uses radians.
function rT(p0, p1){
    var x0 = p0[0];
    var y0 = p0[1];
    var x1 = p1[0];
    var y1 = p1[1];
    var dx = x1 - x0;
    var dy = y1 - y0;
    
    return ([
        Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)),
        Math.atan2(dy, dx)
    ]);
}

//Given a list of points, convert it into a base location and a list of polar coordinates from the previous point. First polar is base on 0, others are based on previous.
function splineToTentacle(spline){
    //start tentacle
    var t = [spline[0]];
    var prev = 0;
    for (var i = 0; i < spline.length - 1; i++){
        var rth = rT(spline[i], spline[i+1]);
        var next = rth[1];
        rth[1] -= prev;
        if (rth[1] > Math.PI){
            rth[1] -= Math.PI * 2; 
        }
        if (rth[1] < - Math.PI){
            rth[1] += Math.PI * 2;
        }
        prev = next;
        t.push(rth);
    }
    
    return t;
}

function tentacleToSpline(t){
    var s = [t[0]];
    var prev = 0;
    for (var i = 1; i < t.length; i++){
        var point = plr(s[s.length-1], t[i][1] + prev, t[i][0]);
        s.push(point);
        prev += t[i][1];
    }
    return s;
}

//Given an elapsed number of rockspans, a set of words, and a context, draw the correct frame in that context.
function frame(rsecs, words){
    //TODO make param
    var accel = 3;

    rsecs *= accel;

    var whole = Math.floor(rsecs);
    var remainder = rsecs - whole;
    var index;
    if (remainder < .5){
	index = whole;
    } else {
	index = whole + (remainder - .5) * 2; 
	//log(index);
    }
    var splines = interpolate(words, index);
    draw(splines);
    
}

//Given a set of sets of splines and a float indicating where in the set to be, interpolates the control points.
function interpolate(words, position){
    var count = words.length;
    if (position <= 0 || isNaN(position)){
	return words[0];
    } 
    if (position >= words.length - 1){
	return words[words.length - 1];
    }
    if (position == position.toFixed(0) ) {
	return words[position];
    }
    var splines = [];
    var from = words[Math.floor(position)];
    var to = words[Math.ceil(position)];
    var tween = position - Math.floor(position);
    for (var i = 0; i < from.length; i++){
	var ftent = from[i];
	var ttent = to[i];
	points = [];
	//do first point cartesian
	points.push(ctween(ftent[0], ttent[0], tween));
	
	//do remaining points polar
	for (var j = 0; j < ftent.length - 1; j++){
	    if (isRelativeAngles()){
		//asString([ftent[i+1], ttent[i+1], tween, ctween(ftent[i+1], ttent[i+1], tween)])
		points.push(ctween(ftent[j+1], ttent[j+1], tween))
	    } else {
	        var polar = ptween(ftent[j], ftent[j+1], ttent[j], ttent[j+1], tween)
	        var lastpoint = points.slice(-1)[0];
	        var px = lastpoint[0];
	        var py = lastpoint[1];
	        var rr = polar[0];
	        var rth = polar[1];
	        var point = plr(lastpoint, rth, rr);
	        points.push(point);
      }
	}

	//2q1wwwwujjjjhy``````tgrrrrrrrrrrrrrrrrrqN BBBBBB
	      splines.push(points);
    }
    //asString(['r',splines[0]]);
    return splines;
}

function asSp(splines){
    if (isRelativeAngles()){
        var newSplines = [];
        for (var i = 0; i < splines.length; i++){
            newSplines.push(tentacleToSpline(splines[i]));
        }
        return newSplines;
    } else {
        return splines;
    }
}
 
function ctween(fp, tp, d){
    return [
	fp[0] + (tp[0] - fp[0]) * d,
	fp[1] + (tp[1] - fp[1]) * d
    ];
}

//given two lines, and an index between them, returns the r and theta of the intermediate line.
function ptween(fp0, fp1, tp0, tp1, d){
    var fx0 = fp0[0];
    var fy0 = fp0[1];
    var fx1 = fp1[0];
    var fy1 = fp1[1];
    var fdx = fx1 - fx0;
    var fdy = fy1 - fy0;
    
    var fr = Math.sqrt(fdx * fdx + fdy * fdy);
    var fth = Math.atan2(fdy, fdx);

    var tx0 = tp0[0];
    var ty0 = tp0[1];
    var tx1 = tp1[0];
    var ty1 = tp1[1];
    var tdx = tx1 - tx0;
    var tdy = ty1 - ty0;
    
    var tr = Math.sqrt(tdx * tdx + tdy * tdy);
    var tth = Math.atan2(tdy, tdx);

    var rr = fr + (tr - fr) * d;
    if (tth - fth > Math.PI) {
	tth -= 2 * Math.PI;
    }
    if (tth - fth < -1 * Math.PI) {
	tth += 2 * Math.PI;
    }
    var rth = fth + (tth - fth) * d;
    var deg = 180 / Math.PI;
    //asString([fth * deg, tth * deg, rth * deg]);
    return [rr, rth];
}

function draw(splines){
    //asString(['draw', splines[4]])
    splines = asSp(splines);
    var c = document.getElementById("rik_win");
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, 300, 300);

    ctx.save();
    ctx.translate(50, 50);
    ctx.scale(2,-2);
    
    for (var s = 0; s < splines.length; s++){
	points = splines[s];

	for (var i = 0; i < getDivisions() * 3; i+=3){
	    
	    p0 = points[i];
	    p1 = points[i+1];
	    p2 = points[i+2];
	    p3 = points[i+3];
	    x0 = p0[0];
	    y0 = p0[1];
	    x1 = p1[0];
	    y1 = p1[1];
	    x2 = p2[0];
	    y2 = p2[1];
	    x3 = p3[0];
	    y3 = p3[1];
	    ctx.beginPath();
	    ctx.moveTo(x0,y0);
	    ctx.bezierCurveTo(x1,y1,x2,y2,x3,y3);
	    ctx.stroke();
	    if (isDebugLines()){
		ctx.save();
		ctx.setLineDash([s,1]);
		ctx.strokeStyle = "green";
		ctx.beginPath();
		ctx.moveTo(x0,y0);
		ctx.lineTo(x1,y1);
		ctx.lineTo(x2,y2);
		ctx.lineTo(x3,y3);
		ctx.stroke();
		ctx.restore();
	    }
	    //asString(p0);
	}
	//asString(points);
    }
    //asString(splines[0]);
    ctx.restore();
    
}

// Given a word and the riklang data, returns a list of splines
function parseWord(word,riklang){
    parts = word.split("-"); 
    //log(parts);
    var tents = [];
    var m = parts[0];
    var m_t = riklang.morphemes[m].glyph;
    for (let t in m_t){
	      tents.push(m_t[t]);
    } 
    var a = parts[1];
    var a_t = riklang.aspects[a].glyph;
    tents.push(a_t[0]);
    var r = parts[2];
    var r_t = riklang.relations[r].glyph;
    tents.push(r_t[0]);
    var c = parts[3];
    var p = '';
    if (c.length == 2){
        p = c.charAt(1);
        c = c.charAt(0);
    }
    var c_t0 = Object.assign({}, riklang.collectors[c].glyph[0]);
    if (p == 'P'){
        c_t0.y += 20;
        c_t0.y2 += 20;
    }
    if (p == 'O'){
        c_t0.y += 40;
        c_t0.y2 += 40;
    }
    tents.push(c_t0);

    //create splines 
    var splines = [];
    for (let t in tents){
	      var points = getPointsForTentacle(tents[t]);
        if (isRelativeAngles()){
            splines.push(splineToTentacle(points));
        } else {
	          splines.push(points);
        }
    }
    return splines;
    
}

// Given an utterance divided by '.', parses the words in the utterance and returns a list of lists of splines
function parseUtterance(utterance){
    var riklang = getLang();
    var u_words = utterance.split('.');
    var words = [];
    for (let w in u_words){
        u_word = u_words[w];
        words.push(parseWord(u_word, riklang));
    }
    return words;
}

function log(message){
    var logArea = document.getElementById('log');
    var value = logArea.value;
    logArea.value = value + "\n" + message ;
}

function asString(object){
    var string = JSON.stringify(object);
    
    log(string.replace(/\.(\d{4})\d+/g, '.$1'));
}
