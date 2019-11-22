public class BellmanFord{
/*
 * No collaborators
 * NAME : JOENG, SEUNG WON
 * STUDENT ID :
 */

	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}

	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify a negative cycle has been found
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}

	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();}
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *
         *  When throwing an exception, choose an appropriate one from the ones given above
         */

        /* YOUR CODE GOES HERE */
    	int numNodes = g.getNbNodes();
    	int numEdges = g.getEdges().size();
    	this.distances = new int[numNodes];
    	this.predecessors = new int[numNodes];
    	this.source = source;

    	int[] allNodes = new int[numNodes]; // Initialize all slots as MIN_VALUE for preventing confusing 0s.
    	for(int i = 0; i < numNodes; i++) allNodes[i] = Integer.MIN_VALUE;
    	int index = 0;

    	Edge[] allEdges = new Edge[numEdges];
    	for(Edge e: g.listOfEdgesSorted()) {
    		allEdges[index] = e;
    		index++;
    	}

    	index  = 0;

    	for(int i = 0; i < numEdges; i++)
    	{
    		Edge e = allEdges[i];
    		if(index >= numNodes) break;
    		// Using helper method for preventing put duplicate vertices.
    		if(!isContain(e.nodes[0], allNodes)) {
    			allNodes[index] = e.nodes[0];
    			index++;
    		}
    		if(!isContain(e.nodes[1], allNodes)) {
    			allNodes[index] = e.nodes[1];
    			index++;
    		}
//    		allNodes[index] = e.nodes[0];
//    		allNodes[nextIndex] = e.nodes[1];
    	}

    	// Initializing distances and predecessors at starting time
    	for(int i = 0; i < numNodes; i++)
    	{
    		if(i == source)
    			distances[i] = 0;
    		else
    			distances[i] = 10000000;

    		predecessors[i] = -1;
    	}

    	// Relaxation all nodes with updating distances and predecessors
    	for(int i = 0; i < numNodes; i++)
    	{
    		for(int k = 0; k < numEdges; k++)
    		{
    			Edge e = allEdges[k];
    			if(distances[e.nodes[1]] > distances[e.nodes[0]] + e.weight)
    			{
    				distances[e.nodes[1]] = distances[e.nodes[0]] + e.weight;
    				predecessors[e.nodes[1]] = e.nodes[0];
    			}
    		}
    	}

    	// Check the negative cycle - distances[v] > distances[u] + e.weight where u-v.
    	for(Edge e : g.listOfEdgesSorted())
    	{
    		int u = e.nodes[0];
    		int v = e.nodes[1];
    		if(distances[v] > distances[u] + e.weight)
    			throw new NegativeWeightException();
    	}
    }
    // Helper method: returns true if an array contains x.
    private boolean isContain(int x, int[] arr)
    {
    	for(int i : arr)
    	{
    		if(i == x) return true;
    	}
    	return false;
    }


    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */


    	int curNode = destination;
    	int size = 1;
    	while(curNode != this.source)
    	{
    		// if curNode is not source but its predecessor is -1 then throw exception
    		if(this.predecessors[curNode] == -1) throw new PathDoesNotExistException();
    		size++;
    		curNode = this.predecessors[curNode];
    	}

    	int index = size - 1;
    	int[] result = new int[size];
    	curNode = destination;

    	while(curNode != this.source && index != 0)
    	{
    		result[index] = curNode;
    		curNode = this.predecessors[curNode];
    		index--;
    	}

    	result[0] = curNode;

        return result;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   }
}
