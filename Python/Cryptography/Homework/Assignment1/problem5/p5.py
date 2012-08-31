import frequency





def solve(word, wordMonograms, popMonograms, i, j):
    if len(wordMonograms) == 0:
        print word
        return
    if len(popMonograms) == 0:
        print word
        return
    
    newWord = word.replace(wordMonograms[i], popMonograms[j].upper())
    solve(newWord, wordMonograms[1:], popMonograms[1:])
    solve(newWord, wordMonograms, popMonograms[1:])
    solve(newWord, wordMonograms[1:], popMonograms)
    


                
if (__name__ == "__main__"):
    #word = open('word.txt').read().rstrip()
    word = 'aaaabbbbbbcc'
    monograms = frequency.monograms[:2]
    wordMonograms = frequency.getMonograms(word)[:2]
    solve(word, wordMonograms, monograms, 0, 0)
            
            
        

    
