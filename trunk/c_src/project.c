//------------------------------------------------------------------------
//-- project.c  
//------------------------------------------------------------------------

#include "climateControl.h";

static int count = 0;

int main()
{
	
	// could be multithreaded, but since the purpose of the c-implementation
	// is to create the leaf code for the modeling tools, time would be better
	// spent using the system design languages/tools.  
	
	// Therefore, other means (timer/counter?) will be used to start/stop/control program.
	// "count" will allow the program to cycle 100 times.
	// This could be changed to a infinite loop with a means of ending the program
	// setup in monitor (similar to parity program).
	// It could also just execute sequentially (i.e. read sensor data, receive all 
	// sensor data in climateControl, store data to be passed to monitor,
	// output simulation data in monitor, and exit.
	// etc.
	while (count < 100)
	{
			#stimulus.process();
			
			climateControl.process();
			
			#monitor.process();
			
			count = count  + 1;
	}
	
	return 0;
	
}

