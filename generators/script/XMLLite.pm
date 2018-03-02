#!/usr/bin/perl
package XMLLite;
use strict;

#list of subs.=============
#wait (message)
#parse_xml (class,filename)
#new(class,type)
#clone(ref)
#get_all_attribs(ref)
#get_attrib(ref,attname)
#get_type(ref)
#get_element(ref,type+)
#get_parent_element(ref,type+)
#get_sub_element(ref,type+)
#add_element(ref,ref)
#write_element(ref,level?)

#start subs.===============

sub wait{ #Simple wait.
    print "$_[0]\n";
    print "Enter $/ to continue.";
    <STDIN>;
} #end wait

sub parse_xml{
#parses an XML file into a nested data structure.
#Data Structure: 
# Each element is a hash.
# That hash contains five items: $type, %attrib, @elements, %types, and $parent.
# $type is the type of this element.
# %attrib is a hash of the attributes of the element.
# @elements is a list (therefore ordered) of the sub-elements of the element.
# %types is a hash of the sub-elements, by element type, which contains arrays of the elements.
# $parent is a reference to the parent of the element.
    
# Setup file to read. 
    $/ = ">"; #### HEY! input is now separated by right angle brackets! ####
    my $self = shift (@_); # This is a dual-nature method.
    my $class = ref($self) || $self;
    my $file = shift (@_);
    open (IN, $file);
    my $line;
    
# Setup references for the document structure.
    my %root = { #the root level of the document. Will be discarded.
	attrib => {},
	elements => [],
	types => {},
    };
    bless (\%root, $class);
    my (@index, $index); #"tracking" stack of references to the structure, a bookmark
    push @index, \%root; #put the root on the bottom of the stack.
    my $refTemp;
    
#Setup line-parsing variables.
    my ($isDeclaration, $isClose, $tagText, $isEmpty, $CDataText, $tagType, $attribList, @attribArray);
    
    while ($line=<IN>){
	$line =~ s/[\r\n]//g;
	#print $line;
	next if ($line =~ /<!--(.*)-->/); #Skip comments.
	#Read in declarations. Ignore them for now.
	if ($line =~ /<(\?|!)[^>]*>/) { #Does not handle internal document definitions.
	    next;
	}
	if (($isClose, $tagText, $isEmpty)=($line =~ /<(\/?)([^>]*[^>\/])(\/?)>/)){ # Pre-parse tag.
	    my $CDataText = $`;
	    if ($CDataText =~ /\S/){ # text preceding tag, place into element structure.
		$CDataText =~ s/^\s*//;
		$CDataText =~ s/\s*$//;
		push @{$index[-1]{elements}}, bless(\$CDataText, $class);
	    }
	    ($tagType, $attribList) = split(/ +/,$tagText,2); #Parse out type and attributes.
	    #figure out some way... hey! all attributes in XML are quoted! Aha!
#	$attribList && print "$attribList\n";
	    @attribArray = split (/=\"|\" *|\"|=\'|\' *|\'/,$attribList); #pull apart a list like 'x="hey" y="hey you"'
	    unless ($isClose){
		$refTemp = {  #create a new element and set its parts.
		    type => $tagType,
		    attrib => {@attribArray},
		    elements => [],
		    types => {},
		    parent => $index[-1]
		    };
		bless ($refTemp, $class);
		unless (defined($index[-1]{types}{$tagType})) {
		    $index[-1]{types}{$tagType} = [];
		} #create a new element in %types 
		push @{$index[-1]{elements}}, $refTemp; # place it into the elements structure
		push @{$index[-1]{types}{$tagType}}, $refTemp; # place it into the types structure
		if ($isEmpty){
#		print "Start and end $tagType\n";
		} else {
		    push @index, $refTemp; # place it on the tracking stack, if not empty.
#		print "Start $tagType\n";
		}
	    } else {
		if ($tagType eq $index[-1]{type}){
		    pop @index; #move back down the tracking stack
#	             print "End $tagType\n";
		}else{
		    die "Not well formed: tried to close $index[-1]{type} with $tagType ";
		}
	    }
	}
	$index = @index;
#    print ": $index levels.\n";
    }
    close (IN);
# finished parsing. Now we need to discard the root.
    until ($root{type}){
#	print ("Root has ". scalar(@{$root{elements}})." children.\n");
	if (@{$root{elements}} == 1){
	    %root = %{$root{elements}[0]};
	} else {
	    print ("Root has ". scalar(@{$root{elements}})." children.\n");
	    die "No single root element.";
	}
    }
    $/ = "\n";
    return (\%root);
} # end parse_xml

sub new{
#takes the class name and a type for the new object. creates a new object.
    my $self = shift(@_);
    my $class = ref ($self) || $self;
    my $type = shift(@_);
    my $rootRef = { #the root level of the new object.
	type => $type,
	attrib => {} ,
	elements => [],
	types => {}
    };
    bless ($rootRef, $class);
#    print ($rootRef);
#    print (%$rootRef);
    return ($rootRef);
}#end new

    my $temp = ${\$_}; #emacs not wanting to indent

sub clone{
    #takes a ref and returns a pointer to a root-level copy of it. Ugh. Can it be recursive? yes.
    my $ref = shift(@_);
    my $returnRef = $ref->new($ref->get_type);
    my $class = ref($ref);
    my $cdataTemp;
    my $xmlTempRef;
    my $elementTempRef;
    %{$returnRef->{attrib}} = %{$ref->get_all_attribs};
    for $elementTempRef (@{$ref->get_element}){
      SWITCH:{
	  ($elementTempRef =~ /SCALAR/) && do { #element is a piece of CDATA
	      $cdataTemp = $$elementTempRef; 
	      push @{$returnRef->{elements}}, bless(\$cdataTemp,ref($returnRef));
	      last SWITCH;
	  };
	  ($elementTempRef =~ /HASH/) && do { #element is a piece of XML
	      $xmlTempRef = $elementTempRef->clone;
	      unless (defined($returnRef->{types}{$xmlTempRef->get_type})) {
		  $returnRef->{types}{$xmlTempRef->get_type} = [];
	      } #create a new element in %types 
	      push @{$returnRef->{elements}}, $xmlTempRef; # place it into the elements structure
	      push @{$returnRef->{types}{$xmlTempRef->get_type}}, $xmlTempRef; # place it into the types structure
	      $xmlTempRef->{parent} = $returnRef; #set up the correct parent.
	      last SWITCH;
	  };
      } #end SWITCH
    }
    return $returnRef;
}#end clone
    
    my $temp = ${\$_}; #emacs not wanting to indent

sub get_all_attribs{
    #takes a ref to an XML object, and returns all attributes of that element.
    my $ref = shift(@_);
    my $attribs = $ref->{"attrib"};
    return $attribs;
}

sub get_attrib{
    #takes a ref to an XML object and an attribute name, and returns that attribute of that element.
    my $ref = shift(@_);
#    &wait ("element is $ref");
    my $attrib = shift (@_);
    my $value = $ref->{"attrib"}{$attrib};
    return $value;
}#end get_attrib

    my $temp = ${\$_}; #emacs not wanting to indent

sub has_attrib{
    #takes a ref to an XML object and an attribute name, and returns a boolean indicating whether that element has that attribute.
    my $ref = shift (@_);
    my $attrib = shift (@_);
    my @keys = keys (%{$ref->{'attrib'}});
    my $retBool = grep({$_ eq $attrib} @keys);
    return $retBool;
}

sub get_type{
    #takes a ref to an XML object and returns its type.
    my $ref = shift(@_);
    if ($ref =~ /hash/i){
	return $ref->{type};
    } else {
	return undef;
    }
}

sub get_element{
# takes a ref to a parent object and a list of named types, then returns all elements of those types. if no type provided, returns all elements.
    my ($parentRef, @types) = (@_);
#    &wait (join ":", @types);
    my @returnList;
    if (@types == 0){
	@returnList = @{$parentRef->{elements}};
    } else {
	my $elementTempRef;
	foreach $elementTempRef (@{$parentRef->{elements}}){ #look through elements.
	    if ($elementTempRef =~ /HASH/){
		if (grep /^$elementTempRef->{type}$/, @types){
		    push @returnList, $elementTempRef;
		}
	    } else {
#	    print ("Non-hash element: $elementTempRef\n");
	    }
	}
    }
    return (\@returnList);
}#end get_element

    my $temp = ${\$_}; #emacs not wanting to indent

sub get_parent_element{
#takes a ref to a child object and a list of named types, and returns the closest parent with one of those types. Recurses. If no type is given, returns direct parent.
    my ($thisRef, @types) = (@_);
    my $returnTemp;
    if ((grep /^$thisRef->{type}$/, @types) || (@types == 0)){ #this is the element we're looking for.
	$returnTemp = $thisRef;
    } elsif (!$thisRef->{parent}){ #this is the root element. No more parents!
	$returnTemp = undef;
    } else { #this is not the element. Maybe its parent is.
	$returnTemp = &get_parent_element($thisRef->{parent},@types);
    }
    return $returnTemp;
}#end get_parent_element

    my $temp = ${\$_}; #emacs not wanting to indent

sub get_sub_element{
#takes a ref and a list of types. finds all sub-elements, any number of levels down, that match those types. returns those elements in an array. Does not find matching sub-elements of matching elements.
    my ($thisRef, @types) = (@_);
    my $returnRef = [];
    my $refTemp;
    if ($thisRef =~ /SCALAR/){
	#this is a CDATA ref. leave it.
	1;
    } elsif ((grep /^$thisRef->{type}$/, @types)){ #this is the type of element we're looking for.
	push @$returnRef, $thisRef;
    } else { #this is not the type of element. Maybe it contains some.
	for $refTemp (@{$thisRef->get_element}){
	    push @$returnRef, @{$refTemp->get_sub_element(@types)};
	}
    }
    return $returnRef;
}#end get_sub_element

    my $temp = ${\$_};

sub add_element {
    my $thisRef = shift(@_);
#    print ($thisRef->write_element);
    my $addRef = shift(@_);
#    print ($addRef->write_element);
    my $cloneRef = $addRef->clone;
#    print ($cloneRef->write_element);
    unless (defined($thisRef->{types}{$cloneRef->get_type})) {
	$thisRef->{types}{$cloneRef->get_type} = [];
    } #create a new element in %types 
    push @{$thisRef->{elements}}, $cloneRef; # place it into the elements structure
    push @{$thisRef->{types}{$cloneRef->get_type}}, $cloneRef; # place it into the types structure
    $cloneRef->{parent} = $thisRef; #set up the correct parent.
    return $thisRef;
}

sub write_element {
    my ($ref,$level) = @_;
    my $returnTemp;
    my $refType = ref($ref);
#   print "Parsing $refType\n";
    my ($attribTemp,$elemTemp,$errTemp);
  SWITCH:{
      ($ref =~ /SCALAR/) && do { # element is a piece of CDATA
	  $returnTemp .=(("  "x$level).$$ref."\n"); 
	  return $returnTemp;
	  last SWITCH;
      };
      ($ref =~ /HASH/) && do { # element is an xml element.
	  $returnTemp .= ("  "x$level); #tab out to current level
	  $returnTemp .= "<".$ref->get_type; #Start tag.
#	  &wait (keys %$ref);
	  foreach $attribTemp (sort keys %{$ref->get_all_attribs}){ #print out attributes
	      $returnTemp .= (" $attribTemp=\"".$ref->get_attrib($attribTemp)."\"");
	  }
	  if (scalar(@{$ref->{elements}})==0){
	      $returnTemp .= "/>\n"; #end empty tag.
	  } else {
	      $returnTemp .= ">\n"; #end tag.
	      foreach $elemTemp (@{$ref->get_element}){ #print out elements
#		  print $elemTemp;
		  $returnTemp .= $elemTemp->write_element($level+1);
	      }
	      $returnTemp .= ("  "x$level); #tab out to current level
	      $returnTemp .= "</".$ref->get_type.">\n"; #Print end tag.
	  }
	  return $returnTemp;
	  last SWITCH;
      };
      return $returnTemp;
  }
} #end write_element.
1;









