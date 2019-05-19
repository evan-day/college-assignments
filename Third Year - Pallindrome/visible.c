
//--------------------------------------------------
// INCLUDE SECTION
//--------------------------------------------------
#include "visible.h"

//--------------------------------------------------
// gen_num
//--------------------------------------------------
int gen_num(int lb, int ub) {
    //1. We create the output variable with an initial value.
    int res = 0;

    //2. We assign num to a random value in the desired range
    res = (rand() % (ub - lb)) + lb;

    //3. We return the output variable
    return res;
}

//--------------------------------------------------
// my_getchar
//--------------------------------------------------
char my_get_char() {
    //1. We create the output variable with the value the user has input by keyboard
    char res = getchar();

    //2. We discard any extra character the user has input by keyboard
    boolean finish = False;
    char dummy_char = ' ';

    while (finish == False) {
        dummy_char = getchar();
        if (dummy_char == '\n')
            finish = True;
    }

    //3. We return the output variable
    return res;
}