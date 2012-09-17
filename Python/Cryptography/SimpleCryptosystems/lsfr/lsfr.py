
def generateKey(initVector, relation):
    key = list(initVector)
    while (True):
        key.append(relation(key))
        yield key.pop(0)
          

    

def relation(key):
    return key[0] ^ key[1]


if __name__ == '__main__':
     
    sequence = []
    for i, bit in enumerate(generateKey([1,0,0,1,1], relation)):
        sequence.append(bit)
        if (sequence[:(i+1)/2] == sequence[(i+1)/2:]):
            break
        
    print len(sequence)/2
    print sequence[:len(sequence)/2]


