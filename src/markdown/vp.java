import java.io.*;
import java.util.*;

interface Visitor{
	public FileInputStream visit(getCmd getcmd);
	public String visit(tokenizer tok);
	public void visit(htmlHandler handler);
	public String visit(parser parse);
}

interface Visitable{
	public void accept(Visitor visitor);
}

class htmlHandler implements Visitable{
	FileWriter out;
	String has_header = "";
	boolean isOrderList = false;
	boolean isBlockQuote = false;
	boolean isParagraph = true;

	void init(String outputname){
		try{
             out = new FileWriter(outputname);
             String s = "<!DOCTYPE html><html><head><meta charset="+"\"utf-8\"><title>Expected Output</title><style></style></head><body id="+"\"Preview\">\n<p>\n";
             out.write(s);
        }
        catch(Exception e){
            System.out.println("HTML OPEN ERROR");
        }
	}

	String writeHtml(int flag, String token, String sentence){
		// Starts with something
		if(flag == 1){
			if(token.startsWith("h")){
				sentence = sentence.trim();
				if(isOrderList){
					endOfOrderList();
					isOrderList = false;
				}
				if(isBlockQuote){
					endOfBlockQuote();
					isBlockQuote = false;
				}
				try{
					out.write("<" + token + ">");
				}
				catch(Exception e){

				}
				int n = Integer.parseInt(token.substring(1));
				has_header = token;
				return sentence.substring(n+1);
			}
			else if(token.equals("ul")){
				sentence = sentence.trim();
				if(isOrderList){
					endOfOrderList();
					isOrderList = false;
				}
				if(isBlockQuote){
					endOfBlockQuote();
					isBlockQuote = false;
				}
				try{
					out.write("<ul><li>");
					has_header = "ul";
					return sentence.substring(2);
				}
				catch(Exception e){

				}
			}
			else if(token.equals("ol")){
				sentence = sentence.trim();
				if(isBlockQuote){
					endOfBlockQuote();
					isBlockQuote = false;
				}
				if(isOrderList){
					try{
						out.write("<li>");
						has_header = "ol";
						return sentence.substring(sentence.indexOf(". ") + 2);
					}
					catch(Exception e){

					}
				}
				else{
					isOrderList = true;
					try{
						out.write("<ol>\n<li>");
						has_header = "ol";
						return sentence.substring(sentence.indexOf(". ") + 2);
					}
					catch(Exception e){

					}
				}
			}
			else if(token.equals("bq")){
				if(isBlockQuote){
					return sentence.substring(1);
				}
				else{
					try{
						out.write("<blockquote>\n");
						has_header = "bq";
						isBlockQuote = true;
						return sentence.substring(1);
					}
					catch(Exception e){

					}
				}
			}
			else{
				if(isOrderList){
					endOfOrderList();
					isOrderList = false;
				}
				if(isBlockQuote){
					endOfBlockQuote();
					isBlockQuote = false;
				}
				return sentence;
			}
		}
		// Just write down the sentence
		if(flag == 2){
			try{
				out.write(sentence);
			}
			catch(Exception e){
			
			}	
		}
		return "";
	}

	void horizontalRule(){
		try{
			out.write("<hr>\n");
		}
		catch(Exception e){
		
		}
	}

	void decide(){
		if(isOrderList){
			endOfOrderList();
			isOrderList = false;
		}
		if(isBlockQuote){
				endOfBlockQuote();
				isBlockQuote = false;
		}
	}

	void endOfLine(){
		if(has_header.startsWith("h")){
			try{
				out.write("</" + has_header + ">\n");
			}
			catch(Exception e){

			}
		}
		else if(has_header.equals("ul")){
			try{
				out.write("</li></ul>\n");
			}
			catch(Exception e){
				
			}
		}
		else if(has_header.equals("ol")){
			try{
				out.write("</li>\n");
			}
			catch(Exception e){
				
			}
		}
	}

	void endOfOrderList(){
		try{
			out.write("</ol>\n");
		}
		catch(Exception e){
				
		}
	}

	void endOfBlockQuote(){
		try{
			out.write("</blockquote>\n");
		}
		catch(Exception e){
				
		}
	}

	void newLineHtml(){
		try{
			out.write("<br>");
		}
		catch(Exception e){
				
		}
	}

