function getPointsForLine(line){
    x = line.x;
    y = line.y;
    x2 = line.x2;
    y2 = line.y2;
    var points = [];
    var divs = getDivisions();
    dx = (x2 - x) / (divs * 3);
    dy = (y2 - y) / (divs * 3);
    for (i = 0; i <= 1; i+= 1 / (divs * 3)){
        points.push(ctween([x,y],[x2,y2],i));
    }
    return points;
}

function getPointsForArc(arc){
    x = arc.x;
    y = arc.y;
    r1 = arc.r1;
    a1 = arc.a1;
    a2 = arc.a2;
    var points = [];
    var divs = getDivisions();
}

function getPointsForEllarc(ellarc){
    x = ellarc.x;
    y = ellarc.y;
    r1 = ellarc.r1;
    r2 = ellarc.r2;
    a1 = ellarc.a1;
    a2 = ellarc.a2;
    var points = [];
    var divs = getDivisions();
}

function getPointsForSquiggle(squiggle){
    x = squiggle.x;
    y = squiggle.y;
    x2 = squiggle.x2;
    y2 = squiggle.y2;
    r1 = squiggle.r1;
    d = squiggle.d;
    var points = [];
    var divs = getDivisions();
}

function getPointsForCircle(circle){
    x = circle.x;
    y = circle.y;
    r1 = circle.r1;
    offset = r1 * 0.55;
    var points = [];
    points.push([x, y + r1]);
    points.push([x + offset, y + r1]);
    points.push([x + r1, y + offset]);
    points.push([x + r1, y]);
    points.push([x + r1, y - offset]);
    points.push([x + offset, y - r1]);
    points.push([x, y - r1]);
    points.push([x - offset, y - r1]);
    points.push([x - r1, y - offset]);
    points.push([x - r1, y]);
    points.push([x - r1, y + offset]);
    points.push([x - offset, y + r1]);
    points.push([x, y + r1]);
    return points;
}

function getPointsForWicket(wicket){
    x = wicket.x;
    y = wicket.y;
    r1 = wicket.r1;
    leg = wicket.leg;
    a1 = wicket.a1;
    var points = [];
    var divs = getDivisions();
}

function getPointsForHook(hook){
    x = hook.x;
    y = hook.y;
    r1 = hook.r1;
    leg = hook.leg;
    a1 = hook.a1;
    d = hook.d;
    var points = [];
    var divs = getDivisions();
}

function getPointsForHalfhook(halfhook){
    x = halfhook.x;
    y = halfhook.y;
    r1 = halfhook.r1;
    leg = halfhook.leg;
    a1 = halfhook.a1;
    d = halfhook.d;
    var points = [];
    var divs = getDivisions();
}

function getPointsForLbend(lbend){
    x = lbend.x;
    y = lbend.y;
    x2 = lbend.x2;
    y2 = lbend.y2;
    d = lbend.d;
    w = Math.abs(x - x2);
    h = Math.abs(y - y2);
    slope = w/h;
    points = [];
    legs = [];
    if (slope <= .5){
	legs = [1, 3];
    } else if (slope >= 2){
	legs = [3, 1];
    } else {
	legs = [2, 2];
    }
    hsteps = legs[0] * 3;
    vsteps = legs[1] * 3;
    if (d != 0){
	for (i = 0; i < hsteps; i++){
	    points.push(ctween([x,y],[x2,y],i/hsteps))
	}
	for (i = 0; i < vsteps; i++){
	    points.push(ctween([x2,y],[x2,y2],i/vsteps))
	}
    } else {
	for (i = 0; i < vsteps; i++){
	    points.push(ctween([x,y],[x,y2],i/vsteps))
	}
	for (i = 0; i < hsteps; i++){
	    points.push(ctween([x,y2],[x2,y2],i/hsteps))
	}
    }
    points.push([x2, y2]);
    return points;
}

function getPointsForFishbend(fishbend){
    x = fishbend.x;
    y = fishbend.y;
    r1 = fishbend.r1;
    leg = fishbend.leg;
    a1 = fishbend.a1;
    d = fishbend.d;
    var points = [];
    var divs = getDivisions();
}

function getPointsForZigzag(zigzag){
    x = zigzag.x;
    y = zigzag.y;
    x2 = zigzag.x2;
    y2 = zigzag.y2;
    d = zigzag.d;
    var points = [];
    var divs = getDivisions();
}

function getPointsForLobe(lobe){
    x = lobe.x;
    y = lobe.y;
    r1 = lobe.r1;
    r2 = lobe.r2;
    a1 = lobe.a1;
    d = lobe.d;
    var points = [];
    var divs = getDivisions();
}

function getPointsForGreatarc(greatarc){
    a1 = greatarc.a1;
    a2 = greatarc.a2;
}

function getPointsForTentacle(tentacle){
    if (tentacle.type == 'line'){
        return getPointsForLine(tentacle);
	/*
    } else if (tentacle.type == 'arc'){
        return getPointsForArc(tentacle);
    } else if (tentacle.type == 'ellarc'){
        return getPointsForEllarc(tentacle);
    } else if (tentacle.type == 'squiggle'){
        return getPointsForSquiggle(tentacle);
        */
    } else if (tentacle.type == 'circle'){
        return getPointsForCircle(tentacle);
        /*
    } else if (tentacle.type == 'wicket'){
        return getPointsForWicket(tentacle);
    } else if (tentacle.type == 'hook'){
        return getPointsForHook(tentacle);
    } else if (tentacle.type == 'halfhook'){
        return getPointsForHalfhook(tentacle);
*/
    } else if (tentacle.type == 'lbend'){
        return getPointsForLbend(tentacle);
/*
    } else if (tentacle.type == 'fishbend'){
        return getPointsForFishbend(tentacle);
    } else if (tentacle.type == 'zigzag'){
        return getPointsForZigzag(tentacle);
    } else if (tentacle.type == 'lobe'){
        return getPointsForLobe(tentacle);
    } else if (tentacle.type == 'greatarc'){
        return getPointsForGreatarc(tentacle);
*/
    } else {
        log(JSON.stringify(tentacle));
        return [];
    }
}
