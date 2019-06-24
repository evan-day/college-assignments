//Startup.C - Responsible for creating the data area and the other processes for the system
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <spawn.h>
#include <unistd.h>
#include <sys/wait.h>
#include "../.INCLUDE/qsm.h"
#include <errno.h>
#include <fcntl.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include "DataArea.h"
#define RTsig1 SIGRTMIN
#define RTsig2 SIGRTMIN+1
#define RTsig3 SIGRTMIN+2
#define RTsig16 SIGRTMAX

int fd;
struct DataArea *ptr;
void createDataArea()
{

	    /*Create Data Area*/
	    fd = shm_open("/myDataArea", O_RDWR | O_CREAT | O_EXCL, S_IRWXU);

	    if (fd == 1) {
	        printf("Error creating the shared memory object. %s.\n", strerror(errno));
	        exit(EXIT_FAILURE);
	    }
	    /*Set Data Area Size*/
	    ftruncate(fd, DATA_AREA_SIZE);
	    /*Make 'ptr' point into data area*/
	    ptr = mmap(0, DATA_AREA_SIZE, PROT_READ|PROT_WRITE, MAP_SHARED, fd, 0);

	    printf("Data area is now created\n");

}
int main (int argc, char * argv[], char **envp)
	{
	ScreenInitialise(6, 9);	            /* Call required for setting up RTdisplay. */

	createDataArea();
	sleep(2);

	union sigval sval;

	char *args1[] = {"P1", "-1", NULL};
	int status1;
	pid_t pid1;
	struct inheritance inherit1;

	char *args2[] = {"P2", "-1", NULL};
	int status2;
	pid_t pid2;
	struct inheritance inherit2;

	char *args3[] = {"P3", "-1", NULL};
	int status3;
	pid_t pid3;
	struct inheritance inherit3;

	char *args4[] = {"P4", "-1", NULL};
	int status4;
	pid_t pid4;
	struct inheritance inherit4;

	char *args5[] = {"P5", "-1", NULL};
	int status5;
	pid_t pid5;
	struct inheritance inherit5;

	char *args6[] = {"P6", "-1", NULL};
	int status6;
	pid_t pid6;
	struct inheritance inherit6;

	char *args7[] = {"P7", "-1", NULL};
	int status7;
	pid_t pid7;
	struct inheritance inherit7;

	char *args8[] = {"P8", "-1", NULL};
	int status8;
	pid_t pid8;
	struct inheritance inherit8;


	inherit1.flags = 0;

	if ((pid1 = spawn(args1[0], 0, NULL, &inherit1, args1, envp)) == -1)
		perror("child1: spawn() failed");
	else
		printf("child1: spawned child, pid ==> %d\n", pid1);
		ptr->processIDs[0] = pid1;

	inherit2.flags = 0;

	if ((pid2 = spawn(args2[0], 0, NULL, &inherit2, args2, envp)) == -1)
		perror("child2: spawn() failed");
	else
		printf("child2: spawned child, pid ==> %d\n", pid2);
		ptr->processIDs[1] = pid2;

	inherit3.flags = 0;

	if ((pid3 = spawn(args3[0], 0, NULL, &inherit3, args3, envp)) == -1)
		perror("child3: spawn() failed");
	else
		printf("child3: spawned child, pid ==> %d\n", pid3);
		ptr->processIDs[2] = pid3;

	inherit4.flags = 0;

	if ((pid4 = spawn(args4[0], 0, NULL, &inherit4, args4, envp)) == -1)
		perror("child4: spawn() failed");
	else
		printf("child4: spawned child, pid ==> %d\n", pid4);
		ptr->processIDs[3] = pid4;

	inherit5.flags = 0;

	if ((pid5 = spawn(args5[0], 0, NULL, &inherit5, args5, envp)) == -1)
		perror("child5: spawn() failed");
	else
		printf("child5: spawned child, pid ==> %d\n", pid5);
		ptr->processIDs[4] = pid5;

	inherit6.flags = 0;

	if ((pid6 = spawn(args6[0], 0, NULL, &inherit6, args6, envp)) == -1)
		perror("child6: spawn() failed");
	else
		printf("child6: spawned child, pid ==> %d\n", pid6);
		ptr->processIDs[5] = pid6;

	inherit7.flags = 0;

	if ((pid7 = spawn(args7[0], 0, NULL, &inherit7, args7, envp)) == -1)
		perror("child7: spawn() failed");
	else
		printf("child7: spawned child, pid ==> %d\n", pid7);
		ptr->processIDs[6] = pid7;

	inherit8.flags = 0;

	if ((pid8 = spawn(args8[0], 0, NULL, &inherit8, args8, envp)) == -1)
		perror("child8: spawn() failed");
	else
		printf("child8: spawned child, pid ==> %d\n", pid8);
		ptr->processIDs[7] = pid8;


	if (waitpid(pid1, &status1, WEXITED) <= 0)
		perror("child1: Error with waiting???");
	else
		printf("child1: Process pid=%d closed successfully and status ==>%d\n", pid1, status1);

	if (waitpid(pid2, &status2, WEXITED) <= 0)
		perror("child2: Error with waiting???");
	else
		printf("child2: Process pid=%d closed successfully and status ==>%d\n", pid2, status2);

	if (waitpid(pid3, &status3, WEXITED) <= 0)
		perror("child3: Error with waiting???");
	else
		printf("child3: Process pid=%d closed successfully and status ==>%d\n", pid3, status3);

	if (waitpid(pid4, &status4, WEXITED) <= 0)
		perror("child4: Error with waiting???");
	else
		printf("child4: Process pid=%d closed successfully and status ==>%d\n", pid4, status4);

	if (waitpid(pid5, &status5, WEXITED) <= 0)
		perror("child5: Error with waiting???");
	else
		printf("child5: Process pid=%d closed successfully and status ==>%d\n", pid5, status5);

	if (waitpid(pid6, &status6, WEXITED) <= 0)
		perror("child6: Error with waiting???");
	else
		printf("child6: Process pid=%d closed successfully and status ==>%d\n", pid6, status6);

	if (waitpid(pid7, &status7, WEXITED) <= 0)
		perror("child7: Error with waiting???");
	else
		printf("child7: Process pid=%d closed successfully and status ==>%d\n", pid7, status7);

	if (waitpid(pid8, &status8, WEXITED) <= 0)
		perror("child8: Error with waiting???");
	else
		printf("child8: Process pid=%d closed successfully and status ==>%d\n", pid8, status8);

	return(0);
	}
