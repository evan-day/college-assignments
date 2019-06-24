#include <stdio.h>
#include <unistd.h>

int main (int argc, char *argv[])
	{
	int number;
	int largest = -999;
	printf("Child3 : Hello\n");
	printf("Child3 : argc=%d\n", argc);
	for (int i=1; i<argc; i++)
		number = atoi(argv[i]);
		if (number > largest)
			largest = number;
	printf("%d/n", largest);
	sleep(10);
	return(0);
	}
