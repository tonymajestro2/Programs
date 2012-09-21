import itertools


def applyFunction(fun, key):
    return [fun[x] for x in key]
a = 'AE9B5417C362D8F0'

key = [x for x in 'ABCDEFG']
ans = [x for x in 'ABCDEFG']


for i, originalPerm in enumerate(itertools.permutations(key)):
    count = 1
    fun = dict([(x,y) for x,y in zip(key, originalPerm)])
    perm = applyFunction(fun, ans)
    while (perm != ans) and (count <= 50):
        count += 1
        perm = applyFunction(fun, perm)
    print originalPerm, count
    if (count > 50):
        print applyFunction(fun, ans)
        break
print count
    


