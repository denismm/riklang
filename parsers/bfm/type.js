// this file created by update_type - edit maintype
var morphemes_for_click = [ 'Home', 'Leader', 'Number', 'Sequence', 'Word', 'Child', 'Contributor', 'Sun', 'Think', 'Displacement', 'Emotion', 'Parent', 'Big', 'Fight', 'Glass', 'Span', 'Spot', 'Hail', 'Small', 'Choice', 'Fruit', 'Sad', 'Share', 'Tree', 'Want', 'After', 'Toddler', 'Friend', 'Sibling', 'Rock', 'Roller', 'Stroke', 'Similar', 'Gear', 'Change', 'Exchange', 'Box', 'Chaos', 'Dead', 'God', 'Human', 'Me', 'Pain', 'Rikchik', 'Talk', 'Learn', 'Paint', 'Happy', 'Mouth', 'Night', 'Chimp', 'Crawler', 'Give', 'Short', 'Sky', 'Sleep', 'Fire', 'Repulsion', 'Dragon', 'Disgust', 'Machine', 'Surprise', 'Eye', 'Angry', 'Art', 'Move', 'Hat', 'Magic', 'Crab', 'Beast', 'Sound', 'Pulser', 'Ninja', 'Have', 'Love', 'Around', 'Globe', 'Faerie', 'Fear', 'All', 'Ground', 'Junction', 'Seven', 'Metal', 'Three', 'Shield', 'Tentacle', 'Gather', 'Bouncer', 'Book', 'Crystal', 'Above', 'Six', 'Water', 'Five', 'Across', 'Thing', 'Insect', 'One', 'What', 'Zero', 'Door', 'Four', 'Two', 'Fish', 'Work', 'Play', 'Shoe', 'Rail', 'Grass', 'Cloth', 'Wind', 'Front', 'Circle', 'Long', 'Beach', 'Flower']

var cgidir = 'http://suberic.net/~dmm/cgi-bin/';
var imagedir = 'http://www.suberic.net/~dmm/rikchik/images/';

var morphemes = new Array;

for (var i = 0; i < morphemes_for_click.length; i++) {
    morphemes[i] = morphemes_for_click[i];
}

morphemes.sort();

var aspects = ['T','R','P','I', 'V','M','N'];

var relations = new Array();
relations['a'] = 'Agent';
relations['s'] = 'Source';
relations['t'] = 'Instrument';
relations['q'] = 'Quality';
relations['d'] = 'Destination';
relations['p'] = 'Patient';
relations['i'] = 'Includes';

relations['A'] = 'Agentrec';
relations['S'] = 'Sourcerec';
relations['T'] = 'Instrumentrec';
relations['Q'] = 'Qualityrec';
relations['D'] = 'Destinationrec';
relations['P'] = 'Patientrec';
relations['I'] = 'Includesrec';

relations['e'] = 'End';

var relations_for_click = 
    ['Agent', 'Source', 'Instrument', 'Quality', 'Destination', 'Patient', 'Includes', 'End',
     'Agentrec', 'Sourcerec', 'Instrumentrec', 'Qualityrec', 'Destinationrec', 'Patientrec', 'Includesrec', 'End'];

var collectors = ['0','1','2','3','4','5','6','7','S'];

var lowerMorphs = new Array();

//page elements:
var check_elements = ['overwrite', 'structure'];
var select_elements = 
    [ 'writtensize', 'writtenstyle', 'writtenlineheight',
      'parsedsize', 'parsedstyle'];
var words; 
var wordlist;
var completion;
var text;
var structure;
var overwrite;
var statelink;
var fulltext;

function bid(id) {
    return document.getElementById(id);
}

