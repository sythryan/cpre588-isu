/****************************************************************************
*  Title: Process_Moisture.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Moisture evaluation/control for greenhouse environment 
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

import "MoistProcessing";
import "SprinklerControl";
import "i_sender";
import "i_receiver";
import "c_queue";


behavior Process_Moisture(i_receiver moistsettingsin, i_receiver moistdatain, 
i_sender sprinklercontrolout, i_sender moistdataout)
{

  const unsigned long SIZE = 10;

	c_queue CC1((SIZE));
	
	moistprocessing MP(moistsettingsin, moistdatain, CC1, moistdataout);
	sprinklercontrol SC(CC1, sprinklercontrolout);


	void main(void) {
		par {
			MP.main();
			SC.main();
		}

	}
};

