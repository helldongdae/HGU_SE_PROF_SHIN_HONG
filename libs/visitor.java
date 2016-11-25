import java.io.*;
import java.util.*;
import org.w3c.tidy.Tidy;

interface InterfaceVisitor{
	public void visit(mdParser parser);
	public void visit(mdToHtml mainProgram);
	public void visit(cmdReader reader);
	public void visit(htmlWriter writer);
}

interface Interface{
	public void accept(InterfaceVisitor visitor);
}


class htmlWriter implements Interface{

	public void accept(InterfaceVisitor visitor){
		init();
		startParse();
		closeHtml();
		visitor.visit(this);
	}

	FileWriter out;
	FileInputStream file;

	void getFile(FileInputStream f){
		 try{
             file = f;
        }
         catch(Exception e){
            System.out.println("ERROR: File does not exist!");
        }
	}

	void init(){
		 try{
             out = new FileWriter("output.html");
             String s = "<!DOCTYPE html><html><head><meta charset="+"\"utf-8\"><title>Expected Output</title><style></style></head><body id="+"\"Preview\">\n";
             out.write(s);
        }
         catch(Exception e){
            System.out.println("ERROR: File does not exist!");
        }
	}

	void closeHtml(){
         try{
            String s = "\n</body></html>";
            out.write(s);
            out.close();
        }
         catch(Exception e){
            System.out.println("ERROR: File closing error!");
        }
    }

    void startParse(){
        int c = 0;
        int prev = 0;
        try{
            FileInputStream f = file;
            String s = "";
            boolean flag = false;
            boolean f2 = false;
            int tok = -1;
            // Parse string by string till -1 appears
            while(true){
                if(!flag){
                    initParagraph();
                    flag = true;
                }
                c = f.read();
                // Reached to the end of the file   
                if(c == -1){
                	writeHtml(s);
                    closeParagraph();
                    System.out.print(s); 
                    break;
                }

                else if((char)c == '\n'){
                    flag = false;
                    writeHtml(s);
                    closeParagraph();
                }
                s += (char)c;
                prev = c;
            }
        }
        catch(Exception e){
              System.out.print("Exception");
        }
    }

    void writeHtml(String s){
    	if(s.startsWith("######")){
    		try{
          		out.write("<h6>");
    			out.write(s.substring(6));
    			out.write("</h6>");
        	}
         	catch(Exception e){
           
        	}
    	}
    	else if(s.startsWith("#####")){
    		try{
          		out.write("<h5>");
    			out.write(s.substring(5));
    			out.write("</h5>");
        	}
         	catch(Exception e){
           
        	}
    	}
    	else if(s.startsWith("####")){
    		try{
          		out.write("<h4>");
    			out.write(s.substring(4));
    			out.write("</h4>");
        	}
         	catch(Exception e){
           
        	}
    	}
    	else if(s.startsWith("###")){
    		try{
          		out.write("<h3>");
    			out.write(s.substring(3));
    			out.write("</h3>");
        	}
         	catch(Exception e){
           
        	}
    	}
    	else if(s.startsWith("##")){
    		try{
          		out.write("<h2>");
    			out.write(s.substring(2));
    			out.write("</h2>");
        	}
         	catch(Exception e){
           
        	}
    	}
    	else if(s.startsWith("#")){
    		try{
          		out.write("<h1>");
    			out.write(s.substring(1));
    			out.write("</h1>");
        	}
         	catch(Exception e){
           
        	}
    	}
    }

	boolean is_alphabet(int character){
			// uppercase
            if(character >= 65 && character <= 90)
                return true;
          	//lowercase
            else if(character >= 97 && character <= 122)
                return true;
            //numbers
            else if(character >= 48 && character <= 57)
                return true;
            else if((char)character == ' ')
            	return true;
            else if((char)character == '\n')
                return true;
            else
                return false;
    }

	void initParagraph(){
        try{
            String s = "<p>";
            out.write(s);
        }
        catch(Exception e){
           System.out.println("ERROR: initParagraph");
        }
    }

     void closeParagraph(){
        try{
             String s = "</p>";
             out.write(s);
        }
        catch(Exception e){
           System.out.println("ERROR: closeParagraph");
        }
    }

    int is_token(String s){
    	if(s.equals("*"))
    		return 1;
    	else if(s.equals("**"))
    		return 2;
    	else if(s.equals("~~"))
    		return 3;
    	else if(s.equals("#"))
    		return 4;
    	else if(s.equals("##"))
    		return 5;
    	else if(s.equals("###"))
    		return 6;
    	else if(s.equals("####"))
    		return 7;
    	else if(s.equals("######"))
    		return 8;
    	else if(s.equals("#######"))
    		return 9;
    	else
    		return -1;
    }
}



class mdParser implements Interface{
	FileInputStream file = null;
	htmlWriter writer = null;
	public void accept(InterfaceVisitor visitor){
		writer.getFile(file);
		writer.accept(visitor);
		visitor.visit(this);
	}

	void getFile(FileInputStream f){
		writer = new htmlWriter();
		file = f;
	}

	
}


// THIS IS THE MOST IMPORTANT CLASS
class mdToHtml implements Interface{
	mdParser parser;
	cmdReader reader;
	String arguments[];
	FileInputStream file = null;
	public mdToHtml(String args[]){
		parser = new mdParser();
		reader = new cmdReader();
		arguments = args;
	}
	public void accept(InterfaceVisitor visitor){
		reader.getargs(arguments);
		reader.accept(visitor);
		file = reader.readFile(arguments[0]);
		if(file == null)
			System.exit(1);
		printSuccess();
		parser.getFile(file);
		parser.accept(visitor);
		visitor.visit(this);
	}

	public void printSuccess(){
		System.out.println("Received valid arguments");
    	System.out.println("Option: "+ reader.option);
	}
}



class cmdReader implements Interface{
	String arguments[];
	String option = "plain";

	public void accept(InterfaceVisitor visitor){
		if(!checkIfValid(arguments))
			System.exit(1);
		visitor.visit(this);
	}

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
            f = new FileInputStream(fileName);
        }
        catch(Exception e){
            System.out.println("ERROR: File does not exist!");
        }
        return f;
    }
}



class mainLogic implements InterfaceVisitor{

	public void visit(mdToHtml mainProgram){
 	
	}
	public void visit(mdParser parser){
		
	}
	public void visit(cmdReader reader){
	
	}
	public void visit(htmlWriter writer){
	
	}
}


public class visitor {
   public static void main(String[] args) {
   		mdToHtml program = new mdToHtml(args);
   		program.accept(new mainLogic());
   }
}
