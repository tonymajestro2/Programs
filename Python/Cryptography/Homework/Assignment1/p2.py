import re  # regular expressions library
import collections  # contains Counter library

# read encrypted message from file
encryptedMessage = open('encryptedMessage.txt').read().rstrip()

# Scans the encrypted message, generating a list of digraphs.
# The list of digraphs contains ['gy', 'yo', 'om', 'mx', ' xn'...]
messageDigraphs = re.findall(r'(?=(..))', encryptedMessage)

# Create a map/dictionary of the frequencies of each message digraph
# The Counter class counts the number of occurrences of each item in
# a list and creates a map from it.
digraphFrequencyMap = collections.Counter(messageDigraphs)
print digraphFrequencyMap