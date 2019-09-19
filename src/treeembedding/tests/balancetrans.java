package treeembedding.tests;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
//import java.io.IOException;
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
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;




public class balancetrans {

	public static void main(String[] args) {
		
		Config.overwrite("SKIP_EXISTING_DATA_FOLDER", "false");
		String path = "/Documents/data_test/";
		
		
		// GTNA.example1();
		// GTNA.example2();
		// GTNA.example3();
		// GTNA.example4();
		// GTNA.example5();
		// GTNA.example6();
		Instant start = Instant.now();
		//Setup network, create spanning tree
		Graph graph = balancetrans.example7();
		Random rand = null;
		Treeroute rs;

		Treeembedding embeed = new Treeembedding("T",5);
		Graph g2 = embeed.transform(graph);
		Node[] n = g2.getNodes();
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
		System.out.println("Time Elapsed for setup, spanning tree:"+timeElapsed);
		
		Instant start1 = Instant.now();
		// Broadcast Message
		String text = "Node 23, 100";
		BufferedWriter writer;
		try {
		writer = new BufferedWriter(new FileWriter("./data/Mesage.txt"));
		writer.write(text);
		writer.close();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		Instant finish1 = Instant.now();
		long timeElapsed1 = Duration.between(start1, finish1).toNanos();
		System.out.println("Time Elapsed for Broadcast:"+timeElapsed1);
		
		// FindRoute and Reply
		Instant start2 = Instant.now();
		String text1 = "997, 100";
		BufferedWriter writer1;
		try {
		writer1 = new BufferedWriter(new FileWriter("./data/Mesageto23.txt"));
		writer1.write(text1);
		writer1.close();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		Treeroute d = new TreerouteTDRAP();
		int[] x = d.getRoute(997, 100, 0, g2, n);
		new GtnaGraphWriter().writeWithProperties(g2, "./data/firstExample-graph2.graph");
		//g.getEdges().add(999, 2);// add edge to src
		Edges edges = g2.getEdges();
		edges.add(992, 2);
		edges.fill();
		
		new GtnaGraphWriter().writeWithProperties(g2, "./data/firstExample-graphedge.graph");
		Instant finish2 = Instant.now();
		long timeElapsed2 = Duration.between(start2, finish2).toMillis();
		System.out.println("Time Elapsed for FindRoute and respond:"+timeElapsed2);
		
		
	}
	
	//Creating function to generate a graph 
	private static Graph example7() {
		Metric[] metrics = new Metric[] { new DegreeDistribution() };
			double weights;
			Metric ra;

			Network nw1 = new ErdosRenyi(1000,25 , true, null);
			Graph g = nw1.generate();
			Edge[] e = g.generateEdges();
			//Edge h = new Edge(999, 2);
			 
			
			
	
			
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
	
	
	
	
