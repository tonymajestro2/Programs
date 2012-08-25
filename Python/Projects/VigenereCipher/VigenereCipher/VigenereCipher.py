import string
import re

def encrypt(text, cipher, encrypt=False):
    up = string.ascii_uppercase
    decode = 1 if encrypt else -1
    cipher = (cipher * (len(text) / len(cipher) + 1))[:len(text)]
    return [up[up.find(x[0]) + (decode * up.find(x[1]))] for x in zip(text, cipher)]



if (__name__ == "__main__"):
    x = encrypt('ABCDE', 'FDEA', True)
    y = encrypt(x, 'FDEA', False)
    z = 3