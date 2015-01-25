/*
Project 1
Girish Dasarathy-1207555332
Niranjan Krishna Ravichandran-1207518529
*/
#include<stdio.h>
#include<stdlib.h>
#include "sem.h"

semaphore_t *r_sem,*w_sem,*mutex;//Declaring required semaphore variables
int wwc=0,wc=0,rc=0,rwc=0,id=0,idw=0;//Initializing the variables for readers and writers

//Reader entry module
void reader_entry(int id)
{

P(mutex);
	if(wwc>0 || wc>0)
	{
		rwc++;
		V(mutex);
		P(r_sem);
		rwc--;
	}	
		rc++;
		if(rwc>0)
			V(r_sem);
		else
			V(mutex);
	
}

//Writer exit module
void writer_exit(int id)
{
P(mutex);
wc--;
	if(rwc>0)
		V(r_sem);
	else if(wwc>0)
		V(w_sem);
	else
		V(mutex);
}

//Writer entry module
void writer_entry(int id)
{

P(mutex);
	if(wc>0 || rc>0)
	{
		wwc++;
		V(mutex);
		P(w_sem);
		wwc--;
	}
wc++;
V(mutex);
}

//Reader exit module
void reader_exit(int id)
{
P(mutex);
rc--;
	if(wwc > 0 && rc==0)
		{
		V(w_sem);
		}
	else
		{
		V(mutex);
		}
}


void reader()
{
int local_id;
//Locking and unlocking the critical section to modify the shared variable
P(mutex);
	local_id=id++;
V(mutex);


while(1)
	{
		reader_entry(local_id);
		printf("\n Reader %d reading \n",local_id);	
		sleep(1);
		reader_exit(local_id);
	}
}


void writer()
{
int local_id;
//Locking and unlocking the critical section to modify the shared variable
P(mutex);
	local_id=idw++;
V(mutex);

while(1)
	{
		writer_entry(local_id);
		printf("\n Writer %d writing \n",local_id);	
		sleep(1);
		writer_exit(local_id);
	}
}


int main()
{
r_sem=create_sem(0);
w_sem=create_sem(0);
mutex=create_sem(1);

//Creation of 4 reader and 3 writer threads
start_thread(writer);
start_thread(writer);
start_thread(writer);
start_thread(reader);
start_thread(reader);
start_thread(reader);
start_thread(reader);

run();//Start the execution of the reader and writer threads.
while(1)
sleep(1);
}
