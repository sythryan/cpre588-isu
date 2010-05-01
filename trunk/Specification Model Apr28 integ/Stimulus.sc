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

import "c_queue";


static  int cannedTemp[30] = 
{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
 75, 76, 77, 78, 79, 80, 79, 78, 80, 81, 
 82, 83, 82, 81, 80, 79, 79, 78, 79, 79};
 
static  int cannedMoist[30] = 
{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
 75, 76, 77, 78, 79, 80, 79, 78, 80, 81, 
 82, 83, 82, 81, 80, 79, 79, 78, 79, 79};  // update to realistic values

#define FILE_SIZE 30
#define STRING_SIZE 2



behavior TempSense(i_sender TSENSE)
{ 
	void main()
	{    	
    int i;
    
   		for(i=0; i<FILE_SIZE; i++)
   		{
   			TSENSE.send(cannedTemp[i],STRING_SIZE); 
     	}
  }
};

behavior MoistSense(i_sender MSENSE){

	void main()
	{    	
    int i;
   		for(i=0; i<FILE_SIZE; i++)
   		{
   			MSENSE.send(cannedMoist[i],STRING_SIZE); 
     	}
  }	
};

behavior UserEntry(i_sender USERSET){

	void main(){
		
		  char cmpBuf[] = "exit";
    	char cmpTemp[] = "Temp";
    	char cmpMoist[] = "Moist";
    	char function[40]; // make big enough to avoid overflow
    	char data[40]; 
    	int convertedData;
    	 
    	printf("\n--****--Climate Control--****--");
     	printf("\n-------------------------------");
     	printf("\n\nMoisture Level (Moist) Temperature Level (Temp) or (exit) to quit program: ");
     	fgets(function, sizeof(function), stdin);

     	//********** Exit ********** 
     	if (!(strcmp(function,cmpBuf))) {
			printf("Simulation Finished Successfuly!\n");
			exit(0);
     	}   

     	if(!(strcmp(function, cmpTemp)))
     	{
	      	printf("\n\n\n\nEnter your desired temperature: ");
      		fgets(data, sizeof(data), stdin);
     	}

     	if(!(strcmp(function, cmpMoist)))
     	{
	     	printf("\n\n\n\nEnter your desired humidity: ");
   			fgets(data, sizeof(data), stdin);
     	}
     	sscanf(data, "%d", &convertedData);
     	USERSET.send(convertedData, sizeof(convertedData));
    }
};

behavior Stimulus(i_sender USER_SET, i_sender M_SENSE, i_sender T_SENSE)
{    
  TempSense	  TTSense(T_SENSE);
  MoistSense	MSense(M_SENSE);
	UserEntry	  User(USER_SET);

	void main(void)
  {
	  par{
		  User.main();
     	TTSense.main();
     	MSense.main();
     }
  }
};

