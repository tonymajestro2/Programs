
#include <stddef.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#define SHELL "/bin/sh"

int my_system(char *command)
{
    int status;
    pid_t pid;

    if((pid = fork()) == 0) {
        execl(SHELL, SHELL, "-c", command, NULL);
        _exit(EXIT_FAILURE);
    }
    else if (pid < 0) {
        status = -1;
    }
    else {
        if (waitpid(pid, &status, 0) != pid) {
            status = -1;
        }
    }
    
    return status;
}

int main()
{
    my_system("echo hello world");
    return 0;
}
