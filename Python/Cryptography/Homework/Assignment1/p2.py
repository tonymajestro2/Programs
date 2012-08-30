
import string

word = open('word.txt').read().rstrip()
d = dict([(v,k) for k,v in enumerate(string.lowercase)])

