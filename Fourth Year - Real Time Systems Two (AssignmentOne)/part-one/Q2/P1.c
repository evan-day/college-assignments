#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/neutrino.h>

#define RTsig1 SIGRTMIN
#define RTsig2 SIGRTMIN+1
#define RTsig3 SIGRTMIN+2
#define RTsig16 SIGRTMAX
						/* Handler for signals that arrive. */
void myHandler(int sig, siginfo_t *info, void *other)
    {
    printf("myHandler: signal to arrive = %d\n", sig);
    printf("info value %d\n", info ->si_value.sival_int);

       switch (info->si_value.sival_int) {
          case 100: printf("P1 - Signal100 received\n");
                    break;
          case 200: printf("P1 - Signal200\n");
                    break;
          case 300: printf("P1 - Signal300\n");
                    break;
          case 400: printf("P1 - Signal400\n");
                    break;
          default:
                    printf("Test5: Unexpected signal of %d\n", info->si_value.sival_int);
          }
    }

int main(int argc, char *argv[])
    {
    struct sigaction action;
    int retval;
    action.sa_handler = (void *)myHandler;
    sigfillset(&action.sa_mask);
    action.sa_flags = 0;
    if ((retval = sigaction(RTsig1,&action, NULL)) < 0)
        {
        fprintf(stderr, "Main(): cannot add alarm signal handler\n");
        exit(retval);
        }

    while (1)
        {
        pause();
        }
    }
