This directory contains data about the Rikchik language.

# riklang.xml

The riklang.xml file contains what is needed to create the main
matter of a Rikchik dictionary, including glyph shapes. 
This file is what should be parsed to make other Rikchik reference content
including graphics.
To make it easier to deal with, there are a number of convenience scripts:

- expandxml.pl - this creates a "language" directory, not to be committed, that breaks the giant riklang.xml file into a number of smaller files.
- contractxml.pl - this turns those files back into the riklang.xml file.
- morph - this creates or finds the reading file for a given morpheme-aspect pair, and then calls the system editor on that file. It takes a Morpheme-Aspect pair, like "Home-R".
- compound - this creates or finds the compound file for a given morpheme-aspect pair, and then calls the system editor on that file. It takes a Morpheme:Gloss string, like "Home:Town".

# corpus

The corpus directory contains YAML files of known utterances in Rikchik.  These are organized by year directories and then date files within those directories.
