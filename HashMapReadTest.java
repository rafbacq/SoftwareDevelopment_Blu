package smth;

//Java program to reading 
//text file to HashMap 

import java.io.*; 
import java.util.*;
import java.util.Map.Entry;
import java.time.*;

public class HashMapReadTest { 
	
	//File path where the versions are being read from
	
	
	public static void main(String[] args) 
	{ 
		// read text file to HashMap 
		Map<Integer, ArrayList<ActionItem>> mapFromFile 
			= HashMapFromTextFile(); 
		

		// iterate over HashMap entries 
		for (Map.Entry<Integer, ArrayList<ActionItem>> entry : 
			mapFromFile.entrySet()) { 
			System.out.println("Version" + entry.getKey() + ":");
			for(ActionItem a : entry.getValue()) 
			{
				System.out.println(a.toString());
			}
		} 
	} 
	public String getHistory()
	{ 
		// read text file to HashMap 
		Map<Integer, ArrayList<ActionItem>> mapFromFile 
			= HashMapFromTextFile(); 
		String finalString = "";

		// iterate over HashMap entries 
		for (Entry<Integer, ArrayList<ActionItem>> entry : 
			mapFromFile.entrySet()) { 
			System.out.println("Version" + entry.getKey() + ":");
			for(ActionItem a : entry.getValue()) 
			{
				finalString += a.toString();
			}
		} 
		return finalString;
	} 

	/*
	 * Returns a map of the keys and according values as written in the file
	 * */
	public static Map<Integer, ArrayList<ActionItem>> HashMapFromTextFile() 
	{ 
		
		//Creates map that will be returned
		Map<Integer, ArrayList<ActionItem>> map 
			= new HashMap<Integer, ArrayList<ActionItem>>(); 
			
		//Initialize Buffered Reader to read file
		BufferedReader br = null; 

		try { 
			// create file object 
			File file = new File(HashMapWriteTest.getFilePath()); 

			// create BufferedReader object from the File 
			br = new BufferedReader(new FileReader(file)); 

			//reads file line by line and adds line to text
			String text = "";
			String line = null; 
			while ((line = br.readLine()) != null) 
			{
				text += line;
			}

				// split the text by . to separate versions 
				String[] versions = text.trim().split("split"); 
				for(String version : versions) 
				{
					System.out.println(version);
				}
				
				// iterate over each version in version history
				for(String version : versions) 
				{
					//initializing where actionItems will be stored before map is created
					ArrayList<ActionItem> actionItems = new ArrayList<ActionItem>();
					
					//splits version ID and actionItemElements
					String[] parts = version.trim().split(";");
					int versionNumber = parts[0].charAt(1) - 48;
					System.out.println("\nVersion Number:" + versionNumber);
					
					//splits specific elements
					String[] actionItemElements = parts[1].trim().split(",");
					
					int count = 0;
					
					//traversing backwards
					for(int i = (actionItemElements.length / 5) - 1; i >= 0; i--) 
					{
						int newIndex = (actionItemElements.length - 1) - (count * 5);
						String comments = actionItemElements[newIndex];
						
						String dateCompleteString = actionItemElements[newIndex - 1];
						String[] dateCompleteComponents = dateCompleteString.split("-");
						
						int day = ((dateCompleteComponents[2].charAt(0) - 48) * 10) + (dateCompleteComponents[2].charAt(1) - 48);
						
						LocalDateTime dateComplete = LocalDateTime.of(Integer.parseInt(dateCompleteComponents[0]), (Integer.parseInt(dateCompleteComponents[1])), 
								day, 11, 59);
						
						String dateAddedString = actionItemElements[newIndex - 2];
						String[] dateAddedComponents = dateAddedString.split("-");
						
						day = ((dateAddedComponents[2].charAt(0) - 48) * 10) + (dateAddedComponents[2].charAt(1) - 48);

						LocalDateTime dateAdded = LocalDateTime.of(Integer.parseInt(dateAddedComponents[0]), Integer.parseInt(dateAddedComponents[1]), 
								day, 11, 59); 
						
						String status = actionItemElements[newIndex - 3];
						
						String name = actionItemElements[newIndex - 4];
						
						ActionItem ai = new ActionItem(name, status, dateAdded, dateComplete, comments);
						actionItems.add(ai);
						
						count = count + 1;
					}
					map.put(versionNumber, actionItems);
				}
		} 
		catch (Exception e) { 
			System.out.println("Error");
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