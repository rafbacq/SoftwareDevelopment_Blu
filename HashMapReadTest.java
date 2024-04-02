
package org.example;

//Java program to reading 
//text file to HashMap 

import java.io.*; 
import java.util.*;
import java.util.Map.Entry;
import java.time.*;

public class HashMapReadTest { 
	static String outputFilePath = "version.txt";
	File file = new File(outputFilePath);
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
	public static Map<Integer, ArrayList<ActionItem>> HashMapFromTextFile() {
		Map<Integer, ArrayList<ActionItem>> map = new HashMap<>();
		BufferedReader br = null;

		try {
			File file = new File(outputFilePath); // Use the correct file path

			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.trim().split(":"); // Split by semicolon to separate version number and action items
				String verStr = parts[1].substring(3, 4);
				int versionNumber = Integer.parseInt(verStr); // Extract version number

				String[] actionItemStrings = parts[1].split(","); // Split action items by comma

				ArrayList<ActionItem> actionItems = new ArrayList<>();
				for (int i = 0; i < actionItemStrings.length; i += 5) {
					String name = actionItemStrings[i].trim();
					String status = actionItemStrings[i + 1].trim();
					LocalDateTime dateAdded = LocalDateTime.parse(actionItemStrings[i + 2].trim());
					LocalDateTime dateComplete = LocalDateTime.parse(actionItemStrings[i + 3].trim());
					String comments = actionItemStrings[i + 4].trim();

					ActionItem ai = new ActionItem(name, status, dateAdded, dateComplete, comments);
					actionItems.add(ai);
				}

				map.put(versionNumber, actionItems);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
}
