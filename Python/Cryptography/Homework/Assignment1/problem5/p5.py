import collections
import re

message = open('encryptedMessage.txt').read().rstrip()

# Get frequency of each letter
letters = collections.Counter(message)

# Get frequency of each digraph
digraphs = collections.Counter(re.findall(r'(?=(..))', message))

# Get frequency of each trigraph
trigraphs = collections.Counter(re.findall(r'(?=(...))', message))

# Print out letter frequencies
#print
#print letters
#print
#print digraphs
#print
#print
#print trigraphs
#print

d = {'Z':'e', 'G':'r', 'K':'t', 'O':'w', 'J':'h', 'P':'a'}

def decrypt():
    ls = []
    for c in message:
        if d.has_key(c):
            ls.append(d[c])
        else:
            ls.append(c)
    return ''.join(ls)

decrypt()



if __name__ == '__main__':
    print decrypt()
