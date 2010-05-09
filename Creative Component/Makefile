#
# Makefile: Greenhouse Creative Component
#

SCC = scrc

SCCOPT  = -vv -ww -g

all: tb

tb: tb.sc Stimulus.sc Creative.sc Monitor.sc c_queue.sc i_tranceiver.sc i_sender.sc i_receiver.sc
	$(SCC) tb $(SCCOPT)

clean:
	-rm -f *~ *.o *.cc *.h
	-rm -f *.si tb
