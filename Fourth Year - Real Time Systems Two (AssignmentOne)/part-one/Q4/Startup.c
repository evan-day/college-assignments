#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <spawn.h>
#include <unistd.h>
#include <sys/wait.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include "DataArea.h"

/* For problem four, we have to create a shared DataArea for two individual processes to write to since a process cannot directly write to another process.
We use a struct to create a DataArea with various values that reflect what a real world printer would look like.
The start up process creates the data area, which process one then reads the values from. We then call process two to update these values and we then call process one again to read the values out to us.
*/

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

	    ptr->PrinterID = 5;
	    ptr->NumberOfPages = 500;
	    ptr->BlackInkLevel = 100;
	    ptr->ColourInkLevel = 80;
	    strcpy(ptr->PrinterStatus, "Ready");
	    strcpy(ptr->PrinterRollerStatus, "Normal");
	    strcpy(ptr->PrinterJamStatus, "Not Jammed");

	    printf("Data area is now created\n");

}

int main (int argc, char * argv[], char **envp)
	{
	createDataArea();
	sleep(2);


	char *args1[] = {"P1", "-1", NULL};
	int status1;
	pid_t pid1;
	struct inheritance inherit1;

	char *args2[] = {"P2", "-1", NULL};
	int status2;
	pid_t pid2;
	struct inheritance inherit2;

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

	if ((pid1 = spawn(args1[0], 0, NULL, &inherit1, args1, envp)) == -1)
		perror("child1: spawn() failed");
	else
		printf("child1: spawned child, pid ==> %d\n", pid1);

	if (waitpid(pid1, &status1, WEXITED) <= 0)
		perror("child1: Error with waiting???");
	else
		printf("child1: Process pid=%d closed successfully and status ==>%d\n", pid1, status1);

	if (waitpid(pid2, &status2, WEXITED) <= 0)
		perror("child2: Error with waiting???");
	else
		printf("child2: Process pid=%d closed successfully and status ==>%d\n", pid2, status2);

    sleep(20);

    printf("20 seconds passed --- Data Area will be closed/deleted\n");

    close(fd);
    munmap(ptr, DATA_AREA_SIZE);

    shm_unlink("/myDataArea");

	return(0);
	}
