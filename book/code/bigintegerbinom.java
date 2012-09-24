// n choose k
// works only if result fits in signed 64-bit long
static long choose(int n, int k) {
    return fac(n).divide(fac(n - k)).divide(fac(k)).longValue();
}
