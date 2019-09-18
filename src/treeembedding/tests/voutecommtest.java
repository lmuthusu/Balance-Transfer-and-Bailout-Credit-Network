package treeembedding.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import gtna.data.Series;
import gtna.graph.Graph;
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
import gtna.util.Config;
import gtna.util.Util;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.itextpdf.text.List;


public class voutecommtest {
	public static void main(String[] args) {
		Config.overwrite("SKIP_EXISTING_DATA_FOLDER", "false");
		String path = "/Users/lalitha/Documents/data_test/";
		
		
		// GTNA.example1();
		// GTNA.example2();
		// GTNA.example3();
		// GTNA.example4();
		// GTNA.example5();
		// GTNA.example6();
		voutecommtest.example7();
		String graph = "graph.txt";
		String resGraph = "jan2013";
		String add = "ripple_links_history.txt";
		String name = "RIPPLEJan29";
		HashMap<String, Integer> map = turnGraphs(graph, resGraph+".graph", name, add);
		
	}
	
	private static void example7() {
		//Metric[] metrics = new Metric[] { new DegreeDistribution() };
		Network nw1 = new ErdosRenyi(1000, 5, true, null);
		Graph g = nw1.generate();
		new GtnaGraphWriter().write(g, "./data/firstExample-graph.graph");
		Map<Integer, List> graph = new HashMap<>();
		
		
	}
	
	private static int getIndex(String name, HashMap<String, Integer> map){
		Integer a = map.get(name);
		if (a == null){
			a = map.size();
			map.put(name, a);
		}
		return a;
	}
	private static boolean addNeigh(int src, int dst, HashMap<Integer, Vector<Integer>> map){
		Vector<Integer> vec = map.get(src);
		if (vec == null){
			vec = new Vector<Integer>();
			map.put(src, vec);
		}
		if (vec.contains(dst)) return false;
		vec.add(dst);
		return true;
	}
	
	private static HashMap<String, Integer> turnGraphs(String in, String out, String name,
			String addList){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap<Integer, Vector<Integer>> neighbors = new HashMap<Integer, Vector<Integer>>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(in));
			BufferedWriter bw = new BufferedWriter(new FileWriter(out+"_CREDIT_LINKS"));
			bw.write("# Graph Property Class \ntreeembedding.credit.CreditLinks\n# Key \nCREDIT_LINKS");
			String line;
			int c = 0;
			while ((line = br.readLine()) != null){
				String[] parts = line.split(" ");
				if (parts.length == 5){
					c++;
					bw.newLine();
					int src = getIndex(parts[0],map);
					int dst = getIndex(parts[1],map);
					addNeigh(src, dst, neighbors);
					addNeigh(dst, src, neighbors);
					double low = -Double.parseDouble(parts[2]);
					double cur = Double.parseDouble(parts[3]);
					double up = Double.parseDouble(parts[4]);
					if (src < dst){
						bw.write(src + " " + dst + " " + low + " " + cur + " " + up);
					} else {
						bw.write(dst + " " + src + " " + -up + " " + -cur + " " + -low);
					}
						
				}
			}
			br.close();
			
			
			br = new BufferedReader(new FileReader(addList));
			while ((line = br.readLine()) != null){
				String[] parts = line.split(" ");
				if (parts.length == 4){
					c++;
					int src = getIndex(parts[0],map);
					int dst = getIndex(parts[1],map);
					if (addNeigh(src, dst, neighbors)
							&& addNeigh(dst, src, neighbors)) {
						bw.newLine();
						double low = 0;
						double cur = 0;
						double up = 0;
						if (src < dst) {
							bw.write(src + " " + dst + " " + low + " " + cur
									+ " " + up);
						} else {
							bw.write(dst + " " + src + " " + -up + " " + -cur
									+ " " + -low);
						}
					}
						
				}
			}
			br.close();
			bw.flush();
			bw.close();
			
			bw = new BufferedWriter(new FileWriter(out));
			bw.write("# Name of the Graph:\n"+name+"\n# Number of Nodes:\n"+map.size()+"\n# Number of Edges:\n"+2*c+"\n");
            for (int i = 0; i < map.size(); i++){
            	bw.newLine();
            	String l = i+":";
            	Vector<Integer> vec = neighbors.get(i);
            	l = l + vec.get(0);
            	for (int j = 1; j < vec.size(); j++){
            		l = l + ";" + vec.get(j);
            	}
            	bw.write(l);
            }
            bw.flush();
            bw.close();
		} catch (IOException e){
			
		}
		
		return map;
	}
	
	
	

}
