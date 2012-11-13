
#include <stdlib.h>
#include <stdio.h>

int main()
{
    int *ptr = malloc(6 * sizeof(int));
    ptr = ptr - 2;
    printf("%d\n", *ptr ^ 1);
    printf("%d\n", *ptr & 1);
}
