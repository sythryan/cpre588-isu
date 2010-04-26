#
# Makefile: Parity Checker
#

SCC = scrc

SCCOPT  = -vv -ww -g

all: tb

tb: tb.sc Stimulus.sc Climate_Control.sc Monitor.sc Read_Settings.sc c_double_handshake.sc c_queue.sc i_sender.sc i_receiver.sc i_tranceiver.sc
	$(SCC) tb $(SCCOPT)

clean:
	-rm -f *~ *.o *.cc *.h
	-rm -f *.si tb
