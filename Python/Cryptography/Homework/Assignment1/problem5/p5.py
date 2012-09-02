import frequency

def solve(word, mg, fmg):
    if (len(mg) == 0 or len(fmg) == 0):
        #print word
        return
        
    a = mg[0]
    b = fmg[0]
    print ("{0}, {1}".format(a,b))
    
    newWord = word.replace(a, b.upper())
    solve(newWord, mg[1:], fmg[1:])
    solve(word, mg, fmg[1:] + [fmg[0]])
    
    




                
if (__name__ == "__main__"):
    
    word = open('word.txt').read().rstrip()
    monographs = frequency.getMonograms(word)[:2]
    frequentMonographs = frequency.monograms[:2]
    solve(word, monographs, frequentMonographs)
    