//Process Four - Responsible for printing the specified message when it receives a signal from Process One and with updating the Actuator to hold the specified
//value.
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
      default:
    	  	  	printf("Process Four: - The bus is now full, setting the actuator\n");
    	  	  ActuatorSetValue(1, 1);
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



ScreenInitialise(1, 1);	            /* Call required for setting up RTdisplay. */
ActuatorSetName(1, "Bus Full");

while (1)
    {
    sleep(1);      /* Infinite loop - program just waits for interrupt calls.  */
    }
}
