




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

behavior temp_receiver(i_receiver temp, char *data){

	void main(){
		temp.receive(data, SIZE);
	}

};

behavior moisture_receiver(i_receiver moisture, char *data){

	void main(){
		moisture.receive(data, SIZE);
	}

};

behavior Heater_Flag(i_receiver Heater, char *data){

	void main(){
		Heater.receive(data, SIZE);
	}

};

behavior Sprinkler_Flag(i_receiver Sprinkler, char *data){

	void main(){
		Sprinkler.receive(data, SIZE);
	}

};

//behavior Monitor(i_receiver heatcontrol, i_receiver sprinklercontrol, i_receiver tempout, i_receiver moistureout)

behavior Monitor(i_receiver HEATER, i_receiver SPRINKLER, i_receiver M_OUT, i_receiver T_OUT)
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
    
    temp_receiver		readTemp(H_OUT, Tdata);
    moisture_receiver	readMoisture(H_OUT, Mdata);
    Heater_Flag			readHeater(HEATER, Hdata);
    Sprinkler_Flag		readSprinkler(SPRINKLER, Sdata);
	
    printf("\nStarting Monitor");
        
    par{
    	readTemp.main();
    	readMoisture.main();
    	readHeater.main();
    	readSprinkler.main();
    }
    
    if((fp = fopen(name, "w")) == NULL)
    {
     	printf("\nError opening file!");
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


