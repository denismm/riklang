#!/usr/bin/perl
use strict;
my $RIKLANG = $ENV{'RIKLANG'} || $ENV{'RIKCHIK_SRC'};
my $RIKHOME = $ENV{'RIKCHIK_HOME'};
die unless $RIKLANG;
$ENV{'CLASSPATH'} = ".:$RIKLANG/lib/saxon-9.1.jar";
chdir "$RIKHOME/language";
my $transform = "/usr/bin/java net.sf.saxon.Transform";
my $xsldir = "$RIKLANG/generators/json";
my $jsondir = "$RIKLANG/parsers/animator/js";
system "$transform $RIKLANG/data/riklang.xml $xsldir/draw_only.xsl > $xsldir/drawonly.xml";
my $generate = "$transform  $xsldir/drawonly.xml $xsldir/riklang_json.xsl";
system "echo 'function getLang() {' > $jsondir/tentacles.js";
system "echo 'return (' >> $jsondir/tentacles.js";
system "$generate >> $jsondir/tentacles.js";
system "echo '); }' >> $jsondir/tentacles.js";
#system "$generate | json_pp";
system "wc -l $jsondir/tentacles.js";
