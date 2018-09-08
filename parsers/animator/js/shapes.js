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


function getPointsForArc(arc){
    x = arc.x;
    y = arc.y;
    r1 = arc.r1;
    a1 = arc.a1;
    a2 = arc.a2;
}

function getPointsForEllarc(ellarc){
    x = ellarc.x;
    y = ellarc.y;
    r1 = ellarc.r1;
    r2 = ellarc.r2;
    a1 = ellarc.a1;
    a2 = ellarc.a2;
}

function getPointsForSquiggle(squiggle){
    x = squiggle.x;
    y = squiggle.y;
    x2 = squiggle.x2;
    y2 = squiggle.y2;
    r1 = squiggle.r1;
    d = squiggle.d;
}

function getPointsForCircle(circle){
    x = circle.x;
    y = circle.y;
    r1 = circle.r1;
}

function getPointsForWicket(wicket){
    x = wicket.x;
    y = wicket.y;
    r1 = wicket.r1;
    leg = wicket.leg;
    a1 = wicket.a1;
}

function getPointsForHook(hook){
    x = hook.x;
    y = hook.y;
    r1 = hook.r1;
    leg = hook.leg;
    a1 = hook.a1;
    d = hook.d;
}

function getPointsForHalfhook(halfhook){
    x = halfhook.x;
    y = halfhook.y;
    r1 = halfhook.r1;
    leg = halfhook.leg;
    a1 = halfhook.a1;
    d = halfhook.d;
}

function getPointsForLbend(lbend){
    x = lbend.x;
    y = lbend.y;
    x2 = lbend.x2;
    y2 = lbend.y2;
    d = lbend.d;
}

function getPointsForFishbend(fishbend){
    x = fishbend.x;
    y = fishbend.y;
    r1 = fishbend.r1;
    leg = fishbend.leg;
    a1 = fishbend.a1;
    d = fishbend.d;
}

function getPointsForZigzag(zigzag){
    x = zigzag.x;
    y = zigzag.y;
    x2 = zigzag.x2;
    y2 = zigzag.y2;
    d = zigzag.d;
}

function getPointsForLobe(lobe){
    x = lobe.x;
    y = lobe.y;
    r1 = lobe.r1;
    r2 = lobe.r2;
    a1 = lobe.a1;
    d = lobe.d;
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
    } else if (tentacle.type == 'circle'){
        return getPointsForCircle(tentacle);
    } else if (tentacle.type == 'wicket'){
        return getPointsForWicket(tentacle);
    } else if (tentacle.type == 'hook'){
        return getPointsForHook(tentacle);
    } else if (tentacle.type == 'halfhook'){
        return getPointsForHalfhook(tentacle);
    } else if (tentacle.type == 'lbend'){
        return getPointsForLbend(tentacle);
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
