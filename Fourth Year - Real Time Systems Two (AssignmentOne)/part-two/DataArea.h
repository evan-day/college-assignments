#define MESS_SZ 30
struct DataArea
    {
		pid_t processIDs[4];
    };
#define DATA_AREA_SIZE sizeof (struct DataArea)
