README

MARKDOWN 2 HTML CONVERTER v.1.0 

How to use

1. import cmdReader class

2. import java.io.* class

3. receive command line arguments in form of : 

	FILENAME.md OPTION or FILENAME.md or HELP

4. make an instance of cmdReader and give arguments as its parameter.
 
***VALID OPTIONS***

A. plain

B. fancy

C. slide
## All options are CASE SENSITIVE
5. if you type in FILENAME.md and OPTION, program will read the specified file name from the classpath. If the filename is invalid or the file does not exist or the option is invalid, the program will print error message and terminate.
6. if you type in only FILENAME.md, program will read the specified file name from the classpath. If the filename is invalid or the file does not exist, the program will print error message and terminate.
7. if you type in only HELP, program will print out help message and terminate.
8. if there exist an error, only one error will be told.
9. if there is no error, then the the content of the file specified by FILENAME will be stored in <file> inputfilestream in the instance of cmdReader.