package todo;

import java.io.*;
import java.util.*;

public class HashMapWriteTest {
	HashMap<Integer, ActionItem[]> map = new HashMap<>();
	
	final static String outputFilePath = "text.txt";
	File file = new File(outputFilePath);
	
	public void createVersion(int versionNumber, ActionItem[] list) 
	{
		map.put(versionNumber, list);
		
		FileWriter bf = null;

		try { 
			
			if(!file.exists()) 
			{
				file.createNewFile();
			}
			  
            // create new BufferedWriter for the output file 
            bf = new FileWriter(outputFilePath, true); 
  
            // iterate map entries 
            for (int i: map.keySet()) {
            	bf.write("V" + i + ";\n");
            	for(ActionItem item : map.get(i)) 
            	{
            		bf.write(item.toFile() + "\n"); 
            	}
            	bf.write("split\n");
            } 
  
            bf.flush(); 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally { 
  
            try { 
  
                // always close the writer 
                bf.close(); 
            } 
            catch (Exception e) { 
            } 
        }
		System.out.println("Complete");
	}
	
	public static void main(String [] args)
	{
		ActionItem[] list = {new ActionItem("Nidhin", "Urgent", 2, 5, 2024, "Doing work"), new ActionItem("Sam", "Current", 2, 4, 2024, "Not doing work"), new ActionItem("Tristan", "Awaiting", 2, 3, 2024, "Working")};
		ActionItem[] list2 = {new ActionItem("Maddie", "Lost", 2, 6, 2024, "Playing Games"), new ActionItem("Lily", "Current", 2, 4, 2024, "Actively Working"), new ActionItem("Aiden", "Awaiting", 2, 3, 2024, "Chipotle")};
		HashMapWriteTest h = new HashMapWriteTest();
		h.createVersion(1, list);
		h.createVersion(2, list2);
		
	}
}