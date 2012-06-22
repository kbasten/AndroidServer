package com.kbasten.androidserver;

public class Command {
	private String location;
	private int minArguments;
	private String msg;
	
	public Command(String location, int minArguments){
		this(location, minArguments, "");
	}
	
	public Command(String location, int minArguments, String msg){
		// optional msg argument
		this.location = location;
		this.minArguments = minArguments;
		this.msg = msg;
	}
	
	public int getMinArguments(){
		return this.minArguments;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public String getMsg(){
		return this.msg;
	}
}
