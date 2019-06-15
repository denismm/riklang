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
	return ((&get_sortvalue($a).$a->get_attrib('name'))
		cmp
		(&get_sortvalue($b).$b->get_attrib('name')));
    } else {
	return ($a->get_type cmp $b->get_type || $a->get_attrib('name') cmp $b->get_attrib('name'));
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

sub sort_compounds {
    my @compound_list = @_;
    return sort by_compound @compound_list;
}

sub get_words {
    return $_[0]->get_element('addition')->[0]->get_element('utterance')->[0]->get_element('word');
}
sub by_compound {
    my @a_addition = @{get_words($a)};
    my @b_addition = @{get_words($b)};
    my $i = 0;

    while ($i < @a_addition && $i < @b_addition) {
        my $a_word = $a_addition[-1 - $i];
        my $b_word = $b_addition[-1 - $i];
        # this should do rikchik sort but abc for now
        my $sort = $a_word->get_attrib('morpheme')
            cmp $b_word->get_attrib('morpheme');
        return $sort if $sort != 0;
        $i++;
    }
    return scalar (@a_addition) <=> scalar (@b_addition);
}

1;
