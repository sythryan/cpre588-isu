/****************************************************************************
*  Title: Process_Temperature.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Temperature evaluation/control for greenhouse environment
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

import "Process_Temperature\TempProcessing";
import "Process_Temperature\HeatControl";

behavior processtemp(i_receiver tempsettingsin, i_receiver tempdatain, i_sender heatcontrol, i_sender tempdataout)
{

	c_queue C1;
	tempprocess P(tempsettingsin, tempdatain, C1, tempdataout);
	heatcontrol C(C1, heatcontrol);

	void main(void) {
		par {
			P.main();
			C.main();
		}

	}
};

