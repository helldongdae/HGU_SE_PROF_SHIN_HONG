import java.io.*;

public class mdToHtml{
public static void main(String args[]) {
    cmdReader reader = new cmdReader(args);
    System.out.println("Received valid arguments");
    System.out.println("Option: "+ reader.option);
    System.out.print("Number of inputs: ");
    System.out.println(reader.numOfMd);
  }
}

class cmdReader extends mdToHtml1{
    public FileInputStream file[] = null;
    public String filenames[] = null;
    public String option;
    public int numOfMd;

    cmdReader(String args[]){
        if(args.length == 0){
            System.out.println("Invalid arguments!");
            System.exit(101);
        }
        if(args.length == 1 && args[0].equals("HELP"))
            help();
        numOfMd = howManyMds(args);
        if(numOfMd == 0){
            System.out.println("No md file is given!");
            System.exit(101);
        }
        file = new FileInputStream[numOfMd];
        filenames = new String[numOfMd];
        int i;
        for(i = 0; i < numOfMd; i++){
            if(checkIfRedundant(args[i], i)){
                System.out.println("Redundant file name!");
                System.exit(101);
            }
            file[i] = readFile(args[i]);
            filenames[i] = args[i];
        }
        if(i == args.length-1){
            if(!checkIfValidOption(args[args.length-1])){
                System.out.println("Invalid Option!");
                System.exit(101);
            }
            option = args[i];
        }
        else if(i == args.length)
            option = "plain";
        else{
            System.out.println("Invalid Option!");
            System.exit(101);
        }
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
        System.exit(101);
    }   

    boolean checkIfRedundant(String s, int n){
        for(int i = 0;i < n;i++){
            if(filenames[i].equals(s))
                return true;
        }
        return false;
    }

    int howManyMds(String arguments[]){
        int ret = 0;
        for(int i = 0;i<arguments.length;i++){
            if(isMd(arguments[i]))
                ret++;  
        }
        return ret;
    }

    boolean checkIfValidOption(String option){
       if(option.equals("plain") || option.equals("fancy") || option.equals("slide"))
        return true;
       else
        return false;
    }

    boolean isMd(String name){
        int locationOfDot = 0;
        String checkValidMD = null;

        locationOfDot = name.indexOf('.');
        
        if(locationOfDot == -1)
            return false;
        
        checkValidMD = name.substring(locationOfDot);    
        
        if(!checkValidMD.equals(".md"))
            return false;

        return true;
    }

    FileInputStream readFile(String fileName){
        FileInputStream f = null;
        try{
            f = new FileInputStream(fileName);
        }
        catch(Exception e){
            System.out.println("ERROR: File does not exist!");
            System.exit(101);
        }
        return f;
    }

}