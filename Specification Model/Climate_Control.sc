/****************************************************************************
*  Title: Climate Control.sc
*  Author: Brandon Tomlinson
*  Date: 03/15/2010
*  Description: Specification for parity generator/encoder
****************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

import "init";
import "io";
import "parity";

behavior ClimateControl(i_receiver settingsin, i_receiver tempin, i_receiver moisturein, i_sender heatcontrol, i_sender sprinklercontrol, i_sender tempout, i_sender moistureout)
{
	

	readsettings R();
	clock C();
	processtemp T();
	processmoisture M();

	void main (void)
	{
		par {
			R.main();
			C.main();
			T.main();
			M.main();
		}
	}
};
