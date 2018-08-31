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

//more getPointsFor

function getPointsForTentacle(tentacle){
    if (tentacle.type == 'line'){
        return getPointsForLine(tentacle);
    } else {
        log(JSON.stringify(tentacle));
        return [];
    }
}
