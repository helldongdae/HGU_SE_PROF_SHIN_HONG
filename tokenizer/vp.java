package se.markdowm;

import java.io.FileInputStream;
import java.io.FileWriter;

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