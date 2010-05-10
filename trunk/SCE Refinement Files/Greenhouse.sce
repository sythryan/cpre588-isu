<!DOCTYPE SCE>
<sce>
 <project>Greenhouse.sce</project>
 <compiler>
  <option name="libpath" ></option>
  <option name="libs" ></option>
  <option name="incpath" ></option>
  <option name="importpath" >.</option>
  <option name="defines" ></option>
  <option name="undefines" ></option>
  <option verbosity="0" name="options" warning="0" ></option>
 </compiler>
 <simulator>
  <option type="0" name="tracing" debug="False" calls="False" ></option>
  <option type="2" name="terminal" >xterm -title %e -e</option>
  <option name="logging" enabled="False" >.log</option>
  <option name="command" >./%e</option>
 </simulator>
 <models>
  <item type="" name="GreenhouseSpec.sir" >
   <item input="GreenhouseArch.in.sir" type="scar,GreenhouseSpec,-b,-m,-c,-w,-i,/home/bltomlin/SCE_ArchRefine/GreenhouseArch.in.sir,-o,/home/bltomlin/SCE_ArchRefine/GreenhouseArch.sir" level="0x700L" name="GreenhouseArch.sir" />
  </item>
 </models>
 <imports>
  <item name="Climate_Control" />
  <item name="HeatControl" />
  <item name="MoistProcessing" />
  <item name="Monitor" />
  <item name="Process_Moisture" />
  <item name="Process_Temperature" />
  <item name="Read_Settings" />
  <item name="SprinklerControl" />
  <item name="Stimulus" />
  <item name="TempProcessing" />
  <item name="c_queue" />
  <item name="i_receiver" />
  <item name="i_sender" />
  <item name="i_tranceiver" />
  <item name="c_double_handshake" />
  <item name="c_handshake" />
  <item name="dsp56600_0" />
  <item name="i_receive" />
  <item name="i_send" />
  <item name="stdHW" />
 </imports>
 <sources>
  <item name="./Climate_Control.sc" />
  <item name="./HeatControl.sc" />
  <item name="./MoistProcessing.sc" />
  <item name="./Monitor.sc" />
  <item name="./Process_Moisture.sc" />
  <item name="./Process_Temperature.sc" />
  <item name="./Read_Settings.sc" />
  <item name="./SprinklerControl.sc" />
  <item name="./Stimulus.sc" />
  <item name="./TempProcessing.sc" />
  <item name="/opt/sce-20080601/inc/sim.sh" />
  <item name="/opt/sce-20080601/inc/sim/bit.sh" />
  <item name="/opt/sce-20080601/inc/sim/longlong.sh" />
  <item name="/opt/sce-20080601/inc/sim/time.sh" />
  <item name="/usr/include/_G_config.h" />
  <item name="/usr/include/alloca.h" />
  <item name="/usr/include/bits/pthreadtypes.h" />
  <item name="/usr/include/bits/sigset.h" />
  <item name="/usr/include/bits/sys_errlist.h" />
  <item name="/usr/include/bits/time.h" />
  <item name="/usr/include/bits/types.h" />
  <item name="/usr/include/gconv.h" />
  <item name="/usr/include/libio.h" />
  <item name="/usr/include/stdio.h" />
  <item name="/usr/include/stdlib.h" />
  <item name="/usr/include/string.h" />
  <item name="/usr/include/sys/select.h" />
  <item name="/usr/include/sys/types.h" />
  <item name="/usr/include/time.h" />
  <item name="/usr/include/wchar.h" />
  <item name="/usr/lib/gcc/i386-redhat-linux/4.1.2/include/stdarg.h" />
  <item name="/usr/lib/gcc/i386-redhat-linux/4.1.2/include/stddef.h" />
  <item name="c_queue.sc" />
  <item name="i_receiver.sc" />
  <item name="i_sender.sc" />
  <item name="i_tranceiver.sc" />
  <item name="tb.sc" />
  <item name="/opt/sce-20080601/share/sce/db/processors/dsp/dsp56600.sc" />
  <item name="c_double_handshake.sc" />
  <item name="c_handshake.sc" />
  <item name="i_receive.sc" />
  <item name="i_send.sc" />
  <item name="stdHW.sc" />
 </sources>
 <metrics>
  <option name="types" >pthread_barrier_t,unsigned long int[2],unsigned long long int,fd_set,char[48],int,unsigned short int[3],float,char[40],struct tm,int[2],char,pthread_mutex_t,struct __pthread_internal_slist,struct _IO_marker,__fsid_t,char[1],pthread_mutexattr_t,struct __gconv_step_data,pthread_cond_t,unsigned short int,long long int,event,__sigset_t,struct itimerspec,short int,struct drand48_data,long double,struct __gconv_info,div_t,pthread_condattr_t,struct random_data,bool,__u_quad_t,ldiv_t,unsigned short int[7],void,char[32],__mbstate_t,unsigned long int[32],void*,struct _IO_FILE,long int[2],unsigned char,_G_iconv_t,struct __gconv_step,char[4],char[21],struct timespec,long int,pthread_rwlockattr_t,pthread_barrierattr_t,unsigned int,struct __gconv_trans_data,char[24],struct timeval,struct __gconv_step_data[1],unsigned long int,long int[32],double,enum __codecvt_result,_G_fpos_t,__quad_t,pthread_attr_t,pthread_rwlock_t,_G_fpos64_t,char[36],char[20],void*[2],char[8]</option>
  <option name="operations" ></option>
 </metrics>
</sce>
