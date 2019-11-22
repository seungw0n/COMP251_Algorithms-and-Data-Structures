import java.io.*;
import java.util.*;

/*
 * No collaborators
 * NAME : JOENG, SEUNG WON
 * STUDENT ID :
 */

public class FordFulkerson {


	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE
		//
		//
		//
		//
		//
		//
		//
		*/
		ArrayList<Integer> compareStack = new ArrayList<Integer>();
		Stack.add(source);
		compareStack.add(source);

		while(!compareStack.isEmpty())
		{
			int input = compareStack.get(0);
			compareStack.remove(0);

			if(input == destination) break;

			for(Edge edge: graph.listOfEdgesSorted())
			{
				if((edge.nodes[0] == input) && (edge.weight > 0))
				{
					if(!Stack.contains(edge.nodes[1]))
					{
						Stack.add(edge.nodes[1]);
						compareStack.add(edge.nodes[1]);
						break;
					}
				}
			}
		}
		return Stack;
	}



	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = ""; //Please initialize this variable with your McGill ID
		int maxFlow = 0;

		/* YOUR CODE GOES HERE
		//
		//
		//
		//
		//
		//
		//
		*/

		// First create a residual graph
		WGraph rGraph = new WGraph(graph);
		ArrayList<Integer> path = pathDFS(source,destination, rGraph);

		// Initialize all edges of original graph to 0
		for(Edge edge : graph.getEdges()) edge.weight = 0;

		while(checkValidation(path, source, rGraph))
		{
			if(path.contains(destination))
			{
				// Set the bottleneck, which is the max flow it can pass.
//				Integer bottleneck = Integer.MAX_VALUE;
				int bottleneck = rGraph.getEdge(path.get(0), path.get(1)).weight;

				// Comparing all weights.
				for(int i = 0; i < path.size() - 1; i++)
				{
					if(rGraph.getEdge(path.get(i), path.get(i+1)).weight < bottleneck)
						bottleneck = rGraph.getEdge(path.get(i), path.get(i+1)).weight;
				}

				// Update maxFlow
				maxFlow = maxFlow + bottleneck;

				// Update residual graph && originial graph
				for(int i =0; i < path.size() - 1; i++)
				{
					rGraph.getEdge(path.get(i), path.get(i+1)).weight -= bottleneck;
					graph.getEdge(path.get(i), path.get(i+1)).weight += bottleneck;
				}
			}
			else
			{
				// re - initialized residual graph to 0 if path is incomplete.
				for(int i = 0; i < path.size() - 1; i++)
				{
					rGraph.getEdge(path.get(i), path.get(i+1)).weight = 0;
				}
			}

			// Update new path
			path = pathDFS(source, destination, rGraph);
			if(path.size() == 1 && !path.contains(destination)) break;
		}

		answer += maxFlow + "\n" + graph.toString();
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}

	// Helper method for checking validation of pathDFS
	private static boolean checkValidation(ArrayList<Integer> pathDFS, int source, WGraph graph)
	{
		if(pathDFS.size() == 1 && pathDFS.contains(graph.getSource())) return false;
		else return true;
	}

	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it

		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
