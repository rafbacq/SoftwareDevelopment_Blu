package dev;

import java.io.*;
import java.util.*;

public class HashMapTest {
	static HashMap<Integer, ActionItem> map = new HashMap<>();
	
	final static String outputFilePath = "text.txt";
	
	public static void main(String [] args)
	{
		System.out.println("Hello world");
		map.put(1, new ActionItem("Nidhin", "Urgent", 2, 5, 2024, "Doing work"));
		map.put(2, new ActionItem("Sam", "Current", 2, 4, 2024, "Not doing work"));
		map.put(3, new ActionItem("Tristan", "Awaiting", 2, 3, 2024, "Working"));
		
		
		File file = new File(outputFilePath);
	
		FileWriter bf = null;

		try { 
			
			if(!file.exists()) 
			{
				file.createNewFile();
			}
			  
            // create new BufferedWriter for the output file 
            bf = new FileWriter(outputFilePath); 
  
            // iterate map entries 
            for (Map.Entry<Integer, ActionItem> entry : 
                 map.entrySet()) { 
  
                // put key and value separated by a colon 
                bf.write(entry.getKey() + ":"
                         + entry.getValue().toString()); 
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
    } 
}
