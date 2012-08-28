import math
import sys
import random


def solve():
    f = sys.stdin
    while True:
        n = int(f.readline())
        if (n == 0):
            return
        center = tuple(map(float, f.readline().split()))
        towers = [tuple(map(float, f.readline().split())) for i in range(n-1)]
        getCoverage(center, towers)


def getCoverage(center, towers):
    count = total = 0
    
    for i in range(100000):
        x,y,r = center
        rx = (random.random() * r * 2) - r + x
        ry = (random.random() * r * 2) - r + y
        
        # Check that point is actually inside the town circle
        if not isInCircle(x, y, r, rx, ry):
            continue
        
        for tower in towers:
            x,y,r = tower
            if isInCircle(x, y, r, rx, ry):
                count += 1
                break
        total += 1

    print "{0:.2f}".format(float(count) / total)

        
def isInCircle(x1, y1, r, x2, y2):
    return math.sqrt(((x1 - x2) ** 2) + ((y1 - y2) ** 2)) <= r


if __name__ == '__main__':
    solve()