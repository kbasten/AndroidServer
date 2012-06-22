package com.kbasten.androidserver;

public class Client {
	private String name;
	
	public Client(String name){
		this.name = name;
	}
	
	@Override 
	public String toString(){
		return this.name;
	}
}
