function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function getDivisions(){
    return 4;
}

//given an elapsed number of rockspans, a set of words, and a context, draw the correct fram in that context.
function frame(rsecs, words){
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
    splines = [];
    from = words[Math.floor(position)];
    to = words[Math.ceil(position)];
    tween = position - Math.floor(position);
    for (var i = 0; i < from.length; i++){
	var ftent = from[i];
	var ttent = to[i];
	points = [];
	//do first point cartesian
	points.push(ctween(ftent[0], ttent[0], tween));
	
	//do remaining points polar
	for (var j = 0; j < ftent.length - 1; j++){
	    var polar = ptween(ftent[j], ftent[j+1], ttent[j], ttent[j+1], tween)
	    var lastpoint = points.slice(-1)[0];
	    var px = lastpoint[0];
	    var py = lastpoint[1];
	    var rr = polar[0];
	    var rth = polar[1];
	    point = [
		px + Math.cos(rth) * rr,
		py + Math.sin(rth) * rr
	    ];
	    points.push(point);
	}

	//2q1wwwwujjjjhy``````tgrrrrrrrrrrrrrrrrrqN BBBBBB
	splines.push(points);
    }
    //asString(splines[0]);
    return splines;
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
    var c = document.getElementById("rik_win");
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, 300, 300);

    ctx.save();
    ctx.translate(50, 50);
    ctx.scale(2,-2);
    
    for (let s in splines){
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
    m = parts[0];
    m_t = riklang.morphemes[m].glyph;
    for (let t in m_t){
	      tents.push(m_t[t]);
    } 
    a = parts[1];
    a_t = riklang.aspects[a].glyph;
    tents.push(a_t[0]);
    r = parts[2];
    r_t = riklang.relations[r].glyph;
    tents.push(r_t[0]);
    c = parts[3];
    c_t = riklang.collectors[c].glyph;
    tents.push(c_t[0]);
    //asString(tents);

    //create splines 
    var splines = [];
    for (let t in tents){
	      var points = getPointsForTentacle(tents[t]);
	      splines.push(points);
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