package se.markdowm;

import java.io.FileInputStream;

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