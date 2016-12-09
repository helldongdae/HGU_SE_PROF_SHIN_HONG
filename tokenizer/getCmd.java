package se.markdowm;

import java.io.FileInputStream;

class getCmd implements Visitable{
	String option;
	String arguments[];

	void getargs(String args[]){
		arguments = args;
	}

	void help(){
        System.out.println("-----------HELP-------------");
        System.out.println("MarkDown -> HTML Converter");
        System.out.println("v. 1. 0.");
        System.out.println("Valid Options");
        System.out.println("1. plain   2. fancy   3. slide");
        System.out.println("argument 1 = File name");
        System.out.println("argument 2 = Option   ");
        System.out.println("________________________________");
    }   

    String getOption(){
    	return option;
    }

    boolean checkIfValid(String arguments[]){
    // Check if given file is a valid md file
        int locationOfDot = 0;
        String checkValidMD = null;

        if(arguments.length > 2 || arguments.length == 0){
            System.out.println("ERROR : Invalid command line arguments!");
            return false;
        }

        if(arguments[0].equals("HELP")){
        	help();
        	return false;
        }

        locationOfDot = arguments[0].indexOf('.');
        
        if(locationOfDot == -1){
            System.out.println("ERROR : Invalid fle name!");
            return false;
        }
        
        checkValidMD = arguments[0].substring(locationOfDot);    
        
        if(!checkValidMD.equals(".md")){
            System.out.println("ERROR: Invalid fle name!");
            return false;
        }

        if(arguments.length > 1){
         	if(arguments[1].equals("plain")){
         		option = "plain";
         		return true;
         	} 

         	if(arguments[1].equals("fancy")){
         		option = "fancy";
         		return true;
         	}

         	if(arguments[1].equals("slide")){
         		option = "slide";
         		return true;
         	}
	       		
	     	else{
	     		System.out.println("ERROR: Invalid Option!");
	     		return false;
	     	}
		}
	    else if(arguments.length == 1)
           return true;
       return true;
    }

    FileInputStream readFile(String fileName){
        FileInputStream f = null;
        try{
            f = new FileInputStream(fileName);`
        }
        catch(Exception e){
            System.out.println("ERROR: File does not exist!");
        }
        return f;
    }

	public void accept(Visitor visitor){
		visitor.visit(this);
	}	
}