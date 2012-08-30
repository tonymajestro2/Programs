# common monograms, digrams, and trigrams

import collections
import re

monograms = [('a', 0.082), 
             ('b', 0.015),
             ('c', 0.028),
             ('d', 0.043),
             ('e', 0.127),
             ('f', 0.022),
             ('g', 0.020),
             ('h', 0.061),
             ('i', 0.07),
             ('j', 0.002),
             ('k', 0.008),
             ('l', 0.04),
             ('m', 0.024),
             ('n', 0.067),
             ('o', 0.075),
             ('p', 0.019),
             ('q', 0.001),
             ('r', 0.06),
             ('s', 0.063),
             ('t', 0.091),
             ('u', 0.028),
             ('v', 0.010),
             ('w', 0.023),
             ('x', 0.001),
             ('y', 0.02),
             ('z', 0.001)]

monograms = [(x[1], x[0]) for x in monograms]
monograms.sort(reverse = True)
monograms = [x[1] for x in monograms]





digrams = ['th', 'he', 'in', 'er', 're', 'on', 'an',
           'en', 'at', 'es', 'ed', 'te', 'ti', 'or',
           'st', 'ar', 'nd', 'to', 'nt', 'is', 'of',
           'it', 'al', 'as', 'ha', 'ng', 'co', 'se',
           'me', 'de']

trigrams = ['the', 'and', 'tio', 'ati', 'for', 'tha',
            'ter', 'res', 'ere', 'con', 'ted', 'com']


commonWords = ['the', 'and', 'that', 'have', 'for', 'not', 'with', 'you', 'this', 'but', 'his']




def convertDictToSortedList(d):
    swapped = [(count, letter) for letter, count in d.iteritems()]
    swapped.sort(reverse=True)
    return [x[1] for x in swapped]


def getMonograms(s):
    '''Get monograms ordered by frequency'''
    monogramDict = collections.Counter(s)
    return convertDictToSortedList(monogramDict)
    
def getDigrams(s):
    '''Get digrams ordered by frequency'''
    digramDict = collections.Counter(re.findall(r'(?=(..))', s))
    return convertDictToSortedList(digramDict)


def getTrigrams(s):
    '''Get trigrams ordered by frequency'''
    trigramDict = collections.Counter(re.findall(r'(?=(...))', s))
    return convertDictToSortedList(trigramDict)



if __name__ == '__main__':
    print getTrigrams('abbccc')





