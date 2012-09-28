import re
import collections
import string

d = dict([(v,k) for k,v in enumerate(string.uppercase)])


def gcd(a, b):
    a,b = max(a, b), min(a,b)
    if (b == 0): return a
    return gcd(b, a % b)

# Decrypt a shift cipher given the shift amount
def decrypt(ciphertext, shift):
    # Get list of all uppercase letters in string library
    letters = string.uppercase;
    plaintext = []
    for c in ciphertext:
        # convert letter to number and decrypt
        encryptedNum = letters.find(c)               
        decryptedNum = (encryptedNum - shift) % 26
        plaintext.append(letters[decryptedNum])   
    return ''.join(plaintext) 


# Return the transposed decrypted columns to decrypt the Vigenere cipher 
def decryptVigenere(ciphertextColumns):
    # Generate list of tuples consisting of (ciphertext column, shift amount)
    shifts = [1, 3, 5, 7, 9]
    colsAndShifts = zip(ciphertextColumns, shifts)
    
    # Decrypt each column using each shift
    # Return the transposed columns to get readable plaintext
    decryptedCols = [decrypt(col, shift) for col, shift in colsAndShifts]
    return ''.join(transpose(decryptedCols))
    
    



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


# Gets the trigraphs that appear more than one time in the ciphertext
def getTrigraphs(ciphertext):
    # Find all 3 letter trigraphs
    trigraphs = collections.Counter(re.findall(r'(?=(...))', ciphertext))
    
    # Returns the trigraphs that appeared more than once in the ciphertext
    return [trigraph for trigraph, count in trigraphs.items() if count > 1]
    


# Returns a list of the starting index of each trigraph in the ciphertext
def getTrigraphIndexes(ciphertext, trigraph):
    return [match.start() for match in re.finditer(trigraph, ciphertext)]


# Given w, the length of the key, return w columns of text
def getColumns(ciphertext, w):
    # Separate the ciphertext into sections of length 5 and store in a list
    numRows = len(ciphertext) / w
    rows = [ciphertext[i*w : i*w + w] for i in range(numRows)]
    
    # Transpose the rows to get the columns
    return transpose(rows)
    
    
# Return the letter frequencies for each column
def getLetterFrequencies(columns):
    # For each column, return a map of (letter : frequency) 
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

      

def shift(str):
    a = str[0]
    b = str[1]
    return (d[b] - d[a]) % 26

# Transposes the rows and columns of a matrix
def transpose(matrix):
    # Get a list of the ith letter of each row and add it to the column list
    columns, rowLength = [], len(matrix[0])
    for i in range(rowLength):
        columns.append(''.join([row[i] for row in matrix]))    
    return columns
        

if (__name__ == '__main__'):
    
    # Read ciphertext from file and print repeating trigraphs
    ciphertext = open('ciphertext.txt').read().rstrip()
    
    columns = getColumns(ciphertext, 5)
    print decryptVigenere(columns)
    
    
    #columns = getColumns(ciphertext, w)
    #shifts = [1,3,5,7,9]
    
    #plains = []
    #for column,shift in zip(columns, shifts):
     #   plains.append(decrypt(column, shift))
        
    #print ''.join(transpose(plains))
        





