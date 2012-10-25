#!/usr/bin/env python
import os
import cgi
import cgitb; cgitb.enable()  # for troubleshooting

print "Content-type: text/html"
print

print """
<html>

<head><title>Sample CGI Script</title></head>

<body>

  <h3> Sample CGI Script </h3>
"""

form = cgi.FieldStorage()
filedata = form['datafile']
message="sd"
if filedata.filename:
	fn = os.path.basename(filedata.filename)
	open('../uploads/' + fn, 'wb').write(filedata.file.read())


print """

  <p>Previous message: %s</p>

  <p>form

  <form method="post" action="index.cgi">
    <p>message: <input type="text" name="message"/></p>
  </form>

</body>

</html>
""" % cgi.escape(message)

