/****************************************************************************
*  Title: Process_Temperature.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Temperature evaluation/control for greenhouse environment
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

import "TempProcessing";
import "HeatControl";
import "i_receiver";
import "i_sender";
import "c_queue";

behavior Process_Temperature(i_receiver tempsettingsin, 
i_receiver tempdatain,
 i_sender heatcontrolout, 
 i_sender tempdataout)
{

const unsigned long SIZE = 16;
	c_queue C1((SIZE));
	
	tempprocessing P(tempsettingsin, tempdatain, C1, tempdataout);
	heatcontrol C(C1, heatcontrolout);

	void main(void) {
		par {
			P.main();
			C.main();
		}

	}
};

