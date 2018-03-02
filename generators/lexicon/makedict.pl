#!/usr/bin/perl
use strict;
my $RIKCHIK_HOME = $ENV{'RIKCHIK_HOME'};
my $RIKCHIK_SRC = $ENV{'RIKCHIK_SRC'};
$ENV{'CLASSPATH'} = ".:$RIKCHIK_HOME/lib/saxon-9.1.jar";
chdir "$RIKCHIK_HOME/language";
system "/usr/bin/java net.sf.saxon.Transform $RIKCHIK_SRC/data/riklang.xml $RIKCHIK_SRC/generators/lexicon/riklang.xsl";
