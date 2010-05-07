/****************************************************************************
*  Title: IP.sc
*  Author: Brandon Tomlinson
*  Date: 5/5/2010
*  Description: IP to evaluate/control a greenhouse environment based on 
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <constant.sh>

import "i_receiver";
import "i_sender";

behavior IP(i_receiver tempdatain, i_receiver moistdatain, i_sender heatcontrolout, i_sender tempdataout)
{

	void main(void) {
		int command, h1, tempset;

		tempdatain.receive(&tempset, sizeof(tempset));
		printf("Temp Setting: %i\n",tempset);
		
		while(1) 
		{
			tempdatain.receive(&h1, sizeof(h1));
			tempdataout.send(&h1, sizeof(h1));
			printf("TempIn: %u\n",h1);
		
			if ((h1 - tempset) >( - DEVIATION ))
				command = 1;
			else
				command = 0;

			tempdatacontrol.send(&command, sizeof(command));
		}
		}

	}
};

