#!/usr/bin/perl
use strict;
use autodie;
my $javabin = "/usr/bin/java";
$javabin = "/scratch/usr/local/java/jdk1.7.0_40/bin/java";
my $RIKLANG = $ENV{'RIKLANG'} || $ENV{'RIKCHIK_SRC'};
my $RIKHOME = $ENV{'RIKCHIK_HOME'};
die unless $RIKLANG;
$ENV{'CLASSPATH'} = ".:$RIKLANG/lib/saxon-9.1.jar";
chdir "$RIKHOME/language";
system "$javabin net.sf.saxon.Transform $RIKLANG/data/riklang.xml $RIKLANG/generators/lexicon/riklang.xsl";
