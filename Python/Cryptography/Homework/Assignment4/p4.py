

# Library containing function for generating permutations
import itertools  

# Calculates the number of derangements for a set of size n
def derangements(n):
    count = 0
    
    # Generate all permutations for the list [0, ..., n-1]
    for perm in itertools.permutations(range(n)):
        
        # Look through each value in the permutation
        foundDerangement = True
        for i, value in enumerate(perm):    
            # If the value is equal to its position, it is not
            # a derangement
            if (i == value):
                foundDerangement = False
                break
            
        # Increment derangement count if none of the values in
        # the permutation was equal to its position in the permuation
        if (foundDerangement):
            count += 1
            
    return count
            
# Print number of derangements for n = 1, 2, 3, 4, and 5
for n in range(1,6):
    numDerangements = derangements(n)
    print 'Num derangements for n = {0}: {1}'.format(n, numDerangements)