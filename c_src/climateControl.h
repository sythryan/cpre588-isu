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
