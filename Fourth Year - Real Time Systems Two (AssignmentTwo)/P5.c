//Process Five. Behaves as the outgoing loading bay controller. Communicates with three other sensors in processes two, three and four using a sigqueue.
#include <stdio.h>
#include <time.h>
#include <sys/netmgr.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <process.h>
#include <sys/types.h>
#include <sys/neutrino.h>
#include <sys/wait.h>
#include "../.INCLUDE/qsm.h"  /* Required for using RTDisplay           */
#include "DataArea.h"
#define RTsig1 SIGRTMIN
#define RTsig2 SIGRTMIN+1
#define RTsig3 SIGRTMIN+2
#define RTsig16 SIGRTMAX


#define MY_CYCLIC_PULSE _PULSE_CODE_MINAVAIL


typedef union {

struct _pulse pulse;

     /* your other message structures would go here too */

} my_message_t;


int main()

{
	union sigval sval;
    int fd;
    struct DataArea *ptr;

    /*Open Data Area*/
    fd = shm_open("/myDataArea", O_RDWR, S_IRWXU);
    if (fd == -1) {
        printf("Error opening the data area: %s\n", strerror(errno));
        exit(EXIT_FAILURE);
    }
    /*Pointer To Data Area*/
    ptr = mmap(0, DATA_AREA_SIZE, PROT_READ|PROT_WRITE, MAP_SHARED, fd, 0);

  //The code from Test6
  struct sigevent event;

  struct itimerspec itime;

  timer_t timer_id;

  int chid;

  int rcvid;

  int x1;
  int x2;
  int x3;
  int y = 1000;
  my_message_t msg;

  chid = ChannelCreate(0);

  event.sigev_notify = SIGEV_PULSE;

  event.sigev_coid = ConnectAttach(ND_LOCAL_NODE, 0, chid, _NTO_SIDE_CHANNEL, 0);

  event.sigev_priority = -1;

  event.sigev_code = MY_CYCLIC_PULSE;

  timer_create(CLOCK_REALTIME, &event, &timer_id);

  itime.it_value.tv_sec = 4;

  itime.it_value.tv_nsec = 400000000;

  itime.it_interval.tv_sec = 4;

  itime.it_interval.tv_nsec = 400000000;

  timer_settime(timer_id, 0, &itime, NULL);

  ScreenInitialise(6, 9);

  SensorSetName(4, "Outgoing Bay One");
  SensorSetName(5, "Outgoing Bay Two");
  SensorSetName(6, "Outgoing Bay Three");

  while (1) {
    /* Wait for a PULSE every period.                               */

    rcvid = MsgReceive(chid, &msg, sizeof(msg), NULL);

    if (rcvid == 0) {
    /* Check to see if our CYCLIC pulse.           */

      if (msg.pulse.code == MY_CYCLIC_PULSE) {

    	  x1 = SensorGetValue(4);
    	  x2 = SensorGetValue(5);
    	  x3 = SensorGetValue(6);

    	  if (x1 == 1) {
            ActuatorSetValue(4, 100);
            printf("Outgoing Bay Controller - A truck has arrived at bay one\n");
		    sigqueue(ptr->processIDs[6], RTsig1, sval);
    	  } else if (x2 == 1) {
    		ActuatorSetValue(5, 100);
    		printf("Outgoing Bay Controller - A truck has arrived at bay two\n");
		    sigqueue(ptr->processIDs[7], RTsig1, sval);
    	  } else if (x3 == 1)  {
    		ActuatorSetValue(6, 100);
    		printf("Outgoing Bay Controller - A truck has arrived at bay three\n");
		    sigqueue(ptr->processIDs[8], RTsig1, sval);

      	  }else if (x1 == 0)  {
      		ActuatorSetValue(4, 0);
        	  }
      	   else if (x2 == 0)  {
			ActuatorSetValue(5, 0);
      	      }
      	   else if (x3 == 0)  {
			ActuatorSetValue(6, 0);
      	      }
      	   else {
              printf("Outgoing Bay Controller - An unknown value was detected by the sensor. Try again!");
          }

      }
    }
  }
  close(fd);

  munmap(ptr, DATA_AREA_SIZE);
  return 0;
}
