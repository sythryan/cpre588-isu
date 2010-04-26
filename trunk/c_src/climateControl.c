//------------------------------------------------------------------------
//-- climateControl.c
//------------------------------------------------------------------------

#include "stimulus.h"
#include "stdbool.h"


/*-------------------------------------------------------*\
 *   Local Variables                                     *
\*-------------------------------------------------------*/

// Data about greenhouse
static float localCurrentTemp = 70.0;
static float localDesiredTemp = 70.0;
 bool heaterNeeded = false;
 bool sprinklerNeeded = false;

/*-------------------------------------------------------*\
 *   Public                                              *
\*-------------------------------------------------------*/

/* for MONITOR - Control Flags, Output for LCD*/
bool isHeaterNeeded(){return heaterNeeded;};
bool isSprinklerNeeded(){return sprinklerNeeded;};
float desiredTemp(){return localDesiredTemp;};
float desiredHumd(){return localDesiredTemp;};
float currentTemp(){return localCurrentTemp;};
float currentHumd(){return localCurrentTemp;};




//---------------------------------------------------------
//
//---------------------------------------------------------
void process()
{
	 	localCurrentTemp = currentSensorTemp();	
		localDesiredTemp = keypadDesiredTemp();
}
