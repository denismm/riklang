/***************************************************************************/
/*                                                                         */
/*  edit.js                                                                */
/*                                                                         */
/*  Uyghur Unicode Input System -- edit file.                              */
/*                                                                         */
/*  Copyleft  2003-2004 by Muhammad Abdulla (muhammad@yulghun.com)         */
/*  Edited to serve as a library for a Rikchik input system 2005           */
/*    by Marc Moskowitz: still Copyleft                                    */
/*                                                                         */
/*  Anyone is free to use and distribute this piece of software, provided  */
/*  that this copyright notice is retained. The author is not responsible  */
/*  for any damage caused by the usage of this software.                   */
/*                                                                         */
/*  Uyghurcha:                                                             */
/*  Bu programmini harkim mushu nashir hoquqi sozini saqlap qalghan halda  */
/*  ishlatsa wa tarqatsa bolidu. Bu programmini tuzguchi ishlitishtin      */
/*  kilip chiqqan ziyangha masul bolmaydu.                                 */
/*  Pikirliringiz bolsa muhammad@yulghun.com digan addrisqa hat awating.   */
/*  		  			     	   	    		   */
/*  Rikchik:                                                               */
//  All-R-Agent-0 Book-V-Patientrec-0 Word-I-Instrument-0 
//  Machine-T-Patient-2 Give-V-Patient-1 Machine-T-Instrument-0P 
//  Work-V-Patient-1 Choice-V-Patient-2 Have-V-End-2 Zero-M-Quality-0 
//  Book-V-Patientrec-0 Word-I-Patient-1 Change-V-Patient-1 Have-V-End-2P
//  Machine-T-Patient-0P Fruit-V-Agentrec-1 Human-R-Agent-1 Zero-M-Quality-0 
//  Machine-T-Instrument-0P Pain-V-End-3
/***************************************************************************/

var inited = 0 ;
//var editable = true ;

var isIE = false ;
var isGecko = false ;
var isMaxthon = false ;

function storeCaret ( content ) { // text area content
   if ( content.createTextRange ) {
      content.caretPos = document.selection.createRange().duplicate() ;
   }

   return true ;
};

function findSpecialString ( input )
{
   // creates a string that does not appear in input, using some cursor like chars so as not to disturb
   // users much on slow computers. This is used to locate cursor position correctly. 
   var scarr = "|Il!" ;

   var esp = scarr.substr ( 0, 1 ) ;
   var i ;

   while ( input.indexOf ( esp ) != -1 ) {
      i = Math.floor ( Math.random() * scarr.length ) ;
      esp = esp + String.fromCharCode ( scarr.charCodeAt(i) ) ;
   }

   return esp ;
};

function insertAtContext ( content, charcode ) { // text area content
   var tmp = "" ;
   var prev_str = "", next_str = "" ;
   var prev = -1, cur = -1, next =-1 ; // previous, current, next character codes
   var repl = "" ; //replacement string
   var line_no = 0 ; // which line we are at
   var pos ;
   var charcount = 1 ;
   var esp = "" ;
   var cursor_position = 0 ;

   if ( (isIE && content.createTextRange && content.caretPos) ||
      (isGecko && ( content.setSelectionRange || document.selection )) ) {
      // clear selected text
      if ( isIE ) {
        document.selection.createRange().text = "" ;
      } else if ( isGecko ) {
         content.value = content.value.substring (0, content.selectionStart ) +
                         content.value.substr ( content.selectionEnd ) ;
      }

      // backup the current text content
      tmp = content.value ;
      cursor_position = cursorPosition(content);
      prev_str = tmp.substring(0, cursor_position - 1 );  //string to the left
      next_str = tmp.substr( cursor_position + 1 ); //string to the right

      line_no = get_lineno ( prev_str ) ;


      if ( cursor_position > 0 ) {
         prev = tmp.charCodeAt ( cursor_position - 1 ) ;
         charcount++ ;
      }

      if ( cursor_position < content.value.length - esp.length ) {
         next = content.value.charCodeAt ( cursor_position + esp.length ) ;
         charcount++ ;
      }

      cur = charcode ;

      repl = getContextString ( prev, cur, next ) ;

      tmp = prev_str + repl + next_str ;
      content.value = tmp ;

      pos = cursor_position + 1 - line_no ; // compensate for "\r\n"
      pos = pos + repl.length - charcount ; // compensate for special joint letters: "LA", etc. 
      setSelectionRange ( content, pos, pos ) ;
   }
};

function cursorPosition(content){ 
    //changes content.value in IE
    var cursor_position;
      if ( isIE ) {
         // IE does not give cursor position.
         // Insert a special character array and find its index to obtain cursor position
         var esp = findSpecialString ( tmp )  ;
         content.caretPos.text = esp ;
         cursor_position = content.value.indexOf ( esp );
      } else { // Mozilla gives range variables
         cursor_position = content.selectionStart ;
      }
      return cursor_position;
}

function setSelectionRange(input, selectionStart, selectionEnd) {
  if (input.setSelectionRange) {
    input.focus();
    input.setSelectionRange(selectionStart, selectionEnd);
  } else if (input.createTextRange) {
    var range = input.createTextRange();
    range.collapse(true);
    range.moveEnd('character', selectionEnd);
    range.moveStart('character', selectionStart);
    range.select();
  }
};

// returns the number of newlines in a string
function get_lineno ( str )
{
      var nl = "\r" ;
      var index ;
      var nolines = 0 ;

      index = str.indexOf ( nl ) ;

      while ( index != -1 ) {
         nolines++ ;
         index = str.indexOf ( nl, index + 1 ); //start search after last match found
      }

      return nolines ;
};

