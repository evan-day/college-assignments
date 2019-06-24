#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <spawn.h>
#include <unistd.h>
#include <sys/wait.h>

/* In problem one, we needed to be able to create child processes from one, start up process.
Creating each process using multiple variables, then running the required commands within each process.
*/

int main (int argc, char * argv[], char **envp)
	{
	char *args1[] = {"P1", "-1", NULL};
	int status1;
	pid_t pid1;
	struct inheritance inherit1;

	char *args2[] = {"P2", "-1", "SecondParm", NULL};
	int status2;
	pid_t pid2;
	struct inheritance inherit2;

	char *args3[] = {"P3", "-1", "2", "5", "7", "9", "11", NULL};
	int status3;
	pid_t pid3;
	struct inheritance inherit3;

	inherit1.flags = 0;

	if ((pid1 = spawn(args1[0], 0, NULL, &inherit1, args1, envp)) == -1)
		perror("child1: spawn() failed");
	else
		printf("child1: spawned child, pid ==> %d\n", pid1);

	inherit2.flags = 0;

	if ((pid2 = spawn(args2[0], 0, NULL, &inherit2, args2, envp)) == -1)
		perror("child2: spawn() failed");
	else
		printf("child2: spawned child, pid ==> %d\n", pid2);

	inherit3.flags = 0;

	if ((pid3 = spawn(args3[0], 0, NULL, &inherit3, args3, envp)) == -1)
		perror("child3: spawn() failed");
	else
		printf("child3: spawned child, pid ==> %d\n", pid3);

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

	return(0);
	}
