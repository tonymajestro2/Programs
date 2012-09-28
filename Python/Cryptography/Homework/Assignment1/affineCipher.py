
import string
import frequency

word = open('encryptedMessage.txt').read().rstrip()
d = dict([(letter, pos) for pos,letter in enumerate(string.lowercase)])

def encrypt(p, a1, b1):
    numbers = [((a1 * d[x1]) + b1) % 26 for x1 in p]
    return ''.join([string.lowercase[x1] for x1 in numbers])


# Decrypt message using Affine cipher
# Equation for decryption is in the form: ((n - b) * inverseA) % 26
def decrypt(message, a, b):
    inverseA = getInverse(a, 26)
    decryptedMessage = []
    
    # Convert each letter to a number and decrypt the number
    for letter in message:
        number = convertLettersToNumbers([letter])[0]
        decryptedNumber = ((number - b) * inverseA) % 26
        
        # Convert back to a letter and add it to the list of
        # decrypted letters
        decryptedLetter = string.lowercase[decryptedNumber]
        decryptedMessage.append(decryptedLetter)
        
    # Return the final decrypted string
    return ''.join(decryptedMessage)
    
    
    #numbers = [((d[x1] - b) * inverseA) % 26 for x1 in c]
    #return ''.join([string.lowercase[x1] for x1 in numbers])


# Function to find the multiplicative inverse of a mod m.
# The function will enumerate through all values [0, m). If
# it finds a value whose product with a is relatively prime
# with m, then it returns that number.  Otherwise the number
# did not have an inverse mod m. 
def getInverse(a, m):
    for i in range(0, m):
        if (a * i) % m == 1:
            return i
    return None


# Gets all of the values between [0, m) that are relatively
# prime with n.
def getCoprimes(n):
    
    # Helper function gcd using the Euclidean algorithm
    def gcd(a, b):
        a,b = max(a,b), min(a,b)
        if (b == 0):
            return a
        return gcd(b, a % b)

    # Get list of values between [0, n) that have gcd(i, n) == 1
    coprimes = []
    for i in range(0, n):
        if gcd(i, n) == 1:
            coprimes.append(i)
            
    return coprimes


# Solves the equations for a in the form
#     x1 * a + b = r1 mod 26
#     x2 * a + b = r2 mod 26
# If a does not have an inverse, None is returned 
def getA(x1, r1, x2, r2, m):
    aSub = (x1 - x2) % m
    rSub = (r1 - r2) % m
    aInverse = getInverse(aSub, m)
    
    if (aInverse == None):
        return None
    
    return (aInverse * rSub) % m


# Solves the equation for b in the form
#    x * a + b = r mod m
def getB(x, a, r, m):
    return (r - (a * x)) % m


# Converts a list of numbers into values starting with
# a = 0, b = 1, ...
def convertLettersToNumbers(listOfLetters):
    numbers = []
    for letter in listOfLetters:
        # number is the letter's position in the list of
        # lowercase letters
        numbers.append(string.lowercase.find(letter))
    return numbers



def enumeratePlaintexts(c, m):
    p = []
    for a1 in getCoprimes(m):
        for b1 in range(0, m):
            p.append(decrypt(c, a1, b1))
    return p


# Checks if a plaintext string could be english.
def couldBeEnglish(text):
    count = 0
    
    # Looks through each common word.  If there is more than
    # 1 occurrence of a common english word, then return True.
    # Otherwise, the text is probably not English, so return False.
    for commonWord in frequency.commonWords:
        if (text.find(commonWord) >= 0):    # found the word
            count += 1 
            if (count >= 2):
                return True
    return False
            



if (__name__ == '__main__'):
    
    # Read in encrypted message from file
    message = open('encryptedMessage.txt').read().rstrip()
    
    # Get list of most common digraphs for the message
    messageDigrams = frequency.getDigraphs(word)
    
    # Get list of most common digraphs in English language
    commonDigrams = frequency.commonDigraphs
    
    # Go through list of message digraphs and common digraphs sorted
    # by frequency and convert each digraph to their corresponding numbers
    decryptedTexts = []
    s = set()
    for i, messageDigraph in enumerate(messageDigrams):
        for j, commonDigraph in enumerate(commonDigrams):
            messageNumbers = convertLettersToNumbers(messageDigraph)
            commonDigraphNumbers = convertLettersToNumbers(commonDigraph)
            
            # Get values for x1, r1, x2, r2 to solve for a
            x1, r1 = commonDigraphNumbers[0], messageNumbers[0], 
            x2, r2 = commonDigraphNumbers[1], messageNumbers[1]
            
            # m = 26 for this cipher
            a = getA(x1, r1, x2, r2, 26)
            
            # Skip pairs of digraphs that cannot generate a valid
            # value for a
            if (a == None) or (a not in getCoprimes(26)):
                continue
            
            # Get value for b
            b = getB(x1, a, r1, 26)
            
            # Check if we've already tried the combination for a and b
            # If not, try to decrypt
            if ((a,b) not in s):
                s.add((a,b))
                
                # Format the decrypted message to include a and b
                decryptedMessage = decrypt(word, a, b)
                text = "a:{0}, b:{1}, {2}".format(a, b, decryptedMessage)
                
                # If the word is probably english, print it out
                if (couldBeEnglish(text)):
                    print text + '\n'
                
                
            
    
