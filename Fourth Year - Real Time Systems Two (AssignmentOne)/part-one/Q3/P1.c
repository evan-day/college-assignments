#include <stdio.h>
#include <time.h>
#include <sys/netmgr.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <process.h>
#include <sys/neutrino.h>
#include "../.INCLUDE/qsm.h"          /* Required for using RTDisplay           */

/* In this problem, we have to set up the primary process to print a value based on a fixed time of 4.4 seconds.
We use a variable that accepts inputs from a sensor in the RT Display.
The values entered into the sensor, then directly change the output from the process, due to the boundary checks that are implemented.
*/

#define MY_CYCLIC_PULSE _PULSE_CODE_MINAVAIL


typedef union {

struct _pulse pulse;

     /* your other message structures would go here too */

} my_message_t;


int main()

{
  //The code from Test6
  struct sigevent event;

  struct itimerspec itime;

  timer_t timer_id;

  int chid;

  int rcvid;

  int x1;
  int y = 1000;
  my_message_t msg;



  chid = ChannelCreate(0);

  event.sigev_notify = SIGEV_PULSE;

  event.sigev_coid = ConnectAttach(ND_LOCAL_NODE, 0, chid,

                                                           _NTO_SIDE_CHANNEL, 0);


  event.sigev_priority = -1;

  event.sigev_code = MY_CYCLIC_PULSE;


  timer_create(CLOCK_REALTIME, &event, &timer_id);

  itime.it_value.tv_sec = 4;

  itime.it_value.tv_nsec = 400000000;

  itime.it_interval.tv_sec = 4;

  itime.it_interval.tv_nsec = 400000000;

  timer_settime(timer_id, 0, &itime, NULL);

  //The Code from Test7A
  ScreenInitialise(1, 0);

  SensorSetName(1, "MyFirstSensor");

  while (1) {
                                                                /* Wait for a PULSE every period.                               */

    rcvid = MsgReceive(chid, &msg, sizeof(msg), NULL);

    if (rcvid == 0) {
                                                                /* Check to see if our CYCLIC pulse.           */

      if (msg.pulse.code == MY_CYCLIC_PULSE) {

    	  x1 = SensorGetValue(1);
    	  printf("Current Sensor Value%d\n", x1);

    	  if (x1 >= 25) {
    		  printf("Temperatures are normal today%d\n", x1);
    	  } else if (x1 <= 50) {
    		  printf("The great drought of 2018 is upon us %d\n", x1);
    	  } else  {
    		  printf("Now its way too hot %d\n", x1);
    	  }

      } /* else other pulses ... */

    } /* else other messages ... */

  }
  return 0;
}
