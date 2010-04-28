//------------------------------------------------------------------------
//-- climateControl.h
//------------------------------------------------------------------------
//-- Purpose: This package handles the input and processing of greenhouse 
//--          sensor data. 
//--
//-- Effects: Outputs control data for climate regulation.
//--         
//--   
//------------------------------------------------------------------------

#include "stdbool.h"


#define MAX_HUMD_DEVIATION 10.0;
#define MAX_TEMP_DEVIATION 10.0;

#define SPRINKLER_DURATION 5.0;  // Minutes
#define HEATER_DURATION 5.0;    // Minutes

#define ONE_SECOND 1.0;          // 1 Minute  (1 Hz)


//enum (temp, humd) variable;


/*--------------- FUNCTION DECLARATIONS -----------------*/

void process(); 


/*-------------------------------------------------------*\
 *   For MONITOR                                         *
\*-------------------------------------------------------*/

/*  Control  */
bool isHeaterNeeded();
bool isSprinklerNeeded();

/*  Passthrough Data (stimulus->climateControl->monitor) */
float desiredTemp();
float desiredHumd();
float currentTemp();
float currentHumd();
