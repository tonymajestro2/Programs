

def lcm(a,b):
    if (b == 0):
        return a
    gcd = lcm(b, a % b)
    return a * b / gcd


if (__name__ == '__main__'):
    lcm(7, 4)