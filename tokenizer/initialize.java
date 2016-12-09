package se.markdowm;

import java.io.FileInputStream;

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
