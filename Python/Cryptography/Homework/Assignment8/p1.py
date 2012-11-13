
def hamming74(code):
    # isolate bits  for alpha, beta, and gamma, convert to binary string,
    # and count number of 1 bits mod 2 to get parity
    alpha = '{0:b}'.format(code & 0b0001111).count('1') % 2  # bits 4, 5, 6, 7
    beta  = '{0:b}'.format(code & 0b0110011).count('1') % 2  # bits 2, 3, 6, 7
    gamma = '{0:b}'.format(code & 0b1010101).count('1') % 2  # bits 1, 3, 5, 7
    
    # concatentate alpha, beta, and gamma to get error bit
    error_position = int('{0}{1}{2}'.format(alpha, beta, gamma), 2)
    return error_position

def correctMessage(code, error_position):
    # If error_position bit is 0, no correction needs to be made
    if (error_position == 0):
        return code
    # If error_position bit is non-zero, flip the error_position bit
    else:
        return code ^ 1 << (error_position - 1)



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
    
    
    
    
   
     
    