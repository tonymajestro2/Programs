
import string

d = dict([(letter, pos) for pos,letter in enumerate(string.lowercase)])

def encrypt(p, a, b):
    numbers = [((a * d[x]) + b) % 26 for x in p]
    return ''.join([string.lowercase[x] for x in numbers])


def decrypt(c, a, b):
    inverseA = getInverse(a, 26)
    numbers = [((d[x] - b) * inverseA) % 26 for x in c]
    return ''.join([string.lowercase[x] for x in numbers])


def getInverse(a, m):
    for i in range(0, m):
        if (a * i) % m == 1:
          return i
    
    return None

def getCoprimes(n):
    def gcd(a, b):
       a,b = max(a,b), min(a,b)
       if (b == 0):
          return a
       return gcd(b, a % b)

    return [x for x in range(0, n) if gcd(x, n) == 1]

def enumeratePlaintexts(c, m):
    plaintexts = []
    for a in getCoprimes(m):
        for b in range(0, m):
            plaintexts.append(decrypt(c, a, b))
    return plaintexts
