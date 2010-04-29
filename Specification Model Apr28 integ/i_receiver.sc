// i_receiver.sc: type-less receiver interface
//
// author: Rainer Doemer
//
// modifications: (most recent first)
//
// 02/13/02 RD	applied naming convention, integrated with distribution
// 02/05/02 RD	parameterized name of the queue
// 02/04/02 RD	refined usage rules
// 02/01/02 RD	defined specific features and inserted actual code
// 01/31/02 RD	initial version
//
//
// interface rules:
//
// - a connected thread acts as a receiver
// - a call to receive() receives a packet of data from a connected channel
// - data packets are typeless (i.e. an array of bytes in the memory)
//   and may vary in size for separate calls to receive()
// - calling receive() may suspend the calling thread indefinitely


interface i_receiver
{
    void receive(void *d, unsigned long l);
};


// EOF i_receiver.sc