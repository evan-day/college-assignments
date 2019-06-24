/*
 *  Test4.c
 *
 *  Receive signals.
 *
 *  To generate signals from a terminal:
 *  TERMINAL(1)  :   $Test4
 *  				Note: This is an infinite loop waiting for signals.
 *
 *  TERMINAL(2)  :	 $top
 *  				Note: Look for the process number of program Test4.
 *                                n 40 <-- this will display the 40 processes....
 *  			 $kill -s 41 <number_of_process for process Test4>
 *  				Note: 41 is the signal number of RTsig1 or SIGRTMIN.
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/neutrino.h>

#define RTsig1 SIGRTMIN
#define RTsig2 SIGRTMIN+1
#define RTsig3 SIGRTMIN+2
#define RTsig16 SIGRTMAX
						/* Handler for signals that arrive. */
void myHandler(int sig, siginfo_t *info, void *other)
    {
    printf("myHandler: signal to arrive = %d\n", sig);
    }

int main(int argc, char *argv[])
    {
    struct sigaction action;
    int retval;
    action.sa_handler = (void *)myHandler;
    sigfillset(&action.sa_mask);
    action.sa_flags = 0;
    if ((retval = sigaction(RTsig1,&action, NULL)) < 0)
        {
        fprintf(stderr, "Main(): cannot add alarm signal handler\n");
        exit(retval);
        }

    while (1)
        {
        pause();
        }
    }