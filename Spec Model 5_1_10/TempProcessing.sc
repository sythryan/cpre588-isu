/****************************************************************************
*  Title: TempProcessing.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Processing of temperature data for heater control ****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "constant.sh"

import "i_sender";
import "i_receiver";

behavior tempprocessing(i_receiver tempsettingsin, i_receiver tempdatain, i_sender tempdatacontrol, i_sender tempdataout)
{
	int h;
	unsigned int command;
	int tempset;

	void main(void) {
		tempsettingsin.receive(&tempset, sizeof(tempset));
		printf("Temp Setting: %i\n",tempset);
		while(1) {
			tempdatain.receive(&h, sizeof(h));
			printf("TempIn: %i\n",h);
		
			if ((h - tempset) >( - DEVIATION ))
				command = 1;
			else
				command = 0;

			tempdatacontrol.send(&command, sizeof(command));
			h = 0;
		}
	}

};
