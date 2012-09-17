
def generateKeyStream(initVector, recurrence):
    registers = list(initVector)
    while (True):
        registers.append(recurrence(registers))
        yield registers.pop(0)
    

def getKeyPeriod(initVector, recurrence):
    registers = list(initVector)
    period = 0
    while (period == 0) or (registers != initVector):
        registers.append(recurrence(registers))
        registers.pop(0)
        period += 1
    return period





        
def recurrence(key):
    return key[0] ^ key[1]

    

def getPermutations(num):
    permStrings = ['{0:05b}'.format(i) for i in range(num)]
    perms = []
    for perm in permStrings:
        perms.append([int(c) for c in perm])
    return perms



    
if __name__ == '__main__':
    for perm in getPermutations(32):
        print perm, getKeyPeriod(perm, recurrence)
    
    
    
