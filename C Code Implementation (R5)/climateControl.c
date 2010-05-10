
/*-------------------------------------------------------*\
 *   FUMNCTIONS - Not Public                             *
\*-------------------------------------------------------*/

//------------------------------------------------------------------------
//-- climateControl.c
//------------------------------------------------------------------------

#include "stimulus.h"
#include "climateControl.h"
#include "stdbool.h"
#include "time.h"


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


//Time from System
  time_t heaterStartTime, sprinklerStartTime; 
  time_t lastCycle;


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
void readSensors()
{
		// Sensor Data
	 	localCurrentTemp = currentSensorTemp();	
	 	localCurrentHumd = currentSensorHumd();	
}

//---------------------------------------------------------
// READSETTINGS
// Get sensor data and keypad data
// Note: this fucntion assumes sensor is digital so 
//    original design was modified to remove AD conversion.
//---------------------------------------------------------
void desiredValues()
{
	float dt = keypadDesiredTemp();
	float dh = keypadDesiredHumd();

	if ((localDesiredTemp /= dt) ||	(localDesiredHumd /= dh))
	{
			//  Desired Values
			localDesiredTemp = keypadDesiredTemp();
			localDesiredHumd = keypadDesiredHumd();
	}
}




//---------------------------------------------------------
//   CONTROL 
//      checkIfInRange
//---------------------------------------------------------
bool checkIfInRange(float current, float desired, float deviation )
{
	float minDesired  = desired - deviation;
	 	
		if (current < minDesired)
			return true;
		else
			return false;
}
						
//---------------------------------------------------------
//  PROCESSCONTROLTEMP
//---------------------------------------------------------
void processControlTemp()
{ 
	time_t clk; 
	
	// Determine time since last temperature check
	double deltaTime = difftime( clk, lastCycle);
	double duration = difftime( clk, heaterStartTime);

	// Check temperature if it's been > 1 second
	if (deltaTime > ONE_SECOND)
	{
			// Check if heater should be turned on from off  OR
			// Heater has been on long enough to recheck
			if ( ( heaterNeeded == false) || 	(duration > HEATERDURATION) )
			{
					heaterNeeded =	checkIfInRange(localCurrentTemp ,
							localDesiredTemp , MAX_TEMP_DEVIATION);
			
					if heaterNeeded
						heaterStartTime = clock();
			}
	}
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
	desiredValues();
	readSensors();
	
	processControlTemp();
	
	processControlHumd();

}


