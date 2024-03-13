package Software_Development;

import java.io.*;
import java.util.Date;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class HashMapWriteTest {
	HashMap<Integer, ArrayList<ActionItem>> map = new HashMap<>();
	
	static String outputFilePath = "version.txt";
	File file = new File(outputFilePath);
	public static int listNum=1;
	
	public HashMapWriteTest()
	{
		map = new HashMap<>();
		System.out.println("Map Created");
	}
	public static String getFilePath()
	{
		return outputFilePath;
	}
	public void createVersion(int versionNumber, ArrayList<ActionItem> list) 
	{
		map = new HashMap<>();
		map.put(versionNumber, list);
		FileWriter bf = null;

		try { 
			
			if(!file.exists()) 
			{
				file.createNewFile();
			}
			  
            // create new BufferedWriter for the output file 
            bf = new FileWriter(outputFilePath, true); 
            System.out.println("Buffered Reader Created");
  
            // iterate map entries 
            for (int i: map.keySet()) {
            	if (map != null) 
            	{
            		bf.write("V" + i + " -- " + LocalDateTime.now().toString().substring(0, 10) + ";\n");
            		System.out.println("V" + i + " -- " + LocalDateTime.now().toString().substring(0, 10) + ";\n");
	            	for(ActionItem item : map.get(i)) 
	            	{
	            		bf.write(item.toFile() + "\n");
	            		System.out.println(item.toFile() + "\n");
	            	}
	            	bf.write("split\n");
	            	System.out.println("split\n");
            	}
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
	
	public static void main(String[] args) 
	{
		ArrayList<ActionItem> list = new ArrayList<ActionItem>();
		list.add(new ActionItem("Nidhin", "Urgent", 2, 5, 2024, "Doing work"));
		list.add(new ActionItem("Sam", "Current", 2, 4, 2024, "Not doing work"));
		list.add(new ActionItem("Tristan", "Awaiting", 2, 3, 2024, "Working"));
		ArrayList<ActionItem> list2 = new ArrayList<ActionItem>();
		list2.add(new ActionItem("Maddie", "Lost", 2, 6, 2024, "Playing Games"));
		list2.add(new ActionItem("Lily", "Current", 2, 4, 2024, "Actively Working"));
		list2.add(new ActionItem("Aiden", "Awaiting", 2, 3, 2024, "Chipotle"));
		HashMapWriteTest h = new HashMapWriteTest();
		h.createVersion(1, list);
		h.createVersion(2, list2);
		h.createVersion(3, list);
		h.createVersion(4, list2);
	}
}
