A JAVA Project has been submitted for PartB.
pom.xml file is present within the project.
All the required files/classes are present in the folder.

The code can be simply run by adding the run configuration in INTELLIJ and setting main class as main.

The code runs for 9 simulations with different values of Nodes(N) and threads created(T).
For smaller values of no. of nodes such as 10, 1000, 10000 etc. the processes searching/finding height/creation of the tree take more time with threads as the overhead due to thread creation exceeds the time taken for execution.
Therefore, parallelization takes more time than sequential execution.

Further, for higher values of number of nodes there is a slight improvement in time while running with multiple threads.

File I/O has been used in the code to store the node to be searched for in the searching process since searching must be done for the same node for all searching processes(with or without parallelization).
Generics has not been used as it was not necessary to use it since type was specified in all classes/methods etc. eg: nodes of binary tree had to be integers only.

Note: References for implementation of the Binary Tree used for PartB has been taken from geekforgeeks.



