import sys, string
from collections import defaultdict

def NextLine(): return sys.stdin.readline().rstrip()

class State:
    def __init__(self):
        self.transitions = defaultdict(set)

    def addTransition(self, symbol, destState):
        self.transitions[symbol].add(destState)

pattern = NextLine()
firststate = laststate = State()

for p in pattern:
    if p == '*':
        # wildcard - add self transitions for all 
        # input characters and .
        for l in string.lowercase + '.':
            laststate.addTransition(l, laststate)
    else:
        # add transition to next state
        nextstate = State()
        laststate.addTransition(p, nextstate)
        laststate = nextstate

# lastState is now goal state for a full match

N = int(NextLine())
for i in range(N):
    fname = NextLine()
    # simulate NFA
    activestates = set([firststate])
    for f in fname:
        activestates = set(d for s in activestates \
                                 for d in s.transitions[f])

    if laststate in activestates:
        print fname

