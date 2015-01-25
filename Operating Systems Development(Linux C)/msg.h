//STRATEGY 2

#include<stdio.h>
#include"sem.h"
const int N=100;//number of ports

typedef struct message{
int message_value[10][10];//Dimension 1 - number of messages , Dimension 2 - Each message can have 10 integers
semaphore_t *sem_send;
semaphore_t *sem_recv;
semaphore_t *mutex;
int in;
int out;
}message_structure;

//each port will have a producer and consumer semaphore and a mutex - strategy 2
message_structure *port[100];

int i=0,j=0,k=0;

int cp=0;

void init_message()
{
	
for(i=0;i<N;i++)
{

port[i]=(message_structure *)malloc(sizeof(message_structure));

port[i]->sem_send=create_sem(0);
port[i]->sem_recv=create_sem(10);
port[i]->mutex=create_sem(1);
port[i]->in=0;
port[i]->out=0;

for(j=0;j<10;j++)
{
for(k=0;k<10;k++)
{
port[i]->message_value[j][k]=0;//init message values to 0

}
}
}

}

//Send routine
void send(int port_s,int message[])
{

P(port[port_s]->sem_recv);
P(port[port_s]->mutex);

for(i=0;i<10;i++)
{
port[port_s]->message_value[port[port_s]->in][i]=message[i];
}
port[port_s]->in=(port[port_s]->in+1)%10;
V(port[port_s]->mutex);
V(port[port_s]->sem_send);

}


//Receive routine
void receive(int port_s,int message[])
{
P(port[port_s]->sem_send);
P(port[port_s]->mutex);

for(i=0;i<10;i++)
{
message[i]=port[port_s]->message_value[port[port_s]->out][i];
}

port[port_s]->out=(port[port_s]->out+1)%10;

V(port[port_s]->mutex);
V(port[port_s]->sem_recv);

}

