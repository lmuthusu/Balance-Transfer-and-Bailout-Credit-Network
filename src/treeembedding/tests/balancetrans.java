package treeembedding.tests;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;

import gtna.data.Series;
import gtna.graph.Graph;
import gtna.graph.weights.EdgeWeights;
import gtna.io.graphWriter.GtnaGraphWriter;
import gtna.metrics.Metric;
import gtna.metrics.basic.DegreeDistribution;
import gtna.metrics.basic.ShortestPaths;
import gtna.networks.Network;
import gtna.networks.model.ErdosRenyi;
import gtna.plot.Plotting;
import gtna.transformation.Transformation;
import gtna.transformation.edges.Bidirectional;
import gtna.transformation.id.RandomRingIDSpace;
import gtna.transformation.spanningtree.BFSRand;
import gtna.transformation.spanningtree.MultipleSpanningTree;
import gtna.util.Config;
import gtna.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import gtna.graph.Edges;
import gtna.graph.Edge;
import gtna.graph.weights.EdgeWeights;
import gtna.graph.GraphProperty;
import treeembedding.treerouting.*;
import treeembedding.vouteoverlay.Treeembedding;
import java.util.Random;
import gtna.transformation.spanningtree.BFS;
import gtna.graph.Node;
import treeembedding.treerouting.Treeroute;



public class balancetrans {

	public static void main(String[] args) {
		
		Config.overwrite("SKIP_EXISTING_DATA_FOLDER", "false");
		String path = "/Users/lalitha/Documents/data_test/";
		
		
		// GTNA.example1();
		// GTNA.example2();
		// GTNA.example3();
		// GTNA.example4();
		// GTNA.example5();
		// GTNA.example6();
		Graph graph = balancetrans.example7();
		Random rand = null;
		Treeroute rs;

	   // HashMap<Edge, Double> weights;
	    //double weight;
		//double defaultWeight;
		//Edge[] edges = graph.generateEdges();
		//EdgeWeights EdgeWeights(edges, weight, defaultWeight);
		
		//String graph = "./data/firstExample-graph1.txt";
		//String resAdd =  "./data/firstExample-graph2.txt";
		//String resGraph = "./data/hashmap";
		//String add = "./data/ripple_links_history.txt";
		//String name = "./data/RIPPLEJan29";
		//toAddList(graph, resAdd+".txt", map);
		//HashMap<String, Integer> map = turnGraphs(resAdd, resGraph+".graph", name);
		//boolean roots = false;
		//Transformation TBFS =  new BFS();
	 	//Graph g3 = TBFS.transform(graph);
		Treeembedding embeed = new Treeembedding("T",5);
		Graph g2 = embeed.transform(graph);
		Node[] n = g2.getNodes();
		Treeroute d = new TreerouteTDRAP();
		int[] x = d.getRoute(2, 996, 0, g2, n);
		new GtnaGraphWriter().writeWithProperties(g2, "./data/firstExample-graph2.graph");
		//new GtnaGraphWriter().writeWithProperties(g3, "./data/firstExample-graph3.graph");
		
		
	}
	
	//Creating function to generate a graph 
	private static Graph example7() {
		Metric[] metrics = new Metric[] { new DegreeDistribution() };
			double weights;
			Metric ra;

			Network nw1 = new ErdosRenyi(1000,25 , true, null);
			Graph g = nw1.generate();
			Edge[] e = g.generateEdges();
			
	
			
			EdgeWeights nw2 = new EdgeWeights(e,4, 0);
			g.addProperty(g.getNextKey("ID_SPACE"), nw2);
			//Treeroute nw = computeData(g,nw1,HashMap<String, metrics> m );
			
			//int[] arr = g.getRoute(3,99,0,g,g.getNodes());
			
			

			new GtnaGraphWriter().writeWithProperties(g, "./data/firstExample-graph.graph");
			new GtnaGraphWriter().writeWithProperties(g, "./data/firstExample-graph.txt");
			
			
			//new GraphProperty().write("./data/firstExample-graphprop.txt", nw2)
			//new GtnaGraphWriter().write(nw2, "./data/firstExample-graphedge.txt");
					
		//	Map<Integer, List> graph = new HashMap<>();
			
			return g;
			
		}
			public static double getRandomNumber(){

					double x = Math.random();

					return x;
			}

	}
	
	
	
	

