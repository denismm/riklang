#!/usr/bin/perl
# read rikchik.ps and draw bitmaps of each component
use strict;
my $rikhome = $ENV{'RIKCHIK_HOME'} || ".";

# get arguments
my %arg = map {$_ => 1} @ARGV;

# anti-aliasing scale
my $aasize = 4;
my $aascale = 1 / $aasize;
# command to draw a single bitmap
my $command = "gs -sOutputFile=- -sDEVICE=ppmraw -gWIDTHxHEIGHT -q -dNOPAUSE -dBATCH temp.ps | pnmscale $aascale | pnmtopng > ${rikhome}/images/STYLE/SIZE/NAME.png\n";
my ($header, $definition, $name, $stage, $nextstage, $laststage);
# baseheight = height in units, basewidth = width in units
# xposition, yposition = position of lower left corner in glyph space, in units
# my %styles = 
#     ( "classic" => "classic_bits.ps",
#       "pointy" => "pointy_bits.ps",
#       "blunt" => "blunt_bits.ps");
my @styles = qw(classic pointy blunt);
my (%stageinfo);
%stageinfo = (
    "header" => {
	'next' => 'morphemes'
    },
    "morphemes" => {
	'baseheight' => 14, 'basewidth' => 14,
	'xposition' => 15, 'yposition' => -85,
	'next' => 'aspects'
    },
    "aspects" => {
	'baseheight' => 6, 'basewidth' => 6,
	'xposition' => -5, 'yposition' => -105,
	'color' => [1, 0, 0], 'next' => 'relations'
    },
    "relations" => {
	'baseheight' => 4, 'basewidth' => 16,
	'xposition' => 10, 'yposition' => -15,
	'color' => [0, 0, 1], 'next' => 'collectors'
    },
    "collectors" => {
	'baseheight' => 6, 'basewidth' => 4,
	'xposition' => 85, 'yposition' => -105,
	'color' => [0, 0.5, 0], 'next' => 'symbols'
    },
    "symbols" => {
	'baseheight' => 14, 'basewidth' => 14,
	'xposition' => 15, 'yposition' => -85,
	'next' => 'pronomials'
    },
);
$laststage = 'pronomials';
my $style;
foreach $style (@styles){
    #read in bits file.
    # my $bits = "";
    # open BITSIN, $styles{$style};
    # while (<BITSIN>){
	# $bits .= $_;
    # }
    # close BITSIN;

    my $in = $rikhome."/ps/rikchik.ps";
    open IN, "<$in" || die "Can't open $in: $!";;
    $stage = 'header';
    $nextstage = '';
  # SKIP_BITS: while (<IN>){
      # if (/bd \{bind def\} bind def/) {
	  # last SKIP_BITS;
      # }
  # }
  READ_HEADER: while (<IN>) {
      if (/^% ([a-z]+)$/) {
	  $nextstage = $1;
	  last READ_HEADER;
      }
      $header .= $_;
  }
    die "Malformed file: EOF in header" if $nextstage eq '';
    
    while ($nextstage ne $laststage) {
	$stage = $stageinfo{$stage}{'next'};
	if ($stage ne $nextstage) {
	    die "Malformed file: stage $nextstage instead of $stage";
	}
	$nextstage = '';
	my ($baseheight) = $stageinfo{$stage}{'baseheight'};
	my ($basewidth) = $stageinfo{$stage}{'basewidth'};
	my ($xposition) = $stageinfo{$stage}{'xposition'};
	my ($yposition) = $stageinfo{$stage}{'yposition'};
	my ($color) = $stageinfo{$stage}{'color'};
      READ_DEFINITIONS: while (<IN>) {
	  if (/^$/) {
	      warn "Warning: Blank line within $name\n" if $definition ne '';
	      next READ_DEFINITIONS;
	  }
	  if (/^% ([a-z]+)/) {
	      $nextstage = $1;
	      last READ_DEFINITIONS;
	  }
	  if ($definition eq '') {	#beginning of definition
	      ($name) = /^\/([a-z][A-Z0-9]\w*) \{/; # } for vi
						die "Malformed file: name expected instead of $_" if (!$name);
	  }
	  $definition .= $_; 	# { for vi
	  if (/^\} def/) {	# end of definition
	      if (scalar %arg) {
		unless ($arg{$name}) {
		    $definition = '';
		    next READ_DEFINITIONS;
		}
	      }
	      #write definition to file
	      my ($size, $color_p);
	      foreach $size (5,3,2,1) {
		COLOR: foreach $color_p (0,1) {
		    my ($t_name) = $name;
		    if ($color_p) {
			next COLOR unless $color;
			$t_name = "color" . $t_name;
		    }
		    print STDERR "$style/$size/$t_name:\t ";
		    my $height = $baseheight * $size * $aasize + 1;
		    my $width = $basewidth * $size * $aasize + 1;
		    open OUT, ">temp.ps" || die "Can't open temp.ps: $!";
		    # print OUT $bits;
		    print OUT $header;
		    print OUT $definition;
		    print OUT "set$style\n";
		    print OUT "5 $size div setlinewidth\n";
		    print OUT "$size 5 div dup scale\n";
		    print OUT "$aasize dup scale\n";
		    print OUT "0.5 dup translate\n";
		    print OUT "$xposition -1 mul $yposition -1 mul translate\n";
		    if ($color_p) {
			print OUT join(" ", @$color);
			print OUT " setrgbcolor\n";
		    }
		    print OUT "$name\nshowpage\n";
		    close OUT || die "Can't close temp.ps: $!";
		    my $gscommand = $command;
		    $gscommand =~ s/NAME/$t_name/g;
		    $gscommand =~ s/HEIGHT/$height/g;
		    $gscommand =~ s/WIDTH/$width/g;
		    $gscommand =~ s/SIZE/$size/g;
		    $gscommand =~ s/STYLE/$style/g;
		    # print STDERR $gscommand;
		    my $return = system $gscommand;
		    if ($return != 0) {
			print STDERR "problem: $!";
			die;
		    } else {
			print STDERR "OK\n";
		    }
		    unlink "temp.ps" || die "Can't delete temp.ps: $!";
		}
	      }
	      $definition = '';
	  }
      }
	die "Malformed file: EOF in $stage" if $nextstage eq '';
    }
    $stage = $stageinfo{$stage}{'next'};
    if ($stage ne $nextstage) {
	die "Malformed file: stage $nextstage instead of $stage";
    }
    close IN;
}
print STDERR "Drawing Completed.\n";
