#include <stdio.h>
#include <unistd.h>

int main (int argc, char *argv[])
	{
	printf("Child2 : Hello\n");
	sleep(3);
	printf("OS Call One = sleep \n");
	sleep(3);
	printf("OS Call Two = printf\n");
	sleep(3);
	printf("OS Call Three = date\n");
	return(0);
	}
