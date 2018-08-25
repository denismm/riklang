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

function getPointsForLine(line){
    x = line.x;
    y = line.y;
    x2 = line.x2;
    y2 = line.y2;
    var points = [];
    var divs = getDivisions();
    dx = (x2 - x) / (divs * 3);
    dy = (y2 - y) / (divs * 3);
    for (i = 0; i <= divs * 3; i++){
        points.push([x + dx * i, y + dy * i]);
    }
    return points;
}


function getPointsForTentacle(tentacle){
    if (tentacle.type == 'line'){
        return getPointsForLine(tentacle);
    } else {
        log(JSON.stringify(tentacle));
        return [];
    }
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
	    asString(p0);
	    asString(points);
	}
	
    }
}

function log(message){
    var logArea = document.getElementById('log');
    var value = logArea.value;
    logArea.value = value + "\n" + message ;
}

function asString(object){
    log(JSON.stringify(object));
}
