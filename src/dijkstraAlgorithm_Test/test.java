package dijkstraAlgorithm_Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class test {
	
	public test() {
		
	}
	
	public void doTest() {
		
		int V = 16;
		int source = 0;
		
		List<List<Node>> adj = new ArrayList<List<Node>>();
		
		for(int i = 0; i<V;i++) {
			
			List<Node> item = new ArrayList<Node>();
			adj.add(item);
		}
		
		adj.get(0).add(new Node(1,1));
		
		adj.get(1).add(new Node(2,1));
		adj.get(1).add(new Node(3,1));
		
		adj.get(3).add(new Node(4,1));
		adj.get(3).add(new Node(5,1));
		
		adj.get(5).add(new Node(6,1));
		adj.get(5).add(new Node(7,1));
		
		adj.get(7).add(new Node(8,1));
		adj.get(7).add(new Node(9,1));
		
		adj.get(9).add(new Node(10,1));
		adj.get(9).add(new Node(11,1));
		
		adj.get(11).add(new Node(12,1));
		adj.get(11).add(new Node(13,1));
		
		adj.get(13).add(new Node(14,1));
		
		adj.get(14).add(new Node(15,1));
		
		DijkstraPriorityQueue dpq = new DijkstraPriorityQueue(V);
		
		dpq.dijkstra_algorithm(adj, source);
	
		
		System.out.println("The shorted path from node: ");
		for(int i = 0; i<dpq.distances.length;i++)
			//if(i==dpq.distances.length-1)
				System.out.println(source + " to " + i + " is " + dpq.distances[i]);
	}


}

