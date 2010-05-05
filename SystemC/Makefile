TARGET_ARCH = linux

CC     = g++
OPT    = -O3
DEBUG  = -g
OTHER  = -Wall
CFLAGS = $(OPT) $(OTHER)
# CFLAGS = $(DEBUG) $(OTHER)

MODULE = pt
SRCS = Main.cpp 
OBJS = $(SRCS:.cpp=.o)

## Variable that points to SystemC installation path
SYSTEMC = /usr/local/systemc-2.2.0


INCDIR = -I. -I.. -I$(SYSTEMC)/include
LIBDIR = -L. -L.. -L$(SYSTEMC)/lib-$(TARGET_ARCH)

LIBS   =  -lsystemc -lm $(EXTRA_LIBS)


EXE    = $(MODULE).x

.SUFFIXES: .cc .cpp .o .x

$(EXE): $(OBJS) $(SYSTEMC)/lib-$(TARGET_ARCH)/libsystemc.a 
	$(CC) $(CFLAGS) $(INCDIR) $(LIBDIR) -o $@ $(OBJS) $(LIBS) 2>&1 | c++filt

.cpp.o:
	$(CC) $(CFLAGS) $(INCDIR) -c $<

.cc.o:
	$(CC) $(CFLAGS) $(INCDIR) -c $<

clean::
	rm -f $(OBJS) *~ $(EXE) core


