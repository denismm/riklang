#!/usr/bin/perl
use strict;
my $RIKLANG = $ENV{'RIKLANG'} || $ENV{'RIKCHIK_SRC'} || $ENV{'RIKCHIK_HOME'}; 
die unless $RIKLANG;
$ENV{'CLASSPATH'} = ".:$RIKLANG/lib/saxon-9.1.jar";
chdir "$RIKLANG/language";
system "/usr/bin/java net.sf.saxon.Transform $RIKLANG/data/riklang.xml $RIKLANG/generators/lexicon/riklang.xsl";
