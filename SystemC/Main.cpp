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
