#
# Makefile: Parity Checker
#

SCC = scrc

SCCOPT  = -vv -ww -g

all: tb

tb: tb.sc Stimulus.sc Climate_Control.sc Monitor.sc Read_Settings.sc Process_Temperature.sc Process_Moisture.sc TempProcessing.sc MoistProcessing.sc HeatControl.sc SprinklerControl.sc c_double_handshake.sc c_queue.sc i_tranceiver.sc i_sender.sc i_receiver.sc
	$(SCC) tb $(SCCOPT)

clean:
	-rm -f *~ *.o *.cc *.h
	-rm -f *.si tb
