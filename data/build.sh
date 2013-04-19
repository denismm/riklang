#!/bin/tcsh
./contractxml.pl >! riklang.xml && ../generators/lexicon/makedict.pl
