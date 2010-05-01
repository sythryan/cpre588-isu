/****************************************************************************
*  Title: Read_Settings.sc
*  Author: Brandon Tomlinson
*  Date: 04/25/2010
*  Description: Settings reader/processor for greenhouse environmental control
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sim.sh>

import "constant.sh";
import "i_receiver";
import "i_sender";

behavior Read_Settings(i_receiver settingsin, i_sender tempsetout, i_sender moisturesetout)
{
	int tSetting, mSetting;
	
	void main(void) {
		
		settingsin.receive(&tSetting, sizeof(tSetting));
		settingsin.receive(&mSetting, sizeof(mSetting));
		
		tempsetout.send(&tSetting, sizeof(tSetting));
		moisturesetout.send(&mSetting, sizeof(mSetting));
		
		waitfor 1;

	}
};
