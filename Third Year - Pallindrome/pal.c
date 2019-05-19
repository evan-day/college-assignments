//--------------------------------------------------
// INCLUDE SECTION
//--------------------------------------------------
#include "pal.h"

//--------------------------------------------------
// is_pal
//--------------------------------------------------
boolean is_pal(int a[6]) {
	boolean res = True;

	int leftIndex = 0;
	int rightIndex = 5;


	
	while ((leftIndex < rightIndex) && (res == True)) {
		//If the number at the left is not equalled to the number at the right, then is not a pallindrome.
		if (a[leftIndex] != a[rightIndex]) {
			res = False;
		}
		else {
			//If they're equalled, then move the left and right index in one position and check again if equalled. 
			leftIndex = leftIndex + 1;
			rightIndex = rightIndex - 1;
		}
	}
	return res;
}

//--------------------------------------------------
// ask_for_command
//--------------------------------------------------
char ask_for_command() {
	char res = ' ';
	printf("New Movement: Enter Valid Command By Keyboard");
	printf("\n");
	printf("Valid commands: a d w x");
	printf("\n");
	res = my_get_char();

	return res;
}

//--------------------------------------------------
// process_movement
//--------------------------------------------------
void process_movement(int a[6], int** p_p, int* p_nm, char c) {
	switch (c) {

	case 'a':
		if (*p_p == a + 0) {
			//If the pointer pointer is equalled to the array at position 0, do not allow to move to the left
			printf("You Cannot move to the left as you're as far left as you can go!");
		}
		else {
			//Take the position of the pointer pointer and move it one position to the left
			*p_p = *p_p - 1;
			*p_nm = *p_nm + 1;
			//Take the position of the pointer to the number of moves and increment it.
		}
		break;
	case 'd':
		if (*p_p == a + 5) {
			//If the pointer pointer is equalled to the array at position 5, do not allow to move to the right
			printf("You Cannot move to the left as you're as far right as you can go!");
		}
		else {
			//Take the position of the pointer pointer and move it one position to the right
			*p_p = (*p_p + 1);
			*p_nm = *p_nm + 1;
			//Take the position of the pointer to the number of moves and increment it.
		}
		break;
	case 'w':
		if (**p_p == 9) {
			//If the number at pointer pointer is already at nine, alert the user that it cannot be incremented.
			printf("You cannot increment past nine");
		}
		else {
			//Take the value of the pointer pointer and increment it by one 
			**p_p = **p_p + 1;
			*p_nm = *p_nm + 1;
			//Increment the pointer for the number of moves.
		}
		break;
	case 'x':
		if (**p_p == 0) {
			//If the number at pointer pointer is already at zero, alert the user that it cannot be incremented.
			printf("You cannot decrement past 0");
		}
		else {
			//Take the value of the pointer pointer and decrement it by one
			**p_p = **p_p - 1;
			*p_nm = *p_nm + 1;
			//Increment the pointer for number of moves.
		}
		break;
	default:
		//If none of the above cases are valid, then the user entered the incorrect command into the program.
		printf("Invalid movement option");
	}

}

//--------------------------------------------------
// print_status
//--------------------------------------------------
void print_status(int a[6], int* p, int nm) {
	printf("----- Game Status -----");
	printf("\n     ");

	//Prints out the array and its individual digits.
	for (int index = 0; index < 6; index++) {
		printf("%d", a[index]);
	}
	printf("\n");
	//Prints out the white space for formatting the assignment.
	for (int indextwo = 0; indextwo < 6; indextwo++) {
		if (p == (a + indextwo)) {
			int aux = 0;
			printf("     ");

			while (aux < (0 + indextwo)) {
				printf(" ");
				aux++;
			}

		}
	}
	printf("^");
	printf("\n");
	printf("Number Of Moves = %d\n", nm);

}

//--------------------------------------------------
// user_game_palindrome
//--------------------------------------------------
void user_game_palindrome(int pal_num) {
	int a[6];
	//Pointer to address of array
	int* p = &a[0];
	//Pointer to the address of the pointer
	int** p_p = &p;
	int nm = 0;
	int* p_nm = &nm;
	int index = 5;
	int res = 0;


	char c = " ";

	while (pal_num > 0) {
		//Takes pal num and breaks it up into its individual digits. Since it's reversed in this operation, digits are added at the back of the array first.
		a[index--] = pal_num % 10;
		pal_num = (int)pal_num / 10;
	}
	res = is_pal(a);
	//printf("res is %d", res);

	while (res == 0) {
		res = is_pal(a);
		if (res == 1) {
			printf("You won the game!");
			break;
		}

		print_status(a, p, nm);
		c = ask_for_command();
		process_movement(a, p_p, p_nm, c);
	}
}