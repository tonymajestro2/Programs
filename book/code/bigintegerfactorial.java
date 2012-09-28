// factorial
static BigInteger fac(int n) 
{
    BigInteger r = BigInteger.ONE;
    for (int i = 1; i <= n; i++)
        r = r.multiply(BigInteger.valueOf(i));
    return r;
}
