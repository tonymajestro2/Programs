
# Decrypt the cipher text using the given key and
# sbox mapping
def decrypt(ct, key, sboxMap):
    printBinary('ct  ', ct)
    
    # The first round key for decryption is the last 4
    # hex values of the key
    k1 = key & 0x0000ffff
    printBinary('key1', k1)
    
    # XOR ciphertext with round key 1
    u = ct ^ k1
    printBinary('xor ', u)
    
    # sbox before the first round begins
    v = sbox(u, sboxMap)
    printBinary('sbox', v)
    
    # Begin round loop.  Each round for decryption consists
    # of first doing an XOR with the round key, the permutation
    # step, and finally performing the sbox step
    for i in range(3):
        print '\nRound {0}:\n-------------------------'.format(i + 1)
        
        # Shift key by 4 and grab the first 4 hex values
        # to get the next round key
        key >>= 4
        ki = key & 0x0000ffff
        printBinary('key' + str(i+1), ki)
        
        # Next, perform xor
        u = v ^ ki
        printBinary('xor ', u)
        
        # Next, perform permutation
        w = perm(u)
        printBinary('perm', w)
        
        # Final step of the round is to perform sbox
        v = sbox(w, sboxMap)
        printBinary('sbox', v)   

    print '\nRound 4:\n-------------------------'
    
    # The last round consists of only an xor
    key >>= 4
    k4 = key & 0x0000ffff
    printBinary('key4', k4)
    
    # xor with round key 5
    u = v ^ k4
    printBinary('xor ', u)
    
    return u
    
    
# Sbox stage: for each 4 hexadecimal values in the 16 bit
# value, replace it with the mapping provided in the sbox map
def sbox(value, sboxMap):
    v = 0
    for i in range(4):
        # Get the ith hex value and look up its sbox mapping
        temp = sboxMap[value & 0xf]
        
        # Set the value temp in v
        v ^= temp << (i * 4)
        
        # Shift value to prepare for the next hex value
        value >>= 4
    return v


# Permutation stage.  Convert the number to 16 bit string and
# convert that to a 4x4 binary matrix.  Then, transpose and flatten
# the matrix to get the final value
def perm(x):
    # Convert to binary string
    xStr = '{0:016b}'.format(x)
    
    # Get 4x4 binary matrix
    matrix = [xStr[i*4:i*4+4] for i in range(4)]
    
    # Transpose binary matrix
    transposed = [''.join([row[i] for row in matrix]) for i in range(4)]
    
    # Return flattened transposed matrix
    return int(''.join(transposed), 2)


# Print a number as a 16 bit binary value in 4 bit chunks
def printBinary(name, value):
    binaryStr = '{0:016b}'.format(value)
    chunks = [''.join(binaryStr[i*4:i*4+4]) for i in range(4)]
    print '{0}: {1}'.format(name, ' '.join(chunks))



key = 0x3A94D63F
ct = 0xCD48
sboxMap = {14:0, 4:1, 13:2, 1:3, 2:4, 15:5, 11:6, 8:7, 3:8, 10:9,
            6:10, 12:11, 5:12, 9:13, 0:14, 7:15}
   
# Decrypt the ciphertext using the key and sbox mapping
decrypt(ct, key, sboxMap)