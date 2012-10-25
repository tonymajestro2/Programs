#!/usr/bin/perl -w
print "Content-type: text/plain\n\n";
use CGI;
$upload_dir = "/var/www/uploads";
$query = new CGI;
print "Reading params\n";
$filename = $query->param("song");
$filename =~ s/.*[\/\\](.*)/$1/;
print "Getting a handle\n";
$upload_filehandle = $query->upload("song");
print "open upload file '$upload_dir/$filename'\n";
open(UPLOADFILE, ">$upload_dir/$filename") or die "Can't open '$upload_dir/$filename': $!";
print "Before bin mode\n";
binmode UPLOADFILE;
print "Going to read file\n";
while ( <$upload_filehanle>)
{
print "Still reading\n";
print UPLOADFILE;
}
close UPLOADFILE;
print "File copied.\n";
