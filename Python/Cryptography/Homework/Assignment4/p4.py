import itertools

def derangements(n):
    count = 0
    for perm in itertools.permutations(range(n)):
        foundDerangement = True
        for i, value in enumerate(perm):
            if (i == value):
                foundDerangement = False
                break
        if (foundDerangement):
            count += 1
    return count
            
print derangements(4)