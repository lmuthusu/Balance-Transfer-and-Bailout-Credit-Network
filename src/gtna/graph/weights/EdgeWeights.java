/* ===========================================================
 * GTNA : Graph-Theoretic Network Analyzer
 * ===========================================================
 *
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors
 *
 * Project Info:  http://www.p2p.tu-darmstadt.de/research/gtna/
 *
 * GTNA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GTNA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * ---------------------------------------
 * EdgeWeights.java
 * ---------------------------------------
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors 
 *
 * Original Author: benni;
 * Contributors:    -;
 *
 * Changes since 2011-05-17
 * ---------------------------------------
 *
 */
package gtna.graph.weights;

import gtna.graph.Edge;
import gtna.graph.GraphProperty;
import gtna.graph.spanningTree.ParentChild;
import gtna.io.Filereader;
import gtna.io.Filewriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Implements a graph property to hold edge weights, i.e., a weight assigned to
 * each edge in the network. If the weight of a node is requested that has not
 * yet been assigned, the defaultWeight is returned.
 * 
 * @author benni
 * 
 */
public class EdgeWeights extends GraphProperty {

	private Map<Edge, Double> weights;

	private double defaultWeight;

	public EdgeWeights() {
		this(-1);
	}

	public EdgeWeights(double defaultWeight) {
		this(new Edge[0], 0, defaultWeight);
	}
	
	public  double getRandomDoubleBetweenRange(double min, double max){

	    double x = (Math.random()*((max-min)+1))+min;

	    return x;

	}

	public EdgeWeights(Edge[] edges, double weight, double defaultWeight) {
		this.weights = new HashMap<Edge, Double>();
		this.defaultWeight = defaultWeight;
		for (Edge edge : edges) {
			this.weights.put(edge, getRandomDoubleBetweenRange(2,100));
		}
	}

	public Set<Entry<Edge, Double>> getWeights() {
		return this.weights.entrySet();
	}

	public void setWeight(Edge edge, double weight) {
		this.weights.put(edge, weight);
	}

	public double getWeight(Edge edge) {
		try {
			return this.weights.get(edge);
		} catch (NullPointerException e) {
			return this.defaultWeight;
		}
	}
	
	

	@Override
	public boolean write(String filename, String key) {
		Filewriter fw = new Filewriter(filename);

		this.writeHeader(fw, this.getClass(), key);

        Iterator<Entry<Edge, Double>> it = this.weights.entrySet().iterator();
        while (it.hasNext()){
        	Entry<Edge, Double> entry = it.next();
        	fw.writeln(entry.getKey().getSrc()+ " " + entry.getKey().getDst() + " " + entry.getValue());
        }

		return fw.close();
	}

	@Override
	public String read(String filename) {
		Filereader fr = new Filereader(filename);

		String key = this.readHeader(fr);
        this.weights = new HashMap<Edge, Double>();
		String line = null;
		while ((line = fr.readLine()) != null) {
			String[] parts = line.split(" ");
			Edge e = new Edge(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			double val = Double.parseDouble(parts[2]);
			this.weights.put(e, val);
		}

		fr.close();

		return key;
	}

}
