import collections
import math

# Get guess counts
guessCounts = []
for line in open('frequencies.txt'):
    guessCounts.extend([int(x) for x in line.split()])
   
# Get guess frequencies
guessFrequencies = collections.Counter(guessCounts)

# Calculate entropy for guesses based on P(x) = frequency / entropy guesses
entropy = 0
for num, frequency in guessFrequencies.iteritems():
    px = float(frequency) / len(guessCounts)
    entropy = entropy + (math.log(px, 2) * px)

print entropy * -1
