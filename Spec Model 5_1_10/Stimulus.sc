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

behavior TempSense(i_sender TSENSE)
{ 
	void main()
	{    	
		FILE *f1;
		char temp[3];
		unsigned int count = 0;

		f1 = fopen("tempin.txt","r");

		while (fgets(temp,3,f1) != NULL) {
			TSENSE.send(&temp,sizeof(temp));
			count ++;
		}
		fclose(f1);
 	}
};

behavior MoistSense(i_sender MSENSE){

	void main()
	{
		FILE *f1;
		char moist[3];
		unsigned int count = 0;

		f1 = fopen("moistin.txt","r");

		while (fgets(moist,3,f1) != NULL) {
			MSENSE.send(&moist,sizeof(moist));
			count ++;
		}
		fclose(f1);
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
