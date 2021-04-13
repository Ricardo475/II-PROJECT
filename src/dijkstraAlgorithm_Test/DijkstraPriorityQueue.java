package dijkstraAlgorithm_Test;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraPriorityQueue

{
	
	private static final int NO_PARENT = -1;
	
    int distances[];
    private Set<Integer> settled;
    //private boolean[] added;
    private int parents[];
    private PriorityQueue<Node> priorityQueue;
    private int number_of_nodes;
    List<List<Node>> adj;

    public  DijkstraPriorityQueue(int number_of_nodes)
    {
        this.number_of_nodes = number_of_nodes;
        distances = new int[number_of_nodes];
        settled = new HashSet<Integer>();
        parents = new int[number_of_nodes];
        //added = new boolean[number_of_nodes];
        priorityQueue = new PriorityQueue<Node>(number_of_nodes,new Node());
    }
    
    public void dijkstra_algorithm(List<List<Node>> adj, int source)
    {
    	
    	
        this.adj = adj;
        
        for (int i = 0; i < number_of_nodes; i++)
        {
            distances[i] = Integer.MAX_VALUE;
        }
        
        
        priorityQueue.add(new Node(source, 0));
        distances[source] = 0;
        parents[source] = NO_PARENT;
        
        
        while (!priorityQueue.isEmpty())
        {
            int evaluationNode = priorityQueue.remove().node;
            //System.out.println(evaluationNode);
            settled.add(evaluationNode);
            evaluateNeighbours(evaluationNode);

        }
        
        //System.out.print("PATH: ");
        for(int i = 0; i < this.number_of_nodes; i++) {
        	
        	if(i!=source) {
        		printPath(i,parents);
        		System.out.println("");
        	}
        }

    } 

    private void printPath(int currentVertex, int[] parents) {
    	
    	if(currentVertex == NO_PARENT)
    		return;
    	
    	printPath(parents[currentVertex],parents);
    	System.out.print(currentVertex + " ");
		
	}

	private void evaluateNeighbours(int evaluationNode)
    {
        int edgeDistance = -1;
        int newDistance = -1;

        for (int i = 0; i < adj.get(evaluationNode).size(); i++)
        {
        	Node v = adj.get(evaluationNode).get(i);
        	
            if (!settled.contains(v.node))
            {
            	edgeDistance = v.cost;
            	newDistance = distances[evaluationNode] + edgeDistance;
            	
            	if(newDistance < distances[v.node]) {
            		parents[v.node] = evaluationNode;
            		distances[v.node] = newDistance;
            	}
            	
            	priorityQueue.add(new Node(v.node,distances[v.node]));
            }
        }
        
    }
}