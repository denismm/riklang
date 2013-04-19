#!/usr/bin/perl

my $base = `cat direction.xml` || die "no base";

my @dir = qw(East North West South);
my @mor = qw(Sun Dead Eye God);

for (my $i = 0; $i < 4; $i++) {
    my $dir = $dir[$i];
    my $mor = $mor[$i];
    for (my $j = -1; $j <= 1; $j += 2) {
	my $k = ($i + 4 + $j) % 4;
	my $subdir = $dir[$k];
	my $submor = $mor[$k];
	my $file = $base;
	$file =~ s/SUBDIR/$subdir/g;
	$file =~ s/SUBMOR/$submor/g;
	$file =~ s/DIR/$dir/g;
	$file =~ s/MOR/$mor/g;
	my $dirname = "language/morphemes/$mor/compounds/";
	if (! -d $dirname) {
	    mkdir($dirname, 0777);        system "cvs add $dirname\n";
	}
	my $filename = "language/morphemes/MOR/compounds/SUBDIRern-DIR.xml";
	$filename =~ s/SUBDIR/$subdir/g;
	$filename =~ s/SUBMOR/$submor/g;
	$filename =~ s/DIR/$dir/g;
	$filename =~ s/MOR/$mor/g;
	open (FILE, "> $filename");
	print FILE $file;
	close FILE;
	system "cvs add $filename";
    }
}
