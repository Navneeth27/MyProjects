package osp.Threads;

import java.util.Vector;
import java.util.Enumeration;
import osp.Utilities.*;
import osp.IFLModules.*;
import osp.Tasks.*;
import osp.EventEngine.*;
import osp.Hardware.*;
import osp.Devices.*;
import osp.Memory.*;
import osp.Resources.*;

/**
 * This class is responsible for actions related to threads, including creating,
 * killing, dispatching, resuming, and suspending threads.
 * 
 * @OSPProject Threads
 */
public class ThreadCB extends IflThreadCB {

	private static GenericList readyQueue;  //List implementation for ready queue
	public ThreadCB() {

		super();

	}

	public static void init() {

		readyQueue = new GenericList();    //Initialize the ready queue

	}

	static public ThreadCB do_create(TaskCB task) {

		ThreadCB thread = null;            //Don't create the thread if the task is null 
		if (task == null) {          
			ThreadCB.dispatch();
			return null;
		}

		if (task.getThreadCount() >= MaxThreadsPerTask) {   //If number of threads per task is exceeded, no new thread is created
			ThreadCB.dispatch();
			return null;
		}

		thread = new ThreadCB();                 //Thread object is created
		thread.setPriority(task.getPriority());  //Ask reference for the parent task
		thread.setStatus(ThreadReady);           //set status to Thread Ready
		thread.setTask(task);                    
		if (task.addThread(thread) == 0) {       //return null if FAILURE  from addThread()
			ThreadCB.dispatch();
			return null;
		}
		readyQueue.append(thread);              //append the thread to the ready queue
		ThreadCB.dispatch();
		return thread;                          //return the thread object

	}

	public void do_kill() {

		switch (getStatus()) {
		case ThreadReady:                        //Handle the ThreadReady case
			readyQueue.remove(this);
			break;
		case ThreadRunning:                      //Handle the ThreadRunning case 
			ThreadCB thread = null;
			try {
				thread = MMU.getPTBR().getTask().getCurrentThread();
				if (this == thread) {
					MMU.setPTBR(null);
					getTask().setCurrentThread(null);
				}
			} catch (NullPointerException e) {
			}
			break;
		}

		getTask().removeThread(this);            //Get the associated task and kill the thread
		setStatus(ThreadKill);

		for (int i = 0; i < Device.getTableSize(); i++) { //Make sure IO resources are released
			Device.get(i).cancelPendingIO(this);
		}

		ResourceCB.giveupResources(this);
		ThreadCB.dispatch();
		if (getTask().getThreadCount() == 0) {   //Kill the task if there are no remaining threads
			getTask().kill();
		}

	}

	public void do_suspend(Event event) {

		int status = getStatus();
		if (status >= ThreadWaiting) {
			setStatus(getStatus() + 1);

		}

		else if (status == ThreadRunning) {     //Handle the ThreadRunning case
			ThreadCB thread = null;
			try {
				thread = MMU.getPTBR().getTask().getCurrentThread();
				if (this == thread) {           //Handle the ThreadWaiting case
					MMU.setPTBR(null);
					getTask().setCurrentThread(null);
					setStatus(ThreadWaiting);
				}
			} catch (NullPointerException e) {
			}

		}

		if (!readyQueue.contains(this)) {
			event.addThread(this);
		} else {
			readyQueue.remove(this);
		}

		ThreadCB.dispatch();                      //Call for another thread

	}

	public void do_resume() {

		if (getStatus() < ThreadWaiting) {
			MyOut.print(this, "Attempt to resume " + this
					+ ", which wasn't waiting");
			return;
		}

		MyOut.print(this, "Resuming " + this);        //Message to indicate we are resuming this thread

		if (this.getStatus() == ThreadWaiting) {      //if the status is ThreadWaiting, make it ThreadReady
			setStatus(ThreadReady);
		} else if (this.getStatus() > ThreadWaiting) {  //else if the status is greater than ThreadWaiting, decrement the status towards ThreadWaiting
			setStatus(getStatus() - 1);
		}

		if (getStatus() == ThreadReady) {            //Put the thread on the ready queue
			readyQueue.append(this);
		}

		dispatch(); // dispatch a thread

	}

	public static int do_dispatch() {

		ThreadCB thread = null;

		try {                                      //Handle the ThreadRunning Case
			thread = MMU.getPTBR().getTask().getCurrentThread();
		} catch (NullPointerException e) {
		}

		if (thread != null) {
			thread.getTask().setCurrentThread(null); //Give away control to the CPU
			MMU.setPTBR(null);
			thread.setStatus(ThreadReady);           //Make ThreadReady and add it to the ready queue
			readyQueue.append(thread);
		}

		if (readyQueue.isEmpty()) {                   //If no Thread is running in the queue
			MMU.setPTBR(null);
			return FAILURE;                           //Indicating that we are not dispatching the thread
		}

		else {
			thread = (ThreadCB) readyQueue.removeHead(); //If the the ready queue has a thread, remove the head and cast as a ThreadCB object
			MMU.setPTBR(thread.getTask().getPageTable()); //Get threads' task, set as the current thread
			thread.getTask().setCurrentThread(thread);
			thread.setStatus(ThreadRunning);

		}

		HTimer.set(50);
		return SUCCESS;                               

	}

	public static void atError() {
		System.out.println("Error");

	}

	public static void atWarning() {
		System.out.println("Warning");

	}

}
