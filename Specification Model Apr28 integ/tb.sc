/****************************************************************************
*  Title: tb.sc
*  Author: Daniel Zundel
*  Date: 04/19/2010
*  Description: testbench for Climate Controller
****************************************************************************/

import "c_queue";

import "Climate_Control";
import "Stimulus";
import "Monitor";
import "i_sender";
import "i_receiver";

const unsigned long SIZE = 10;
const unsigned long MSize = 1;

behavior Main
{
	c_queue				USER_SET((MSize));
	c_queue				M_SENSE((SIZE));
	c_queue				T_SENSE((SIZE));
	c_queue				HEATER((SIZE));
	c_queue				SPRINKLER((SIZE));
	c_queue				T_OUT((SIZE));
	c_queue				M_OUT((SIZE));

	Stimulus			STM(USER_SET, M_SENSE, T_SENSE);
	Climate_Control				C_CONTROLLER(USER_SET, M_SENSE, T_SENSE
									 HEATER, SPRINKLER, M_OUT, T_OUT);
	Monitor				MTR(HEATER, SPRINKLER, M_OUT, T_OUT);
	

	int main (void)
	{
		par {
			STM.main();
			PE.main();
			MTR.main();			
		}

		return 0;
	}
};
