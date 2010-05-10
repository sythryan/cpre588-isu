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
	void main(void) 
	{
	  
int rh[cT+1][cD+1][cRH+1]=
{{{50,2,87}, {50,4,75}, {50,6,62}, {50,8,51}, {50,10,39}, {50,12,29}, {50,14,18}, {50,16,9}, {50,18,0}, {50,20,0}}, 
{{52,2,87}, {52,4,75}, {52,6,64}, {52,8,52}, {52,10,42}, {52,12,32}, {52,14,21}, {52,16,12}, {52,18,6}, {52,20,0}}, 
{{54,2,88}, {54,4,76}, {54,6,65}, {54,8,53}, {54,10,43}, {54,12,33}, {54,14,23}, {54,16,14}, {54,18,8}, {54,20,0}}, 
{{56,2,88}, {56,4,77}, {56,6,66}, {56,8,55}, {56,10,45}, {56,12,35}, {56,14,26}, {56,16,16}, {56,18,10}, {56,20,0}}, 
{{58,2,88}, {58,4,78}, {58,6,67}, {58,8,56}, {58,10,47}, {58,12,37}, {58,14,28}, {58,16,18}, {58,18,12}, {58,20,4}}, 
{{60,2,89}, {60,4,78}, {60,6,68}, {60,8,58}, {60,10,48}, {60,12,39}, {60,14,30}, {60,16,21}, {60,18,14}, {60,20,5}}, 
{{62,2,89}, {62,4,79}, {62,6,69}, {62,8,59}, {62,10,50}, {62,12,41}, {62,14,32}, {62,16,24}, {62,18,17}, {62,20,8}}, 
{{64,2,90}, {64,4,79}, {64,6,70}, {64,8,60}, {64,10,51}, {64,12,43}, {64,14,34}, {64,16,26}, {64,18,19}, {64,20,11}}, 
{{66,2,80}, {66,4,80}, {66,6,71}, {66,8,61}, {66,10,53}, {66,12,44}, {66,14,36}, {66,16,29}, {66,18,22}, {66,20,14}}, 
{{68,2,90}, {68,4,80}, {68,6,71}, {68,8,62}, {68,10,54}, {68,12,46}, {68,14,38}, {68,16,31}, {68,18,24}, {68,20,16}}, 
{{70,2,90}, {70,4,81}, {70,6,72}, {70,8,64}, {70,10,55}, {70,12,48}, {70,14,40}, {70,16,33}, {70,18,27}, {70,20,19}}, 
{{72,2,91}, {72,4,82}, {72,6,73}, {72,8,65}, {72,10,57}, {72,12,49}, {72,14,42}, {72,16,34}, {72,18,28}, {72,20,21}}, 
{{74,2,91}, {74,4,82}, {74,6,74}, {74,8,65}, {74,10,58}, {74,12,50}, {74,14,43}, {74,16,36}, {74,18,30}, {74,20,23}}, 
{{76,2,91}, {76,4,82}, {76,6,74}, {76,8,66}, {76,10,59}, {76,12,51}, {76,14,44}, {76,16,38}, {76,18,32}, {76,20,25}}, 
{{78,2,91}, {78,4,83}, {78,6,75}, {78,8,67}, {78,10,60}, {78,12,53}, {78,14,46}, {78,16,39}, {78,18,33}, {78,20,27}}, 
{{80,2,91}, {80,4,83}, {80,6,75}, {80,8,68}, {80,10,61}, {80,12,54}, {80,14,47}, {80,16,41}, {80,18,34}, {80,20,29}}};

		int tempcommand, moistcommand,  h1, h2,  diff, RH, shouldRH, RHdiff, control, prevvaltemp, prevvalmoist, x,  command;
    int iT, iD, iRH;
    int i;
		while(1) 
		{
			command = 2;
			
			tempdatain.receive(&h1, sizeof(h1));//dry-bulb temp
			moistdatain.receive(&h2, sizeof(h2));//wet-bulb temp

			diff = abs(h1 - h2);
						printf("Dry-bulb,Wet-bulb: %i,%i,  diff: %2i       ",h1, h2, diff);

      for( i = 0; i <= 	cT; i++)//cT; i++)
        {if (h1 <= rh[i][0][0]) {
            iT=i-1; break;} }		
      for( i = 0; i <= 	cD; i++)
        {if (diff <= rh[iT][i][1] ){
            iD=i;break;}}	
            		
      iRH=rh[iT][iD][2];
      // index into array, RH in array, current temp, 
      // printf(" [%2i][%2i][2]->  RH: %2i, Temp: %i  ",iT,iD,iRH, h1);
   
      printf("Computed RH: %2i.   ",iRH);
      
      RH = iRH;
        
		

			if ((h1 >= 50) && (h1 < 59))
				shouldRH = 83;
			else if ((h1 >=60) && (h1 < 69))
				shouldRH = 89;
			else if ((h1 >=70) && (h1 < 79))
				shouldRH = 92;
			else if ((h1 >=80) && (h1 < 89))
				shouldRH = 95;
		
		 RHdiff = shouldRH - RH;
		
			if ( RHdiff < DEVIATION1)
				{control = 0; x = 0; printf("       0min");}  //do nothing				
			else if ((RHdiff > DEVIATION1) && ( RHdiff < DEVIATION2))  		
				{control = 1; x = 10;printf("    S 20min");}  // turn on sprinkler for 10 min
			else if ((RHdiff > DEVIATION2) && (RHdiff < DEVIATION3))
				{control = 2; x = 10;printf("   HS 10min");}  //turn on heater and sprinkler for 10 min
			else if (RHdiff > DEVIATION3)
				{control = 3; x = 20;printf("   HS 20min");}  //turn on heater and sprinkler for 20 min

			if (control == 0){
				tempcommand = 0; moistcommand = 0;}
			else if (control == 1){
				tempcommand = 0; moistcommand = 1;}
			else if (control == 2){
				tempcommand = 1; moistcommand = 1;}
			else if (control == 3){
				tempcommand = 1; moistcommand = 1;}
			else
				tempcommand = 2; moistcommand = 2;

			if (prevvaltemp == tempcommand)
				heatcontrolout.send(&command, sizeof(command));
			else if (prevvaltemp != tempcommand)
				heatcontrolout.send(&tempcommand, sizeof(tempcommand));

			if (prevvalmoist == moistcommand)
				sprinklercontrolout.send(&command, sizeof(command));
			else if (prevvalmoist != moistcommand)
				sprinklercontrolout.send(&moistcommand, sizeof(moistcommand));

			prevvaltemp = tempcommand;
			prevvalmoist = moistcommand;

			tempdataout.send(&h1, sizeof(h1));
			moistdataout.send(&h2, sizeof(h2));
			
			printf("\n");
		}
	}
};
