//------------------------------------------------------------------------
//-- monitor.c
//------------------------------------------------------------------------

#include "climateControl.h"



// get information from climate control 
//   display control information
//   display desired and current temp/humd values 


void tbd()
{
 	float cst = currentSensorTemp();
	float csh = currentSensorHumd();

	float dt = desiredTemp();
	float dh = desiredHumd();
}