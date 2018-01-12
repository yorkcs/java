package networkADT;

import java.util.*;
import java.io.*;

public class Network { 
	String[] cities; // will hold city names
	int[][] network; // will hold edges
	int size; // number of cities
	
	public Network() { // default constructor
		cities = new String[8];
		network = new int[8][8];
		size = 0;
	}
	
	public Network(String filename) throws FileNotFoundException {
		// constructor that loads from file
		load(filename);
	}
	
	public void addCity(Scanner keyboard) { 
		
		// helper method for keyboard input, uses addCity method to add
		System.out.print("Enter new city: ");
		String city = keyboard.next();
		addCity(city);
	}
	
	public void addCity(String city) {
		
		// this method adds a city to the array and matrix
		
		int i = 0;
		
		if(size == cities.length) { // array doubling
			String[] tmp = new String[size * 2];
			for(int j = 0; j < size; j++){
				tmp[j] = cities[j];
			}
			cities = tmp;
		}
		
		if(size == network.length) { // array doubling
			int[][] tmp = new int[size * 2][size * 2];
			for(int j = 0; j < size; j++) {
				for(int k = 0; k < size; k++) {
					tmp[j][k] = network[j][k];
				}
			}
			network = tmp;
		}
		
		while(cities[i] != null && city.compareToIgnoreCase(cities[i]) > 0) {
			
			// moves down the list until proper location is found
			
			i++;
		}
		
		if(cities[i] == null) { // if there are no cities, set inserting city to first
			cities[i] = city;
			size++;
			return;
		}
		
		if(city.compareToIgnoreCase(cities[i]) < 0) { // finds the proper alphabetical place
			size++; // increase size for insert
			for(int j = size; j >= i; j--) { // move existing values down to avoid overwriting
				cities[j+1] = cities[j];
			}
			cities[i] = city; // insert new city
				
			for(int j = size - 1; j > 0; j--) { // move down and over all existing edges
				for(int k = size - 1; k > i - 1; k--) {
				network[k + 1][j] = network[k][j];
				}
			}
			
			for(int j = size - 1; j > i - 1; j--) {// move down and over all existing edges
				for(int k = size - 1; k > 0; k--) {
				network[k][j + 1] = network[k][j];
				}
			}
			
			for(int j = i; i < size; i++) { // set new city's edges to -1
				for(int k = 0; k < size; k++) {
					network[j][k] = -1;
					network[k][j] = -1;
				}
			}
		}
		else if(city.compareTo(cities[i]) == 0) { // if city is already in array
			return;
		}
	}
	
	public void shortestPath(Scanner keyboard) { // helper method for shortest path with keyboard selection
		System.out.println("Starting city: ");
		String city1 = keyboard.next();
		System.out.println("Ending city: ");
		String city2 = keyboard.next();
		shortestPath(city1, city2);
	}
	
	public void shortestPath(String city1, String city2) {
		
		// This method uses Dijkstra's algorithm to traverse each
		// path of the matrix and find the shortest route to the final destination.
		
		System.out.println("Traveling from: " + city1);
		System.out.println("Traveling to: " + city2);
		
		int[] distance = new int[size]; // array for edge distances
		String[] path = new String[size]; // preceding nodes in path
		boolean[] included = new boolean[size]; // tracks which nodes have been visited
		
		for(int i = 0; i < size; i++) { // initialize arrays to default values
			distance[i] = Integer.MAX_VALUE;
			included[i] = false;
		}
		
		int index1 = getIndex(city1); // get indexes for chosen cities
		int index2 = getIndex(city2);
		
		for(int k = 0; k < size; k++) {
			if(network[index1][k] != 0 && network[index1][k] != -1) { // don't copy negatives or zeros
				distance[k] = network[index1][k]; // copy edges into distance array
			}
		}
		
		distance[index1] = 0; // set start city to zero
		included[index1] = true; // set start city to visited
		
		int j = index1; // initialize min value variable
		int[] neighbors = new int[size]; // neighbor array for finding adjacent nodes
		
		for(int k = 0; k < size; k++) {
			if(j!=-1) { // if not null value
				neighbors = getNeighbors(j); // gets next city's values
				included[j] = true; // set min value to visited
				for(int i = 0; i < size; i++) {
					if(!included[i]) { // if not visited already
						if(distance[i] > distance[j] + getWeight(distance, j, i)) {
							distance[i] = distance[j] + getWeight(distance, j, i); // updating edge total in distance array
							path[i] = cities[i]; // add city to path array
						}
					}
				}
				j = minVertex(neighbors, included); // finding next min value from next city
			}
		}
		
	}
	
