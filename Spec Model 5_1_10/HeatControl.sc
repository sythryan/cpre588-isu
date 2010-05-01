/****************************************************************************
*  Title: HeatControl.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Heater control for greenhouse environmental system ****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#include "constant.sh"

import "i_sender";
import "i_receiver";

behavior heatcontrol(i_receiver tempdatain, i_sender heatcontrolout)
{
	unsigned int h, command;

	void main(void) 
	{
		while(1)
		{
		  tempdatain.receive(&h, sizeof(h));
		  
			if ( h == 0)
				command = 1;
			else
				command = 0;

				heatcontrolout.send(&command, sizeof(command)); //don't turn on
		}
	}

};
