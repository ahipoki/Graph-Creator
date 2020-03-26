import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GraphPanel extends JPanel
{	
	ArrayList<Node> nodeList = new ArrayList<Node>();
	//Array list of the nodes
	ArrayList<Edge> edgeList = new ArrayList<Edge>();
	//Array list of the edges
	int circleRadius = 20;
	//Circle's radius is 20
	
	ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>();
	
	public GraphPanel()
	{
		super();
	}
	
	public ArrayList<String> getConnectedLabels(String label)
	{//Get the labels of the connected nodes
		ArrayList<String> toReturn = new ArrayList<String>();
		int b = getIndex(label);
		//Get the label of an index value
		for (int a = 0; a < adjacency.size(); a++)
		{//For the adjacency matrix
			if ((adjacency.get(b).get(a) == true) && (nodeList.get(a).getLabel().equals(label)))
			{
				toReturn.add(nodeList.get(a).getLabel());
			}
		}
		return toReturn;
	}
	
	public void printAdjacency()
	{//Adjacency matrix
		System.out.println();
		//Print it out
		for (int a = 0; a < adjacency.size(); a++)
		{//Adjacency's length
			for (int b = 0; b < adjacency.size(); b++)
			{//Adjacency's width
				System.out.print(adjacency.get(a).get(b)+ "\t");
				//Print out the adjacency matrix
			}
			System.out.println();
			//Print out
		}
	}
	
	public void addNode(int newx, int newy, String newlabel)
	{//Add a node
		nodeList.add(new Node(newx, newy, newlabel));
		//Add a node to the node list
		adjacency.add(new ArrayList<Boolean>());
		//Add values to the adjacency matrix
		for (int a = 0; a < adjacency.size() - 1; a++)
		{//For the adjacency matrix
			adjacency.get(a).add(false);
			//Add a false
		}
		for (int a = 0; a < adjacency.size(); a++)
		{//For the adjacency matrix
			adjacency.get(adjacency.size() - 1).add(false);
			//Add false
		}
		printAdjacency();
		//Print adjacency matrix method
	}
	
	public void addEdge(Node first, Node second, String newlabel)
	{//Add an edge
		edgeList.add(new Edge(first, second, newlabel));
		//Add an edge to the edge list
		int firstIndex = 0;
		//Edge's first index
		int secondIndex = 0;
		//Edge's second index
		for (int a = 0; a < nodeList.size(); a++)
		{//For the node list's size
			if (first.equals(nodeList.get(a)))
			{//If the first equals the node list value
				firstIndex = a;
				//First index = a
			}
			if (second.equals(nodeList.get(a)))
			{//If the second equals the node list value
				secondIndex = a;
				//Second index = a
			}
		}
		adjacency.get(firstIndex).set(secondIndex, true);
		//Get the first index and set it to the second index
		adjacency.get(secondIndex).set(firstIndex, true);
		//Get the second index and set it to the first index
		printAdjacency();
		//Print adjacency matrix method
	}
	
	public Node getNode(int x, int y)
	{//Get node integers
		for (int a = 0; a < nodeList.size(); a++)
		{//For the node list's size
			Node node = nodeList.get(a);
			//Get 'a' in the node list
			//a squared plus b squared = c squared
			double radius = Math.sqrt(Math.pow(x-node.getX(), 2) + Math.pow(y-node.getY(), 2));
			if (radius < circleRadius) {
				return node;
			}
		}
		return null;
	}
	
	public Node getNode(String s)
	{//Get node string
		for (int a = 0; a < nodeList.size(); a++)
		{//For the node list's size
			Node node = nodeList.get(a);
			//Get 'a' in the node list
			if (s.equals(node.getLabel()))
			{//If 's' equals the label of the node
				return node;
				//Return the node
			}
		}
		return null;
		//Otherwise return null
	}
	
	public int getIndex(String s)
	{//Get the index
		for (int a = 0; a < nodeList.size(); a++)
		{//For the node list's size
			Node node = nodeList.get(a);
			//Get 'a' in the node list
			if (s.equals(node.getLabel()))
			{//If 's' equals the node's label
				return a;
				//Return a
			}
		}
		return -1;
		//If it isn't a real string, return something invalid
	}
	
	public boolean nodeExists(String s)
	{//Node exists
		for (int a = 0; a < nodeList.size(); a++)
		{//For the node list's size
			if (s.equals(nodeList.get(a).getLabel()))
			{//If 's' equals the label from 'a' in the node list
				return true;
				//Return true
			}
		}
		return false;
		//Otherwise return false
	}
	
	public void paintComponent(Graphics g)
	{//Drawing method
		super.paintComponent(g);
		//draw my stuff
		for (int a = 0; a < nodeList.size(); a++)
		{
			if (nodeList.get(a).getHighlighted() == true)
			{
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLACK);
			}
			g.drawOval(nodeList.get(a).getX() - circleRadius, nodeList.get(a).getY() - circleRadius, circleRadius * 2, circleRadius * 2);
			g.drawString(nodeList.get(a).getLabel(), nodeList.get(a).getX(), nodeList.get(a).getY());
		}
		for (int a = 0; a < edgeList.size(); a++)
		{
			g.setColor(Color.BLACK);
			g.drawLine(edgeList.get(a).getFirst().getX(), 
					   edgeList.get(a).getFirst().getY(), 
					   edgeList.get(a).getSecond().getX(), 
					   edgeList.get(a).getSecond().getY());
			int fx = edgeList.get(a).getFirst().getX();
			int fy = edgeList.get(a).getFirst().getY();
			int sx = edgeList.get(a).getSecond().getX();
			int sy = edgeList.get(a).getSecond().getY();
			g.drawString(edgeList.get(a).getLabel(),
					Math.min(fx, sx) + (Math.abs(sx - fx) / 2),
					Math.min(fy, sy) + (Math.abs(sy - fy) / 2));
		}
	}

	public void stopHighlighting()
	{//Stop highlighting a node
		for (int a = 0; a < nodeList.size(); a++)
		{//For the node list's size
			nodeList.get(a).setHighlighted(false);
			//Unhighlight the node
		}
	}
}
