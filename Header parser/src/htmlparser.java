import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;

public class htmlparser {
	public static String linecheck(String input)
	{
		int h_count = 0 ;
		String to_app ; 
		String content = "";
		
		// checking if starting with header 
		if(input.startsWith("#"))
		{
			// get header count 
			to_app = "<h";
			for (int i = 0 ; i != input.length() ; i++) {
				char a_char = input.charAt(i);
			    if(a_char == '#' )
			    {
			    	h_count++;
			    }
			    else 
			    {
			    	content = content + input.charAt(i);
			    }
			}
			// switch case to determine h#
			switch(h_count)
			{
				case 1: to_app = to_app.concat("1>");
					break;
				case 2: to_app = to_app.concat("2>");
					break;
				case 3: to_app = to_app.concat("3>");
					break;
				case 4: to_app = to_app.concat("4>");
					break;
				case 5: to_app = to_app.concat("5>");
					break;
				case 6: to_app = to_app.concat("6>");
					break;
				
			}
			StringBuffer at_end = new StringBuffer(to_app);
			at_end.insert(1, '/');
			
			input = to_app + content + at_end;
		}
		// <p> paragraph. If input starts with any alphabets
			// under paragraph, also implement emphasis code and link 
		
		// <li> lists. 
			// under lists, also implement empahsis and link
		
		
		
		return input;
	}
	public static void main(String[] args) throws IOException
	{
		 try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) 
		 {
			    String  line;
	            // flushing text file to delete content
	            FileWriter writer = new FileWriter("out.txt",false);
	            writer.flush();
	            writer.close();
	            while ((line = br.readLine()) != null) 
	            {
	            	
		            System.out.println(line);
		                
		            // writing into text file 
		            writer = new FileWriter("out.txt",true);
		            line = linecheck(line);
		            writer.write(line);
		            writer.write("\r\n");   // write new line
		            writer.flush();
		            writer.close();
		        }
	      } catch (IOException e)
		  {
	            e.printStackTrace();
	      }
	}
}