	public int getWeight(int[] list, int a, int b) {
		// returns combined weight of two edges
		int weight1 = list[a];
		int weight2 = list[b];
		int total = weight1 + weight2;
		return total;
	}
	
	
	public int[] getNeighbors(int row) {
		
		// get next city's values for shortest route method
		
		int[] n = new int[size];
		for(int i = 0; i < size; i++) {
			// sets -1 or 0 edges to max
			n[i] = Integer.MAX_VALUE;
		}
		for(int i = 0; i < size; i++) {
			if(network[row][i] != -1 && network[row][i] != 0) {
				n[i] = network[row][i]; // create array of next city's edges
			}
		}
		return n;
	}
	
	public int minVertex(int[] distance, boolean[] visited) {
		int x = Integer.MAX_VALUE;
		int y = -1;
		for(int i = 0; i < size; i++) {
			if(!visited[i] && distance[i] < x) { // finds the minimum value not yet visited
				y = i;
				x = distance[i];
			}
		}
		return y;
	}
	
	public void addEdge(Scanner keyboard) { 
		
		// helper method for adding an edge
		System.out.print("Enter starting city: ");
		String city1 = keyboard.next();
		System.out.print("Enter ending city: ");
		String city2 = keyboard.next();
		System.out.print("Enter cost: ");
		int cost = keyboard.nextInt();
		addEdge(city1, city2, cost);
	}
	
	public void addEdge(String city1, String city2, int cost) {
		
		// sets new edge in network matrix
		
		int city1Index = getIndex(city1);
		int city2Index = getIndex(city2);
		network[city1Index][city2Index] = cost;
	}
	
	public void deleteEdge(Scanner keyboard) {
		
		// helper method for deleting an edge
		System.out.print("Enter starting city to delete: ");
		String city1 = keyboard.next();
		System.out.print("Enter ending city to delete: ");
		String city2 = keyboard.next();
		deleteEdge(city1, city2);
	}
	
	public void deleteEdge(String city1, String city2) {
		
		// deletes edge from network matrix
		
		int city1Index = getIndex(city1);
		int city2Index = getIndex(city2);
		network[city1Index][city2Index] = -1;
	}
	
	public void print() {
		
		// this method formats the matrix as a table
		// and prints out the values
		System.out.format("%10s", "");
		for(int i = 0; i < size; i++) {
			System.out.format("%10s%10s", "", cities[i]);
		}
		for(int i = 0; i < size; i++) {
			System.out.println("");
			System.out.format("%10s", cities[i]);
			for(int j = 0; j < size; j++) {
				System.out.format("%10s%10d", "", network[i][j]);
			}
		}
	}
	
	public int getIndex(String city) {
		
		// returns the index of a city in the cities array
		
		for(int i = 0; i < cities.length; i++) {
			if(cities[i].equals(city)) {
				return i;
			}
		}
		return -1;
	}
	
