import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GraphCreator implements ActionListener, MouseListener
{
	JFrame frame = new JFrame();
	//JFrame
	GraphPanel panel = new GraphPanel();
	//New graph panel
	JButton nodeB = new JButton("Node");
	//JButton for adding nodes
	JButton edgeB = new JButton("Edge");
	//JButton for adding edges
	JTextField labelsTF = new JTextField("A");
	//Text field to add labels
	JTextField firstNode = new JTextField("First");
	//Text field for first node to test for connection
	JTextField secondNode = new JTextField("Second");
	//Text field for second node to test for connection
	JButton connectedB = new JButton("Test Connected");
	//JButton to test for connection
	Container west = new Container();
	//West container
	Container east = new Container();
	//East container
	Container south = new Container();
	//South container
	JTextField salesmanStartTF = new JTextField("A");
	//Text field to check for travelling salesman
	JButton salesmanB = new JButton("Shortest Path");
	//JButton to check for shortest path
	final int NODE_CREATE = 0;
	//Create node
	final int EDGE_FIRST = 1;
	//First end of the edge
	final int EDGE_SECOND = 2;
	//Second end of the edge
	int state = NODE_CREATE;
	//Starting state to create nodes
	Node first = null;
	ArrayList<ArrayList<Node>> completed = new ArrayList<ArrayList<Node>>();
	
	public GraphCreator()
	{
		frame.setSize(800, 600);
		//Size of the frame set to 800 by 600 pixels
		frame.setLayout(new BorderLayout());
		//Set a border layout on the frame
		frame.add(panel, BorderLayout.CENTER);
		//Add border layout to the center
		west.setLayout(new GridLayout(3,1));
		//Set a grid layout to the west container
		west.add(nodeB);
		//Add the JButton to add nodes to the west container
		nodeB.addActionListener(this);
		//Add an action listener to that button
		nodeB.setBackground(Color.GREEN);
		//Set the background color of the JButton to green
		west.add(edgeB);
		//Add the JButton to add edges to the west container
		edgeB.addActionListener(this);
		//Add an action listener to that JButton
		edgeB.setBackground(Color.LIGHT_GRAY);
		//Set the background color of the JButton to light gray
		west.add(labelsTF);
		//Add a text field to the west container to make labels
		frame.add(west, BorderLayout.WEST);
		//Add the west container to the frame
		east.setLayout(new GridLayout(3,1));
		//Set the layout for the east container
		east.add(firstNode);
		//Add the button to test for the first node's connection to the east container
		east.add(secondNode);
		//Add the button to test for the second node's connection to the east container
		east.add(connectedB);
		//Add the button to test for connection to the east container
		connectedB.addActionListener(this);
		//Add an action listener to the button to test for connections
		frame.add(east, BorderLayout.EAST);
		//Add the east container to the east side of the frame
		panel.addMouseListener(this);
		//Add a mouse listener to the panel
		south.setLayout(new GridLayout(1,2));
		//Set the layout of the south container
		south.add(salesmanStartTF);
		//Add the salesman button to the south container
		south.add(salesmanB);
		//Add the button to check shortest path to the south container
		salesmanB.addActionListener(this);
		//Add an action listener to the button to check shortest path
		frame.add(south, BorderLayout.SOUTH);
		//Add the south container to the south of the frame
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new GraphCreator();

	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (state == NODE_CREATE)
		{//If it's in the button to create nodes
			panel.addNode(e.getX(), e.getY(), labelsTF.getText());
			//Add a node with the given label to the panel
		}
		else if (state == EDGE_FIRST)
		{//Else if it's in the button to create edges
			Node n = panel.getNode(e.getX(), e.getY());
			//Get the 2 nodes to create an edge between
			if (n != null)
			{//If there are 2 valid nodes selected
				first = n;
				//Select the first node
				state = EDGE_SECOND;
				//Set the state to the second end of the edge
				n.setHighlighted(true);
				//Highlight the first node
			}
		}
		else if (state == EDGE_SECOND)
		{//If it's selecting the second end of an edge
			Node n = panel.getNode(e.getX(), e.getY());
			//Select a second node on the panel
			if (n != null && !first.equals(n))
			{//If it's an actual node
				String s = labelsTF.getText();
				//Create an edge with the given label
				boolean valid = true;
				//Set valid to true
				for (int a = 0; a < s.length(); a++)
				{//For the label's length
					if (Character.isDigit(s.charAt(a)) == false)
					{//If it's an invalid digit
						valid = false;
						//Set valid to false
					}
				}
				if (valid == true)
				{//If it's valid
					first.setHighlighted(false);
					//Unhighlight the highlghted node
					panel.addEdge(first, n, labelsTF.getText());
					//Add the edge to the panel
					first = null;
					state = EDGE_FIRST;
					//Set the state to selecting the first node for an edge
				}
				else
				{//If the label doesn't consist of digits
					JOptionPane.showMessageDialog(frame, "Can only have digits in edge labels.");
					//Tell the user the label can only contain digits
				}
			}
		}
		frame.repaint();
		//Repaint the frame
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(nodeB))
		{//If the button pressed is the button to create nodes
			nodeB.setBackground(Color.GREEN);
			//Set the button's background color to green
			edgeB.setBackground(Color.LIGHT_GRAY);
			//Set the button to create edges' background color to light gray
			state = NODE_CREATE;
			//You can now create nodes
		}
		if (e.getSource().equals(edgeB))
		{//If the button pressed is the button to create edges
			edgeB.setBackground(Color.GREEN);
			//Set the button's background color to green
			nodeB.setBackground(Color.LIGHT_GRAY);
			//Set the button to create nodes' background color to light gray
			state = EDGE_FIRST;
			//You can now create edges
			panel.stopHighlighting();
			//Stop highlighting
			frame.repaint();
			//Repaint the frame
		}
		if (e.getSource().equals(connectedB))
		{//If it's the button to check connections
			if (panel.nodeExists(firstNode.getText()) == false)
			{//If the first node is not in the panel
				JOptionPane.showMessageDialog(frame, "First node is not in your graph.");
				//Tell the user the first node is not in the panel
			}
			else if (panel.nodeExists(secondNode.getText()) == false)
			{//If the second node is not in the panel
				JOptionPane.showMessageDialog(frame, "Second node is not in your graph.");
				//Tell the user the second node is not in the panel
			}
			else
			{//If both nodes are in the panel
				Queue queue = new Queue();
				//Start the queue
				ArrayList<String> connectedList = new ArrayList<String>();
				connectedList.add(panel.getNode(firstNode.getText()).getLabel());
				ArrayList<String> edges = panel.getConnectedLabels(firstNode.getText());
				for (int a = 0; a < edges.size(); a++)
				{
					queue.enqueue(edges.get(a));
					//Add the nodes to the queue
				}
				while (queue.isEmpty() == false)
				{//While the queue is not empty
					String currentNode = queue.dequeue();
					//Remove 1 node from the queue
					if (connectedList.contains(currentNode) == false)
					{//If the connected list does not have the current node
						connectedList.add(currentNode);
						//Add it to the list of connections
					}
					edges = panel.getConnectedLabels(currentNode);
					for (int a = 0; a < edges.size(); a++) {
						if (connectedList.contains(edges.get(a)) == false) {
							queue.enqueue(edges.get(a));
						}
					}
				}
				if (connectedList.contains(secondNode.getText())) {
					JOptionPane.showMessageDialog(frame, "Connected!");
				}
				else {
					JOptionPane.showMessageDialog(frame, "Not Connected.");
				}
			}
		}
		//if (e.getSource().equals(salesmanB)) {
			//if (panel.getNode(salesmanStartTF.getText()) != null) {
				//travelling(panel.getNode(salesmanStartTF.getText()), new ArrayList<Node>(), 0);
			//}
			//else {
				//JOptionPane.showMessageDialog(frame, "Not a valid starting node!");
			//}
		//}
	}
	
	//public void travelling(Node n, ArrayList<Node> path, int total) {
		//if the # of nodes in path = # of nodes
		//	Add this path to the completed list
		//	remove the last thing in the path
		//	return
		//else
		//for (int a = 0; a < edgeList.size(); a++) {
			//Edge e = edgeList.get(a);
			//if (e.getOtherEnd(n) != null) {
				//if (path.contains(e.getOtherEnd(n)) == false) {
					//path.add(e.getOtherEnd(n));
					//travelling(e.getOtherEnd(n), path, total + Integer.parseInt(e.getLabel());
				//}
			//}	
		//}
		//	for each edge
		//		see if they're connected to the current node
		//		if they are not already in the path
		//		  add node to the path
		//		  travelling(connected node, path, total + edge cost);
		//remove the last thing in the path
	//}
}
