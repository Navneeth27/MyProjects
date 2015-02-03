package osp.Tasks;

import java.util.Enumeration;

import osp.FileSys.FileSys;
import osp.FileSys.OpenFile;
import osp.Hardware.HClock;
import osp.IFLModules.IflTaskCB;
import osp.Memory.MMU;
import osp.Memory.PageTable;
import osp.Ports.PortCB;
import osp.Threads.ThreadCB;
import osp.Utilities.GenericList;

/**
 * The student module dealing with the creation and killing of tasks. A task
 * acts primarily as a container for threads and as a holder of resources.
 * Execution is associated entirely with threads. The primary methods that the
 * student will implement are do_create(TaskCB) and do_kill(TaskCB). The student
 * can choose how to keep track of which threads are part of a task. In this
 * implementation, an array is used.
 * 
 * @OSPProject Tasks
 */
public class TaskCB extends IflTaskCB {
	//private PageTable pagetable;
	//private ThreadCB thread;
	//private OpenFile openFile;

	private GenericList threadList;
	private GenericList portList;
	private GenericList openFileList;

	// private String swapPathName;

	/**
	 * The task constructor. Must have
	 * 
	 * super();
	 * 
	 * as its first statement.
	 * 
	 * @OSPProject Tasks
	 */
	public TaskCB() {
		// your code goes here
		super();

	}

	/**
	 * This method is called once at the beginning of the simulation. Can be
	 * used to initialize static variables.
	 * 
	 * @OSPProject Tasks
	 */
	public static void init() {
		// your code goes here

	}

	/**
	 * Sets the properties of a new task, passed as an argument.
	 * 
	 * Creates a new thread list, sets TaskLive status and creation time,
	 * creates and opens the task's swap file of the size equal to the size (in
	 * bytes) of the addressable virtual memory.
	 * 
	 * @return task or null
	 * @OSPProject Tasks
	 */
	static public TaskCB do_create() {

		// your code goes here
		TaskCB task = new TaskCB();
		PageTable pagetable = new PageTable(task);
		task.setPageTable(pagetable);
		// task.pagetable = pagetable;

		task.openFileList = new GenericList();
		task.portList = new GenericList();
		task.threadList = new GenericList();

		task.setCreationTime(HClock.get());
		task.setStatus(TaskLive);
		task.setPriority(0);

		String swapPathName = SwapDeviceMountPoint+ Integer.toString(task.getID());
		int size = (int) Math.pow(2.0, MMU.getVirtualAddressBits());
		FileSys.create(swapPathName, size);

		OpenFile swap = OpenFile.open(swapPathName, task);
		task.setSwapFile(swap);

		if (swap == null) {
			ThreadCB.dispatch();
			task.do_kill();
			return null;
		}

		ThreadCB.create(task);
		return task;

	}

	/**
	 * Kills the specified task and all of it threads.
	 * 
	 * Sets the status TaskTerm, frees all memory frames (reserved frames may
	 * not be unreserved, but must be marked free), deletes the task's swap
	 * file.
	 * 
	 * @OSPProject Tasks
	 */
	public void do_kill() {
		// your code goes here

		Enumeration number;

		// Remove threads
		number = threadList.forwardIterator();
		while (number.hasMoreElements()) {
			ThreadCB thread = (ThreadCB) number.nextElement();
			thread.kill();
		}

		// Remove ports
		number = portList.forwardIterator();
		while (number.hasMoreElements()) {
			PortCB port = (PortCB) number.nextElement();
			port.destroy();
		}

		// Change status to terminated
		this.setStatus(TaskTerm);

		// Deallocate memory
		PageTable page = this.getPageTable();
		page.deallocateMemory();

		// Close Files
		number = openFileList.forwardIterator();
		while (number.hasMoreElements()) {
			OpenFile file = (OpenFile) number.nextElement();
			file.close();
		}

		// Delete swap file
		FileSys.delete(SwapDeviceMountPoint + this.getID());
	}

	/**
	 * Returns a count of the number of threads in this task.
	 * 
	 * @OSPProject Tasks
	 */
	public int do_getThreadCount() {
		// your code goes here

		return threadList.length();

	}

	/**
	 * Adds the specified thread to this task.
	 * 
	 * @return FAILURE, if the number of threads exceeds MaxThreadsPerTask;
	 *         SUCCESS otherwise.
	 * @OSPProject Tasks
	 */
	public int do_addThread(ThreadCB thread) {
		// your code goes here

		if (threadList.length() < ThreadCB.MaxThreadsPerTask) {
			threadList.append(thread);
			return SUCCESS;
		} else
			return FAILURE;

	}

	/**
	 * Removes the specified thread from this task.
	 * 
	 * @OSPProject Tasks
	 */
	public int do_removeThread(ThreadCB thread) {
		// your code goes here

		if (this.threadList.contains(thread)) {
			this.threadList.remove(thread);
			return SUCCESS;
		} else
			return FAILURE;

	}

	/**
	 * Return number of ports currently owned by this task.
	 * 
	 * @OSPProject Tasks
	 */
	public int do_getPortCount() {
		// your code goes here

		return this.portList.length();

	}

	/**
	 * Add the port to the list of ports owned by this task.
	 * 
	 * @OSPProject Tasks
	 */
	public int do_addPort(PortCB newPort) {
		// your code goes here

		if (portList.length() < PortCB.MaxPortsPerTask) {
			portList.insert(newPort);
			return SUCCESS;
		} else
			return FAILURE;

	}

	/**
	 * Remove the port from the list of ports owned by this task.
	 * 
	 * @OSPProject Tasks
	 */
	public int do_removePort(PortCB oldPort) {
		// your code goes here

		if (portList.remove(oldPort) != null) {
			return SUCCESS;
		} else
			return FAILURE;

	}

	/**
	 * Insert file into the open files table of the task.
	 * 
	 * @OSPProject Tasks
	 */
	public void do_addFile(OpenFile file) {
		// your code goes here
		
		openFileList.append(file);

	}

	/**
	 * Remove file from the task's open files table.
	 * 
	 * @OSPProject Tasks
	 */
	public int do_removeFile(OpenFile file) {
		// your code goes here
		
		if(openFileList.remove(file)!=null){
			return SUCCESS;
		}
		else 
			return FAILURE;

	}

	/**
	 * Called by OSP after printing an error message. The student can insert
	 * code here to print various tables and data structures in their state just
	 * after the error happened. The body can be left empty, if this feature is
	 * not used.
	 * 
	 * @OSPProject Tasks
	 */
	public static void atError() {
		// your code goes here
		System.out.println("ERROR");

	}

	/**
	 * Called by OSP after printing a warning message. The student can insert
	 * code here to print various tables and data structures in their state just
	 * after the warning happened. The body can be left empty, if this feature
	 * is not used.
	 * 
	 * @OSPProject Tasks
	 */
	public static void atWarning() {
		// your code goes here
		System.out.println("WARNING");

	}

	/*
	 * Feel free to add methods/fields to improve the readability of your code
	 */

}

/*
 * Feel free to add local classes to improve the readability of your code
 */
