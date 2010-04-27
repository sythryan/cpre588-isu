CC=gcc
CFLAGS= -g -Wall 
#OBJ:  stimulus.o climate.o

#all:  project.c $(OBJ)
all: stimulus.h  climateControl.h  stimulus.c  climateControl.c  project.c
	$(CC) $(CFLAGS) -o project $^

#climate.o: climateControl.c climateControl.h
#	$(CC) $(CFLAGS) -o $@ $^
	
#timulus.o: stimulus.c stimulus.h
#	$(CC) $(CFLAGS) -o $@ $^

clean: 
	rm *.o