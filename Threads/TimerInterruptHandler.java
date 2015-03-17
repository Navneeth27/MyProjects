package osp.Threads;

import osp.IFLModules.*;
import osp.Utilities.*;
import osp.Hardware.*;

/**
 * The timer interrupt handler. This class is called upon to handle timer
 * interrupts.
 * 
 * @OSPProject Threads
 */
public class TimerInterruptHandler extends IflTimerInterruptHandler {

	public void do_handleInterrupt() {

		ThreadCB.dispatch();       //Schedule the next Thread to run

	}

}
