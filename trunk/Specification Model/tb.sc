/****************************************************************************
*  Title: tb.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: testbench for greenhouse environmental monitor
****************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

import "c_double_handshake";
import "c_queue";
import "Stimulus";
import "Climate_Control";
import "Monitor";

const unsigned long SIZE = 10;

behavior Main
{
	c_queue Settingsin((SIZE)), Tempin((SIZE)), Moisturein((SIZE)), Heatercontrol((SIZE)), Sprinklercontrol ((SIZE)), Tempout((SIZE)), Moistureout((SIZE));

	Stimulus S(Settingsin, Tempin, Moisturein);
	ClimateControl C(Settingsin, Tempin, Moisturein, Heatercontrol, Sprinklercontrol, Tempout, Moistureout);
	Monitor M(Heatercontrol, Sprinklercontrol, Tempout, Moistureout);

	int main (void)
	{
		par {
			S.main();
			C.main();
			M.main();
		}

	return 0;
	}
};
