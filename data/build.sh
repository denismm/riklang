#!/bin/bash
./contractxml.pl >| riklang.xml && ../generators/lexicon/makedict.pl
echo "lexicon ok"

cd ../generators/script/
./riklang2ps.pl
echo "script ps ok"
./rikref.pl
echo "script pdf ok"

cd ../json
./makejson.pl
if [ -f "$RIKCHIK_HOME/animator/tentacles.js" ]; then
    cp ../../parsers/animator/js/tentacles.js $RIKCHIK_HOME/animator/tentacles.js 
fi
echo "animator ok"
