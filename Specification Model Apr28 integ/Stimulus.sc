/****************************************************************************
*  Title: Stimulus.sc
*  Author: Daniel Zundel
*  Date: 03/20/2010
*  Description: 
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sim.sh>

#define SIZE 30
#define STRING_SIZE 2

import "c_queue";


behavior TempSense(i_sender TSENSE){

	void main(){
	
		int i;
		FILE *fp;
		//unsigned 
		char dataT[SIZE];
		
		if((fp = fopen("Canned_temp.txt", "r")) != NULL)
     	{
     		printf("\nFile Found!");
      	
     		for(i=0; i<SIZE; i++)
     		{
     			fgets(dataT, STRING_SIZE, fp);
     			TSENSE.send(dataT,STRING_SIZE);
      		}
     	}
     	fclose(fp);
	}

};

behavior MoistSense(i_sender MSENSE){

	void main(){
	
	int i, data;
	FILE *fp;
	
		if((fp = fopen("Canned_moist.txt", "r")) != NULL)
     	{
     		printf("\nFile Found!");
      	
     		for(i=0; i<SIZE; i++)
     		{
     			fgets(data, STRING_SIZE, fp);
     			MSENSE.send(data,STRING_SIZE);
      		}
     	}
     	fclose(fp);
	}
};

behavior UserEntry(i_sender USERSET){

	int data;

	void main(){
		//unsigned
		 char cmpBuf[] = "exit";
    	//unsigned 
    	char cmpTemp[] = "Temp";
    	//unsigned 
    	char cmpMoist[] = "Moist";
    	//unsigned 
    	char function[40];
    	 
    	printf("\n--****--Climate Control--****--");
     	printf("\n-------------------------------");
     	printf("\n\nMoisture Level (Moist) Temperature Level (Temp) or (exit) to quit program: ");
     	gets(function);
     	//********** Exit ********** 
     	if (!(strcmp(function,cmpBuf))) {
			printf("Simulation Finished Successfuly!\n");
			exit(0);
     	}   
     	if(!(strcmp(function, cmpTemp)))
     	{
	      	printf("\n\n\n\nEnter your desired temperature: ");
      		gets(data);
     	}
     	if(!(strcmp(function, cmpMoist)))
     	{
	     	printf("\n\n\n\nEnter your desired humidity: ");
   			gets(data);
     	}
     	USERSET.send(data, SIZE);
    }
};

behavior Stimulus(i_sender USER_SET, i_sender M_SENSE, i_sender T_SENSE)
{
  
    //unsigned
     char T_data[SIZE];
    //unsigned 
    char M_data[SIZE];
    //unsigned 
    char U_data[SIZE];
    
    TempSense	TTSense(T_SENSE);
  	MoistSense	MSense(M_SENSE);
	UserEntry	User(USER_SET);

	void main(void)
  {

	par{
		User.main();
     	TTSense.main();
     	MSense.main();
     }
  }
};

