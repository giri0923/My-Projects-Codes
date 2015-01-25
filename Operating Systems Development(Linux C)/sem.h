#include "threads.h"

//Declaring a semaphore type
typedef struct semaphore_t
{
	int count; 
	Queue_Node *semQ;
}semaphore_t;

//Initialization of a semaphore
semaphore_t* create_sem(int i)
{
	semaphore_t *s=(semaphore_t *)malloc(sizeof(semaphore_t));
	s->count=i;
	return s;
}

//P operation on a semaphore
void P(semaphore_t *s)
{
	s->count--;
	Queue_Node *del;

	if(s->count<0)
		{
			del=delQ(&head);
			addQ(&(s->semQ),&del);
			swapcontext(&(del->data),&(head->data));
		}	

}

//V operation on a semaphore
void V(semaphore_t *s)
{
	Queue_Node *del;
	s->count++;

	if(s->count<=0)
		{
			del=delQ(&(s->semQ));
			addQ(&head,&(del));
		}
	yield();
}