	String analyze(String s){
		int n1 = s.indexOf("**");
		if(n1 < 0)
			n1 = 999999;
		int n2 = s.indexOf("*");
		if(n2 < 0)
			n2 = 999999;
		int n3 = s.indexOf("__");
		if(n3 < 0)
			n3 = 999999;
		int n4 = s.indexOf("_");
		if(n4 < 0)
			n4 = 999999;
		int n5 = s.indexOf("~~");
		if(n5 < 0)
			n5 = 999999;
		int n6 = s.indexOf("[");
		if(n6 < 0)
			n6 = 999999;
		int n7 = s.indexOf("(");
		if(n7 < 0)
			n7 = 999999;
		int nMin = Math.min(n1, Math.min(n2, Math.min(n3, Math.min(n4, Math.min(n5, Math.min(n6, n7))))));
		if(nMin == 999999)
			nMin = nMin+1;
		// Bold
		if(nMin == n1){
			writeHtml(2, "", s.substring(0, s.indexOf("**")));
			s = s.substring(s.indexOf("**") + 2);
			if(s.indexOf("**") != -1){
				try{
					out.write("<strong>");
					out.write(s.substring(0, s.indexOf("**")));
					out.write("</strong>");
				}
				catch(Exception e){

				}
				return s.substring(s.indexOf("**") + 2);
			}
			else{
				writeHtml(2, "", "**");
				return s;
			}
		}
		// Bold
		else if(nMin == n3){
			writeHtml(2, "", s.substring(0, s.indexOf("__")));
			s = s.substring(s.indexOf("__") + 2);
			if(s.indexOf("__") != -1){
				try{
					out.write("<strong>");
					out.write(s.substring(0, s.indexOf("__")));
					out.write("</strong>");
				}
				catch(Exception e){

				}
				return s.substring(s.indexOf("__") + 2);
			}
			else{
				writeHtml(2, "", "__");
				return s;
			}
		}
		// Emphasize
		else if(nMin == n2){
			writeHtml(2, "", s.substring(0, s.indexOf("*")));
			s = s.substring(s.indexOf("*") + 1);
			if(s.indexOf("*") != -1){
				try{
					out.write("<em>");
					out.write(s.substring(0, s.indexOf("*")));
					out.write("</em>");
				}
				catch(Exception e){

				}
				return s.substring(s.indexOf("*") + 1);
			}
			else{
				writeHtml(2, "", "*");
				return s;
			}
		}
		// Emphasize
		else if(nMin == n4){
			writeHtml(2, "", s.substring(0, s.indexOf("_")));
			s = s.substring(s.indexOf("_") + 1);
			if(s.indexOf("_") != -1){
				try{
					out.write("<em>");
					out.write(s.substring(0, s.indexOf("_")));
					out.write("</em>");
				}
				catch(Exception e){

				}
				return s.substring(s.indexOf("_") + 1);
			}
			else{
				writeHtml(2, "", "_");
				return s;
			}
		}
		// Strikethrough
		else if(nMin == n5){
			writeHtml(2, "", s.substring(0, s.indexOf("~~")));
			s = s.substring(s.indexOf("~~") + 2);
			if(s.indexOf("~~") != -1){
				try{
					out.write("<strike>");
					out.write(s.substring(0, s.indexOf("~~")));
					out.write("</strike>");
				}
				catch(Exception e){

				}
				return s.substring(s.indexOf("~~") + 2);
			}
			else{
				writeHtml(2, "", "~~");
				return s;
			}
		}
		// link with inline style
		else if(nMin == n6){
			writeHtml(2, "", s.substring(0, s.indexOf("[")));
			s = s.substring(s.indexOf("[") + 1);
			if(s.indexOf("](") != -1){
				try{
					if(s.indexOf(")") != -1){
						String txt = s.substring(0, s.indexOf("]("));
						s = s.substring(s.indexOf("](") + 2);
						String link = s.substring(0, s.indexOf(")"));
						out.write("<a href=");
						out.write("\"" + link + "\"");
						out.write(">" + txt + "</a>");
						return s.substring(s.indexOf(")") + 1);
					}
					else{
						writeHtml(2, "", s.substring(0, s.indexOf("])") + 2));
						return s.substring(s.indexOf("](") + 2);
					}
				}
				catch(Exception e){

				}
				return s.substring(s.indexOf("~~") + 2);
			}
			else{
				writeHtml(2, "", "~~");
				return s;
			}
		}
		// link w/o inline style
		else if(nMin == n7){

		}
		else{
			writeHtml(2, "", s);
			newLineHtml();
		}
		return "";
	}

	void closeHtml(){
		if(isOrderList){
			endOfOrderList();
			isOrderList = false;
		}
		if(isBlockQuote){
			endOfBlockQuote();
			isBlockQuote = false;
		}
        try{
            String s = "\n</p>\n</body></html>";
            out.write(s);
            out.close();
        }
         catch(Exception e){
            System.out.println("HTML CLOSE ERROR");
        }
    }

    void paragraph(){
    	if(isOrderList){
			endOfOrderList();
			isOrderList = false;
		}
		if(isBlockQuote){
			endOfBlockQuote();
			isBlockQuote = false;
		}
    	try{
           out.write("</p><p>");
        }
        catch(Exception e){
            
        }
    }

	public void accept(Visitor visitor){
		visitor.visit(this);
	}
}

