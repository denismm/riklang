function getPointsForLine(line){
    x = line.x;
    y = line.y;
    x2 = line.x2;
    y2 = line.y2;
    var points = [];
    var divs = getDivisions();
    addPointsForLineSegment(points, [x, y], [x2, y2], divs * 3);
    points.push([x2, y2]);
    return points;
}

/*
  Modifies points by adding the points from the beginning toward the end of
  the segment from x,y to x2, y2.
*/
function addPointsForLineSegment(points, p, p2, steps){
    for (i = 0; i < steps; i++){
        points.push(ctween(p, p2,i/steps));
    }
}

function rotPoints(points, center, angle){
    var a = angle * (Math.PI / 180);
    var sn = Math.sin(a);
    var cs = Math.cos(a);
    var newPoints = [];
    for (i = 0; i < points.length; i++){
	p = points[i];
	dx = p[0] - center[0];
	dy = p[1] - center[1];
	x = dx * cs - dy * sn;
	y = dx * sn + dy * cs;
	newPoints.push ([x + center[0], y + center[1]]);
    }
    return newPoints;
}

function getPointsForArc(arc){
    arc.r2 = arc.r1;
    return getPointsForEllarc(arc);
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
    center = [x,y];
    offset = r1 * 0.55;
    addPointsForLineSegment(points, [x + leg, y - r1], [x, y - r1], 3);
    points.push([x, y - r1]);
    points.push([x - offset, y - r1]);
    points.push([x - r1, y - offset]);
    points.push([x - r1, y]);
    points.push([x - r1, y + offset]);
    points.push([x - offset, y + r1]);
    addPointsForLineSegment(points, [x, y + r1], [x + leg, y + r1], 3);
    points.push([x + leg, y + r1]);
    var newPoints = rotPoints(points, [x,y], a1);
    return newPoints;
}

function getPointsForHook(hook){
    x = hook.x;
    y = hook.y;
    r1 = hook.r1;
    leg = hook.leg;
    a1 = hook.a1;
    d = hook.d;
    var points = [];
    center = [x,y];
    offset = r1 * 0.55;
    if (d == 1){
	points.push([x, y + r1]);
	points.push([x - offset, y + r1]);
	points.push([x - r1, y + offset]);
	points.push([x - r1, y]);
	points.push([x - r1, y - offset]);
	points.push([x - offset, y - r1]);
	addPointsForLineSegment(points, [x, y - r1], [x + leg, y - r1], 6);
	points.push([x + leg, y - r1]);
    } else {
	points.push([x, y - r1]);
	points.push([x - offset, y - r1]);
	points.push([x - r1, y - offset]);
	points.push([x - r1, y]);
	points.push([x - r1, y + offset]);
	points.push([x - offset, y + r1]);
	addPointsForLineSegment(points, [x, y + r1], [x + leg, y + r1], 6);
	points.push([x + leg, y + r1]);
    }
    var newPoints = rotPoints(points, [x,y], a1);
    return newPoints;
}

function getPointsForHalfhook(halfhook){
    x = halfhook.x;
    y = halfhook.y;
    r1 = halfhook.r1;
    leg = halfhook.leg;
    a1 = halfhook.a1;
    d = halfhook.d;
    var points = [];
    center = [x,y];
    offset = r1 * 0.55;
    if (d == 1){
	points.push([x - r1, y]);
	points.push([x - r1, y - offset]);
	points.push([x - offset, y - r1]);
	addPointsForLineSegment(points, [x, y - r1], [x + leg, y - r1], 9);
	points.push([x + leg, y - r1]);
    } else {
	points.push([x - r1, y]);
	points.push([x - r1, y + offset]);
	points.push([x - offset, y + r1]);
	addPointsForLineSegment(points, [x, y + r1], [x + leg, y + r1], 9);
	points.push([x + leg, y + r1]);
    }
    var newPoints = rotPoints(points, [x,y], a1);
    return newPoints;
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
	addPointsForLineSegment(points, [x, y], [x2, y], hsteps);
	addPointsForLineSegment(points, [x2, y], [x2, y2], vsteps);
    } else {
	addPointsForLineSegment(points, [x, y], [x, y2], vsteps);
	addPointsForLineSegment(points, [x, y2], [x2, y2], hsteps);
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
    greatarc.x = 50;
    greatarc.y = -50;
    greatarc.r1 = 30;
    return getPointsForArc(greatarc);
}

function getPointsForTentacle(tentacle){
    var points;
    if (tentacle.type == 'line'){
        points = getPointsForLine(tentacle);
	/*
    } else if (tentacle.type == 'arc'){
        points = getPointsForArc(tentacle);
    } else if (tentacle.type == 'ellarc'){
        points = getPointsForEllarc(tentacle);
    } else if (tentacle.type == 'squiggle'){
        points = getPointsForSquiggle(tentacle);
        */
    } else if (tentacle.type == 'circle'){
        points = getPointsForCircle(tentacle);
    } else if (tentacle.type == 'wicket'){
        points = getPointsForWicket(tentacle);
    } else if (tentacle.type == 'hook'){
        points = getPointsForHook(tentacle);
    } else if (tentacle.type == 'halfhook'){
        points = getPointsForHalfhook(tentacle);
    } else if (tentacle.type == 'lbend'){
        points = getPointsForLbend(tentacle);
/*
    } else if (tentacle.type == 'fishbend'){
        points = getPointsForFishbend(tentacle);
    } else if (tentacle.type == 'zigzag'){
        points = getPointsForZigzag(tentacle);
    } else if (tentacle.type == 'lobe'){
        points = getPointsForLobe(tentacle);
    } else if (tentacle.type == 'greatarc'){
        points = getPointsForGreatarc(tentacle);
*/
    } else {
        log(JSON.stringify(tentacle));
        points = [];
    }
    if (tentacle.r1 == 1){
	points.reverse();
    }
    return points;

}
