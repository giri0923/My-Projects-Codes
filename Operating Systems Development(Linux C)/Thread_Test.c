/*
Project 1
Girish Dasarathy-1207555332
Niranjan Krishna Ravichandran-1207518529
*/

#include "threads.h"
#include<stdio.h>
#include<stdlib.h>

int num1=1;//global variables for arithmetic operations


void add()
{
    int num2=1;//local variable
    while(1)//infinite loop
    {
        printf("\nFunction 1: Addition of numbers num1=%d and num2=%d is : %d",num1,num2,num1+num2);
        num1++;
        num2++;
        sleep(1);
        yield();//yield to another process
    }
    
}
void subtract()
{
    int num2=100;
    while(1)//infinite loop
    {
        printf(" \nFunction 2: Difference of numbers num2=%d and num1=%d is :%d",num2,num1,num2-num1);
        num2--;
        sleep(1);
        yield();//yield to another process
    }
}
void multiply()
{
    int num2=5;//local variable
    while(1)//infinite loop
    {
        printf("\nFunction 3: Multiplication of numbers num1=%d and num2=%d is : %d",num1,num2,num1*num2);
        num1++;
        num2++;
        sleep(1);
        yield();//yield to another process
    }
}



void main()
{
    //creating 3 functions with infinite loops and starting the threads
    start_thread(add);
    start_thread(subtract);
    start_thread(multiply);
    run();//calling the run method in threads.h
    
}
