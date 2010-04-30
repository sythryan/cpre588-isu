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
	int h;

	void main(void) {
		tempdatain.receive(&h, sizeof(h));
		while(1)
		{
			if ( h == 0)
				heatcontrolout.send(0,1);
			else
				heatcontrolout.send(1,1);
		}
	}

};
