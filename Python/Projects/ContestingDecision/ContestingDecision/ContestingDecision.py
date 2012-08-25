
import sys

def solve():
    f = open('in.txt')
    numTeams = int(f.readline())
    maxCorrect = -1
    maxName = ''
    minPenalty = 721 
    for i in range(numTeams):
        splitLine = f.readline().rstrip().split(' ')
        name = splitLine[0]
        numCorrect = 0
        penalty = 0

        for i in range(1, 9, 2):
            minutes = int(splitLine[i + 1])
            if (minutes != 0):
                numCorrect += 1
                penalty += (int(splitLine[i]) - 1) * 20 + minutes

        if (numCorrect > maxCorrect):
            maxName = name
            maxCorrect = numCorrect
            minPenalty = penalty

        elif (numCorrect == maxCorrect):
            if (penalty < minPenalty):
                maxName = name
                maxCorrect = numCorrect
                minPenalty = penalty
    
    print " ".join([maxName, str(maxCorrect), str(minPenalty)])
    raw_input()
        
if (__name__ == "__main__"):
    solve()