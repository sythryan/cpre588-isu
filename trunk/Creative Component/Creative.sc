/****************************************************************************
*  Title: IP.sc
*  Author: Brandon Tomlinson
*  Date: 5/5/2010
*  Description: IP to evaluate/control a greenhouse environment based on 
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "constant.sh"

import "i_receiver";
import "i_sender";

behavior ip(i_receiver tempdatain, i_receiver moistdatain, i_sender heatcontrolout, i_sender sprinklercontrolout, i_sender tempdataout, i_sender moistdataout)
{
	void main(void) {
		int tempcommand, moistcommand, h, h1, h2, tempest, moistest, diff, RH, shouldRH, control, prevvaltemp, prevvalmoist, x, timeval, command;

		while(1) 
		{
			command == 2;
			tempdatain.receive(&h1, sizeof(h1));//dry-bulb temp
			moistdatain.receive(&h2, sizeof(h2));//wet-bulb temp

			diff = h1 - h2;
			printf("diff value: %i\n",diff);
			if (diff = 2)
				if ((h1 >= 50) && (h1 < 59))
					RH = 88;
				else if ((h1 >=60) && (h1 < 69))
					RH = 90;
				else if ((h1 >=70) && (h1 < 79))
					RH = 90;
				else if ((h1 >=80) && (h1 < 89))
					RH = 91;
				else
					RH = 0;
			else if (diff = 4)
				if ((h1 >= 50) && (h1 < 59))
					RH = 77;
				else if ((h1 >=60) && (h1 < 69))
					RH = 79;
				else if ((h1 >=70) && (h1 < 79))
					RH = 82;
				else if ((h1 >=80) && (h1 < 89))
					RH = 83;
				else
					RH = 0;
			else if (diff = 6)
				if ((h1 >= 50) && (h1 < 59))
					RH = 65;
				else if ((h1 >=60) && (h1 < 69))
					RH = 70;
				else if ((h1 >=70) && (h1 < 79))
					RH = 74;
				else if ((h1 >=80) && (h1 < 89))
					RH = 75;
				else
					RH = 0;
			else if (diff = 8)
				if ((h1 >= 50) && (h1 < 59))
					RH = 55;
				else if ((h1 >=60) && (h1 < 69))
					RH = 60;
				else if ((h1 >=70) && (h1 < 79))
					RH = 65;
				else if ((h1 >=80) && (h1 < 89))
					RH = 68;
				else
					RH = 0;
			else if (diff = 10)
				if ((h1 >= 50) && (h1 < 59))
					RH = 45;
				else if ((h1 >=60) && (h1 < 69))
					RH = 52;
				else if ((h1 >=70) && (h1 < 79))
					RH = 58;
				else if ((h1 >=80) && (h1 < 89))
					RH = 61;
				else
					RH = 0;
			else if (diff = 12)
				if ((h1 >= 50) && (h1 < 59))
					RH = 35;
				else if ((h1 >=60) && (h1 < 69))
					RH = 43;
				else if ((h1 >=70) && (h1 < 79))
					RH = 51;
				else if ((h1 >=80) && (h1 < 89))
					RH = 54;
				else
					RH = 0;
			else if (diff = 14)
				if ((h1 >= 50) && (h1 < 59))
					RH = 25;
				else if ((h1 >=60) && (h1 < 69))
					RH = 35;
				else if ((h1 >=70) && (h1 < 79))
					RH = 43;
				else if ((h1 >=80) && (h1 < 89))
					RH = 47;
				else
					RH = 0;
			else if (diff = 16)
				if ((h1 >= 50) && (h1 < 59))
					RH = 15;
				else if ((h1 >=60) && (h1 < 69))
					RH = 27;
				else if ((h1 >=70) && (h1 < 79))
					RH = 37;
				else if ((h1 >=80) && (h1 < 89))
					RH = 41;
				else
					RH = 0;
			else if (diff = 18)
				if ((h1 >= 50) && (h1 < 59))
					RH = 9;
				else if ((h1 >=60) && (h1 < 69))
					RH = 20;
				else if ((h1 >=70) && (h1 < 79))
					RH = 31;
				else if ((h1 >=80) && (h1 < 89))
					RH = 34;
				else
					RH = 0;
			else if (diff = 20)
				if ((h1 >= 50) && (h1 < 59))
					RH = 0;
				else if ((h1 >=60) && (h1 < 69))
					RH = 12;
				else if ((h1 >=70) && (h1 < 79))
					RH = 24;
				else if ((h1 >=80) && (h1 < 89))
					RH = 29;
				else
					RH = 0;
			else
				RH = 0;

			printf("RH value: %i\n",RH);

			if (h1 >= 50 & h1 < 59)
				shouldRH == 83;
			else if (h1 >=60 & h1 < 69)
				shouldRH == 89;
			else if (h1 >=70 & h1 < 79)
				shouldRH == 92;
			else if (h1 >=80 & h1 < 89)
				shouldRH == 95;
		
			if ((shouldRH - RH) < DEVIATION1)
				control == 0, x == 0;//do nothing
			else if ((shouldRH - RH) > DEVIATION1 & (shouldRH - RH) < DEVIATION2)
				control == 1, x == 10;// turn on sprinkler for 10 min
			else if((shouldRH - RH) > DEVIATION2 & (shouldRH - RH) < DEVIATION3)
				control == 2, x == 10;//turn on heater and sprinkler for 10 min
			else if((shouldRH - RH) > DEVIATION3)
				control == 3, x == 20;//turn on heater and sprinkler for 20 min

			if (control == 0)
				tempcommand == 0, moistcommand == 0;
			else if (control == 1)
				tempcommand == 0, moistcommand == 1;
			else if (control == 2)
				tempcommand == 1, moistcommand == 1;
			else if (control == 3)
				tempcommand == 1, moistcommand == 1;
			else
				tempcommand == 2, moistcommand == 2;

			if (prevvaltemp == tempcommand)
				heatcontrolout.send(&command, sizeof(command));
			else if (prevvaltemp != tempcommand)
				heatcontrolout.send(&tempcommand, sizeof(tempcommand));

			if (prevvalmoist == moistcommand)
				sprinklercontrolout.send(&command, sizeof(command));
			else if (prevvalmoist != moistcommand)
				sprinklercontrolout.send(&moistcommand, sizeof(moistcommand));

			prevvaltemp == tempcommand;
			prevvalmoist == moistcommand;

			tempdataout.send(&h1, sizeof(h1));
			moistdataout.send(&h2, sizeof(h2));
		}
	}
};
