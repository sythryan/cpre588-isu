/****************************************************************************
*  Title: Monitor.sc
*  Author: Daniel Zundel, Brandon Tomlinson, Heather Garnell
*  Date: 04/19/2010
*  Description: Displays the results of our Climate Controller
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sim.sh>

import "c_queue";

#define SIZE 30
#define N 8
#define STRING_SIZE 2

behavior temp_receiver(i_receiver temp){

	void main(){

		FILE *f1;
		int count, data;

		count = 0;
		f1 = fopen("tempout.txt","w");

		while(count<=29) {
			temp.receive(&data, sizeof(data));
			fprintf(f1,"%i\n", data);
			count++;
		}
		fclose(f1);
		exit(0);
	}
};

behavior moisture_receiver(i_receiver moisture){

	void main(){
		FILE *f1;
		int count, data;

		count = 0;
		f1 = fopen("moistout.txt","w");

		while(count<=29) {
			moisture.receive(&data, sizeof(data));
			fprintf(f1,"%i\n", data);
			count++;
		}
		fclose(f1);
		exit(0);
	}

};

behavior Heater_Flag(i_receiver Heater){

	void main(){
		FILE *f1;
		int count, data;

		count = 0;
		f1 = fopen("heaterout.txt","w");

		while(count<=29) {
			Heater.receive(&data, sizeof(data));
			if ( data == 1 )
			  fprintf(f1,"Heater On\n");
			else
			  fprintf(f1,"Heater Off\n");
			count++;
		}
		fclose(f1);
		exit(0);
	}

};

behavior Sprinkler_Flag(i_receiver Sprinkler){

	void main(){
		FILE *f1;
		int count, data;

		count = 0;
		f1 = fopen("sprinklerout.txt","w");

		while(count<=29) {
			Sprinkler.receive(&data, sizeof(data));
			if ( data == 1 )
			  fprintf(f1,"Sprinker On\n");
			else
			  fprintf(f1,"Sprinkler Off\n");
			count++;
		}
		fclose(f1);
		exit(0);

	}

};

behavior Monitor(i_receiver HEATER, i_receiver SPRINKLER, i_receiver M_OUT, i_receiver T_OUT)
{
//        char Tdata[SIZE];
//        char Mdata[SIZE];
//        char Hdata[SIZE];
//        char Sdata[SIZE];
//        int resultCount;
        
    temp_receiver readTemp(T_OUT);
    moisture_receiver readMoisture(M_OUT);
    Heater_Flag readHeater(HEATER);
    Sprinkler_Flag readSprinkler(SPRINKLER);

 	void main(void)
 	{
    		par{
    			readTemp.main();
    			readMoisture.main();
    			readHeater.main();
    			readSprinkler.main();
  		}
  }
};
