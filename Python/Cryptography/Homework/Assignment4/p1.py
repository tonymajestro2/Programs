import string

da_map = dict()
eb_map = dict()
fc_map = dict()

# For each message, map the 1st letter to the 4th, the 2nd
# letter to the 5th, and the 3rd letter to the 6th.
for message in open('in.txt').readlines():
    da_map[message[0]] = message[3]
    eb_map[message[1]] = message[4]
    fc_map[message[2]] = message[5]

def printCycles(cycleMap):
    # Keep track of current key, used keys, and current cycle
    remainingKeys = list(string.uppercase)
    key = remainingKeys[0]
    cycle = []
    
    # Loop until all keys in the map have been used
    while len(cycleMap.keys()) > 0:
            
        # If the map contains the current key, add it to the current cycle.
        # The key's value becomes the new key, and the old key is removed
        # from the remainingKeys list and the cycle map
        if (cycleMap.has_key(key)):
            cycle.append(key)
            remainingKeys.remove(key)
            newKey = cycleMap[key]
            del cycleMap[key]
            key = newKey
                
        # If the map does not contain the key, then it has been already
        # processed. This means the cycle has been completed and it can be 
        # printed.  The cycle is cleared and the key is set to the first
        # available key.
        else:
            # Check for cycle errors as mentioned in problem 2
            if (key != cycle[0]):
                raise Exception('Invalid cycle')
            print '(' + ''.join(cycle) + ')',
            key = remainingKeys[0]
            cycle = []
   
    # print final cycle
    print '(' + ''.join(cycle) + ')'
                
# Print cycles for each decomposition
printCycles(da_map)
printCycles(eb_map)
printCycles(fc_map)
        
