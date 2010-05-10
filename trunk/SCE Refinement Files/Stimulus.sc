/****************************************************************************
*  Title: Stimulus.sc
*  Author: Daniel Zundel, Brandon Tomlinson, Heather Garnell
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
    		int t1 = 0;
    
		f1 = fopen("tempin.txt","r");

		while (! feof(f1) ) 
		{
		   fscanf(f1, "%d", &t1);  	
			 TSENSE.send(&t1,sizeof(t1));			
		}

		fclose(f1);
 	}
};

behavior MoistSense(i_sender MSENSE){

	void main()
	{
		FILE *f1;
    		int t1 = 0;

		f1 = fopen("moistin.txt","r");

		while (! feof(f1) ) 
		{
		   fscanf(f1, "%d", &t1);  	
			 MSENSE.send(&t1,sizeof(t1));			
		}
		fclose(f1);
	}
};

behavior UserEntry(i_sender USERSET){

	void main()
	{
		
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
