#define MESS_SZ 30
struct DataArea
    {
        int PrinterID;
        int NumberOfPages;
        int BlackInkLevel;
        int ColourInkLevel;
        char PrinterStatus[MESS_SZ];
        char PrinterRollerStatus[MESS_SZ];
        char PrinterJamStatus[MESS_SZ];
    };
#define DATA_AREA_SIZE sizeof (struct DataArea)
