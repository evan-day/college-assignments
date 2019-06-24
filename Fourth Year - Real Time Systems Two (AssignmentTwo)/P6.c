//Process Six - Behaves as Outgoing Bay One. Using actuators, it checks what the level of the truck is. If full, it states it's full and ready to depart.
//If it is not full, it will increment the stock to the truck while attempting to reduce the stock level of the warehouse.
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
int y = 0;
int z = 0;

void myHandler(int sig, siginfo_t *info, void *other)
   {
   switch (info->si_value.sival_int) {
      default:
	  	  	ActuatorSetValue(4, y);
	  	  	if (y == 100) {
	  	  		printf("Outgoing Bay One: Truck is full and ready to depart\n");
	  	  	} else if (z > 0){
	  	  		printf("Outgoing Bay One: Loading the truck\n");
	  	  		y = y + 25;
	  	  		z = z - 25;
	  	  		ActuatorSetValue(7, z);

	  	  	} else if (z == 0) {
	  	  		printf("Outgoing Bay One: Product Is Out of Stock");
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

ActuatorSetName(4, "Truck Four Status");
ActuatorSetName(7, "Product A Status");

while (1)
    {
    sleep(1);      /* Infinite loop - program just waits for interrupt calls.  */
    }
}
