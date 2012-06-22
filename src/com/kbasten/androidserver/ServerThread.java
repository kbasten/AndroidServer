package com.kbasten.androidserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private CommandParser cp;
	private Log l;
	private PrintWriter out;
	private BufferedReader in;
	private String inputLine;

	private Socket clientSocket;

	public ServerThread(Socket cs, Log l, CommandParser cp) {
		this.l = l;
		this.cp = cp;

		this.clientSocket = cs;

		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			l.msg("Client connected.");
		} catch (IOException e) {
			l.msg("Couldn't get streams for client.");
		}
	}

	@Override
	public void run() {
		try {
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("dc")) { // disconnect request
					l.msg("Received exit packet from client: " + clientSocket.getPort());
					in.close();
					out.close();
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
			try {
				in.close();
				out.close();
			} catch (IOException e1) {
				l.msg("Couldn't close BufferedReader.");
			}
		}
	}
}