function init() {
    browser_detect();

    words = bid('words');
    wordlist = words.options;
    completion = bid('completion');
    text = bid('text')
    structure = bid('structure');
    overwrite = bid('overwrite');
    statelink = bid('statelink');
    fulltext = bid('fulltext');

    //set up key handling
    text.value = "";
    text.onkeyup = handle_keyup;
    if (navigator.appName.indexOf("Explorer") != -1)
      text.onkeydown = handle_keydown;
    else
      text.onkeypress = handle_keydown;

    //create array for Morpheme completion
    for (i = 0; i < morphemes.length; i++){
	lowerMorphs.push(morphemes[i].toLowerCase());
    }
    
    //populate image-based text entry area.
    populate_clicks();

    if (location.search.length > 0){
	populate_form(location.search)
    }
}

function populate_clicks(){
    var innerHTML;
    //morphemes
    innerHTML = '';
    for (i = 0; i < morphemes_for_click.length; i++){
	var morpheme = morphemes_for_click[i];
	innerHTML += click_image ('m', morpheme, 1);
	if (i % 17 == 16){
	    innerHTML += "<br/>";
	}
    }
    bid("clickMorphemes").innerHTML = innerHTML;

    //aspects
    innerHTML = '';
    for (i = 0; i < aspects.length; i++){
	var aspect = aspects[i];
	innerHTML += click_image('a', aspect, 2);
	innerHTML += "<br/>";
    }
    bid("clickAspects").innerHTML = innerHTML;

    //relations
    innerHTML = '';
    for (i = 0; i < relations_for_click.length; i++){
	var relation = relations_for_click[i];
	innerHTML += click_image('r', relation, 2);
	if (i == 7)
	    innerHTML += "<br/>";
    }
    bid("clickRelations").innerHTML = innerHTML;

    //collectors
    innerHTML = '';
    for (i = 0; i < collectors.length; i++){
	var collector = collectors[i];
	innerHTML += click_image('c', collector, 2);
	innerHTML += "<br/>";
    }
    bid("clickCollectors").innerHTML = innerHTML;

    innerHTML = '';
    for (i = 0; i < collectors.length; i++){
	var collector = collectors[i];
	innerHTML += click_image_with_val('c', collector + 'P', collector, 2);
	innerHTML += "<br/>";
    }
    innerHTML += '<img src="clear.gif" height="7"><br/>';
    
    bid("clickCollectorsP").innerHTML = innerHTML;

    innerHTML = '';
    for (i = 0; i < collectors.length; i++){
	var collector = collectors[i];
	innerHTML += click_image_with_val('c', collector + 'O', collector, 2);
	innerHTML += "<br/>";
    }
    innerHTML += '<img src="clear.gif" height="14"><br/>';
    
    bid("clickCollectorsO").innerHTML = innerHTML;


}


function click_image (initial, value, size){
    return click_image_with_val(initial, value, value, size);
}
function click_image_with_val(initial, value, image, size){
    return '<a href="#" border="0" onMouseOver="set_complete(\'' + value + '\');" onClick="return click_' + initial +'(\''+value+'\');"><img alt="' + value + '" class="click_image" src="' + imagedir + 'classic/' + size + '/' + initial + image + '.png"/></a>';
}

function populate_form(search){
    search = search.substring(1);
    var terms = search.split('&');
    var values = new Array();
    for (i = 0; i < terms.length; i++) {
	var pair = terms[i].split('=');
	values[pair[0]] = pair[1];
    }
    if (values['current'] != null){
	var s_words = values['current'].split('.');
	for (var j = 0; j < s_words.length; j++){
	    add_word(s_words[j]);
	}
    }
    for (var i = 0; i < check_elements.length; i++){
	var check_element = check_elements[i];
	if (values[check_element] = 'true'){
	    bid(check_element).checked = true;
	}
    }
    for (var i = 0; i < select_elements.length; i++){
	var select_element = select_elements[i];
	if (values[select_element] != null){
	    init_select(select_element, values[select_element]);
	}
    }
    update();
}

function handle_keyup() {
    complete_item();
}

