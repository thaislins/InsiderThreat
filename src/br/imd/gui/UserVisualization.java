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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import br.imd.profile.DateFilter;
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
	private JButton btnUserId;
	private JButton btnReadLDAP;
	private JButton btnReadFiles;
	private JButton btnReadDevice;
	private JButton btnReadLogon;
	private JButton btnReadHttp;
	private JButton btnChooseDate;
	private JComboBox chooseFile;
	private JComboBox dateType;
	private JTextField txtUserId;
	private JScrollPane scrollpane;
	private Date date1;
	private Date date2;
	private Main t;
	private String userId;

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
		userId = "";
		frameSize = 960;
		setSize(frameSize, frameSize);
		setTitle("Insider Threat");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

	}

	public void init() {
		// Cria JPanel princiapal que será dividido em 2
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

		// JPanel que terá a árvore
		p = new JPanel();
		p.setBackground(Color.white);

		// Permite scroll no JPanel
		scrollpane = new JScrollPane(p);

		// JPanel que terá botôes para interação do usuário
		JPanel p2 = new JPanel();
		p2.setBackground(Color.gray);
		p2.setLayout(null);

		btnReadLDAP = new JButton("Read LDAP");
		btnReadLDAP.setBounds(100, 230, 120, 15);

		p2.add(btnReadLDAP);

		main.add(scrollpane, BorderLayout.CENTER);
		main.add(p2, BorderLayout.CENTER);
		getContentPane().add(main, BorderLayout.CENTER);

		// Implementação das ações dos botões
		btnReadLDAP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t = new Main();
				t.readLDAP();
				System.out.println("User File Read");
				p2.remove(btnReadLDAP);
				addButtons(p2);
				chooseDateType(p2);
			}
		});
	}

	public void chooseDateType(JPanel p2) {
		JLabel lblDateChoice = new JLabel("Choose Date Format");
		dateType = new JComboBox();
		btnChooseDate = new JButton("Enter");

		dateType.setBounds(500, 200, 120, 24);
		btnChooseDate.setBounds(500, 230, 120, 15);
		lblDateChoice.setBounds(490, 170, 150, 24);
		dateType.addItem("All");
		dateType.addItem("Filtered");

		p2.add(btnChooseDate);
		p2.add(dateType);
		p2.add(lblDateChoice);

		btnChooseDate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String dateChoice = (String) dateType.getSelectedItem();

				if (dateChoice.equals("All")) {
					chooseUserId(p2);
					p2.remove(btnChooseDate);
					p2.remove(lblDateChoice);
					p2.remove(dateType);
					p2.repaint();
				} else {
					chooseUserId(p2);
					p2.remove(dateType);
					p2.remove(btnChooseDate);
					JTextField txtDate1 = new JTextField();
					JTextField txtDate2 = new JTextField();
					JButton btnFilteredDate = new JButton("Enter");

					lblDateChoice.setText("Enter Dates");
					txtDate1.setBounds(500, 200, 120, 15);
					txtDate2.setBounds(650, 200, 120, 15);
					btnFilteredDate.setBounds(565, 230, 120, 15);
					lblDateChoice.setBounds(585, 170, 120, 15);
					p2.add(txtDate1);
					p2.add(txtDate2);
					p2.add(btnFilteredDate);
					p2.repaint();

					btnFilteredDate.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							DateFilter filter = new DateFilter();
							DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
							userId = txtUserId.getText();
							txtUserId.setText("");

							try {
								date1 = df.parse(txtDate1.getText());
								date2 = df.parse(txtDate2.getText());
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							createFilteredTree(p2);
						}
					});
				}
			}

		});
	}

	public void chooseUserId(JPanel p2) {
		JLabel lblUserId = new JLabel("Choose User Id");
		btnUserId = new JButton("Enter");
		txtUserId = new JTextField();

		txtUserId.setBounds(300, 200, 120, 15);
		btnUserId.setBounds(300, 230, 120, 15);
		lblUserId.setBounds(305, 170, 150, 24);

		p2.add(btnUserId);
		p2.add(txtUserId);
		p2.add(lblUserId);

		btnUserId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						userId = txtUserId.getText();
						jgraph = createTree(userId);
						p.removeAll();
						p.add(jgraph);
						txtUserId.setText("");
						scrollpane.revalidate();
						scrollpane.repaint();
					}
				});
				t.start();
			}
		});
	}

	public void addButtons(JPanel p2) {
		chooseFile = new JComboBox();
		chooseFile.addItem("Device");
		chooseFile.addItem("Logon");
		chooseFile.addItem("Http");
		chooseFile.setBounds(100, 200, 120, 24);

		btnReadFiles = new JButton("Read File");
		btnReadFiles.setBounds(100, 230, 120, 15);

		p2.add(chooseFile);
		p2.add(btnReadFiles);
		p2.repaint();

		btnReadFiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String fileChoice = (String) chooseFile.getSelectedItem();

				t.readFiles(fileChoice);
				System.out.println("File Read");
				if (!userId.equals("")) {
					Thread t = new Thread(new Runnable() {
						public void run() {
							jgraph = createTree(userId);
							p.removeAll();
							p.add(jgraph);
							scrollpane.revalidate();
							scrollpane.repaint();
						}
					});
					t.start();
				}
			}

		});
	}

	public void createFilteredTree(JPanel p2) {

		DateFilter df = new DateFilter();
		UserProfile userprofile = df.choosePeriod(date1, date2, userId);

		jgraph = createTree("f_" + userprofile.getUser_id());
		p.add(jgraph);
		p.revalidate();
		p.repaint();
	}

	public JGraph createTree(String userID) {

		ListenableGraph<String, DefaultEdge> g = new ListenableDirectedGraph<>(DefaultEdge.class);
		jgAdapter = new JGraphModelAdapter<>(g);
		JGraph jgraph = new JGraph(jgAdapter);

		UserProfile userprofile = Database.users.get(userID);

		if (userprofile.getUser_id().contains("f_")) {
			userprofile.getUser_id().replace("f_", "");
		}

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
				v4 = device.getActivity() + "_" + device.getId();
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
				v5 = http.getUrl() + "_" + http.getId();
				g.addVertex(v5);
				g.addEdge(v4, v5);
				positionVertexAt(v5, emptyX, 400);
			}
			emptyX += 100;
		}
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
