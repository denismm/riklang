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

function draw(splines){
    var c = document.getElementById("rik_win");
    var ctx = c.getContext("2d");
    ctx.translate(100, 100);
    ctx.scale(1,-1);
    
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
	    //asString(points);
	}
	
    }
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

function log(message){
    var logArea = document.getElementById('log');
    var value = logArea.value;
    logArea.value = value + "\n" + message ;
}

function asString(object){
    log(JSON.stringify(object));
}
