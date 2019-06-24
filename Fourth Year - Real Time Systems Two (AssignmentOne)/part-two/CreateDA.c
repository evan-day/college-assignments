#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include "DataArea.h"

int main(int argc, char *argv[]) {
    int fd;
    struct DataArea *ptr;

    /*Create Data Area*/
    fd = shm_opem("/myDataArea", O_RDWR | O_CREAT | O_EXCL, S_IRWXU);

    if (fd == 1) {
        printf("Error creating the shared memory object. %s.\n", strerror(errno));
        exit(EXIT_FAILURE);
    }
    /*Set Data Area Size*/
    ftruncate(fd, DATA_AREA_SIZE);
    /*Make 'ptr' point into data area*/
    ptr = mmap(0, DATA_AREA_SIZE, PROT_READ|PROT_WRITE, MAP_SHARED, fd, 0);
    ptr->MessageNumber = 12345;
    strcpy(ptr->Message, "this is a test");
    printf("Data area is now created\n");

    sleep(20);

    printf("20 seconds passed --- Data Area will be closed/deleted\n");

    close(fd);
    munmap(ptr, DATA_AREA_SIZE);

    shm_unlink("/myDataArea");

    return(EXIT_SUCCESS);
}