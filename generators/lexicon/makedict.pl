#!/usr/bin/perl
use strict;
use autodie;
my $RIKLANG = $ENV{'RIKLANG'} || $ENV{'RIKCHIK_SRC'};
my $RIKHOME = $ENV{'RIKCHIK_HOME'};
die unless $RIKLANG;
$ENV{'CLASSPATH'} = ".:$RIKLANG/lib/saxon-9.1.jar";
chdir "$RIKHOME/language";
system "/usr/bin/java net.sf.saxon.Transform $RIKLANG/data/riklang.xml $RIKLANG/generators/lexicon/riklang.xsl";
