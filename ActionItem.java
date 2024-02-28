package todo;

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
		dateAdded = LocalDateTime.now();
		this.dayAdded = dateAdded.getDayOfMonth();
		this.monthAdded = dateAdded.getMonthValue();
		this.yearAdded = dateAdded.getYear();
		dateComplete = LocalDateTime.of(2000, 1, 1, 11, 59);
		comments = "N/A";
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
		// first constructor 
		ActionItem a = new ActionItem("Clean my room", "Current", 30, 1, 2024, 31, 1, 2024, "Dad will be back home at 7:00 pm");
		ActionItem b = new ActionItem("Clean bathroom", "Urgent", 10, 12, 2024, 3, 01, 2024, "Call shawty at 9:00 PM");
		ActionItem c = new ActionItem("Go gEt foOd", "Eventual", 02, 8, 2024, 21, 3, 2024, "Watch YouTube while eating at 10:00 PM");
		ActionItem d = new ActionItem("Go Home", "Eventual", 3, 11, 2024, 1, 03, 2024, "Mom will wake up at 6:00 AM");
		ActionItem[] list1 = {a, b, c, d};
		
		// second constructor
		ActionItem e = new ActionItem("Wash the dishes", "Eurrent", 18, 4, 2024, "Mom will be back home at 7:00 pm");
		ActionItem f = new ActionItem("Watch girls gone bible", "Eventual", 29, 7, 2024, "You have to watch the pod soon");
		ActionItem g = new ActionItem("Go gym", "Urget", 2, 1, 2024, "Get your grind on");
		ActionItem h = new ActionItem("Pray", "Urgent", 12, 12, 2024, "Give thanks");
		ActionItem[] list2 = {e, f, g, h};
		
		// third constructor
		ActionItem i = new ActionItem("Go get dads bday gift", "Urgent", LocalDateTime.of(2024, 1, 30, 1, 10), LocalDateTime.of(2024, 2, 1, 1, 10), "Dads bday 2/2");
		ActionItem j = new ActionItem("start packing for korea", "Eventual", LocalDateTime.of(2024, 5, 05, 12, 10), LocalDateTime.of(2024, 5, 6, 12, 11), "We leave for korea in a week");
		ActionItem k = new ActionItem("Happy new year", "Current", LocalDateTime.of(2024, 12, 31, 1, 00), LocalDateTime.of(2025, 1, 1, 1, 00), "New year new me");
		ActionItem l = new ActionItem("Start planning for my bday", "Eventual", LocalDateTime.of(2025, 1, 30, 11, 00), LocalDateTime.of(2025, 2, 1, 3, 00), "my birthday is on 2/27");
		ActionItem[] list3 = {i, j, k, l};
		
		// fourth constructor
		ActionItem m = new ActionItem("Do hw");
		ActionItem n = new ActionItem("Go party");
		ActionItem o = new ActionItem("Finish code");
		ActionItem p = new ActionItem("Start trading");
		ActionItem[] list4 = {m, n, o, p};
		
		
		//print for constructor
//		System.out.println(a.toString());
//		System.out.println(b.toString());
//		System.out.println(c.toString());
//		System.out.println(d.toString());
//		System.out.println(e.toString());
//		System.out.println(f.toString());
//		System.out.println(g.toString());
//		System.out.println(h.toString());
//		System.out.println(i.toString());
//		System.out.println(j.toString());
//		System.out.println(k.toString());
//		System.out.println(l.toString());
//		System.out.println(m.toString());
//		System.out.println(n.toString());
//		System.out.println(o.toString());
//		System.out.println(p.toString());
		
		HashMapWriteTest hash = new HashMapWriteTest();
		hash.createVersion(1, list1);
		hash.createVersion(2, list2);
		hash.createVersion(3, list3);
		hash.createVersion(4, list4);
	}
	
}