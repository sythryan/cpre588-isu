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
	void main(void) 
	{
		int h, command;

		while(1)
		{
			tempdatain.receive(&h, sizeof(h));
			if (h == 0)
				command = 1;
			else
				command = 0;
			waitfor(10);
			heatcontrolout.send(&command, sizeof(command));
		}
	}

};
