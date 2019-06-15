#!/usr/bin/perl
use strict;
use autodie;
my $javabin = "/usr/bin/java";
my $backup_javabin = "/scratch/usr/local/java/jdk1.7.0_40/bin/java";
if (-e $backup_javabin) {
    $javabin = $backup_javabin;
}
my $RIKLANG = $ENV{'RIKLANG'} || $ENV{'RIKCHIK_SRC'};
my $RIKHOME = $ENV{'RIKCHIK_HOME'};
die unless $RIKLANG;
$ENV{'CLASSPATH'} = ".:$RIKLANG/lib/saxon-9.1.jar";
chdir "$RIKHOME/language";
system "pwd";
system "$javabin net.sf.saxon.Transform $RIKLANG/data/riklang.xml $RIKLANG/generators/lexicon/riklang.xsl";
print "xsl lexicon ok\n";
