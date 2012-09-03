import frequency


def sub(d, a):
    if (d.has_key(a)):
        return d[a].upper()
    return a


def tryTrigraph(w, a, b):
    d = {}
    for i,c in enumerate(a):
        d[c] = b[i]
        
    if len(d) != len(a):
        return ''
    
    return a


if (__name__ == "__main__"):
    word = open('word.txt').read().rstrip()
    
    for c in frequency.getTrigrams(word):
        s = tryTrigraph(word, c, 'the')
        if len(s) > 0 and (s[2] == 'z'):
            print s