package gui;


import java.util.Scanner;

import Engine.TwitterEngine;

/**
 * TwitterCLI.class
 * 
 * A separate GUI interface that works through the console
 * by using a command line interface.
 * 
 * @author Ben
 */
public class TwitterCLI {
	private TwitterEngine engine;

	/**
	 * Contructs the TwitterCLI
	 */
	public TwitterCLI(){
		engine = new TwitterEngine();
		Console();
	}
	/**
	 * Main method to run the TwitterCLI
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new TwitterCLI();
			}
		});  
	}
	/**
	 * 
	 */
	public void Console() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter a command:");
		String com = sc.next();
		if(com.equalsIgnoreCase("help")){
			System.out.println(
					"getStatus - gets a users Status \n" +
					"getTimeLine - get a users timeline \n"+
					"about - who wrote the program \n"+
					"quit - exits the program");

			Console();
		}
		else if(com.equalsIgnoreCase("getStatus")){
			System.out.println("Enter userName:");
			String userName = sc.next();
			engine.getStatus(userName);

			System.out.println("the " +
					"most recent status for "+userName+
					" is "+"\""
					+engine.getDisplayStatis(0)+"\"");
			Console();
		}

		else if(com.equalsIgnoreCase("getTimeLine")){
			System.out.println("Enter userName:");
			String userName = sc.next();
			engine.getStatusList(userName);

			for(int i = 0; i < engine.getArrayListSize(); i++){
				System.out.println(engine.getDisplayStatis(i));
			}
			Console();
		}

		else if(com.equalsIgnoreCase("about")){
			System.out.println("TwitterLite " +
					"was created by Benjamin Summers \n " +
			"for CIS 163 \n 10/28/11");
			Console();
		}
		else if(com.equalsIgnoreCase("quit")){
			System.out.println("good-bye");
			System.exit(0);
		}
		else {
			System.out.println("You did not input a" +
			" valid command");
			Console();
		}

	}

}