function handle_keydown(e) {
    if (!e) var e = window.event;
    switch (e.keyCode == 0 ? e.which : e.keyCode) {
    case 8:
	return (handle_delete());
	break;
    case 9:
    case 45:
	return (accept_complete());
	break;
    case 32:
	return (enter_word());
	break;
    case 13:
	return (enter_word());
	break;
    case 46:
	return (enter_word());
	break;
    }
}

function complete_item(){
    var word = text.value;
    var pos = cursorPosition(text);
    var prev_str = word.substring(0, pos );
    var parts = prev_str.split('-');
    if (text != '' && parts[parts.length - 1] != '') {
	switch(parts.length){
	case 1:
	    var s = parts[0];
	    for (var i = lowerMorphs.length - 1; i >= 0; i--){
		if (lowerMorphs[i].indexOf(s.toLowerCase()) == 0){
		    //completion match
		    set_complete(morphemes[i]);
		}
	    }
	    break;
	case 2:
	    set_complete(parts[1].charAt(0).toUpperCase());
	    break;
	case 3:
	    set_complete(relations[parts[2].charAt(0)]);
	    break;
	case 4:
	    set_complete(parts[3].toUpperCase());
	    break;
	}
    }
}

function set_complete(value){
    completion.value = value;
}

function accept_complete(){
    var word = text.value;
    var pos = cursorPosition(text);
    var prev_str = word.substring(0, pos );  //string to the left
    var parts = prev_str.split('-');
    var after_str = word.substring(pos);
    if (after_str.length > 0 && after_str.charAt(0) == '-'){
	after_str = after_str.substring(1);
    }
    parts.splice(parts.length - 1,1,completion.value);
    completion.value = '';
    if (parts.length < 4){
	var completed = parts.join('-') + '-';
	text.value = completed + after_str; 
	setSelectionRange(text, completed.length, completed.length);
	return false;
    } else {
	text.value = parts.join('-');
	return(enter_word());
    }
    
}

function handle_delete() {
    var word = text.value;
    var pos = cursorPosition(text);
    var prev_str = word.substring(0, pos );  //string to the left
    var parts = prev_str.split('-');
    if (parts.length == 3 && parts[2] != ''){
	var after_str = word.substring(pos);
	parts[2] = '';
	completion.value = '';
	var completed = parts.join('-');
	text.value = completed + after_str; 
	setSelectionRange(text, completed.length, completed.length);
	return false;	
    }
    
}

function select_none() {
    words.selectedIndex = -1;
}

function delete_current_word() {
    if (words.selectedIndex >= 0){
	delete_word(words.selectedIndex);
    }
}

function delete_word(index) {
    var old_collector = get_cnum(index);
    for (var i = index; i < wordlist.length - 1; i++){
	var word = wordlist[i+1].value;
	wordlist[i] = new Option(word, word);
    }
    wordlist.length--;
    if (structure.checked){
	delete_from_structure(index, old_collector);
    }
    words.selectedIndex = index;
    update();
}

function replace_word(word, index) {
    wordlist[index] = new Option(word, word);
}

function insert_word(word, index) {
    for (var i = wordlist.length - 1; i >= index; i--){
	var t_word = wordlist[i].value;
	wordlist[i+1] = new Option(t_word, t_word)
    }
    wordlist[index] = new Option(word, word);
    if (structure.checked){
	add_to_structure(index);
    }
    words.selectedIndex = index;
}

function add_word(word) {
    wordlist[wordlist.length] = new Option(word, word);
    words.selectedIndex = - 1;
}

function delete_from_structure(old_index, old_collector){
    //alert (old_index);
    if(old_index >= wordlist.length){
	return 0;
    }
    var needed = 1;
    for (var index = old_index; needed > get_cnum(index); index++ ){
	//alert(wordlist[index].value);
	needed += 1 - get_cnum(index);
	if (get_relation(index) == 'End'){
	    needed--;
	}
	if (index == wordlist.length - 1){
	    return 0;
	}
    }
    //alert ('setting ' + wordlist[index].value);
    set_cnum(index, get_cnum(index) - (1 - old_collector));
}

