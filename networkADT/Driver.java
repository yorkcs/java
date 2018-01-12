package networkADT;

import java.util.*;

import java.io.FileNotFoundException;

public class Driver {
	
	public static void main(String args[]) throws FileNotFoundException {
		
		Network newNet = new Network();
		newNet.load("network.txt");
		int choice;
		Scanner keyboard = new Scanner(System.in);
		choice = getUserChoice(keyboard);
		while(choice != 11) {
			switch(choice) {
				case 1: newNet.load(keyboard); break;
				case 2: newNet.addCity(keyboard);break;
				case 3: newNet.addEdge(keyboard);break;
				case 4: newNet.deleteEdge(keyboard);break;
				case 5: newNet.dfs(keyboard);break;
				case 6: newNet.bfs(keyboard);break;
				case 7: newNet.print();break;
				case 8: newNet.shortestPath(keyboard);break;
				case 9: newNet.generateMST(newNet.network);break;
				case 10: newNet.save("network2.txt");break;
			}
			choice=getUserChoice(keyboard);
		}
		
	}
	
	public static int getUserChoice(Scanner in) {
		int tmp;
		System.out.println("\nUser choices\n1. Load network from disk\n2. Add a city\n3. Add an edge\n4. Delete an edge\n5. Perform DFS\n6. Perform BFS\n7. Print\n8. Find shortest path\n9. Generate MST\n10. Save to disk\n11. Quit");
		do {
			System.out.print("Enter your choice: ");
			tmp = in.nextInt();
		} while(tmp < 1 || tmp > 11);
		return tmp;
	}
}
