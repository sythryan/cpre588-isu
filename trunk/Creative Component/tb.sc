/****************************************************************************
*  Title: tb.sc
*  Author: Brandon Tomlinson
*  Date: 5/5/2010
*  Description: Testbench for IP
****************************************************************************/

import "c_queue";

import "IP";
import "Stimulus";
import "Monitor";
import "i_sender";
import "i_receiver";

const unsigned long SIZE = 10;

behavior Main
{
	c_queue USER_SET((MSize));
	c_queue M_SENSE((SIZE));
	c_queue T_SENSE((SIZE));
	c_queue HEATER((SIZE));
	c_queue SPRINKLER((SIZE));
	c_queue T_OUT((SIZE));
	c_queue M_OUT((SIZE));

	Stimulus STM(T_SENSE, M_SENSE);
	ip IP(T_SENSE, M_SENSE, HEATER, SPRINKLER, T_OUT, M_OUT);
	Monitor MTR(HEATER, SPRINKLER, M_OUT, T_OUT);
	

	int main (void)
	{
		par {
			STM.main();
			IP.main();
			MTR.main();			
		}
		return (0);
	}
};


