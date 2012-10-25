#!/usr/bin/perl
print "Content-type: text/plain\n\n";
open (MYFILE, '/proc/loadavg');
$var = <MYFILE>;
chomp $var;
my @values = split(' ', $var);
print @values[0] . "\n";
