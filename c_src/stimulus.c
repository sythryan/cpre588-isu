//------------------------------------------------------------------------
//-- stimulus.c
//------------------------------------------------------------------------


// continually provide sensor data and 
// user's desired values for use in climateControl

float currentSensorTemp(){return 65.0;};
float currentSensorHumd(){return 85.0;};
extern float keypadDesiredTemp(){return 68.0;};
float keypadDesiredHumd(){return 91.0;};



// read sensor data from file (or hardcode)
// http://www.umass.edu/umext/floriculture/fact_sheets/greenhouse_management/humidity.htm
// this website has a table with desired humidity levels for certain temperatures

// wait for input from user
// read desired temp/humd from user
// display user's desired values 
//     (? not sure if this is where we planned to do this)
