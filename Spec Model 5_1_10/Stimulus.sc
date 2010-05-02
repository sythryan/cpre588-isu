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

#define FILE_SIZE 30
#define STRING_SIZE 2

static  char cannedTemp[30][STRING_SIZE] = 
{{'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},
 {'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},
 {'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},
 {'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},
 {'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},
 {'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},{'6', '5'},};

static  int cannedMoist[30] = 
{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
 75, 76, 77, 78, 79, 80, 79, 78, 80, 81, 
 82, 83, 82, 81, 80, 79, 79, 78, 79, 79};  // update to realistic values


behavior TempSense(i_sender TSENSE)
{ 
	void main()
	{    	
		unsigned int i;
		char dataC[STRING_SIZE];
    
   		for(i=0; i<FILE_SIZE; i++)
   		{
   		dataC[0] = cannedTemp[i][0]; 
   		dataC[1] = cannedTemp[i][1];
			 TSENSE.send(&dataC,sizeof(STRING_SIZE));
     	}
 	}
};

behavior MoistSense(i_sender MSENSE){

	void main(){
	
		int i;
		FILE *fp;
		char dataM[STRING_SIZE];
		
		if((fp = fopen("cannedMois.txt", "r")) != NULL)
     	{
     		printf("\nFile Found!");
      	
     		for(i=0; i<FILE_SIZE; i++)
     		{
     			fscanf(fp, "STRING_SIZE%c", dataM);
     			MSENSE.send(dataM,STRING_SIZE);
      		}
     	}
     	fclose(fp);
	}

};



behavior UserEntry(i_sender USERSET){

	void main(){
		
	//char cmpBuf[] = "exit";
  // 	char cmpTemp[] = "Temp";
   // 	char cmpMoist[] = "Moist";
    //	char function[40]; // make big enough to avoid overflow
    	char tempdata[40];
	char moistdata[40];
    	int convertedtempdata;
	int convertedmoistdata;
    	 
    	printf("\n--****--Climate Control--****--");
     	printf("\n-------------------------------");
     	
	printf("\n\nEnter Temperature Level (Temp): ");
	fgets(tempdata, sizeof(tempdata), stdin);
	sscanf(tempdata, "%d", &convertedtempdata);
	printf("Temp Data Entered: %i\n",convertedtempdata);
	USERSET.send(&convertedtempdata, sizeof(convertedtempdata));

	printf("\n\nEnter Moisture Level (Moist): ");
	fgets(moistdata, sizeof(moistdata), stdin);
	sscanf(moistdata, "%d", &convertedmoistdata);
	USERSET.send(&convertedmoistdata, sizeof(convertedmoistdata));

//     	printf("\n\nMoisture Level (Moist) Temperature Level (Temp) or (exit) to quit program: ");
//     	fgets(function, sizeof(function), stdin);

     	//********** Exit ********** 
//     	if (!(strcmp(function,cmpBuf))) {
//			printf("Simulation Finished Successfuly!\n");
//			exit(0);
//     	}   

//     	if(!(strcmp(function,cmpTemp)))
//     	{
//     	printf("\n\n\n\nEnter your desired temperature: ");
//      		fgets(data, sizeof(data), stdin);
//     	}

//     	if(!(strcmp(function,cmpMoist)))
//     	{
//	     	printf("\n\n\n\nEnter your desired humidity: ");
//   			fgets(data, sizeof(data), stdin);
//     	}
//     	sscanf(data, "%d", &convertedData);
//     	USERSET.send(&convertedData, sizeof(convertedData));
//		USERSET.send(&data, sizeof(data));
    }
};

behavior Stimulus(i_sender USER_SET, i_sender M_SENSE, i_sender T_SENSE)
{    
	TempSense	TTSense(T_SENSE);
	MoistSense	MSense(M_SENSE);
	UserEntry	User(USER_SET);

	void main(void) {
		par{
			User.main();
			TTSense.main();
			MSense.main();
		}
	}
};
