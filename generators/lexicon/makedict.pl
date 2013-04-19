#!/usr/bin/perl
use strict;
$ENV{'CLASSPATH'} = '.:/home/marc/java/bin/saxon.jar:/home/marc/java/bin/xerces.jar';
my $RIKCHIK_HOME = $ENV{'RIKCHIK_HOME'};
my $RIKCHIK_SRC = $ENV{'RIKCHIK_SRC'};
chdir "$RIKCHIK_HOME/language";
system "/usr/bin/java com.icl.saxon.StyleSheet $RIKCHIK_SRC/data/riklang.xml $RIKCHIK_SRC/generators/lexicon/riklang.xsl";
