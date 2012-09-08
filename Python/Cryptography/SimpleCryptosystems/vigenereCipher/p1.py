import re
import collections
import vigenere

def gcd(a,b):
    a,b = max(a,b), min(a,b)
    if (b == 0): return a
    return gcd(b, a % b)

def kasiski(ciphertext):
    for i in range(3, 6):
        distSet = set()
        nGramMap = collections.Counter(re.findall(r'(?=({0}))'.format('.' * i), ciphertext))
        nGrams = [tri for tri, count in nGramMap.iteritems() if count > 1]
        for trigram in nGrams:
            indexes = [matca,b = max(a, b), min(a,b)
    if (b == 0): return a
   h.start() for match in re.finditer(trigram, ciphertext)]
            for i in range(len(indexes)-1):
                distSet.add(indexes[i+1] - indexes[i])
        
        distCounts = collections.Counter([gcd(a,b) for a in distSet for b in distSet])
    return max(distCounts.iteritems(), key=lambda x: x[1]) 
            
            

if (__name__ == '__main__'):
    ciphertext = open('message').read().rstrip()
    print kasiski(ciphertext)