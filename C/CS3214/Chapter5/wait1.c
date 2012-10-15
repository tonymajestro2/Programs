
#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
    pid_t child = fork();
    if (child < 0) {
        perror("fork");
        exit(-1);
    }
    if (child != 0) {
        wait(NULL);
        printf("I'm the parent %d, my child is %d\n", getpid(), child);
    }
    else {
        printf("I'm the child %d, my parent is %d\n", getpid(), getppid());
    }

    execl("/bin/echo", "echo", "Hello, World", NULL);
}
