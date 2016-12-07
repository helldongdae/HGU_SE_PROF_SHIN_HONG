import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;

public class htmlparser {
	public static boolean islogo = false; 
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
		// img tag
		else if(input.startsWith("![alt text](") || input.startsWith("![alt text] ("))
		{
			// get src and title
			to_app = "<img src= ";
			String title = " "; 
			boolean istitle = false; 
			boolean isadd= false; 
			for (int i = 0 ; i != input.length() ; i++) {
				char a_char = input.charAt(i);
			    if(a_char == '"' ) // now getting title 
			    {
			    	istitle = true ;     		
	
			    }
			    else if ( istitle == true)
			    {
			    	title = title + input.charAt(i); 
			    }
			    else if (a_char == '(') // look for open bracket for address
			    {
			    	isadd = true; 
			    }
			    else if (isadd == true)
			    {
			    	// contnet in this case is adress
			    	content = content + input.charAt(i);
			    }
			}
			StringBuffer at_add = new StringBuffer(content);
			StringBuffer at_title = new StringBuffer(title);
			at_add.insert(0, '"');
			at_add.insert(at_add.length(), '"');
			
			at_title.insert(0, '"');
			at_title.deleteCharAt(at_title.length()-2); // deleteing ) at end 
			at_title.insert(at_title.length(), '"');
			at_title.insert(at_title.length(), '>');
			input = to_app + at_add+ " alt =" + at_title;
		}
		else if(input.startsWith("![alt text][logo]") || input.startsWith("![alt text] [logo]"))
		{
				islogo = true; 
				input = " ";
				
		}
		else if(input.startsWith("[logo]") && islogo == true)
		{
			// get src and title
			to_app = "<img src= ";
			String title = " "; 
			boolean istitle = false; 
			boolean isadd= false; 
			for (int i = 0 ; i != input.length() ; i++) {
				char a_char = input.charAt(i);
			    if(a_char == '"' ) // now getting title 
			    {
			    	istitle = true ;     		
	
			    }
			    else if ( istitle == true)
			    {
			    	title = title + input.charAt(i); 
			    }
			    else if (a_char == ':') // look for : for address
			    {
			    	isadd = true; 
			    }
			    else if (isadd == true)
			    {
			    	// contnet in this case is adress
			    	content = content + input.charAt(i);
			    }
			}
			StringBuffer at_add = new StringBuffer(content);
			StringBuffer at_title = new StringBuffer(title);
			at_add.insert(0, '"');
			at_add.insert(at_add.length(), '"');
			
			at_title.insert(0, '"');
			at_title.deleteCharAt(at_title.length()-2); // deleteing ) at end 
			at_title.insert(at_title.length(), '"');
			at_title.insert(at_title.length(), '>');
			input = to_app + at_add+ " alt =" + at_title;
			islogo = false; 
		}
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

