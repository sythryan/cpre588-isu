/****************************************************************************
*  Title: Monitor.sc
*  Author: Daniel Zundel
*  Date: 04/19/2010
*  Description: Displays the results of our Climate Controller
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sim.sh>

import "c_queue";
import "i_receiver";

#define SIZE 30
#define N 8

#define STRING_SIZE 2


behavior Printer ()
{
 	void main(void)
 	{
    	FILE *fp;
      	 
        char name[] = "Results.txt";
        char Tdata[SIZE];
        char Mdata[SIZE];
        char Hdata[SIZE];
        char Sdata[SIZE];
        
        unsigned int i;
        unsigned int temp;
        unsigned int moist;
        unsigned int heat;
        unsigned int sprink;
        
      if((fp = fopen(name, "w")) == NULL)  {
         	printf("\nError opening file!"); exit(0); 
        }
        
        fprintf(fp, "Simulation Results:/n");
        fprintf(fp, "--------------------------------------------------------------/n");
        fprintf(fp, "data #\ttemp\tmoist\theater\sprinkler\n");
        
        for(i=0; i<SIZE; i++)
        {
        	temp = (int) Tdata[i];
        	moist = (int) Mdata[i];
        	heat = (int) Hdata[i];
        	sprink = (int) Sdata[i];
        	
        	fprintf(fp, "%d\t%d\t%d\t%d\t%d",(i+1), temp, moist, heat, sprink);
        }
        fclose(fp);
        printf("\nLeaving Monitor\n\n");
        //exit(0);    //Uncomment exit(0); if this is how you would like to exit the program
  }
};

behavior temp_receiver(i_receiver temp, out char[] data){

	void main(){
		int data;
		temp.receive(data, STRING_SIZE);
	}

};

behavior moisture_receiver(i_receiver moisture){

	void main(){
		int data;
		moisture.receive(data, STRING_SIZE);
	}

};

behavior Heater_Flag(i_receiver Heater){

	void main(){
		int data;
		Heater.receive(data, STRING_SIZE);
	}

};

behavior Sprinkler_Flag(i_receiver Sprinkler){

	void main(){
		int data;
		Sprinkler.receive(data, SIZE);
	}

};

behavior Monitor(i_receiver HEATER, i_receiver SPRINKLER, i_receiver M_OUT, i_receiver T_OUT)
{
        char Tdata[SIZE];
        char Mdata[SIZE];
        char Hdata[SIZE];
        char Sdata[SIZE];
        int resultCount;
        
    temp_receiver readTemp(T_OUT, Tdata);
    moisture_receiver readMoisture(M_OUT);
    Heater_Flag readHeater(HEATER);
    Sprinkler_Flag readSprinkler(SPRINKLER);
    Printer printer;

 	void main(void)
 	{
    		par{
    			readTemp.main();
    			readMoisture.main();
    			readHeater.main();
    			readSprinkler.main();
    			printer.main();
  		}
  }
};


