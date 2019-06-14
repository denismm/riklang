#!/usr/bin/perl
use strict;
use lib "../generators/script/", ".";
use XMLLite;
use riksort;

my @elements = qw(morphemes aspects relations collectors symbols glyph addition readings roles idioms compounds utterance note translation examples gloss R T V M N P I Agent Source Instrument Quality Destination Patient Includes Agentrec Sourcerec Instrumentrec Qualityrec Destinationrec Patientrec Includesrec End);
my %elementvals;
for (my $i = 0; $i < @elements; $i++){
    $elementvals{$elements[$i]} = $i;
}
my $startdir = shift || 'language';

my $xmlRef = &contract_dir($startdir);
print STDERR "contract complete\n";
print $xmlRef->write_element;

sub contract_dir{
    my $dir = shift;
    print STDERR "contracting $dir\n";
    chdir $dir;
    my $ref = XMLLite->new($dir);
    opendir DIR, '.';
    my @entries = readdir DIR;
    my @new_elems;
    foreach my $entry (sort by_dtd @entries){
	next if $entry =~ /^\./;
	next if $entry =~ /CVS/;
	if (-d $entry){
	    push @new_elems, &contract_dir($entry);
	} elsif ($entry =~ /\.xml$/){
	    push @new_elems, XMLLite->parse_xml($entry);
	} elsif ($entry =~ /^_TYPE:(.*)$/){
	    $ref->{'type'} = $1;
	} elsif ($entry =~ /^_(.*):(.*)$/){
	    $ref->{'attrib'}->{$1} = $2;
	}
    }
    if ($ref->get_type eq 'morphemes'){
	@new_elems = riksort::sort_glyphs(@new_elems);
    }
    foreach my $new_elem (@new_elems){
	$ref->add_element($new_elem);
    }
    chdir '..';
    return $ref;
}

sub by_dtd{
    my ($aval, $bval) = ($a, $b);
    if ($aval =~ /^(.*)\.xml$/){
	$aval = $1;
    }
    if ($bval =~ /^(.*)\.xml$/){
	$bval = $1;
    }
    my $sorting = $elementvals{$aval} <=> $elementvals{$bval}
        || $elementvals{$aval} cmp $elementvals{$bval}
        || $aval <=> $bval || $aval cmp $bval;
    # print STDERR "$a\t$b\t$sorting\n";
    return $sorting
}

