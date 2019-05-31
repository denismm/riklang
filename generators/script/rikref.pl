#!/usr/bin/perl
my $rikhome = $ENV{'RIKCHIK_HOME'} || ".";
$in = $rikhome."/ps/rikchik.ps";
$out = $rikhome."/ps/reference.ps";
$width = 8;
$height = 10;
$pagesize = $width * $height;
open (IN, "<$in") || die "Can't open $in: $!";
open (OUT, ">$out") || die "Can't open $out: $!";
{
    while (<IN>) {
	print OUT;
	if (/^\/([mr])([A-Z]\w+) \{/) {	# } for vi
	    push (@{$language{$1}}, $2);
	}
	if (/^\/([ac])([A-Z0-9]) \{/) {	# } for vi
	    push (@{$language{$1}}, $2);
	}
    }
}
close IN;
@Morph = sort @{$language{'m'}};
for ($page = 0; $page * $pagesize < @Morph; $page++) {
    print OUT <<EOH;
%%Page: morphemes-$page
PageBegin
2 setlinewidth
% setblunt
% .95 dup scale
/caption { newpath dup stringwidth pop -2 div 50 -100 moveto 0 rmoveto show } bd
/Helvetica findfont 20 scalefont setfont
EOH
    print OUT "gsave\n";
    for ($column = 0; $column < $width; $column++) {	
	last if ($page * $pagesize + $column * $height >= @Morph);
	print OUT "  gsave\n";
	for ($row = 0; $row < $height; $row++) {
	    last if ($page * $pagesize + $column * $height + $row >= @Morph);
	    my ($morph) = $Morph[$page * $pagesize + $column * $height + $row];
	    print OUT "    m$morph ($morph) caption nextword\n";
	}
	print OUT "  grestore\nnextline\n";
    }
    print OUT "grestore\nshowpage\n";
}
print OUT <<EOH;
%%Page: inflections
PageBegin
2 setlinewidth
% setblunt
% .95 dup scale
/caption { newpath dup stringwidth pop -2 div 50 -50 moveto 0 rmoveto show } bd
/Helvetica findfont 20 scalefont setfont
EOH
print OUT "gsave\n";
@type = ('a', 'r', 'c');
foreach $type (@type) {	
    for ($column = 0; $column < $width; $column++) {	
	last if ($column * $height >= @{$language{$type}});
	print OUT "  gsave\n";
	for ($row = 0; $row < $height; $row++) {
	    last if ($column * $height + $row >= @{$language{$type}});
	    my ($item) = $language{$type}[$column * $height + $row];
	    print OUT "    $type$item ($item) caption nextword\n";
	}
	print OUT "  grestore\nnextline\n";
    }
}
print OUT "grestore\nshowpage\n";
close OUT;

system ("gstopdf $out") == 0 or system ("pstopdf $out");
