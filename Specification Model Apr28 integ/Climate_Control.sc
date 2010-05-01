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
import "Process_Temperature";
import "Process_Moisture";


behavior Climate_Control(i_receiver settingsin, i_receiver tempin, i_receiver moisturein,
 i_sender heatcontrolout, i_sender sprinklercontrolout,  i_sender tempout, i_sender moistureout)
{
	const unsigned long SIZE = 10;
	
	c_queue Moistclockdata((SIZE));
	c_queue Moisturesetin((SIZE)); 
	c_queue Tempsetin((SIZE));

	Read_Settings RS(settingsin, Tempsetin, Moisturesetin);
	Process_Temperature PT(Tempsetin, tempin, heatcontrolout, tempout);
	
	Process_Moisture PM (Moisturesetin, moisturein, sprinklercontrolout, moistureout);

	void main (void)
	{
		par {
			RS.main();
			PT.main();
			PM.main();
		}
	}
};
