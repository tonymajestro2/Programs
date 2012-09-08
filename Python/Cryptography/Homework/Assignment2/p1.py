import re
import collections
import string

d = dict([(v,k) for k,v in enumerate(string.uppercase)])


def gcd(a, b):
    a,b = max(a, b), min(a,b)
    if (b == 0): return a
    return gcd(b, a % b)


def kasiski(cipherText, rangeN = range(3,6)):
    distances = []
    # Enumerate through all the 3-graphs, 4-graphs, and 5-graphs in the ciphertext       
    for i in rangeN:
        regex = r'(?=({0}))'.format('.' * i)

        # Map each n-graph to its frequency in the ciphertext
        nGraphsMap = collections.Counter(re.findall(regex, cipherText))
        
        # Get all n-graphs that occurred twice or more 
        for nGraph in nGraphsMap.iteritems():
            if (nGraph[1] < 2):  
                continue
            
            # Find all indexes of the n-graph in the ciphertext
            regex = '(?=({0}))'.format(nGraph[0])
            indexes = [x.start() for x in re.finditer(regex, cipherText)]
            
            # Add the distances between each index to distance list
            for i in range(len(indexes)-1):
                distances.append(indexes[i+1] - indexes[i])
    
    # Calculate gcd for each pair of distances, find most frequent gcd
    # This most frequently occurring gcd is hopefully the key length
    gcdCounts = collections.Counter([gcd(a,b) for a in distances for b in distances])
    return max(gcdCounts.iteritems(), key=lambda x: x[1])[0]


def getColumns(ciphertext, w):
    columns = []
    for i in range(w):
        # For each i, get the string of letters containing ciphertext[i],
        # ciphertext[i+w], ciphertext[i+2w]... to generate list of column strings
        columns.append(''.join([ciphertext[j] for j in range(i, len(ciphertext), w)]))
    return columns
    

def getLetterFrequencies(columns):
    # For each column, get map of (letter, frequency) 
    return [collections.Counter(column) for column in columns]
        
        

def indexOfCoincidence(letterFrequencyMap, alphabet, n):
    total = 0
    for letter in alphabet:
        if (letterFrequencyMap.has_key(letter)):
            letterFrequency = letterFrequencyMap[letter]
        else:
            letterFrequency = 0   
        
        total += (letterFrequency * (letterFrequency - 1.0)) / (n * (n - 1.0))
    return total

      
def decrypt(word, shift):
    letters = []
    for letter in word:
        letters.append(string.uppercase[(d[letter] - shift) % 26])
    return ''.join(letters)
      

def shift(str):
    a = str[0]
    b = str[1]
    return (d[b] - d[a]) % 26

def transpose(matrix):
    x = []
    for i in range(len(matrix[0])):
        x.append(''.join([row[i] for row in matrix]))    
    return x
        

if (__name__ == '__main__'):
    ciphertext = open('ciphertext.txt').read().rstrip()
    w = kasiski(ciphertext)
    columns = getColumns(ciphertext, w)
    shifts = [1,3,5,7,9]
    
    plains = []
    for column,shift in zip(columns, shifts):
        plains.append(decrypt(column, shift))
        
    print ''.join(transpose(plains))
        





