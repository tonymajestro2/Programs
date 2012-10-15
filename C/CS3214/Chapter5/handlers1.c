
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>

volatile sig_atomic_t keep_going = 1;

void catch_alarm(int sig)
{
    keep_going = 0;
    signal(sig, catch_alarm);
}

void do_stuff()
{
    puts("Doing stuff while waiting for alarm....");
}


int main()
{
    signal(SIGALRM, catch_alarm);
    alarm(1);
    while (keep_going)
        do_stuff();

    return EXIT_SUCCESS;
}
