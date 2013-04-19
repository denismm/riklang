#!/usr/bin/perl
use strict;
use lib "../generators/script/";
use XMLLite;

my $infile = "riklang.xml";
my @final = qw( aspect relation collector symbol paradigm glyph reading
	role compound idioms);

my @id = qw( name aspect relation);

my %final = intest(@final);
my %id = intest(@id);

my $indent = 0;

my $rootRef = XMLLite->parse_xml($infile);

#write each node to directories or files
expand_xml($rootRef);

sub expand_xml {
    my ($ref) = (@_);
    my $type = $ref->get_type;
    my $name = $type;
    my $attribs = $ref->get_all_attribs;
    foreach my $attr_name (keys %$attribs) {
	if ($id{$attr_name}) {
	    $name = $attribs->{$attr_name};
	}
    }
    # special ID for compounds
    if ($type eq 'compound') {
	my $gloss = ($ref->get_sub_element('gloss'))->[0] || die "no gloss!";
	my $text = ($gloss->get_sub_element('text'))->[0] || die "empty gloss!";
	my ($data) = ($text->write_element =~ /<text>\s*(\S+(?:\s+\S+)*)\s*<\/text>/);
	die unless $data;
	$name = $data;
    }
    if ($final{$type}) {
	# create file
	open (ELEMENT, ">$name.xml") || die "can't open $name.xml: $!";
	my $data = $ref->write_element;
	print ELEMENT $data;
	close ELEMENT;
	print "  " x $indent, "> $name.xml\n";
    } else {
	# create directory
	mkdir $name;
	print "  " x $indent, "> $name/\n";
	chdir $name || die "can't chdir $name: $!";
	$indent++;
	# create type file if necessary
	if ($type ne $name) {
	    my $type_file = "_TYPE:$type";
	    system "touch $type_file" || die "can't touch $type_file: $!";
	    print "  " x $indent, "> $type_file\n";
	}
	foreach my $attr_name (keys %$attribs) {
	    my $attr_value = $attribs->{$attr_name};
	    my $attr_file = "_$attr_name:$attr_value";
	    system "touch $attr_file" || die "can't touch $attr_file: $!";
	    print "  " x $indent, "> $attr_file\n";
	}
	my $subnodes = $ref->get_element;
	foreach my $node (@$subnodes) {
	    expand_xml($node);
	}
	chdir ".." || die "can't chdir ..: $!";
	$indent--;
    }
}

sub intest {
    my %hash;
    foreach my $element (@_) {
	$hash{$element} = 1;
    }
    return %hash;
}
