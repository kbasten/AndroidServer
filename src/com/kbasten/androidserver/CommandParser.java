package com.kbasten.androidserver;

import java.io.IOException;
import java.util.HashMap;

public class CommandParser {
	private Runtime rt;
	
	private HashMap<String, Command> cList;
	
	private static String VLC_LOCATION = "D:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
	private static String CHROME_LOCATION = "C:\\Users\\Gamepc\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe";
	
	public CommandParser(){
		rt = Runtime.getRuntime();
		
		cList = new HashMap<String, Command>();
		
		cList.put("vlc", new Command(VLC_LOCATION, 1));
		cList.put("chrome", new Command(CHROME_LOCATION, 0));
	}
	
	public String parse(String c) throws IOException, CommandLineException {
		String[] commands = c.split(" ");
		
		String command = commands[0].toLowerCase();
		
		if (!cList.containsKey(command)){
			throw new CommandLineException("Command '" + command + "' not found.");
		}

		Command action = cList.get(command);
		
		if ((commands.length - 1) < action.getMinArguments()){
			throw new CommandLineException("Missing arguments for '" + command + "'.");
		}
		
		// create list of arguments
		StringBuilder arg = new StringBuilder();
		for (int i = 1; i < commands.length; i++){ // start from 1
			arg.append(" " + commands[i]); // watch first space!
		}
		
		String arguments = arg.toString(); // empty if no arguments
		
		System.out.println("Arguments:" + arguments);
		
		// execute command
		rt.exec(action.getLocation() + arguments);
		return command + " " + action.getMsg();
	}
}
