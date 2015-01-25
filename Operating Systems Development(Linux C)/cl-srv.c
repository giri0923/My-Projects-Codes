/*
Project 4
Girish Dasarathy - 1207555332
Niranjan Krishna Ravichandran - 1207518529
*/

#include<stdio.h>
#include "msg.h"
#include<stdlib.h>
#include<math.h>
int client_port=0;

void client()
{
int num[10];//integer message array
int cl=0,sp=0,i=0,j=0,k=0;
int position=-1;
char input_message_1[2][10]={"Rainbow","Malware"};
char input_message_2[2][10]={"Program","Systems"};
char input_message_3[10]={"GetData"};
int rand_index=0;
int int_input_msg1[10];
int int_input_msg2[10];
int int_input_msg3[10];
if(client_port<99){
cl=++client_port;
while(1){
if(cl==1){
rand_index=random()%2;
input_message_1[rand_index][9]=cl;//10th position is allotted for client port
input_message_1[rand_index][8]=rand()%10;
input_message_1[rand_index][7]=rand()%3;
char oper1[30]="Add to table";
char oper2[30]="Replace with Z";
char oper3[30]="Modify each char(+5)";
printf("\n At Client:Message sent to server %d from Client %d is: \n",1,cl);
for(j=0;j<7;j++)
{
int_input_msg1[j]=(int)input_message_1[rand_index][j];
}
for(j=7;j<10;j++)
{
int_input_msg1[j]=input_message_1[rand_index][j];
}
for(j=0;j<7;j++)
{
printf("%c ",input_message_1[rand_index][j]);
}
printf("\n THE operation is ");

switch(input_message_1[rand_index][7])
{
case 0:printf("%s",oper1);
break;

case 1:printf("%s",oper2);
break;

case 2:printf("%s",oper3);
break;
}


sleep(1);
printf("\n");
send(0,int_input_msg1);
}
else if(cl==2){
char oper1[30]="Add to table";
char oper2[30]="Replace with Z";
char oper3[30]="Modify each char(+5)";
rand_index=random()%2;
input_message_2[rand_index][9]=cl;//10th position is allotted for client port
input_message_2[rand_index][8]=rand()%10;
input_message_2[rand_index][7]=rand()%3;
printf("\n At Client:Message sent to server %d from Client %d is: \n",1,cl);
for(j=0;j<7;j++)
{
int_input_msg2[j]=(int)input_message_2[rand_index][j];
}
for(j=7;j<10;j++)
{
int_input_msg2[j]=input_message_2[rand_index][j];
}
for(j=0;j<7;j++)
{
printf("%c",input_message_2[rand_index][j]);
}
printf("\n THE operation is ");

switch(input_message_1[rand_index][7])
{
case 0:printf("%s",oper1);
break;

case 1:printf("%s",oper2);
break;

case 2:printf("%s",oper3);
break;
}


sleep(1);
printf("\n");
send(0,int_input_msg2);
}
int result[10][20];
for(i=0;i<10;i++)
{
for(j=0;j<20;j++)
{
result[i][j]=(int)'#';
}
}



if(cl==3){

input_message_3[9]=cl;//10th position is allotted for client port
input_message_3[8]=rand()%10;
input_message_3[7]=rand()%3;
printf("\n ONLY ONE SEND REQUEST FROM THIS CLIENT:Message sent to server %d from Client %d is: \n",1,cl);
for(j=0;j<7;j++)
{
int_input_msg3[j]=(int)input_message_3[j];
}
for(j=7;j<10;j++)
{
int_input_msg3[j]=input_message_3[j];
}
for(j=0;j<7;j++)
{
printf("%c ",input_message_3[j]);
}
sleep(1);
printf("\n");
//Send only one request to server
send(0,int_input_msg3);
//Receive all entries from server table
receive(cl,result[0]);
receive(cl,result[1]);
receive(cl,result[2]);
receive(cl,result[3]);
receive(cl,result[4]);
receive(cl,result[5]);
receive(cl,result[6]);
receive(cl,result[7]);
receive(cl,result[8]);
receive(cl,result[9]);
printf("\n****** Client:Message (10 Strings) received from Server %d at Client %d .# denotes the field is empty\n", sp,cl);


for(i=0;i<10;i++)
{
printf("\n");
for(j=0;j<7;j++)
{
printf("%c ",result[i][j]);
}
}


}
printf("\n");
sleep(5);
}
}
else
printf("\nNo more ports \n");
}



void server()
{
int counter=0;
char server_table[10][20];
int server_table_t[10][20];

int position=0;
int operation=0;
int cp=0;
int final_num[10];
int i=0,j=0;

for(i=0;i<10;i++)
{
for(j=0;j<20;j++)
{
server_table[i][j]='#';
}
}
while(1){
    receive(0,final_num);
    cp=final_num[9];
printf("\n## Server:Message received at Server %d from Client %d \n",1,cp);
    if(cp==3){
printf("\n HERE %d",cp);
printf("\n@ Server:Sending response from Server %d to Client %d as 10 parts \n",1,cp);
for(i=0;i<10;i++)
{
for(j=0;j<10;j++)
{
server_table_t[i][j]=(int)server_table[i][j];
}
}
//Send response to clients
    send(cp,server_table_t[0]);
    send(cp,server_table_t[1]);
send(cp,server_table_t[2]);
send(cp,server_table_t[3]);
send(cp,server_table_t[4]);
send(cp,server_table_t[5]);
send(cp,server_table_t[6]);
send(cp,server_table_t[7]);
send(cp,server_table_t[8]);
send(cp,server_table_t[9]);
}
else{
position=final_num[8];
operation=final_num[7];
//Server Operations
if(operation==0){
for(i=0;i<7;i++)
{
server_table[counter][i]=(char)final_num[i];
}
counter=(counter+1)%10;
}
else if(operation==1){
for(i=0;i<7;i++)
{
server_table[position][i]='z';
}
}
else{
for(i=0;i<7;i++)
{
server_table[position][i]=(char)(final_num[i]+5);
}
}
    }
}
}



int main()
{
printf("\n *** SERVER STORES ARRAY OF STRINGS AND SENDS THE RESPONSE BACK TO THE 3 rd CLIENT ON REQUEST *** \n");
init_message();//initialize structure variables sem_send,sem_recv and mutex . Initialize message array to zeroes
//Starting 3 client and 1 server thread
start_thread(client);
start_thread(client);
start_thread(client);
start_thread(server);
run();//call run

}
