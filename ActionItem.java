package dev;

import java.util.Date;
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
		dateAdded = LocalDateTime.of(yearAdded, monthAdded, dayAdded, 11, 59);
		dateComplete =  LocalDateTime.of(yearComplete, monthComplete, dayComplete, 11, 59);
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
	
	public static void main(String[] args) 
	{
		System.out.println("Hello");
		ActionItem a = new ActionItem("a", "b", 30, 1, 2024, 31, 1, 2024, "extra");
	}
	
}