/*
 * setHexText(str) -- returns HTML comaptible ASCII representation of str in &#ddddd; form 
 */
function setHexText ( str ) {
   var tmp = "" ;
   var cstr = "" ;
   var i, code ;

   for ( i = 0 ; i < str.length ; i++ ) {
      code = str.charCodeAt(i) ;
      cstr = String.fromCharCode(code) ;

      if ( code < 128 ) {
         tmp += cstr ;
      } else {
         tmp += "&#" + code + ";" ;
      }
   }

   return tmp ;
};


/*
 * validate() -- This was originally used to check for invalid email addresses.
 *  It's kept to support email sending application by changing mail body and
 *   mail subject in Uyghur from UTF-8 to ASCII form ( &#dddd; ).
 */
function validate ( )
{
   var t  = document.forms.textform.mail ;
   var us = document.forms.textform.usubject ;

   var to = document.textform.to.value ;
   var from = document.textform.from.value ;

   document.textform.hex.value  = setHexText ( t.value ) ;
   document.textform.usubhex.value = setHexText ( us.value ) ;
};

function insertAtCaret ( content, text ) { // text area content, text to be inserted

   if ( content.createTextRange && content.caretPos ) {
      var cpos = content.caretPos ;
      cpos.text = text ;
   } else {
      content.value = content.value + text ;
   }
};

function proc_kp ( content, event ) {
   var ev = event ;
   var t = content ;

   var ch, i, k, ch2 ;

   var itoggle = false ; // input mode toggle

   /*
   if ( editable == false ) {
      ev.returnValue = false ;
      return false ;
   }
   */

   // if Contrl-k is pressed, toggle between input modes
   if ( ev.ctrlKey ) {
      if ( isIE ) {
         k = ev.keyCode ;

         if ( isMaxthon && k == 2 ) { // for Maxthon browser: Ctrl-B toggles imode
           itoggle = true ;
         }

         // when "k" is pressed with "Control", the keyCode in IE is equal to 11, strange.
         if ( k == 11 ) {
            itoggle = true ;
         }
      } else if ( isGecko ) { // ev.which is equal to ascii code of "k" in Mozilla.
         k = ev.which ;
         if ( k == 107 ) {
            itoggle = true ;
         } else {
            return true ;
         }
      }

      if ( itoggle ) {
         imode = 1 - imode ; // toggle input mode
         ev.returnValue = false ;
         return false;
      }
   }

   if ( imode == 1 ) { // English
      ev.returnValue = true ;
      return true ;
   }

   if ( isIE ) {
      // for text field, allow default behaviour for "Enter"
      if ( ev.keyCode == 13 && t.type == "text" ) {
         return true ;
      } else {
         insertAtContext ( t, ev.keyCode ) ;
      }
   } else if ( isGecko ) {
      var kcode = ev.which ;
      // for text field, allow default behaviour for "Enter"
      if ( kcode == 13 &&  instanceOf ( t, HTMLTextAreaElement ) ) {
         insertAtContext ( t, kcode ) ;
      } else if ( kcode < 32 || kcode == 46 ) {
         return true ;
      } else {
         insertAtContext ( t, kcode ) ;
      }
   }

   ev.returnValue = false ;
   return false ;
};

function instanceOf(object, class_name ) {
   if ( isIE ) {
      return ( eval ( "object instanceof class_name" ) ) ;
   } else if ( isGecko ) {
      while (object != null) {
         if (object == class_name .prototype) {
            return true
         }
         object = object.__proto__;
      }
   }

   return false;
};

function proc_kd ( content ) {
   var t = content ;

   storeCaret( t ) ;
};

function proc_ku ( content, event ) {
   var t = content ;
   var kcode ;

   storeCaret( t ) ;

   kcode = event.keyCode ;

   if ( kcode == 8 ) {  // backspace key
      insertAtContext ( t, -1 ) ;
   } else if ( kcode == 46 ) { // delete key
      insertAtContext ( t, -1 ) ;
   }

   //setPreviewText ( t.value ) ;
};

function selectLang ( langID )
{
   var idx = document.getElementById(langID).selectedIndex ;
   var selected = document.getElementById(langID).options[idx].value;

   if ( selected == "English" ) {
      imode = 1 ;
   } else {
      imode = 0 ;
   }
};

function setFocus ( content ) {
   if ( inited == 0 ) {
      var flag = 1 ;
      var i, code ;
      var cstr ;

      inited = 1 ;

      t = content ;

      // if all of the text is whitespace, set to blank
      for ( i = 0 ; i < t.value.length ; i++ ) {
         code = t.value.charCodeAt(i) ;
         cstr = String.fromCharCode ( code ) ;
         if ( cstr != " " && cstr != "\t" ) {
            flag = 0 ;
            break ;
         }
      }

      if ( flag == 1 ) {
         t.value = "" ;
      }
   }
};

function browser_detect () {
   // detect browser
   var ua = navigator.userAgent.toLowerCase();

   isIE = ((ua.indexOf("msie") != -1) && (ua.indexOf("opera") == -1) && (ua.indexOf("webtv") == -1)) ;
   isGecko = (ua.indexOf("gecko") != -1 && ua.indexOf("safari") == -1) ;
   isMaxthon = ((ua.indexOf("msie") != -1) && (ua.indexOf("maxthon") != -1)) ;
};
