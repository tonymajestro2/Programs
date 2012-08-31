
import string
import frequency
import p2

word = p2.word
d = dict([(letter, pos) for pos,letter in enumerate(string.lowercase)])

def encrypt(p, a1, b1):
    numbers = [((a1 * d[x1]) + b1) % 26 for x1 in p]
    return ''.join([string.lowercase[x1] for x1 in numbers])


def decrypt(c, a1, b1):
    inverseA = getInverse(a1, 26)
    numbers = [((d[x1] - b1) * inverseA) % 26 for x1 in c]
    return ''.join([string.lowercase[x1] for x1 in numbers])


def getInverse(a1, m):
    for i in range(0, m):
        if (a1 * i) % m == 1:
            return i
    return None

def getCoprimes(n):
    def gcd(a1, b1):
        a1,b1 = max(a1,b1), min(a1,b1)
        if (b1 == 0):
            return a1
        return gcd(b1, a1 % b1)

    return [num for num in range(0, n) if gcd(num, n) == 1]

def convertLetterToNumber(l):
    return string.lowercase.find(l)


def enumeratePlaintexts(c, m):
    p = []
    for a1 in getCoprimes(m):
        for b1 in range(0, m):
            p.append(decrypt(c, a1, b1))
    return p


def getA(a1, r1, a2, r2, m):
    aSub = (a1 - a2) % m
    rSub = (r1 - r2) % m
    aInverse = getInverse(aSub, m)
    if (aInverse == None):
        return None
    return (aInverse * rSub) % m

def getB(c1, a1, r, m):
    return (r - (a1 * c1)) % m



if (__name__ == '__main__'):
    word = open('word.txt').read().rstrip()
    wordDigrams = frequency.getDigrams(word)
    commonDigrams = frequency.digrams
    
    s = set()
    decryptedTexts = []
    for i, wordDigram in enumerate(wordDigrams):
        for j, digram in enumerate(commonDigrams):
            wordNumbers = [convertLetterToNumber(x) for x in wordDigram]
            digramNumbers = [convertLetterToNumber(x) for x in digram]
            a = getA(digramNumbers[0], wordNumbers[0], 
                     digramNumbers[1], wordNumbers[1], 26)
            
            if (a == None):
                continue
            if (a not in getCoprimes(26)):
                continue
            
            b = getB(digramNumbers[0], a, wordNumbers[0], 26)
            if ((a,b) not in s):
                decryptedTexts.append("{3}, ({0},{1}): {2}".format(a, b, decrypt(word, a, b), digram + " " + wordDigram))
                s.add((a,b))
                
        
                
    for plaintext in decryptedTexts:
        count = 0
        for commonWord in frequency.commonWords:
            if (plaintext.find(commonWord) >= 0):
                count += 1 
                if (count > 2):
                    print plaintext
                    break
                
