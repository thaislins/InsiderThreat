/*
 * (C) Copyright 2003-2017, by Barak Naveh and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package br.imd.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
// resolve ambiguity
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.ListenableDirectedGraph;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.Logon;
import br.imd.profile.Database;
import br.imd.profile.Main;
import br.imd.profile.PC;
import br.imd.profile.UserProfile;

/**
 * A demo applet that shows how to use JGraph to visualize JGraphT graphs.
 *
 * @author Barak Naveh
 * @since Aug 3, 2003
 */
public class UserVisualization extends JFrame {
	private JPanel p;
	private JGraph jgraph;
	private int frameSize;
	private JGraphModelAdapter<String, DefaultEdge> jgAdapter;
	private JButton buttonUserId;
	private JTextField textUserId;

	/**
	 * An alternative starting point for this demo, to also allow running this
	 * applet as an application.
	 *
	 * @param args
	 *            ignored.
	 */
	public static void main(String[] args) {
		UserVisualization frame = new UserVisualization();
		frame.setVisible(true);
	}

	public UserVisualization() {
		// getContentPane().setLayout(null);
		frameSize = 960;
		// setBackground(Color.white);
		setSize(frameSize, frameSize);
		setTitle("Insider Threat");
		setVisible(true);
		init();
		// getContentPane().add(textUserId);
		// getContentPane().add(buttonUserId);

	}

	public void init() {
		p = new JPanel();
		p.setBackground(Color.white);

		jgraph = createTree("ACD0647");
		JScrollPane scrollpane = new JScrollPane(p);
		getContentPane().add(scrollpane, BorderLayout.CENTER);

		p.add(jgraph);
		// p.add(buttonUserId);
		// p.add(textUserId);
	}

	public JGraph createTree(String userID) {

		ListenableGraph<String, DefaultEdge> g = new ListenableDirectedGraph<>(DefaultEdge.class);
		jgAdapter = new JGraphModelAdapter<>(g);
		JGraph jgraph = new JGraph(jgAdapter);

		Main t = new Main();
		t.readFiles();
		UserProfile userprofile = Database.users.get(userID);

		// create a JGraphT graph
		String v1 = userprofile.getUser_id();
		String v2;
		String v3;
		String v4;
		String v5;

		if (!userprofile.isFiltered())
			v2 = "All dates";
		else
			v2 = userprofile.getFilteredDate();

		g.addVertex(v1);
		g.addVertex(v2);
		g.addEdge(v1, v2);
		positionVertexAt(v1, 0, 0);
		positionVertexAt(v2, 0, 100);

		// Adiciona valores dos dispositivos
		int emptyX = 0;
		for (Entry<String, PC> pcs : userprofile.getDevices().entrySet()) {
			v3 = pcs.getKey();
			g.addVertex(v3);
			g.addEdge(v2, v3);
			positionVertexAt(v3, emptyX, 200);

			for (Device device : pcs.getValue().getDeviceActivity()) {
				v4 = device.getActivity();
				g.addVertex(v4);
				g.addEdge(v3, v4);
				positionVertexAt(v4, emptyX, 300);
			}
			for (Logon logon : pcs.getValue().getLogonActivity()) {
				v4 = logon.getActivity() + "_" + logon.getId();
				g.addVertex(v4);
				g.addEdge(v3, v4);
				positionVertexAt(v4, emptyX, 300);
			}
			for (HTTP http : pcs.getValue().getHttpActivity()) {
				v4 = http.getActivity() + "_" + http.getId();
				g.addVertex(v4);
				g.addEdge(v3, v4);
				positionVertexAt(v4, emptyX, 300);
				v5 = http.getUrl();
				g.addVertex(v5);
				g.addEdge(v4, v5);
				positionVertexAt(v5, emptyX, 400);
			}
			emptyX += 100;
		}
		// String v6 = "A";
		// String v7 = "B";
		// String v8 = "C";
		// String v9 = "D";
		// String v10 = "E";
		// String v11 = "F";
		//
		// g.addVertex(v6);
		// g.addVertex(v7);
		// g.addVertex(v8);
		// g.addVertex(v9);
		// g.addVertex(v10);
		// g.addVertex(v11);
		// g.addEdge(v2, v6);
		// g.addEdge(v2, v7);
		// g.addEdge(v2, v8);
		// g.addEdge(v2, v9);
		// g.addEdge(v2, v10);
		// g.addEdge(v2, v11);
		// positionVertexAt(v6, 500, 200);
		// positionVertexAt(v7, 600, 200);
		// positionVertexAt(v8, 700, 200);
		// positionVertexAt(v9, 800, 200);
		// positionVertexAt(v10, 900, 200);
		// positionVertexAt(v11, 1000, 200);

		return jgraph;
	}

	@SuppressWarnings("unchecked")
	private void positionVertexAt(Object vertex, int x, int y) {
		DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
		AttributeMap attr = cell.getAttributes();
		Rectangle2D bounds = GraphConstants.getBounds(attr);

		Rectangle2D newBounds = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());

		GraphConstants.setBounds(attr, newBounds);

		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}

	/**
	 * a listenable directed multigraph that allows loops and parallel edges.
	 */
	private static class ListenableDirectedMultigraph<V, E> extends DefaultListenableGraph<V, E>
			implements DirectedGraph<V, E> {

		ListenableDirectedMultigraph(Class<E> edgeClass) {
			super(new DirectedMultigraph<>(edgeClass));
		}
	}
}
