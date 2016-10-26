/**
CSS Graduate Certificate in Software Design and Development (GCSDD)
Student Self-Assessment Toolkit
Graduate Certificate Prerequisite Self-Assessment

CSS161-SelfAssessment.pdf
*/

package css_161;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SoftDrinkInventory {
	
	private String[] name;
	private String[] id;
	private int[] starting_inv;
	private int[] final_inv;
	private int[] trans_count;
	private int total_drinks;
	
	private String[] trans_id;
	private int[] trans_qty;
	private int total_trans;
	
	private static final int SIZE = 100; // 100 different kinds of soft drinks.
	
	// Constructor
	// public constructor: Initializes arrays holding soft drink name and ID to hold all empty strings (calls
	// intitializeString twice to perform the tasks). Initializes arrays holding starting inventory, final inventory, and the
	// counts of the number of transaction to zero (calls initializeInt three times to perform the tasks).
	public SoftDrinkInventory() {
		name = new String[SIZE];  // 100 different kinds of soft drinks.
		initializeString(name);
		id = new String[SIZE];
		initializeString(id);
		starting_inv = new int[SIZE];
		initializeInt(starting_inv);
		final_inv = new int[SIZE];
		initializeInt(final_inv);
		trans_count = new int[SIZE];
		initializeInt(trans_count);
		
		trans_id = new String[SIZE];
		initializeString(trans_id);
		trans_qty = new int[SIZE];
		initializeInt(trans_qty);
	}
	
	// Sets the arrays for soft drink name, ID, and starting inventory from information in the
	// data file. The array holding final inventory is set to the same values as the starting inventory.
	public void buildInventory()  {
			Scanner inventoryFile = null; // inventory data file
			String[] wordArray;
			
			total_drinks = 0;

			// open the inventory initialization file
			try {
				inventoryFile = new Scanner(new FileInputStream("C:/Users/Jimmy Chong/workspace/Uw_self_assessment/src/css_161/data6.txt"));
				
				int i = 0;				
				while(inventoryFile.hasNext()){
					wordArray = inventoryFile.nextLine().split(" ");
					name[i] = wordArray[0];
					id[i] = wordArray[1];
					starting_inv[i] = Integer.parseInt(wordArray[2]);
					final_inv[i] = starting_inv[i]; 
					trans_count[i] = 0;
					i++;
				}
				total_drinks = i;
				inventoryFile.close();
			}
			
			catch (FileNotFoundException e) {
				System.out.println("File not found or not opened.");
				System.exit(0);
			}						
	}
	
	// Processes the transactions by correctly adjusting the final inventory and
	// transaction counts arrays. Data for IDs which don't exist are not processed.
	public void processTransactions() {
		Scanner transFile = null; // transaction data file
		String[] wordArray;
		
		// open the file containing the buy/sell transactions
		try {
			transFile = new Scanner(new FileInputStream("C:/Users/Jimmy Chong/workspace/Uw_self_assessment/src/css_161/data6trans.txt"));
			// transFile = new Scanner(new FileInputStream("https://github.com/jimmy2046/css161/blob/master/data6trans.txt"));
			
			int i = 0;
			total_trans = 0;
			
			while(transFile.hasNext()){
				wordArray = transFile.nextLine().split(" ");
				trans_id[i] = wordArray[0];
				trans_qty[i] = Integer.parseInt(wordArray[1]);
				i++;							
			}
			total_trans = i;
			transFile.close();
			
						
			i = 0;			
			int j = 0;
			
			// System.out.println("j, id, name, starting_inv, final_inv, trans_count");
			
			while (i < total_trans) {
				j = findID(id, trans_id[i]);
				
				// In the case of an invalid ID, do not process the data (ignore it, no error
				// message).
				if (j >= 0) {										
					final_inv[j] += trans_qty[i];
					trans_count[j]++;																		
				}
				i++;				
			}
			
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found or not opened.");
			System.exit(0);
		}		
		
	}
	
	// Displays a report including soft drink name, ID, starting inventory, final inventory, and
	// number of transactions processed.
	public void displayReport() {		
		System.out.println("Soft drink     ID     Starting Inventory    Final Inventory   # transactions");
		
		for (int i = 0; i < total_drinks; i++) {			
			System.out.println(String.format("%-14s", name[i]) + " " + id[i] + " " + String.format("%12s", starting_inv[i])  + " " +
								String.format("%20s", final_inv[i])  + " " + String.format("%18s", trans_count[i]));
		}
		
	}
	
	// Takes an ID parameter and returns the position in the array (the subscript) where the soft
	// drink with that ID is found. Return -1 if the ID is not found.
	private int findID(String[] searchString, String transId) {
		
		int position = Arrays.asList(searchString).indexOf(transId);
		
		return position;
	}
	
	// Takes an int array parameter and initializes all array values to zero.
	private void initializeInt(int[] intArray) {
		for (int i = 0; i < SIZE; i++) {
			intArray[i] = 0;
		}
	}
	
	// Takes a String array parameter and initializes all values to the empty String (“”).
	private void initializeString(String[] stringArray) {
		for (int i = 0; i < SIZE; i++) {
			stringArray[i] = "";			
		}		
	}

	public static void main(String[] args) {

		SoftDrinkInventory s = new SoftDrinkInventory();
		s.buildInventory();				
		s.processTransactions();
		s.displayReport();
		
	}	

}
