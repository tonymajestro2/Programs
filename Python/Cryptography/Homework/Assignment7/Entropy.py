import collections
import math

def log2(num):
    return math.log(num) / math.log(2)

frequencies = []
for line in open('in.txt'):
    frequencies.extend([int(x) for x in line.split()])
   
total = 0 
for num, frequency in collections.Counter(frequencies).items():
    px = float(frequency) / len(frequencies)
    total = total - (log2(px) * px)
    
f = collections.Counter(frequencies).items()
for k in f:
    print k

    
    