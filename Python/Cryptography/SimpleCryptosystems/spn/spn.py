
def encrypt(pt, key, sbox):
    printBinary('x', pt)
    #print 'x: {0:016b}'.format(pt)
    
    k1 = (key & 0xFFFF0000) >> 16
    printBinary('k', k1)
    
    u = pt ^ k1
    printBinary('u', u)
   
    mask = 0xF000
    ct = 0
    for i in range(1, 4):
        v = sbox[(u & mask) >> ((4 - i) * 4)]
        printBinary('v', v)
        
        w = (ct << 4) | v
        w = perm(ct)
        printBinary('w', w)
        
        u ^= (key & (0xFFFF << i * 4)) >> 16
        printBinary('u', u)

def perm(ct):
    ctStr = '{0:016b}'.format(ct)
    matrix = [ctStr[i*4:i*4+4] for i in range(4)]
    transposed = [''.join([row[i] for row in matrix]) for i in range(4)]
    return int(''.join(transposed), 2)

def printBinary(name, value):
    binaryStr = '{0:016b}'.format(value)
    chunks = [''.join(binaryStr[i*4:i*4+4]) for i in range(4)]
    print '{0}: {1}'.format(name, ' '.join(chunks))
 
pt = 0b0010011010110111
key = 0b00111010100101001101011000111111
sbox = {0:14, 1:4, 2:13, 3:1, 4:2, 5:15, 6:11, 7:8, 8:3, 9:10,
        10:6, 11:12, 12:5, 13:9, 14:0, 15:7}
encrypt(pt, key, sbox)
