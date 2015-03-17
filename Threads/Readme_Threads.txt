//Project 2: Threads in OSP2 Operating System

—> We have implemented Round Robin scheduling method for scheduling Threads. In Round Robin scheduling, a thread selected to run continues executing until:

a) voluntarily relinquishes control
b) is preempted by a higher-priority thread
c) consumes its timeslice

—> The data structure used to store the ready queue is a List.

Compiling and running:

Go to the Threads folder in the Terminal and run the following two commands:

javac -g -classpath .:OSP.jar: -d . *.java

And this for starting the simulator,

java -classpath .:OSP.jar osp.OSP


Screenshot of the simulator output is there in the Threads folder.