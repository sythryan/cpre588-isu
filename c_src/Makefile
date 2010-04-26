CC=gcc
CFLAGS= -g -Wall 
OBJ: climate.o stimulus.o

all: $(OBJ) project.c
	$(CC) $(CFLAGS) -o project $(OBJ)

climate.o: climateControl.c climateControl.h
	$(CC) $(CFLAGS) -o $@ $<
	
stimulus.o: stimulus.c stimulus.h
	$(CC) $(CFLAGS) -o $@ $<

clean: 
	rm *.o