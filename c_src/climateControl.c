
/*-------------------------------------------------------*\
 *   FUMNCTIONS - Not Public                             *
\*-------------------------------------------------------*/

//------------------------------------------------------------------------
//-- climateControl.c
//------------------------------------------------------------------------

#include "stimulus.h"
#include "climateControl.h"
#include "stdbool.h"


/*-------------------------------------------------------*\
 *   Local Variables                                     *
\*-------------------------------------------------------*/

// Data about greenhouse
static float localCurrentTemp = 70.0;
static float localDesiredTemp = 70.0;
static float localCurrentHumd = 70.0;
static float localDesiredHumd = 70.0;
bool heaterNeeded = false;
bool sprinklerNeeded = false;



/*-------------------------------------------------------*\
 *   FUNCTIONS - Public                                  *
\*-------------------------------------------------------*/

/* for MONITOR - Control Flags, Output for LCD */
bool isHeaterNeeded(){return heaterNeeded;};
bool isSprinklerNeeded(){return sprinklerNeeded;};

float desiredTemp(){return localDesiredTemp;};
float desiredHumd(){return localDesiredTemp;};
float currentTemp(){return localCurrentTemp;};
float currentHumd(){return localCurrentTemp;};

/* pre-declaration  */
void process(); 


/*-------------------------------------------------------*\
 *   FUNCTIONS - Local                                   *
\*-------------------------------------------------------*/


//---------------------------------------------------------
// READSETTINGS
// Get sensor data and keypad data
// Note: this fucntion assumes sensor is digital so 
//    original design was modified to remove AD conversion.
//---------------------------------------------------------
void readSettings()
{
	 	localCurrentTemp = currentSensorTemp();	
		localDesiredTemp = keypadDesiredTemp();
	 	localCurrentHumd = currentSensorHumd();	
		localDesiredHumd = keypadDesiredHumd();
}


//---------------------------------------------------------
//  CLOCK
//---------------------------------------------------------
void clock()
{
	//time_t localTempTime, localHumdTime;
	//time(&localTempTime);
	//time(&localHumdTime);
	return;
}

//---------------------------------------------------------
//  PROCESSCONTROLTEMP
//---------------------------------------------------------
void processControlTemp()
{
 	// PROCESS 
 	float lowTriggerValue  =  localDesiredTemp - MAX_TEMP_DEVIATION;
 	
	// CONTROL
	if (localCurrentTemp < lowTriggerValue)
		heaterNeeded = true;
	else
		heaterNeeded = false;
} 

//---------------------------------------------------------
//  PROCESSCONTROLHUMD
//---------------------------------------------------------
void processControlHumd()
{
 	// PROCESS 
 	float lowTriggerValue  =  localDesiredHumd - MAX_HUMD_DEVIATION;
 	
	// CONTROL
	if (localCurrentHumd < lowTriggerValue)
		sprinklerNeeded = true;
	else
		sprinklerNeeded = false;
}



//---------------------------------------------------------
//                     PROCESS                           //                  
//---------------------------------------------------------

void process()
{
	readSettings();
	
	// Note: No A/D conversion, considered outside of system.	
			
	processControlTemp();
	processControlHumd();

}