	public void generateMST(int graph[][]) {
		// This method uses Prim's Algorithm to create a
		// minimum spanning tree.
		
		int parent[] = new int[size]; // this stores constructed MST
		int key[] = new int[size]; // stores keys for determining minimum edge
		boolean mstSet[] = new boolean[size]; // which vertices are included in MST
		
		for(int i = 0; i < size; i++) { // initializing arrays to default
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}
		
		key[0] = 0; // set MST to start at beginning
		parent[0] = -1; // root of MST
		int weight = 0;
		
		for(int count = 0; count < size -1; count++) {
			int u = minVertex(key, mstSet); // gets minimum key
			 
			mstSet[u] = true; // adds that key to the MST set
			
			for(int v = 0; v < size; v++) {
				// Updates the key and parent of adjacent vertices
				// Only checks those that aren't already included
				// and those that don't have empty values
				if(graph[u][v] != 0 && graph[u][v] != -1 && !mstSet[v] && graph[u][v] < key[v]) {
					parent[v] = u;
					key[v] = graph[u][v];
				}
			}
		}
		
		printMST(parent, size, graph);
	}
	
	public void printMST(int parent[], int n, int graph[][]) {
		// prints out MST
		System.out.println("\nEdge                   Weight");
		for(int i = 1; i < size; i++) {
			
			System.out.println(cities[parent[i]] + " - " + cities[i] + "        " + graph[i][parent[i]]);
		}
	}
	
	private void DFSUtil(String city, boolean visited[]) {
		
		// mark current node as visited and print
		int index = getIndex(city);
		visited[index] = true;
		System.out.println(cities[index] + " ");
		
		// perform recursively for all adjacent nodes
		for(int i = 0; i < size; i++) {
			String n = cities[i];
			if(!visited[i]) {
				DFSUtil(n, visited);
			}
			
		}
		
	}
	
	public void dfs(Scanner keyboard) {
		// Depth-first search uses recursive method above
		
		System.out.println("Which city to start with?");
		String city = keyboard.next();
		
		System.out.println("\nDFS starting at " + city);
		
		boolean visited[] = new boolean[size];
		
		// call recursive helper to print DFS traversal
		DFSUtil(city, visited);
		System.out.println("");
	}
	
	public void bfs(Scanner keyboard) {
		
		System.out.println("Which city to start with?");
		String city = keyboard.next();
		
		// all vertices marked as not visited
		boolean visited[] = new boolean[size];
		
		// create a queue for BFS
		LinkedList<String> queue = new LinkedList<String>();
		
		// current node is visited and enqueued
		int index = getIndex(city);
		visited[index] = true;
		queue.add(city);
		
		while(queue.size() != 0) {
			
			// dequeue a vertex and print it
			city = queue.poll();
			System.out.print(city + " ");
			
			// get adjacent vertices; if not visited,
			// mark as visited and enqueue
			for(int i = 0; i < size; i++) {
				String n = cities[i];
				if(!visited[i]) {
					visited[i] = true;
					queue.add(n);
				}
			}
		}
	}
	
	public void load(Scanner keyboard) throws FileNotFoundException {
		
		//helper method for loading from keyboard
		System.out.println("Enter file name: ");
		String filename = keyboard.next();
		load(filename);
	}
	
	public void load(String filename) throws FileNotFoundException {
		
		// this method loads a network from a text file
		size = 0;
		
		Scanner inputFile = new Scanner(new File(filename)); // create file
		int counter = inputFile.nextInt(); // get number of cities in file
		cities = new String[counter]; // create cities array
		network = new int[counter][counter]; // create edge matrix
		inputFile.nextLine(); // skips int line
		while(inputFile.hasNextLine()) { 
			for(int i = 0; i < counter; i++) {
				String nextCity = inputFile.nextLine(); // add cities to city array
				addCity(nextCity);
			}
			for(int j = 0; j < counter; j++) {
				for(int k = 0; k < counter; k++) {
					int nextEdge = inputFile.nextInt(); // adds edge to matrix for each city
					addEdge(cities[j], cities[k], nextEdge);
				}
			}
		}
	}
	
	public void save(String filename) throws FileNotFoundException {
										
		PrintWriter out=new PrintWriter(new File(filename)); // opens output file
		out.println("Size: " + size); // prints size
		for(int i = 0; i < size; i++ ) {
			out.println("\n" + cities[i]);// prints matrix and city array (as part of matrix)
			for(int j = 0; j < size; j++) {
				out.println("\n" + network[i][j]);
			}
		}
		out.close(); // closes file 			

	}
}
