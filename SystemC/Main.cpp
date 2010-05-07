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
  sc_fifo_in<bool> IHeaterOn;
   
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
    flag = IHeaterOn.read();
   
			if (flag)   printf("HC Read: On \n");
			else  		  printf("HC Read: Off \n");   
  }
};



//------------------------------------------------------------
//          TempProcessing
//------------------------------------------------------------
SC_MODULE(TempProcessing)
{	
  sc_fifo_in < int> Sensor_T, Desired_T;
  sc_fifo_out<bool> OFlag;
  
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
  while(1)
  {
    int temp = Sensor_T.read();
    printf("TP read sensor temp: %d. \n", temp);
    
    if (temp  > (DESIRED-5))    OFlag.write(true);
    else                        OFlag.write(false);         
  }
}


//------------------------------------------------------------
//                Design_PT
//------------------------------------------------------------
SC_MODULE (Design_PT)
{
  sc_fifo_in< int> SensorT, DesiredT; 
  sc_fifo_out<bool> OFlag;
 
	TempProcessing *TP;
  HeaterControl *HC;
   
	// CONSTRUCTOR
  SC_CTOR(Design_PT)
	{	  
	  // Create a new instance
    TP = new TempProcessing("TP1");
    HC = new HeaterControl("HC1");
    
    // Bind the ports
    TP->Sensor_T(SensorT); 
    TP->Desired_T(DesiredT); 
    TP->OFlag(OFlag); 
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
	sc_fifo_out<int> OTSENSE;
	sc_fifo_out<int> OTDESIRED;
	sc_fifo_in<bool> ITCTRL;
	
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
	  
	  p_t->SensorT(*TSENSE);
	  p_t->DesiredT(*TDESIRED);
	  p_t->OFlag(*TCTRL);
	  OTSENSE(*TSENSE);
	  OTDESIRED(*TDESIRED);
	  ITCTRL(*TCTRL);
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
		FILE *f1;
		int count=0;
	  bool data;
	  
    if ((f1 = fopen("tempout.txt","w"))==NULL) {
      printf("Error opening output file.\n"); exit(1); }
    else
    {
  		while(count<=29) {
  			data = ITCTRL.read();
  			if (data){  printf("Monitor Heater Control: On \n");
  			            fprintf(f1,"Heater Control: On \n"); }
  			else { 		  printf("Monitor Heater Control: Off \n");
  			  	        fprintf(f1,"Heater Control: Off \n");}
  			count++;
  		}
  	}
		fclose(f1);
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









/****************************************************************************
*  Title: Main.cpp
*  Author: Heather Garnell
*  Date: 05/1/2010
*  Description: get temp sensor data, set control flags
****************************************************************************/
/*

#include "systemc.h"
#include <stdio.h>
#include <systemc.h> 
#include <iostream>


//------------------------------------------------------------
// TempProcessing
//------------------------------------------------------------
SC_MODULE(TempProcessing)
{	
  sc_in <unsigned int> ISensor_T, IDesired_T;
  sc_out <bool> IHeaterOn;
  
  void update();

	// CONSTRUCTOR  
  SC_CTOR(TempProcessing)
  {
    SC_THREAD(update);
		sensitive << ISensor_T << IDesired_T;
  }
};

void TempProcessing::update()
{
  // Wait for start signal
  
  // Retrieve data
  if ((ISensor_T + 5) < IDesired_T){
    IHeaterOn = true;
    std::cout << "Heater Off" << std::endl;}
  else{
    IHeaterOn = false;
    std::cout << "Heater Off" << std::endl;
  }
}

//------------------------------------------------------------
// Process_Temperature
//------------------------------------------------------------
SC_MODULE (Process_Temperature)
{
  sc_in<unsigned int> SensorT, DesiredT;
  sc_out<bool> HeaterOn;
  
  sc_signal <unsigned int> ISENSOR_T, IDESIRED_T;
  sc_signal <bool> IHEATER_ON;
  
	TempProcessing *TP;

   
	// CONSTRUCTOR
  SC_CTOR(Process_Temperature)
	{
	  // Initialize inputs
    ISENSOR_T = 0;
    IDESIRED_T = 0;
  
	  // Create a new instance
    TP = new TempProcessing("TP1");
    
    // Bind the ports
    TP->ISensor_T(ISENSOR_T);
  	TP->IDesired_T(IDESIRED_T);
  	TP->IHeaterOn(IHEATER_ON);
	}
  
};


//------------------------------------------------------------
// sc_main
//------------------------------------------------------------
int sc_main(int argc, char *argv[])
{
  sc_signal <unsigned int> SENSOR_T, DESIRED_T;
  sc_signal <bool> HEATER_ON;
  
  // Initialize inputs
  SENSOR_T = 0;
  DESIRED_T = 0;
  HEATER_ON = false;
  
	// Instantiate the Process_Temperature 
	Process_Temperature p_t("Process");
	p_t.SensorT(SENSOR_T);
	p_t.DesiredT(DESIRED_T);
	p_t.HeaterOn(HEATER_ON);
	
	// Begin Simulation
	sc_start();
	return 0;
}
*/
