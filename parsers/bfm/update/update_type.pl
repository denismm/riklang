#!/usr/bin/perl
use strict;
use lib('../../../generators/script/');
use XMLLite;

my $infile = '../../../data/riklang.xml';
my $maintype = '../maintype';
my $typejs = '../type.js';

my $rootRef = XMLLite->parse_xml($infile) || die $!;

my @morphemes = @{$rootRef->get_sub_element('morpheme')};
my @names = map {$_->get_attrib('name')} @morphemes;
my $line = "var morphemes_for_click = [ " .  join (', ', map {"'$_'"} @names). "]\n";

open (MT, '<', $maintype) || die $!;
open (TJS, '>', "$typejs.new") || die $!;
print TJS "// this file created by update_type - edit maintype\n";
print TJS $line;
while (<MT>) {
    print TJS $_;
}
close MT;
close TJS;
rename "$typejs.new", $typejs || die $!;
