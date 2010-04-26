/****************************************************************************
*  Title: Climate Control.sc
*  Author: Brandon Tomlinson
*  Date: 03/15/2010
*  Description: Specification for parity generator/encoder
****************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

import "Read_Settings";
import "Clock";
import "Process_Temperature";
import "Process_Moisture";

const unsigned long SIZE = 10;

behavior ClimateControl(i_receiver settingsin, i_receiver tempin, i_receiver moisturein, i_sender heatcontrol, i_sender sprinklercontrol, i_sender tempout, i_sender moistureout)
{
	c_queue Tempclockdata((SIZE)), Moistclockdata((SIZE)), Moisturesetin((SIZE)), Tempsetin((SIZE));

	readsettings R(settingsin, Tempsetin, Moisturesetin);
	clock C(Tempclockdata, Moistclockdata);
	processtemp T(Tempclockdata, Tempsetin, tempin, heatcontrol, tempout);
	processmoisture M(Moistclockdata, Moisturesetin, moisturein, sprinklercontrol, moistureout);

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
