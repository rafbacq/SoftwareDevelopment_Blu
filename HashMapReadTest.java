package dev;

//Java program to reading 
//text file to HashMap 

import java.io.*; 
import java.util.*;
import java.util.Date;
import java.time.*;

public class HashMapReadTest { 
	
	//File path where the versions are being read from
	final static String filePath 
		= "text.txt"; 
	
	public static void main(String[] args) 
	{ 
		// read text file to HashMap 
		Map<Integer, ActionItem[]> mapFromFile 
			= HashMapFromTextFile(); 

		// iterate over HashMap entries 
		for (Map.Entry<Integer, ActionItem[]> entry : 
			mapFromFile.entrySet()) { 
			System.out.println(entry.getKey() + " : "
							+ entry.getValue()); 
		} 
	} 

	/*
	 * Returns a map of the keys and according values as written in the file
	 * */
	public static Map<Integer, ActionItem[]> HashMapFromTextFile() 
	{ 
		
		//Creates map that will be returned
		Map<Integer, ActionItem[]> map 
			= new HashMap<Integer, ActionItem[]>(); 
			
		//Initialize Buffered Reader to read file
		BufferedReader br = null; 

		try { 
			// create file object 
			File file = new File(filePath); 

			// create BufferedReader object from the File 
			br = new BufferedReader(new FileReader(file)); 
			
			//reads file line by line and adds line to text
			String text = null;
			String line = null; 
			while ((line = br.readLine()) != null) 
			{
				text += line;
			}

				// split the text by . to separate versions 
				String[] versions = line.trim().split("."); 
				
				// iterate over each version in version history
				for(String version : versions) 
				{
					//initializing where actionItems will be stored before map is created
					ArrayList<ActionItem> actionItems = new ArrayList<ActionItem>();
					
					//splits version ID and actionItemElements
					String[] parts = version.trim().split(":");
					int versionNumber = parts[0].charAt(1);
					
					//splits specific elements
					String[] actionItemElements = parts[1].trim().split(",");
					
					int count = 0;
					
					//traversing backwards
					for(int i = actionItemElements.length - 1; i >= 0; i--) 
					{
						  
						String comments = actionItemElements[(actionItemElements.length - 1) - (count * 5)];
						
						String dateCompleteString = actionItemElements[(actionItemElements.length - 2) - (count * 5)];
						String[] dateCompleteComponents = dateCompleteString.split("-");
						LocalDateTime dateComplete = LocalDateTime.of(Integer.parseInt(dateCompleteComponents[0]), Integer.parseInt(dateCompleteComponents[1]), 
								dateCompleteComponents[2].charAt(9) + dateCompleteComponents[2].charAt(10), 11, 59);
						
						String dateAddedString = actionItemElements[(actionItemElements.length - 3) - (count * 5)];
						String[] dateAddedComponents = dateAddedString.split("-");
						LocalDateTime dateAdded = LocalDateTime.of(Integer.parseInt(dateAddedComponents[0]), Integer.parseInt(dateAddedComponents[1]), 
								dateAddedComponents[2].charAt(9) + dateAddedComponents[2].charAt(10), 11, 59); 
						
						String status = actionItemElements[(actionItemElements.length - 4) - (count * 5)];
						
						String name = actionItemElements[(actionItemElements.length - 5) - (count * 5)];
						
						ActionItem ai = new ActionItem(name, status, dateAdded, dateComplete, comments);
						actionItems.add(ai);
						count++;
					}
					ActionItem[] actionItemsList = new ActionItem[actionItems.size()];
					for(int i = 0; i < actionItems.size(); i++) 
					{
						actionItemsList[i] = actionItems.get(i);
					}
					map.put(versionNumber, actionItemsList);
				}
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		} 
		finally { 

			// Always close the BufferedReader 
			if (br != null) { 
				try { 
					br.close(); 
				} 
				catch (Exception e) { 
				}; 
			} 
		} 

		return map; 
	} 
}
