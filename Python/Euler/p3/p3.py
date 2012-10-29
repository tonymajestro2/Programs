# Problem 3
#
# The prime factors of 13195 are 5, 7, 13 and 29.
# What is the largest prime factor of the number 600851475143?

import math

num = 600851475143
prime = 2

while (prime < num):
    if (num % prime == 0):
        num /= prime
    else:
        prime += 1

print prime
    




