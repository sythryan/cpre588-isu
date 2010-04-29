/****************************************************************************
*  Title: HeatControl.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Heater control for greenhouse environmental system ****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "constant.sh"

import "i_sender";
import "i_receiver";

behavior heatcontrol(i_receiver tempdatain, i_sender heatcontrolout)
{

	double h;

	void main(void) {
	
		tempdatain.receive(&h, sizeof(h));
		if ((h - AVG_TEMP) >( - DEVIATION ))
			heatcontrolout.send(1, 1);
		else
			heatcontrolout.send(0, 1); //false
	}
};

	
		
