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

    /*Open Data Area*/
    fd = shm_open("/myDataArea", O_RDWR, S_IRWXU);
    if (fd == -1) {
        printf("Error opening the data area: %s\n", strerror(errno));
        exit(EXIT_FAILURE);
    }
    /*Pointer To Data Area*/
    ptr = mmap(0, DATA_AREA_SIZE, PROT_READ|PROT_WRITE, MAP_SHARED, fd, 0);

    ptr->PrinterID = 5;
    ptr->NumberOfPages = 400;
    ptr->BlackInkLevel = 90;
    ptr->ColourInkLevel = 80;
    strcpy(ptr->PrinterStatus, "Not Ready");
    strcpy(ptr->PrinterRollerStatus, "Normal");
    strcpy(ptr->PrinterJamStatus, "Jammed!");

    printf("P2 - Values Updated!\n");

    /*Close FD and Unmap Shared Area*/
    close(fd);

    munmap(ptr, DATA_AREA_SIZE);

    return(EXIT_SUCCESS);
}
