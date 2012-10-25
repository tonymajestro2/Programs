#!/usr/bin/perl -w
use CGI qw(:standard);
use strict;

my $query = new CGI;

my $name = $query->param('name');
my $loc = $query->param('loc');

print $query->header("text/plain");
print "$name is from $loc\n";
