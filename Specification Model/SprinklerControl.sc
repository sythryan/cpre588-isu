/****************************************************************************
*  Title: SprinklerControl.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Sprinkler control for greenhouse environmental system ****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
import "i_sender";
import "i_receiver";

behavior sprinklercontrol(i_receiver moistdatain, i_sender sprinklercontrolout)
{
	void main(void) {		
		int h, command;

		while(1)
		{
			moistdatain.receive(&h, sizeof(h));
			if (h == 0)
				command = 1;
			else
				command = 0;
			waitfor(10);
			sprinklercontrolout.send(&command, sizeof(command));
		}
     }
};

