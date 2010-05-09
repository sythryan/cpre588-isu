/****************************************************************************
*  Title: Main.cpp
*  Author: Heather Garnell
*  Date: 05/1/2010
*  Description: get temp sensor data, set control flags
****************************************************************************/


#include "systemc.h"
#include <stdio.h>
#include <systemc.h> 
#include <iostream>

//------------------------------------------------------------
// HeaterControl
//------------------------------------------------------------
SC_MODULE(HeaterControl)
{
  sc_fifo_in<bool> HCFlag;
  sc_fifo_out<bool> OHeaterOn;  
  
  void update();

	// CONSTRUCTOR  
  SC_CTOR(HeaterControl)
  {
    SC_THREAD(update);
  }
};
void HeaterControl::update()
{
  bool flag;
  while(1)
  {
      flag = HCFlag.read();
   
			if (flag)   printf("HC Read: On \n");
			else  		  printf("HC Read: Off \n");   
			  
			//waitfor(10);
			OHeaterOn.write(flag);
  }
}



//------------------------------------------------------------
//          TempProcessing
//------------------------------------------------------------
SC_MODULE(TempProcessing)
{	
  sc_fifo_in < int> Sensor_T, Desired_T;
  sc_fifo_out<bool> TPFlag;
  
  void update();

	// CONSTRUCTOR  
  SC_CTOR(TempProcessing)
  {
    SC_THREAD(update);
  }
};

void TempProcessing::update()
{
  int DESIRED = Desired_T.read();
  printf("\nReceived Desired Temp: %d. \n\n", DESIRED);
  int temp = 0;
  while(1)
  {
    temp = Sensor_T.read();
    printf("TP read sensor temp: %d. \n", temp);
    
    if (temp  > (DESIRED-5))    TPFlag.write(true);
    else                        TPFlag.write(false);         
  }
}


//------------------------------------------------------------
//                Design_PT
//------------------------------------------------------------
SC_MODULE (Design_PT)
{
  sc_fifo <bool> CTRLFLAG;
  
  sc_fifo_in<int > SensorT, DesiredT; 
  sc_fifo_out<bool > OFlag;
  sc_fifo_out<int > OTPassthru;
 
	TempProcessing *TP;
  HeaterControl *HC;
   
	// CONSTRUCTOR
  SC_CTOR(Design_PT)
	{	  
	  // Create a new instance
    TP = new TempProcessing("TP1");
    HC = new HeaterControl("HC1");
    
	  CTRLFLAG = new sc_fifo<bool>("internal_ctrl_flag");
	  
    // Bind the ports
    TP->Sensor_T(SensorT); 
    TP->Desired_T(DesiredT); 
    TP->OFlag(OFlag); 
    OTPFlag(*CTRLFLAG);
    IHCGlag(*CTRLFLAG);
	}  
};


//------------------------------------------------------------
//             T E S T      B E N C H
//------------------------------------------------------------
SC_MODULE (Test_Bench)
{  
	// Instantiate the Design_PT 
	Design_PT *p_t;
	
	sc_fifo<int> *TSENSE;
	sc_fifo<int> *TDESIRED;
	sc_fifo<bool> *TCTRL;
	sc_fifo<int> *TPASSTHRU;
	sc_fifo_out<int> OTSENSE;
	sc_fifo_out<int> OTDESIRED;
	sc_fifo_in<bool> ITCTRL;
	sc_fifo_in<int> ITPASSTHRU;
	
	void Stimulus();  
	void Monitor();
   
	// CONSTRUCTOR
  SC_CTOR(Test_Bench)
	{  
	  SC_THREAD(Stimulus);	  
	  SC_THREAD(Monitor); 
	  
	  p_t = new Design_PT("Design_PT1");
	  
	  TSENSE = new sc_fifo<int>("sensor_t_fifo");
	  TDESIRED = new sc_fifo<int>("user_t_fifo");
	  TCTRL = new sc_fifo<bool>("control_t_fifo");
	  TPASSTHRU = new sc_fifo<int>("passthru_t_fifo");
	  
	  p_t->SensorT(*TSENSE);
	  p_t->DesiredT(*TDESIRED);
	  p_t->OFlag(*TCTRL);
	  p_t->OTPassthru(*TPASSTHRU);
	  
	  OTSENSE(*TSENSE);
	  OTDESIRED(*TDESIRED);
	  ITCTRL(*TCTRL);
	  ITPASSTHRU(*TPASSTHRU);
  }	
};

//---------------------------------------
//            S T I M U L U S
//---------------------------------------
void Test_Bench::Stimulus()
{		 
	FILE *f1;
  int t1 = 0, convertedtempdata;
  
	if ((f1 = fopen("tempin.txt","r"))==NULL) {
    printf("Error opening input file.\n"); exit(1); }
  else
  {
    char tempdata[40];	 
  	printf("\n--****--Climate Control--****--");
   	printf("\n-------------------------------");    	
  	printf("\n\nEnter Desired Temperature Level (Temp): ");
  	fgets(tempdata, sizeof(tempdata), stdin);
  	sscanf(tempdata, "%d", &convertedtempdata);
  	OTDESIRED.write(convertedtempdata);
  	
	  while (! feof(f1) ) 
		{
		   fscanf(f1, "%d", &t1);  	
			 OTSENSE.write(t1);		
		}
 	}
	fclose(f1);
}

//---------------------------------------
//            M O N I T O R
//---------------------------------------
void Test_Bench::Monitor()
{		
		FILE *f1, *f2;
		int count=0;
	  bool data, temp;
	  
    if ( ((f1 = fopen("tempout.txt","w"))   == NULL)    || 
         ((f2 = fopen("heaterout.txt","w")) == NULL) ) {
                    printf("Error opening output file.\n"); 
                    exit(1); }
    else
    {
  		while(count<=29) 
  		{
  			data = ITCTRL.read();
  			if (data){  printf("Monitor - Heater Control: On.  ");
  			            fprintf(f1,"Heater Control: On \n"); }
  			else { 		  printf("Monitor - Heater Control: Off.  ");
  			  	        fprintf(f1,"Heater Control: Off \n");}
  			
  			temp = ITPASSTHRU.read();
  			printf(" Sensor Data: %d. \n", temp);
  			fprintf(f2,"%i\n", temp); 
  			
  			count++;
  		}
  	}
		fclose(f1);
		fclose(f2);
		exit(0);	
}

//------------------------------------------------------------
// sc_main
//------------------------------------------------------------
int sc_main(int argc, char *argv[])
{
	Test_Bench tb("tb_1");
	sc_start();
	return 0;
}
