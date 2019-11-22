import java.util.*;
// MAME : JOENG, SEUNG WON
// STUDENT NUMBER :

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
        WGraph result = new WGraph(); // This is MST
        DisjointSets set = new DisjointSets(g.getNbNodes());
        boolean isSafe = false;

        // Need to compare all edges _ Method : WGraph.listOfEdgesSorted() is already sorted the weight of edges.
        for(Edge e : g.listOfEdgesSorted())
        {
        	isSafe = IsSafe(set, e);
        	if(isSafe)
        	{
        		result.addEdge(e);
        		set.union(e.nodes[0], e.nodes[1]);
        	}
        }

        return result;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */

    	// Compare two roots
    	// If both are same, then they are not safe.
    	if(p.find(e.nodes[0]) == p.find(e.nodes[1])) return false;

    	// Otherwise, they are safe.
        return true;

    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   }
}
