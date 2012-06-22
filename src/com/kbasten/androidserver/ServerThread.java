package com.kbasten.androidserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {
	private CommandParser cp;
	private Log l;
	private PrintWriter out;
	private BufferedReader in;
	private String inputLine;

	public ServerThread(Socket clientSocket, Log l, CommandParser cp) {
		this.l = l;
		this.cp = cp;

		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			l.msg("Client connected.");

			run();

			out.close();
			in.close();

			l.msg("Client thread terminated.");
		} catch (IOException e) {
			l.msg("Couldn't get streams for client.");
		}
	}

	@Override
	public void run() {
		try {
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("dc")) { // disconnect request
					l.msg("Received exit packet");
					return;
				} else {
					try {
						l.msg(cp.parse(inputLine));
					} catch (CommandLineException e) {
						l.msg(e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			l.msg("Client disconnected: " + e.getMessage());
		}
	}
}
