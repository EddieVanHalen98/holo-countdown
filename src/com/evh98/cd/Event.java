package com.evh98.cd;

import java.io.Serializable;

public class Event implements Serializable{

	private static final long serialVersionUID = -1355575545054869864L;
	private String name;
	private String time;
	private String date;
	private int icon;
	private String theme;
	
	public Event(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}