class tokenizer implements Visitable{
	// Condition : There is a possibility of table
	String whatToken(String s){
		if(s.startsWith("\r"))
			return "pg";
		String tok = startsWtoken(s);
		if(tok.equals(""))
			return "";
		else
			return tok;
	}

	String startsWtoken(String s){
		s = s.trim();
		try{
			int n = Integer.parseInt(s.substring(0, s.indexOf(". ")));
			return "ol";
		}
		catch(Exception e){
			if(s.startsWith("###### "))
				return "h6";
			else if(s.startsWith("##### "))
				return "h5";
			else if(s.startsWith("#### "))
				return "h4";
			else if(s.startsWith("### "))
				return "h3";
			else if(s.startsWith("## "))
				return "h2";
			else if(s.startsWith("# "))
				return "h1";
			else if(s.startsWith("* "))
				return "ul";
			else if(s.startsWith("+ "))
				return "ul";
			else if(s.startsWith("- "))
				return "ul";
			else if(s.startsWith(">"))
				return "bq";
			else
				return "";
		}
	}

	public void accept(Visitor visitor){
		visitor.visit(this);
	}
}

class parser implements Visitable{
	String s = "";
	int c = 0;
	boolean endOfFile = false;

	boolean isItEnd(){
		return endOfFile;
	}

	String parse(FileInputStream f){
		try{
			while(true){
				c = f.read();
				// END CONDITION
				if(c == -1){
					String rets = s;
					endOfFile = true;
					return rets;
				}
				// EOF
				else if((char)c == '\n'){
					String rets = s;
					s = "";
					return rets;
				}
				s += (char)c;
			}
		}
		catch(Exception e){
			return "";	
		}
	}

	public void accept(Visitor visitor){
		visitor.visit(this);
	}
}

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
            f = new FileInputStream(fileName);
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

class initialize implements Visitor{
	String args[];
	String option;
	FileInputStream file;

	initialize(String arguments[]){
		args = arguments;
	}

	public FileInputStream visit(getCmd getcmd){
		if(args.length == 0){
			System.out.println("Invalid arguments");
			System.exit(1);
		}
		if(args[0] == "help"){
			getcmd.help();
			System.exit(1);
		}
		getcmd.getargs(args);
		if(!getcmd.checkIfValid(args))
			System.exit(1);
		file = getcmd.readFile(args[0]);
		option = getcmd.getOption();
		if(file == null)
			System.exit(1);
		return file;
	}

	public String visit(tokenizer tok){
		return "";
	}

	public void visit(htmlHandler handler){
		handler.init("output.html");
		return;
	}

	public String visit(parser parse){
		return "";
	}
}

class mainProgram implements Visitor{
	FileInputStream mdFile;
	String line;
	String token;
	mainProgram(FileInputStream f){
		mdFile = f;
	}

	public FileInputStream visit(getCmd getcmd){
		return null;
	}

	public String visit(tokenizer tok){
		token = tok.whatToken(line);
		return token;
	}

	public void visit(htmlHandler handler){
		// Starting condition checker
		if(token.startsWith("h") || token.equals("ul") || token.equals("ol") || token.equals("bq")){
			line = handler.writeHtml(1, token, line);
		}
		else{
			handler.decide();
			handler.has_header = "";
		}
		if(token.equals("pg")){
			handler.paragraph();
		}
		// Check through the line
		while(true){
			//System.out.println(line);
			line = handler.analyze(line);
			if(line.equals(""))
				break;
		}
		//System.out.println(token);
		handler.endOfLine();
		if(token.equals("")){
			line = handler.writeHtml(1, token, line);
			handler.writeHtml(2, "", line);
		}
		return;
	}

	public String visit(parser parse){
		String s = "";
		if(!parse.isItEnd())
			s = parse.parse(mdFile);
		line = s;
		return s;
	}
}

class finish implements Visitor{
	public FileInputStream visit(getCmd getcmd){
		return null;
	}

	public String visit(tokenizer tok){
		return "";
	}

	public void visit(htmlHandler handler){
		handler.closeHtml();
		return;
	}

	public String visit(parser parse){
		return "";
	}
}

public class vp {
   public static void main(String[] args) {
   		FileInputStream mdFile;
   		FileWriter htmlFile;
   		initialize init = new initialize(args);
   		mdFile = init.visit(new getCmd());
   		htmlHandler html = new htmlHandler();
   		// INITIATE
   		init.visit(html);

   		// PARSE
   		mainProgram mainP = new mainProgram(mdFile);
   		parser p = new parser();
   		String s = "INITIAL";

   		while(true){
   			s = mainP.visit(p);
   			//System.out.println(s);
   			if(s.equals(""))
   				break;
   			mainP.visit(new tokenizer());
   			mainP.visit(html);
   		}

   		// FINISH
   		finish fin = new finish();
   		fin.visit(html);
		System.out.println("End of the program");
   }
}