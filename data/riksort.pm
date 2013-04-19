#!/usr/bin/perl
use strict;
use XMLLite;

package riksort;
#sorting structures.
my @bits = ('circle', 'line', 'squiggle', 'arc', 'ellarc', 'greatarc', 
	    'wicket', 'hook', 'lbend', 'halfhook', ,
	    'zigzag',  'fishbend', 'lobe');
my %bitvals;
for (my $i = 0; $i < @bits; $i++){
  $bitvals{$bits[$i]} = $i;
}

my %sortvals;

sub sort_glyphs {
  my @glyphlist = @_;
  return sort by_glyph @glyphlist;
}

sub by_glyph {
  #assumes $a and $b are references to morphemes.
    if ($a->get_type eq 'morpheme' && $b->get_type eq 'morpheme'){
	#print &get_sortvalue($a).$a->get_attrib('name')."\t ".&get_sortvalue($b).$b->get_attrib('name')."\n";
	return ((&get_sortvalue($a).$a->get_attrib('name'))
		cmp
		(&get_sortvalue($b).$b->get_attrib('name')));
    } else {
	return ($a->get_type cmp $b->get_type);
    }
}

sub get_sortvalue { 
  my $ref = shift @_;
  if ($sortvals{$ref}){
    return $sortvals{$ref};
  } else {
    my $glyphRef= $ref->get_element('glyph')->[0];
    my $tentRef;
    my @vals;
    foreach $tentRef (@{$glyphRef->get_element(@bits)}){
      push @vals, $bitvals{$tentRef->get_type};
    }
    @vals = sort @vals;
    
    my $retVal = "";
    for (my $i=0; $i < @vals && $retVal == 0 ; $i++){
      $retVal .= chr($vals[$i]+ord('A'));
    }
    $sortvals{$ref} = $retVal;
    return $retVal;
  }
}

sub expand_sortvalue{
  my $sortval = shift @_;
  my %names;
  my @name;
  my $i;
  for ($i = 0; $i < 4; $i++){
    $names{substr($sortval,$i,1)}++;
  }
  foreach $i (sort keys %names){
    my $j = ord($i) - ord('A');
    my $n = $names{$i};
    push @name, "$n $bits[$j]".(($n>1)?"s":"");
  }
  return join ', ' , @name;
}

1;
