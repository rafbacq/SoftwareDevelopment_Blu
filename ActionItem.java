package smth;

import java.util.Date;

import javax.swing.JTextField;
import javax.swing.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class ActionItem {
	private String status;
	private int dayAdded;
	private int monthAdded;
	private int yearAdded;
	private int dayComplete;
	private int monthComplete;
	private int yearComplete;
	private String name;
	private String comments;
	private String theme;
	private LocalDateTime dateAdded;
	private LocalDateTime dateComplete;
	
	
	public ActionItem(String name, String status, int dayAdded, int monthAdded, int yearAdded, int dayComplete, int monthComplete, int yearComplete, String comments) 
	{
		this.name = name;
		this.status = status;
		this.dayAdded = dayAdded;
		this.monthAdded = monthAdded;
		this.yearAdded = yearAdded;
		this.dayComplete = dayComplete;
		this.monthComplete = monthComplete;
		this.yearComplete = yearComplete;
		this.comments = comments;
		this.theme = "default";
		dateAdded = LocalDateTime.of(yearAdded, monthAdded, dayAdded, 11, 59);
		dateComplete =  LocalDateTime.of(yearComplete, monthComplete, dayComplete, 11, 59);
	}
	
	public ActionItem(String name, String status, int dayComplete, int monthComplete, int yearComplete, String comments) 
	{
		this.name = name;
		this.status = status;
		this.dayComplete = dayComplete;
		this.monthComplete = monthComplete;
		this.yearComplete = yearComplete;
		this.comments = comments;
		dateAdded = LocalDateTime.now();
		this.dayAdded = dateAdded.getDayOfMonth();
		this.monthAdded = dateAdded.getMonthValue();
		this.yearAdded = dateAdded.getYear();
		this.theme = "default";
		dateComplete =  LocalDateTime.of(yearComplete, monthComplete, dayComplete, 11, 59);
	}
	
	public ActionItem(String name, String status, LocalDateTime dateAdded,  LocalDateTime dateComplete, String comments) 
	{
		this.name = name;
		this.status = status;
		this.comments = comments;
		this.dateAdded = dateAdded;
		this.dateComplete = dateComplete;
		dayAdded = dateAdded.getDayOfMonth();
		monthAdded = dateAdded.getMonthValue();
		yearAdded = dateAdded.getYear();
		dayComplete = dateComplete.getDayOfMonth();
		monthComplete = dateComplete.getMonthValue();
		yearComplete = dateComplete.getYear();
	}
	
	public ActionItem(String name) 
	{
		this.name = name;
		this.status = "No Status";
	}
	
	public ActionItem() 
	{
		this.name = "Name";
		this.status = "No Status";
		dateAdded = LocalDateTime.of(yearAdded, monthAdded, dayAdded, 11, 59);
		this.dayAdded = dateAdded.getDayOfMonth();
		this.monthAdded = dateAdded.getMonthValue();
		this.yearAdded = dateAdded.getYear();
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public void setStatus(String status) 
	{
		this.status = status;
	}
	
	public void setDate(LocalDateTime date) 
	{
		dateComplete = date;
	}
	
	public void setComments(String comments) 
	{
		this.comments = comments;
	}
	
	public void setTheme(String theme) 
	{
		this.theme = theme;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public String getStatus() 
	{
		return status;
	}
	
	public LocalDateTime getDate() 
	{
		return dateComplete;
	}
	
	public String getComments()
	{
		return comments;
	}	
	
	public String getTheme() 
	{
		return theme;
	}
	
	public String toString() 
	{
		String output = "";
		
		output += "Task Name: " + name + "\n";
		output += "Status: " + status + "\n";
		output += "Date Added: " + dateAdded.toString() + "\n";
		output += "Date to be Completed: " + dateComplete.toString() + "\n";
		output += "Additional Comments: " + comments + "\n";
		
		return output;
		
	}
	
	public String toFile() 
	{
		String output = "";
		
		output += (name + "," + status + "," + dateAdded + "," + dateComplete + "," + comments + ",");
	
		return output;
	}
	
	public static void main(String[] args) 
	{
		ActionItem a = new ActionItem("Clean my room", "Urgent", 30, 1, 2024, 31, 1, 2024, "Mom will be back home at 7:00 pm");
		System.out.println(a.toString());
		System.out.println(a.toFile());
		String[] b = a.toFile().split(",");
		for(String c : b) 
		{
			System.out.println(c.trim());
		}
			
	}
	
}
