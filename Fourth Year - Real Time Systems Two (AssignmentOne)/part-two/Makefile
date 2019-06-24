#
#	Makefile for ForkProcesses directory
#

DEBUG = -g
CC = qcc
LD = qcc

TARGET = -Vgcc_ntox86_64
#TARGET = -Vgcc_ntoppcbe
#TARGET = -V3.3.5,gcc_ntoarmle

CFLAGS += $(DEBUG) $(TARGET)
LDFLAGS+= $(DEBUG) $(TARGET)

BINS = Startup P1 P2 P3 P4

all: $(BINS)

clean:
	rm -f *.o *.tmp $(BINS)