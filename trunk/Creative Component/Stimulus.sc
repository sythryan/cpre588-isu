/****************************************************************************
*  Title: Stimulus.sc
*  Author: Daniel Zundel, Brandon Tomlinson, Heather Garnell
*  Date: 03/20/2010
*  Description: IP Stimulus for Testbench
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
		char moist[3];

		f1 = fopen("moistin.txt","r");

		while (fgets(moist,3,f1) != NULL) {
			MSENSE.send(&moist,sizeof(moist));
		}
		fclose(f1);
	}
};


behavior Stimulus(i_sender T_SENSE, i_sender M_SENSE)
{    
	TempSense	TTSense(T_SENSE);
	MoistSense	MSense(M_SENSE);

	void main(void) {
		par{
			TTSense.main();
			MSense.main();
		}
	}
};

