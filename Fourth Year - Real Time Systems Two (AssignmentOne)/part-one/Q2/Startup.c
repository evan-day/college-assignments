#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <spawn.h>
#include <unistd.h>
#include <sys/wait.h>
#include "../.INCLUDE/qsm.h"
#define RTsig1 SIGRTMIN
#define RTsig2 SIGRTMIN+1
#define RTsig3 SIGRTMIN+2
#define RTsig16 SIGRTMAX

/* In this problem we had to print different messages for the different signals received from another process.
We do this by queuing various signals using sigqueue that is then sent out to the first process created.
Process one then prints a different message based on the signal received.
Process two then prints different messages based on signals sent by the RT Display to it.
*/

int main (int argc, char * argv[], char **envp)
	{
	union sigval sval;

	char *args1[] = {"P1", "-1", NULL};
	int status1;
	pid_t pid1;
	struct inheritance inherit1;

	char *args2[] = {"P2", "-1", NULL};
	int status2;
	pid_t pid2;
	struct inheritance inherit2;

	ScreenInitialise(1, 0);	            /* Call required for setting up RTdisplay. */



	inherit1.flags = 0;

	if ((pid1 = spawn(args1[0], 0, NULL, &inherit1, args1, envp)) == -1)
		perror("child1: spawn() failed");
	else
		printf("child1: spawned child, pid ==> %d\n", pid1);


		sleep(3);
		sval.sival_int = 100;
		sigqueue(pid1, RTsig1, sval);

		sleep(3);
		sval.sival_int = 200;
		sigqueue(pid1, RTsig1, sval);

		sleep(3);
		sval.sival_int = 300;
		sigqueue(pid1, RTsig1, sval);

		sleep(3);
		sval.sival_int = 400;
		sigqueue(pid1, RTsig1, sval);

	inherit2.flags = 0;

	if ((pid2 = spawn(args2[0], 0, NULL, &inherit2, args2, envp)) == -1)
		perror("child2: spawn() failed");
	else
		printf("child2: spawned child, pid ==> %d\n", pid2);






	if (waitpid(pid1, &status1, WEXITED) <= 0)
		perror("child1: Error with waiting???");
	else
		printf("child1: Process pid=%d closed successfully and status ==>%d\n", pid1, status1);

	if (waitpid(pid2, &status2, WEXITED) <= 0)
		perror("child2: Error with waiting???");
	else
		printf("child2: Process pid=%d closed successfully and status ==>%d\n", pid2, status2);

	return(0);
	}
