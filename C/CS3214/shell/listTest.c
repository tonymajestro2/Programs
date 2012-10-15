
#include "list.h"
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

struct foo
{
    struct list_elem elem;
    int bar;
};

int main()
{
    struct list intList;
    list_init(&intList);

    struct list_elem elem1;
    elem1.next = NULL;
    elem1.prev = NULL;

    struct foo a;
    a.elem = elem1;
    a.bar = 1;

    struct list_elem elem2;
    struct foo b;
    b.bar = 2;
    b.elem = elem2;

    struct list_elem elem3;
    struct foo c;
    c.bar = 3;
    c.elem = elem3;

    list_push_back(&intList, &(a.elem));
    list_push_back(&intList, &(b.elem));
    list_push_back(&intList, &(c.elem));

    struct list_elem *e;
    for (e = list_begin(&intList); e != list_end(&intList); e = list_next(e)) {
        struct foo *myFoo = list_entry(e, struct foo, elem);
        printf("%d\n", myFoo->bar);
    }

    return 0;
}

