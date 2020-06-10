import java.io.*;
import java.rmi.*;
import java.util.*;

/**
 * class <em>Client</em> represents a client for the SSD8 Distributed Message
 * Center.
 *
 * @author wben
 * @version 1.0
 *
 */
public class Client {

	/**
	 * main method
	 */
	public static void main(String[] args) {

		int portNumber = 1099;
		String hostname = "localhost";

		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

		/**
		 * Print usage instructions if the incorrect paramteres are supplied.
		 */
		if (args.length != 2) {
			System.out.println("Usage: Client <hostname> <port>");
			System.exit(0);
		} else {
			portNumber = Integer.parseInt(args[1]);
			hostname = args[0];
		}

		int choice = 0;
		String username, password, recipientname, message;

		/**
		 * Create the connection to the message center.
		 */
		MessageInterface mc = null;

		try {
			mc = (MessageInterface) Naming.lookup("rmi://" + hostname + ":" + portNumber + "/Message");
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * Loop forever while displaying the menu and performing operations for
		 * the user.
		 */
		while (true) {

			System.out.println("********** Distributed Message Center ********** ");
			System.out.println("(1) Show registered users ");
			System.out.println("(2) Register a new user");
			System.out.println("(3) Check Messages");
			System.out.println("(4) Leave a message");
			System.out.println("(5) Exit");
			System.out.println("************************************************");
			System.out.println("Enter choice: ");
			try {
				choice = Integer.parseInt(keyboard.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (choice == 1) {
				try {
					System.out.println(mc.showUsers());
//					for (int i = 0; i < users.size(); i++) {
//						System.out.println((String) users.get(i));
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (choice == 2) {
				try {
					System.out.println("Enter username: ");
					username = keyboard.readLine();
					System.out.println("Enter password: ");
					password = keyboard.readLine();
					System.out.println(mc.register(username, password));
//					if (!mc.register(username, password)) {
//						System.out.println("Registration failed, try another username!");
//					} else {
//						System.out.println("Registration succeeded!");
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (choice == 3) {
				try {
					System.out.println("Enter username: ");
					username = keyboard.readLine();
					System.out.println("Enter password: ");
					password = keyboard.readLine();
					System.out.println(mc.checkMessage(username, password));
//					if (messages == null) {
//						System.out.println("Authentication failed or you have no messages!");
//					} else {
//						System.out.println("Your messages: ");
//						for (int i = 0; i < messages.size(); i++) {
//							System.out.println((Message) messages.get(i));
//						}
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (choice == 4) {
				try {
					System.out.println("Enter your username: ");
					username = keyboard.readLine();
					System.out.println("Enter your password: ");
					password = keyboard.readLine();
					System.out.println("Enter the recipient name: ");
					recipientname = keyboard.readLine();
					System.out.println("Enter the message: ");
					message = keyboard.readLine();
					System.out.println(mc.leaveMessage(username, password, recipientname, message));
//					if (!mc.leaveMessage(username, password, recipientname, message)) {
//						System.out.println("Trouble leaving message!");
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (choice == 5) {
				System.exit(0);
			}
		}
	}
}
