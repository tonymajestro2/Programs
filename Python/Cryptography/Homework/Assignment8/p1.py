
def hamming74(code):
    # isolate bits and count number of 1 bits mod 2 to get
    # values for alpha, beta, and gamma
    alpha = '{0:b}'.format(code & 0b0001111).count('1') % 2
    beta  = '{0:b}'.format(code & 0b0110011).count('1') % 2 
    gamma = '{0:b}'.format(code & 0b1010101).count('1') % 2
    
    # concatentate alpha, beta, and gamma to get error bit
    error_bit = int('{0}{1}{2}'.format(alpha, beta, gamma), 2)
    return error_bit

def correctMessage(code, error):
    if (error > 0):
        return code ^ 1 << (error - 1)



codes = []
corrected = []
for code in open('in.txt').read().split():
    error = hamming74(int(code, 2))
    print code, error
    if (error > 0):
        code = list(code)
        code[error-1] = '1' if code[error-1] == '0' else '0'
    print ''.join(code) + '\n'
    corrected.append(''.join(code))
    
print    


    
for corr in corrected:
    messages = [corr[2], corr[4], corr[5], corr[6]]
    print ''.join(messages), int(''.join(messages), 2)
    
    
    
    
   
     
    