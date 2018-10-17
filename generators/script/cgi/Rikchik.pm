package Rikchik;

# This code written by Denis Moskowitz.  All Rights Reversed (K) Doowutchyalike.

use Exporter();
@ISA = qw(Exporter);
@EXPORT = qw(paint_word get_parameters initialize_image @phrase $tight $size $output_type);
@EXPORT_OK = qw($color $style $lineheight $caption);
use GD;
use strict;
use vars qw(@phrase $tight $size $color $style $lineheight $caption $output_type);

#constants
my $imgdir = "/home/dmm/public_html/rikchik/images";
my $mapfile = "$imgdir/map.png";
my $colorprefix = "color"; 
my $wordwidth = 20;
my $wordheight = 20;
my $glyphcenter = 10;
my $rely = 1;
my $formx = 2;
my $formy = 18;
my $collup = 4;
my $collx = 19;
my $capy = 20;


sub get_parameters {
    @phrase = ();
    $size = 5;
    $color = 0;
    $style = "classic";
    $lineheight=0;
    $caption = 0;
    $tight=0;

    # get arg params
    my $input;
    if ($ENV{'REQUEST_METHOD'} eq 'POST') {
        $input = join "", <STDIN>;
    } else {
        $input = $ENV{'QUERY_STRING'};
    }
    my @input = split /[&;]/, $input;
    my %input;
    foreach (@input) {
        my ($name, $value) = ((/=/) ? (split/\=/, $_, 2) : ("message", $_));
        $value =~ s/\+/ /g;
        $value =~ s/%([0-9A-F][0-9A-F])/pack("c", hex($1))/eg;
        $input{$name} = $value;
    }
    if ($input{'size'} =~ /^\d+$/) {
        $size = $input{'size'};
    }
    if ($input{'color'}) {
        $color = 1;
    }
    if ($input{'caption'}) {
        $caption = 1;
    }
    if ($input{'page'}) {
        $output_type = 'page';
    }
    if ($input{'map'}) {
        $output_type = 'map';
    }
    if ($input{'tight'}) {
        $tight = 1;
    }
    if ($input{'style'} =~ /^[A-Za-z]+$/ 
            && -d ( "$imgdir/" . $input{'style'} )) {
        $style = $input{'style'};
    }
    if ($input{'lineheight'} =~ /^(\d+)|(s)$/) {
        $lineheight = $input{'lineheight'};
    }

    #get scriptname params
    if ($0 =~ /tiny/) {
      $size = 1;
    } elsif ($0 =~ /small/) {
      $size = 2;
    } elsif ($0 =~ /med/) {
      $size = 3;
    }
    if ($0 =~ /color/ || $0 =~ /learn/) { $color = 1; }

    # make room for captions
    if ($caption) {
	$size = 5;
	$wordheight += 1;
    }

    #parse message
    if ($input{'message'} !~ /^[A-Za-z0-9\-_\.]*$/)
        { print "Content-type:text/html\n\nBad input: $input"; die;}
    my @message = split (/[_\.]/, $input{'message'});
    my $word;
    foreach $word (@message) {
        #get 4 pieces
        my @component = split(/\-/, $word);
        my %word;
        $word{'m'} = $component[0];
        if (length $component[1] == 1) {
            #marc order
            $word{'a'} = $component[1];
            $word{'r'} = $component[2];
        } else {
            #mrac order: old style
            $word{'r'} = $component[1];
            $word{'a'} = $component[2];
        }
        $word{'c'} = $component[3];
        push @phrase, \%word;
    }
}

sub initialize_image {
    my ($im) = @_;
    my ($width, $height) = $im->getBounds();
    open(MAP, $mapfile) or die "can't open $mapfile: $!";
    my $map = newFromPng GD::Image(\*MAP);
    my ($mapwidth, $mapheight) = $map->getBounds();
    # check bounds
    if ($width < $mapwidth or $height < $mapheight) {
	#image is too small for map
    }
    #copy map into image (to get colors)
    $im->copy($map, 0, 0, 0, 0, $mapwidth, $mapheight);
    #check colormap
    if ($im->colorsTotal < $map->colorsTotal) {
	die "map failed!";
    }
    #allocate white
    my $white = $im->colorResolve(255, 255, 255);
    #overwrite image with white
    $im->filledRectangle(0, 0, $width, $height, $white);
    if ($im->colorsTotal < $map->colorsTotal) {
	die "map undone!";
    }
    #make white transparent
    # $im->transparent($white);
    $im->interlaced(1);

}

sub paint_caption {
    my ($text, $cx, $cy, $im) = @_;
    my $font = gdSmallFont;
    my $black = $im->colorResolve(0, 0, 0);
    my $white = $im->colorResolve(255, 255, 255);
    $im->filledRectangle($cx, $cy, 
	    $cx + $font->width * length ($text), $cy + $font->height, $white);
    $im->string($font, $cx, $cy , $text, $black);
}

sub paint_piece {
    my ($component_name, $cx, $cy, $im) = @_;
    if (open COMPONENT, $component_name) {
        my $component = newFromPng GD::Image(*COMPONENT);
        close COMPONENT;
	my ($width, $height) = $component->getBounds();
        # allocate white
        my $cwhite = $component->colorResolve(255, 255, 255);
        $component->transparent($cwhite);
	my $tx = $cx - int($width / 2);
	my $ty = $cy - int($height / 2);
	$im->copy($component, $tx, $ty, 0, 0, $width, $height);
    } else {
	# print "Content-type:text/html\n\ncouldn't open $component_name: $!";
	# die;
    }
}

sub paint_word {
    my ($word, $x, $y, $im) = @_;
    my %word = %$word;
    my $imglocation = "$imgdir/$style/$size/";
    my $dialocation = $imglocation;
    if ($color) {
        $dialocation .= $colorprefix;
    }

    my $morpheme = ucfirst lc $word{'m'};
    paint_piece($imglocation . "m$morpheme.png",
        $x + $glyphcenter * $size, $y + $glyphcenter * $size, $im);

    my $aspect = uc substr $word{'a'}, 0, 1;
    paint_piece($dialocation . "a$aspect.png", 
	    $x + $formx * $size, $y+ $formy * $size, $im);

    my $relation = ucfirst lc $word{'r'};
    paint_piece($dialocation. "r$relation.png",
	    $x + $glyphcenter * $size, $y + $rely * $size, $im);

    my $collector = uc $word{'c'};
    my $num = substr($collector, 0, 1);
    my $cy = $formy;
    if ($collector =~ /[Pp]$/) {
	$cy -= $collup;
    } elsif ($collector =~ /[Oo]$/) {
	$cy -= $collup * 2;
    }
    paint_piece($dialocation . "c$num.png",
	    $x + $collx * $size, $y + $cy * $size, $im);
    if ($caption) {
	my $fullname = "$morpheme-$aspect-$relation-$collector";
	paint_caption($fullname, $x, $y + $capy * $size, $im);
    }
}

1;
