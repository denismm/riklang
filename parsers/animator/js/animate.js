function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function getPointsForLine(line){
    x = line.x;
    y = line.y;
    x2 = line.x2;
    y2 = line.y2;
    var points = [];
    dx = (x2 - x) / 9;
    dy = (y2 - y) / 9;
    for (i = 0; i <= 9; i++){
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

function log(message){
    var logArea = document.getElementById('log');
    var value = logArea.value;
    logArea.value = value + "\n" + message ;
}

function asString(object){
    log(JSON.stringify(object));
}
