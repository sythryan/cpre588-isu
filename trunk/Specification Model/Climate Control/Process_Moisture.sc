/****************************************************************************
*  Title: Process_Moisture.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Moisture evaluation/control for greenhouse environment 
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

import "Process_Moisture\MoistProcessing";
import "Process_Moisture\SprinklerControl";

behavior processmoisture(i_receiver moistdatain, i_sender sprinklercontrol, i_sender moistdataout)
{

	c_queue C2;
	tempprocess P(moistdatain, C2);
	heatcontrol C(C2, sprinklercontrol);


	void main(void) {
		par {
			P.main();
			C.main();
		}

	}
};

