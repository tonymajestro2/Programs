
def generateKeyStream(initVector, recurrence):
    registers = list(initVector)
    while (True):
        registers.append(recurrence(registers))
        yield registers.pop(0)
    

# seed - the initial state for the LFSR
def getKeyPeriod(seed):
    # model registers as a list of numbers initialized to the seed
    registers = list(seed)
    period = 0
    # loop until the register state is back to the original seed
    while (period == 0) or (registers != seed):
        # add the new value to the end of the register list and
        # remove the first register value
        registers.append(registers[0] ^ registers[1])
        registers.pop(0)
        period += 1
    return period


# recurrence relation for problem 1 is to add the first two values
# mod 2.  This is effectively performing an XOR of of the values        
def recurrence(key):
    return key[0] ^ key[1]

    
# Returns all the permutations up to but not including num as a 
# list of binary values.
# Each binary value is represented as a list of integers, 0 or 1
def getPermutations(num):
    perms = []
    for i in range(num):
        # get ith permutation as a binary string padded with 5 zeroes
        # and convert to a list of integers
        permStr = '{0:05b}'.format(i)
        perm = [int(x) for x in permStr]
        perms.append(perm)
        
    return perms



    
if __name__ == '__main__':
    
    # print all 32 seeds and their periods for the LFSR
    for seed in getPermutations(32):
        period = getKeyPeriod(seed)
        print '{0:2s}\t{1:3d}'.format(''.join([str(x) for x in seed]), period)
    
    
    
