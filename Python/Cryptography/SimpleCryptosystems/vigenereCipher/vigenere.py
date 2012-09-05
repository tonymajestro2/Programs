import collections
import string
import re
import frequency

d = dict([(v,k) for k,v in enumerate(string.uppercase)])

def gcd(a, b):
        a,b = max(a, b), min(a,b)
        if (b == 0): return a
        return gcd(b, a % b)


def kasiski(cipherText):
    
    distances = []
    for i in range(3, 6):
        regex = r'(?=(' + '.' * i + '))'
        
        igraphs = collections.Counter(re.findall(regex, cipherText))
        for graph in sorted([(v,k) for k,v in igraphs.iteritems()], reverse=True):
            frequency = graph[0]
            if (frequency >= 2):
                m = cipherText
                graph = graph[1]
                prevIndex = 0
                for j in range(frequency):
                    index = m.find(graph, prevIndex)
                    if (j > 0):
                        distances.append(index - prevIndex + 1)
                    prevIndex = index+1

    distanceCounts = collections.Counter([gcd(a,b) for a in distances for b in distances])
    return max(distanceCounts.iteritems(), key=lambda x: x[1])[0]

def getColumns(word, w = 0):
    if (w == 0):
        w = kasiski(word)
    return [word[i*w:(i*w) + w] for i in range(len(word)/w + 1)]

def getColumnsTransposed(word, w = 0):
    if (w == 0):
        w = kasiski(word)
    columns = getColumns(word, w)
    transposed = [''.join([row[i] for row in columns if i < len(row)])
                   for i in range(len(columns))]
    return [(x, len(x)) for x in transposed if len(x) > 0]

def getColumnMonographs(word, w = 0):
    if (w == 0):
        w = kasiski(word)
    transposed = getColumnsTransposed(word, w)
    return [(collections.Counter(x[0]), x[1]) for x in transposed]

def getColumnDigraphs(word, w = 0):
    if (w == 0):
        w = kasiski(word)
    transposed = getColumnsTransposed(word, w)
    return [(collections.Counter(re.findall(r'(?=(..))', x[0])), x[1]) for x in transposed]
    

def tryShift(word):
    w = kasiski(word)
    monographs = getColumnMonographs(word, w)[0]
    sums = []
    length = monographs[1]
    frequencies = monographs[0]
    for i in range(26):
        total = 0
        for j in range(26):
            pi = frequency.monograms[j][1]
            letter = string.uppercase[j]
            if (frequencies.has_key(letter)):
                fi = frequencies[letter]
            else:
                fi = 0
            
            total += (fi * pi + i) / (float(length))
            
        sums.append(total)
        
    return sums
            
            
def transpose(matrix):
    return [''.join([row[i] for row in matrix if i < len(row)])
            for i in range(len(matrix[0]))]
    

def decrypt(cipherText, shift):
    return ''.join([string.lowercase[(d[l] - shift) % 26]
                    for l in cipherText])
    
def getPossiblePlaintexts(columns, listShifts):
    decColumns = []
    for i,shift in enumerate(listShifts):
        decColumns.append(decrypt(columns[i], shift))
    return ''.join(transpose(decColumns))
         


    
    
    
def getFrequencyDistributions(word, w = 0):
    if (w == 0):
        w = kasiski(word)
    monographs = getColumnMonographs(word, w)
    dists = []
    for m in monographs:
        total = 0
        frequencies = m[0]
        length = m[1]
        for i in range(26):
            letter = string.uppercase[i]
            if (frequencies.has_key(letter)):
                freq = frequencies[letter]
            else:
                freq = 0
            
            total += ((freq) * (freq-1.0)) / (length * (length-1))
                
        dists.append(total)
    return dists
            
        
    

        
if __name__ == '__main__':
    message = re.sub(r'\W', '', open('message').read())
    w = kasiski(message)
    
    columns = getColumnsTransposed(message, w)
    columns = [x[0] for x in columns]
    maxes = [7, 8, 18, 19, 14, 17, 24]
    print getPossiblePlaintexts(columns, maxes)
    #print transpose(['abc', 'def', 'ghi', 'jkl'])
    
    
    
    
    
    
    
