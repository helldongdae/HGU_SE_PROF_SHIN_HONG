README

MARKDOWN 2 HTML CONVERTER v.1.0 

How to use

If you need help, type in HELP
   Ex) java mdToHtml HELP

1. Execute mdToHtml.java file

2. Type in arguments in form of <filename option>
   Ex) java mdToHtml file.md fancy
   if you want to give multiple md files as input, then 
    Ex) java mdToHtml file1.md file2.md fancy

3. Program will consider if given inputs are valid.

4. If your input is invalid, the program will stop and tell you the reason why your input is invalid.

5. If your input is valid, the program will return result as:
   
   Received valid arguments
   
   Option : plain
   
   Number of inputs: 1

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
