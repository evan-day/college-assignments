//Process Three - Behaves as Incoming Bay Two. Using actuators, it checks what the level of the truck is. If empty, it states it's empty.
//If it is not empty, it will decrement the stock from the truck while attempting to add to the stock level of the warehouse.
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
int y = 100;
int z = 0;

void myHandler(int sig, siginfo_t *info, void *other)
   {
   switch (info->si_value.sival_int) {
      default:
    	  	  	ActuatorSetValue(2, y);
    	  	  	if (y == 0) {
    	  	  		printf("Incoming Bay Two: Truck is empty and ready to depart\n");
    	  	  	} else {
    	  	  		printf("Incoming Bay Two: Unloading from truck\n");
    	  	  		y = y - 25;
    	  	  		z = z + 25;
    	  	  		ActuatorSetValue(8, z);
    	  	  	}
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


ScreenInitialise(6, 9);	            /* Call required for setting up RTdisplay. */

ActuatorSetName(2, "Truck Two Status");

while (1)
    {
    sleep(1);      /* Infinite loop - program just waits for interrupt calls.  */
    }
}
