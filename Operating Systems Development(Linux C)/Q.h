#include<stdio.h>
#include<stdlib.h>
#include "TCB.h"

void initQ(Queue_Node **head);//function prototype for initialize queue method
void rotateQ(Queue_Node **head);//function prototype for rotate queue method
void addQ(Queue_Node **head,Queue_Node **node);//function prototype for add queue node method
Queue_Node * delQ(Queue_Node **head);//function prototype for delete queue node method

Queue_Node * delQ(Queue_Node **head)
{
    Queue_Node *node_to_be_deleted;
    
    if((*head)==NULL)//if queue is empty throw error message when trying to delete
    {
        printf("Queue is empty!!Cannot delete \n");
        node_to_be_deleted = NULL;
    }
    
    else
    {
        Queue_Node *temp=(*head);
        
        while(temp->next!=(*head))
        temp=temp->next;
        node_to_be_deleted=(*head);
        if((*head)->next==(*head))//when trying to delete the last node in the queue make all the pointers NULL
        {
            
            (*head)->prev=NULL;
            (*head)->next=NULL;
            (*head)=NULL;
            
        }
        else//deleting from queue when there are more than one node
        {
            
            temp->next=(*head)->next;
            (*head)=(*head)->next;
            (*head)->prev=temp;
        }
    }
    return node_to_be_deleted;//returns the pointer to the deleted node
}


void initQ(Queue_Node **head)
{
    (*head)->next=(*head);//point the next pointer of head to head itself
    (*head)->prev=(*head);//point the previous pointer of head to head itself
    
}
void addQ(Queue_Node **head,Queue_Node **node)
{
    if(*head==NULL)//to add the first node when the queue is empty
    {
        *head = *node;
        (*head)->data=(*node)->data;
        initQ(head);//calling initQ method
    }
    
    else//add node to the existing queue(queue not empty)
    {
        Queue_Node *temp=(*head);
        /*while(temp->next!=(*head))
        {
            temp=temp->next;
            
        }*/
        (*head)->prev->next=(*node);
        (*node)->prev=(*head)->prev;
        (*head)->prev=(*node);
        (*node)->next=*head;
    }
    
}//end addQ


void rotateQ(Queue_Node **head)
{
    Queue_Node *deleted_node;
    if((*head)==NULL)//if the queue is empty throw error message
    printf("%s","Cannot rotate queue !!");
    else
    {
        deleted_node=delQ(head);//get the deleted node from the front of the queue
        addQ(head,&deleted_node);//add the deleted node to the end of queue
    }
}