function add_to_structure(new_index){
    if(new_index < wordlist.length - 1){
    set_cnum(new_index + 1, get_cnum(new_index + 1) + 1);
    }
}

function get_cnum(index){
    return parse_collector_number(wordlist[index].value);
}

function parse_collector_number(word){
    return parseInt(word.split('-')[3]);
}

function get_relation(index){
    return parse_relation(wordlist[index].value);
}

function parse_relation(word){
    return word.split('-')[2];
}

function set_cnum(index, value){
    word = wordlist[index].value;
    var parts = word.split('-');
    var coll = parts[3];
    var group = coll.match(/([0-7])([op]?)/i); // add s
    var num = group[1];
    var pronom = group[2];
    parts[3] = value + "" + pronom;
    replace_word(parts.join('-'), index);
}

function click_m (value){
    return set_part(0, value);
}
function click_a (value){
    return set_part(1, value);
}
function click_r (value){
    return set_part(2, value);
}
function click_c (value){
    return set_part(3, value);
}

function set_part(part, value){
    var parts = text.value.split('-');
    parts[part] = value;
    text.value = parts.join('-');
    return false;
}

function enter_word() {
    var word = text.value;
    if (words.selectedIndex < 0){
	add_word (word);
    } else {
	if (overwrite.checked){
	    replace_word (word, words.selectedIndex);
	} else {
	    insert_word (word, words.selectedIndex);
	}
    }
    text.value = '';
    return update();
}

function set_full_text(){
    var text_words = fulltext.value.split(/\s+/);
    wordlist.length = 0;
    for (var i = 0; i < text_words.length; i++){
	text_word = text_words[i];
	if (text_word.indexOf('-') >= 0){
	    add_word(text_word);
	}
    }
    update();
}

function select_word(){
    if (overwrite.checked){
	text.value = wordlist[words.selectedIndex].value;
    }
}

function update() {
    var utter = wordlist[0].value;
    for (i = 1; i < wordlist.length; i++){
	utter = utter + '.' +wordlist[i].value;
    }
    var qString = utter;
    bid('parsed').src = cgidir + 'rikparse.cgi?' + style_values('parsed') + qString;
    bid('written').src = cgidir + 'rikchik.cgi?' + style_values('written') + qString;
    fulltext.value = utter.replace(/\./g,' ');
    statelink.href = 'typewriter2.html?current=' + utter + querystring();
    bid("dcdc").value = 
	'<a href="' + cgidir + 'rikparse.cgi?page=1&' + style_values('written') + qString + '">d</a> ' +
	'<a href="' + cgidir + 'rikchik.cgi?' + style_values('written') + 'caption=1&' + qString + '">c</a> ' +
	'<a href="' + cgidir + 'rikparse.cgi?' + style_values('written') + 'caption=1&' + qString + '">dc</a> ';
	
    return false;
}

function querystring(){
    var retval = '';
    for (var i = 0; i < check_elements.length; i++){
	var check_element = check_elements[i];
	if (bid(check_element).checked = true){
	    retval += '&' + check_element + '=true';
	}
    }
    for (var i = 0; i < select_elements.length; i++){
	var select_element = select_elements[i];
	var elem = bid(select_element);
	retval += '&' + select_element +
	    '=' + elem.options[elem.selectedIndex].value;
    }
    return retval;
}

function style_values(output){
    var retval = '';
    var options = ['size', 'style', 'lineheight'];
    for (var i = 0; i < options.length; i++){
	var opt = options[i];
	var sel = bid(output+opt);
	if (sel != null){
	    retval += opt + '=' + sel.options[sel.selectedIndex].value + '&';
	}
    }
    return retval;
}

function init_select(id,value){
    var select = bid(id);
    for (var i = 0; i < select.options.length; i++){
	if (select.options[i].value == value){
	    select.selectedIndex = i;
	    break;
	}
    }
}

