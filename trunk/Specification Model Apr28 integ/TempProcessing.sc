/****************************************************************************
*  Title: TempProcessing.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Processing of temperature data for heater control ****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

import "i_sender";
import "i_receiver";

behavior tempprocessing(i_receiver tempsettingsin, i_receiver tempdatain, i_sender tempdatacontrol, i_sender tempdataout)
{
	double h;
	int AVG_TEMP, DEVIATION;

	void main(void) {
	
		tempdatain.receive(&h, sizeof(h));
		if ((h - AVG_TEMP) >( - DEVIATION ))
			tempdatacontrol.send(1, 1);
		else
			tempdatacontrol.send(0, 1); //false
	}

};
