/*
 *  Test5.c
 *
 *  Use the RTdisplay simulator to send interrupt signals via F1 and F2.
 *  To run this program (or any program with the RTdisplay simulator),
 *       - Open a putty terminal and connect to the QNX target,
 *       - Use the word 'run' in front of the program name, i.e.
 *                run Test5
 *
 *       - CAUTION: Any program that uses the RTdisplay simulator may not
 *                  be run again unless you RESTART the QNX target first.
 */


#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <process.h>
#include <sys/neutrino.h>
#include "../.INCLUDE/qsm.h"         /* Required for setting up RTdisplay. */

#define RTsig1 SIGRTMIN
#define RTsig2 SIGRTMIN+1
#define RTsig3 SIGRTMIN+2
#define RTsig16 SIGRTMAX

void myHandler(int sig, siginfo_t *info, void *other)
   {
   switch (info->si_value.sival_int) {
      case 100: printf("Test5: Signal100\n");
                break;
      case 2000: printf("Test5: Signal2000\n");
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
    fprintf(stderr, "Main(): cannot add signal handler\n");
    exit(retval);
    }


	
ScreenInitialise(0, 0);	            /* Call required for setting up RTdisplay. */

F1INTERRUPT(getpid(), 100);
F2INTERRUPT(getpid(), 2000);

while (1)
    {
    sleep(1);      /* Infinite loop - program just waits for interrupt calls.  */
    }
}