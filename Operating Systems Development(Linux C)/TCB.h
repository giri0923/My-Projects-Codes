#include <ucontext.h>
#include <string.h>

//creating a structure containing the context(data),next and previous pointers to the node TCB_t
typedef struct TCB_t
{
    ucontext_t data;
    struct TCB_t *next,*prev;
}Queue_Node;


void init_TCB(Queue_Node *tcb,void *function,void *stackP,int stack_size)
{
    memset(tcb,'\0',sizeof(Queue_Node));//sets memory pointed by tcb with null to a sizeof(Queue_Node) bytes
    getcontext(&tcb->data);//get the context of calling thread
    tcb->data.uc_stack.ss_sp=stackP;//set stack pointer for the current context
    tcb->data.uc_stack.ss_size=(size_t) stack_size;//set stack size
    makecontext(&tcb->data,function,0);// modifies the context specified by tcb
}
