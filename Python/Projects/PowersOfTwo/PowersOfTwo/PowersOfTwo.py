import math
import collections
import sys
import itertools

cache1 = {}
cache2 = {}

def solve():
    for i in sys.stdin:
        cache1.clear()
        cache2.clear()
        num = int(i)
        simpleFactors = getSimpleFactors(num)
        sums = getSumRepresentations(simpleFactors, 0)
        
        # get rid of duplicates and sort list
        sumList = prepareSums(sums)
        for s in sumList:
            print '{0} = {1}'.format(' + '.join([str(x) for x in s]), str(num))
        print
    

def getSimpleFactors(n):
    factors = []
    while n >= 1:
        factor = 2 ** int(math.log(n) / math.log(2))
        factors.append(factor)
        n -= factor

    return factors


def getSumRepresentations(factors, i):
    if (i == len(factors) - 1):
        return getRepresentations(factors[i])

    representations = []
    currFactor = factors[i]
    for j, currRep in enumerate(getRepresentations(currFactor)):
        if (cache1.has_key((i, j))):
            return cache1[i, j]
        
        nextRepList = getSumRepresentations(factors, i + 1)
        currRepList = []
        for nextRep in nextRepList:
            if (canAddRepresentation(currRep, nextRep)):
                newList = currRep + nextRep
                currRepList.append(newList)

        cache1[(i, j)] = currRepList
        for rep in currRepList:
            representations.append(rep)
        
    return representations


def canAddRepresentation(rep1, rep2):
    rep1Dictionary = collections.Counter(rep1)
    rep2Dictionary = collections.Counter(rep2)

    for num,count in rep1Dictionary.iteritems():
        if (count > 2):
            return false

        if ((rep2Dictionary.has_key(num)) and (rep2Dictionary[num] + count > 2)):
            return False

    return True


def getRepresentations(n):
    if (cache2.has_key(n)):
        return cache2[n]

    if (n == 1):
        return [[1]]

    halfNRepresentations = getRepresentations(n / 2)
    representations = [[n]]
    for halfNRep in halfNRepresentations:
        newRep = [n/2] + halfNRep
        representations.append(newRep)

    cache2[n] = representations
    return representations


def prepareSums(sumsList):
    # sort each sum representation and convert to tuple
    sumsList = [tuple(sorted(ls)) for ls in sumsList]

    # filter out duplicates and sort list of sums
    filtered = sorted(set(sumsList))
    return filtered


if (__name__ == '__main__'):
    solve()
