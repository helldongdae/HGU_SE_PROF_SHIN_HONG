import java.io.*;

public class mdToHtml{
public static void main(String args[]) {
    cmdReader reader = new cmdReader(args);
    FileInputStream f = reader.file;
  }
}

class cmdReader extends mdToHtml{
    cmdReader(String args[]){
        if(!checkIfValid(args))
            System.exit(101);
        file = readFile(args[0]);
        if(file == null)
            System.exit(102);
    }

    public FileInputStream file;

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
            if(arguments[1].equals("plain") || arguments[1].equals("fancy") || arguments[1].equals("slide"))
                return true;
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
