#!/usr/bin/perl
use strict;
use XMLLite;

my $rikhome = $ENV{'RIKCHIK_HOME'} || ".";
my $style = shift || "classic";
my $infile = "../../data/riklang.xml";
my $outfile = $rikhome."/ps/rikchik.ps";
my %names = ('line' => 'Line',
	     'arc' => 'Arc',
	     'ellarc' => 'EllArc',
	     'squiggle' => 'Squiggle',
	     'circle' => 'Circle',
	     'wicket' => 'Wicket',
	     'hook' => 'Hook',
	     'halfhook' => 'HalfHook',
	     'lbend' => 'LBend',
	     'fishbend' => 'FishBend',
	     'zigzag' => 'ZigZag',
	     'lobe' => 'Lobe',
	     'greatarc' => 'GreatArc');
my %args = ('line' => ['r', 'x', 'y', 'x2', 'y2'],
	    'arc' => ['r', 'x', 'y', 'r1', 'a1', 'a2'],
	    'ellarc' => ['r', 'x', 'y', 'r1', 'r2', 'a1', 'a2'],
	    'squiggle' => ['r', 'x', 'y', 'x2', 'y2', 'r1', 'd'],
	    'circle' => ['r', 'x', 'y', 'r1'],
	    'wicket' => ['r', 'x', 'y', 'r1', 'leg', 'a1'],
	    'hook' => ['r', 'x', 'y', 'r1', 'leg', 'a1', 'd'],
	    'halfhook' => ['r', 'x', 'y', 'r1', 'leg', 'a1', 'd'],
	    'lbend' => ['r', 'x', 'y', 'x2', 'y2', 'd'],
	    'fishbend' => ['r', 'x', 'y', 'r1', 'leg', 'a1', 'd'],
	    'zigzag' => ['r', 'x', 'y', 'x2', 'y2', 'd'],
	    'lobe' => ['r', 'x', 'y', 'r1', 'r2', 'a1', 'd'],
	    'greatarc' => ['r', 'a1', 'a2']);
my %defaults = (
		'x' => '50', 'y' => '-50',
		'x2' => '80', 'y2' => '-80',
		'r1' => '10', 'r2' => '10',
		'leg' => '10',
		'a1'  => '0', 'a2' => '90', 	
		'd' => '0', 'r' => '0');
my %ops = ('r' => 'sqrt mul',
	   's' => '2 sqrt sub 2 div mul',
	   '/' => 'div');

my $rootRef = XMLLite->parse_xml($infile);
#print $rootRef->write_element(0);
open (OUT, ">$outfile") || die "could not open $outfile.";
print OUT "%!PS-Adobe-2.0\n\n%%BeginProlog\n\n";
my $inbits = "bits.ps";
open (INBITS, "<$inbits") || die "Can't open $inbits: $!";
my $l;
while ($l = <INBITS>) {
  print OUT $l;
}
print OUT "%!\n";
my $instrokes = "strokes.ps";
open (INSTROKES, "<$instrokes") || die "Can't open $instrokes: $!";
my $l;
while ($l = <INSTROKES>) {
  print OUT $l;
}
print OUT "/tentaclestroke {".substr($style,0,1)."tentaclestroke} def\n\n";
print OUT "%!\n";
#write each dictionary.
my @lists = @{$rootRef->get_element(qw(morphemes aspects relations collectors symbols))};
my $listRef;
foreach $listRef (@lists){
  print "writing list: ".$listRef->get_type().":\n";
  print OUT &write_list($listRef);
}
#write final info.
print OUT &write_end();
print OUT "\n\n%%EndProlog\n";
close OUT;

sub write_dict{
  my $ref = shift(@_);
  my $retStr;
  my $entryRef;
  my $abbr = $ref->get_attrib('abbr');
  $retStr .= "% ".$ref->get_attrib('name')."\n";
  my @entries = @{$ref->get_element('entry')};
  foreach $entryRef (@entries){
    print "writing entry: ".$entryRef->get_attrib('name').":\n";
    $retStr .= &write_entry($entryRef, $abbr);
  }
  return $retStr;
}

sub write_list{
  my $ref = shift(@_);
  my $retStr;
  my $elemRef;
  my $type = $ref->get_type();
  my $abbr;
  my $subtype = substr($type, 0, length($type)-1);
  if ($type eq 'morphemes'){
    $abbr = 'm';
  } elsif ($type eq 'aspects'){
    $abbr = 'a';
  } elsif ($type eq 'relations'){
    $abbr = 'r';
  } elsif ($type eq 'collectors'){
    $abbr = 'c';
  } elsif ($type eq 'symbols'){
    $abbr = 's';
  } 
  $retStr .= "% ".$type."\n";
  my @elements = @{$ref->get_element($subtype)};
  foreach $elemRef (@elements){
    print "writing entry: ".$elemRef->get_attrib('name').":\n";
    $retStr .= &write_element($elemRef, $abbr);
  }
  return $retStr;
}


