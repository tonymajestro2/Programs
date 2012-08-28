
import string


letterMap = dict([(l, i) for i,l in enumerate(string.lowercase)])


def encrypt(p, k):
    '''Encrypt plaintext p using shift key k'''
    return ''.join(string.lowercase[(letterMap[l] + k) % 26] for l in p)


def decrypt(c, k):
    '''Decrypt ciphertext c using shift key k'''
    return encrypt(c, -k)


def enumeratePlaintexts(c):
    '''Enumerate over all the possible plaintexts for a ciphertext c'''
    return [decrypt(c, i) for i in range(26)] 
