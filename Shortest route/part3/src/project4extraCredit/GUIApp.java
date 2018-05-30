package project4extraCredit;


/** GUIApp - a class that deals with the UI.
 *  Creates the window with the MapPanel and displays the map of the US.
 *  Takes the instance of KruskalAlgorithm as a parameter and shows minimal spanning tree 
 *  produced by KruskalAlgorithm. You do not need to change it for part 1.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIApp extends JFrame {
	private MapPanel panel;

	GUIApp(KruskalAlgorithm kr) {
		// Creating a window
		JFrame frame = new JFrame("USA Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// Creating a panel with the image of the map, and with buttons
		panel = new MapPanel(kr);
		// Adding the panel to the window
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	/** The panel for the GUI */
	private class MapPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		public final static int RAD = 3;

		private KruskalAlgorithm kruskal;

		private BufferedImage image; // for showing the image of the map

		public MapPanel(KruskalAlgorithm kr) {
			this.kruskal = kr;
			this.setLayout(new BorderLayout());
			this.setPreferredSize(new Dimension(500, 290));
			this.setBackground(Color.lightGray);

			try { // load the image of the map of the USA
				image = ImageIO.read(new File("USA.bmp"));
			} catch (IOException ex) {
				System.out.println("Could not load the image. ");
			}
			repaint();
		}

		/**
		 * The method is responsible for drawing everything on the panel. Do not
		 * call it explicitly. Instead, call repaint() when something changes
		 * and needs to be repainted.
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
			drawNodes(g);
			drawEdges(g);
			drawMSTEdges(g);
		}

		/**
		 * Draws a little circle at the given location of the node; Uses the
		 * given color; "city" parameter is used to draw a label next to the
		 * circle.
		 */
		public void drawNode(Graphics g, Point location, Color col, String city) {
			g.setColor(col);
			g.fillOval(location.x - RAD, location.y - RAD, 2 * RAD, 2 * RAD);
			g.setColor(Color.black);
			g.setFont(new Font("SANS_SERIF", Font.PLAIN, 11));
			g.drawString(city, location.x + 2, location.y - 2);
		}

		/** Display the nodes of the graph as little circles on the map */
		public void drawNodes(Graphics g) {
			Point[] nodes = kruskal.getNodes();
			String[] labels = kruskal.getCities();
			for (int i = 0; i < nodes.length; i++) {
				drawNode(g, nodes[i], Color.BLACK, labels[i]);
			}

		}

		/** Draw edges of the graph */
		public void drawEdges(Graphics g) {
			Point[][] edges = kruskal.getEdges();
			g.setColor(Color.lightGray);

			for (int i = 0; i < edges.length; i++) {
				Point[] edge = edges[i];
				assert(edge.length == 2); // should contain two vertices
				Point p1 = edge[0];
				Point p2 = edge[1];
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		} // drawEdges

		/**
		 * Draw minimal spanning tree. Gets the edges of MST from
		 * KruskalAlgorithm.
		 */
		public void drawMSTEdges(Graphics g) {
			Point[][] edges = kruskal.getMSTEdges();
			g.setColor(Color.RED);
			for (int i = 0; i < edges.length; i++) {
				Point[] edge = edges[i];
				assert(edge.length == 2); // should contain two vertices
				Point p1 = edge[0];
				Point p2 = edge[1];
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		} // drawMSTEdges
	}
}