sub write_element{
  my $ref = shift (@_);
  my $abbr = shift (@_);
  my $name = $ref->get_attrib('name');
  my $glyphRef = $ref->get_element('glyph')->[0];
  return &write_glyph($glyphRef,$abbr,$name);
}

sub write_glyph{
  my $ref = shift (@_);
  my $abbr = shift (@_);
  my $name = shift (@_);
  my $retStr;
  $retStr .= "/".$abbr.$name." {\n    newpath\n";
  my @strokes = @{$ref->get_element
		      ('line','arc','ellarc','squiggle','circle','wicket','hook','halfhook','lbend','fishbend','zigzag','lobe','greatarc','ps')
			};
  if ($abbr eq 'm'){
      for (my $i = 0; $i < @strokes; $i++ ){
	  my $strokeRef = $strokes[$i];
	  $retStr .= ' ' x 8 . "$i idTm\n";
	  $retStr .= &write_stroke($strokeRef);
      }
  } else {
      foreach my $strokeRef (@strokes) {
	$retStr .= ' ' x 8 . "idT$abbr\n";
	$retStr .= &write_stroke($strokeRef);
      }
  }
  $retStr .= "    stroke\n} def\n";
  return $retStr;
}

#sub write_entry{
#    my $ref = shift (@_);
#    my $abbr = shift (@_);
#    my $retStr;
#    my $strokeRef;
#    $retStr .= "/".$abbr.$ref->get_attrib('name')." {\n    newpath\n";
#    my @strokes = @{$ref->get_element
#			('line','arc','ellarc','squiggle','circle','wicket','hook','halfhook','lbend','fishbend','zigzag','lobe','greatarc','ps')
#			};
#    foreach $strokeRef (@strokes){
#	$retStr .= &write_stroke($strokeRef);
#    }
#    $retStr .= "    stroke\n} def\n";
#    return $retStr;
#}

sub write_stroke{
  my $ref = shift (@_);
  die "Empty stroke!\n" unless $ref;
  my $retStr;
  my $type = $ref->get_type();
  my $text;
  my @text;
  my $arg;
  $retStr .= ' 'x8;
  if (@text = @{$ref->get_element()}){ #contains text
    foreach $text (@text){
      $retStr .= $$text." ";
    }
    if ($type ne 'ps'){ #isn't ps
      $retStr .= "$names{$type}";
    }
  } else { #defined by attribs
    my @args = @{$args{$type}};
    foreach $arg (@args){ #write args
      if ($ref->has_attrib($arg)){
	# print "has $arg.\n";
	$retStr .= &convert_arg($ref->get_attrib($arg));
      } else {
	print STDERR "$type lacks $arg. using $defaults{$arg}.\n";
	$retStr .= $defaults{$arg};
      }
      $retStr .= " ";
    }
    $retStr .= $names{$type}; #write function
  }
  $retStr .= "\n";
  return $retStr;
}

sub write_end{
  my $end = `cat prolog_end.txt`;
  return $end;
}

sub convert_arg{
  my $arg = shift (@_);
  #converts simple arithmetic to PostScript.
  #form: 3.5r2/4.7
  #output: 3.5 2 sqrt mul 4.7 div
  #form: 3s2
  #output: 3 2 2 sqrt sub 2 div mul
  #build data structure:
  #tree of arg0,arg1,op
  if ($arg =~ /^-?[0-9.]*$/){
    return $arg;
  }
  my $treeRef = &expand_tree(\$arg);
  my $retStr = &write_tree($treeRef);
  return $retStr;
}

sub expand_tree{
  #let's be recursive!
  my $ref = shift(@_);
  if ($ref =~ /^-?[0-9.]*$/){
    return $ref;
  } else {
    #attempt splits, in order, of /, r, and s.
    my $op;
    my @ops = ('/','r','s');
    foreach $op (@ops){
      my @args = split (/$op/,$$ref);
      if (@args == 2){
	# print "splitting on $op:\n";
	my $retRef = {'arg0' => &expand_tree(\$args[0]),
		      'op' => $op,
		      'arg1' => &expand_tree(\$args[1])
			};
	return $retRef;
      }
    }
    return $ref;
  }
}

sub write_tree{
  #write out args.
  my $ref = shift (@_);
  my $retStr;
  if (ref $ref eq "SCALAR"){
    $retStr .= $$ref;
  } else {
    #print "ref: ".(ref $ref)."\n";
    $retStr .= &write_tree($ref->{'arg0'});
    $retStr .= " ";
    $retStr .= &write_tree($ref->{'arg1'});
    $retStr .= " ";
    $retStr .= $ops{$ref->{'op'}};
  }
  return $retStr;
}







