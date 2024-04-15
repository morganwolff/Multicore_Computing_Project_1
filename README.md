# Prime Numbers Calculation Programs Guide
This directory contains three Java programs that calculate the number of prime numbers within a specified range using multithreading. Each program uses a different load balancing method: static block decomposition, static cyclic decomposition, and dynamic load balancing.

## Source Files
- **`pc_static_block.java`**: Uses static load balancing with block decomposition.
- **`pc_static_cyclic.java`**: Uses static load balancing with cyclic decomposition.
- **`pc_dynamic.java`**: Uses dynamic load balancing.
## Prerequisites
Ensure that Java is installed on your machine. You can verify this by running **`java -version`** in your terminal. If Java is not installed, please download and install the latest JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/#java11) or choose another JDK provider.

## Compiling the Programs
Open a terminal and navigate to the directory containing the source files. Use the following command to compile each program:

```
javac <FileName>.java
```
Example for **`pc_static_block.java`**:

```
javac pc_static_block.java
```
Repeat this for **`pc_static_cyclic.java`** and **`pc_dynamic.java`**.

## Running the Programs
Each program can be executed with or without arguments. If no arguments are provided, default values are used.

### General Format
```
java <ClassName> [numberOfThreads] [upperLimit]
```
- **`numberOfThreads`**: The number of threads to use for the computation (default is 4).
- **`upperLimit`**: The upper limit of the range of numbers to test for primality (default is 200,000).
### Execution Examples
1. **Running with default values**:

```
java pc_static_block
```
2. **Running with custom arguments**:

To use 8 threads and test numbers up to 500,000:

```
java pc_static_block 8 500000
```
Repeat similarly for **`pc_static_cyclic`** and **`pc_dynamic`**.

## Output Explanation
Each program will display:

The execution time of each thread.
The total program execution time.
The total number of prime numbers found within the specified range.
### Output exemple
```
> java problem1.pc_static_cyclic 32 20000000

Thread 25 Execution Time: 632ms
Thread 24 Execution Time: 688ms
Thread 21 Execution Time: 643ms
Thread 26 Execution Time: 726ms
Thread 32 Execution Time: 664ms
Thread 20 Execution Time: 735ms
Thread 48 Execution Time: 574ms
Thread 47 Execution Time: 587ms
Thread 23 Execution Time: 766ms
Thread 27 Execution Time: 766ms
Thread 44 Execution Time: 617ms
Thread 49 Execution Time: 616ms
Thread 39 Execution Time: 671ms
Thread 29 Execution Time: 798ms
Thread 31 Execution Time: 765ms
Thread 42 Execution Time: 672ms
Thread 30 Execution Time: 790ms
Thread 45 Execution Time: 659ms
Thread 28 Execution Time: 819ms
Thread 34 Execution Time: 729ms
Thread 46 Execution Time: 688ms
Thread 50 Execution Time: 679ms
Thread 38 Execution Time: 726ms
Thread 40 Execution Time: 727ms
Thread 33 Execution Time: 765ms
Thread 37 Execution Time: 751ms
Thread 22 Execution Time: 875ms
Thread 35 Execution Time: 761ms
Thread 36 Execution Time: 763ms
Thread 43 Execution Time: 746ms
Thread 51 Execution Time: 705ms
Thread 41 Execution Time: 754ms
Program Execution Time: 895ms
1...19999999 prime# counter=1270607
```
## Known Issues
If you encounter errors related to "Could not find or load main class," ensure that the .class files are in the same directory from which you are executing the command or check your CLASSPATH environment variable setting.


>*This document was written with the help of ChatGPT*