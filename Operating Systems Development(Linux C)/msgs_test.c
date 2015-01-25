/*
Project 3
Girish Dasarathy - 1207555332
Niranjan Krishna Ravichandran - 1207518529
STRATEGY 2 -A mutex,producer and consumer semaphore per port
*/
#include<stdio.h>
#include "msg.h"
int server_port=0;//server port of communication
int msg_offset=0;//index of the message array (of 10 numbers) in the message queue
int client_port=1;//client port of communication 

//client process
void client()
{
int num[10];//integer message array
int cl=0,sp=0;
if(client_port<99){
cl=++client_port;//ports for client start from 2(0 and 1 are assigned for servers)98 ports available for clients

for(j=0;j<9;j++)
{
num[j]=cl;//assigning values to message array (9 locations)
}
num[9]=cl;//10th position is alloted for client port

//Distribution of clients among 2 servers
if(cl%2==0)//if client port is even , communicate with server 1
{
sp=0;

}
else//if client port is odd , communicate with server 2
{
sp=1;
}
//infinite loop for client process
while(1){

printf("\n At Client:Message sent to server %d from Client %d is: \n",sp+1,cl-1);
for(j=0;j<9;j++)
{
printf("%d ",num[j]);//message array sent from client
}
sleep(1);
printf("\n");

send(sp,num);//calling send routine with server port and the message array

receive(cl,num);//calling receive with current client port and message array

printf("\nAt Client:Message received from Server %d at Client %d \n", sp+1,cl-1);
for(i=0;i<9;i++)
{
printf("%d ",num[i]);//printing the received message from server

}
printf("\n");
sleep(2);
}
}
else
printf("\nNo more ports \n");//if number of ports becomes more than 100
}

void server()
{
int sp=0;
int final_num[10];
if(server_port<2)//assigning 0 and 1 as server ports for 2 server threads
sp=++server_port;

while(1){//infinite loop for server

receive(sp-1,final_num);//call receive routine with server port and message array

cp=final_num[9];//retrieving client port (where reply has to be sent) from message array
printf("\nAt Server:Message received at Server %d from Client %d \n",sp,cp-1);

for(i=0;i<9;i++)
{
printf("%d ",final_num[i]);
final_num[i]*=2;//doubling each message value in array 

}
sleep(1);
printf("\n");
printf("\nAt Server:Sending response from Server %d to Client %d\n",sp,cp-1);

send(cp,final_num);//calling send with client port (where reply has to be sent and the message array

sleep(2);
}	

}


int main()
{
printf("\n *** SERVER MULTIPLIES MESSAGE VALUE BY 2 AND SENDS THE RESPONSE BACK TO THE CLIENT *** \n");
init_message();//initialize structure variables sem_send,sem_recv and mutex . Initialize message array to zeroes
//Starting 3 client and 2 server threads
start_thread(client);
start_thread(client);
start_thread(client);
start_thread(server);
start_thread(server);

run();//call run

}
