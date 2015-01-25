#include "Q.h"

Queue_Node *head=NULL;//create a global head pointer to the TCB_t structure


void start_thread(void (*function)(void))
{

    void* stck=malloc(8192);//allocate a stack with size 8192
    Queue_Node *tcb=(Queue_Node *)malloc(sizeof(Queue_Node));//  Allocate memory to a TCB_t structure
    init_TCB(tcb,function,stck,8192);//call init_tcb in TCB.h

addQ(&head,&tcb);//calling addQ in Q.h


}

void run()
{

    ucontext_t parent;
    getcontext(&parent);
    swapcontext(&parent,&(head->data));//saves curr context and executes the next context in the queue

}

void yield()
{
    rotateQ(&head);//call rotateQ in Q.h
    swapcontext(&(head->prev->data),&(head->data));//saves curr context and executes the next context in the queue
}
