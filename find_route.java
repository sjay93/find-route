import java.io.*;
import java.util.*;

class Node
{
	public String val;
	public int cost;
	public Node parent;
	public ArrayList<Edge> children;

	public Node(int cost,Node parent)
	{
		super();
		this.cost = cost;
		this.parent = parent;
	}
	public Node(Node child) 
	{
		val = child.val;
		cost = child.cost;
		for(Edge ed : child.children)
		{
			this.children.add(ed);
		}
		parent = child.parent;
	}
	public Node(String val) 
	{
		super();
		this.val = val;
	}
}
class Edge
{
	public int cost;
	Node dest;
	
	public Edge(Node dest, int cost)
	{
		super();
		this.cost = cost;
		this.dest = dest;
	}
}
public class find_route {
	public static void main(String[] args) throws FileNotFoundException {
		File f= new File(args[0]);
		Scanner sc = new Scanner(f);
		Set<Node> set_node = new LinkedHashSet<Node>();
		Node start,finish,src1 = null,dest1 = null,edge1,edge2;		
		while (sc.hasNext() && !sc.equals("END OF INPUT"))
		{
			String s = sc.next();
			if(s.equalsIgnoreCase("End"))
			{
				break;
			}
			if(search(s,set_node) == null)
			{
				src1 = new Node(s);
				set_node.add(src1);
	
				src1.children = new ArrayList<Edge>();
			}
			String s1 = sc.next();
			if(search(s1,set_node) == null)
			{
				dest1 = new Node(s1);
				dest1.children = new ArrayList<Edge>();
				set_node.add(dest1);
			}
			edge1 = search(s,set_node);
			edge2 = search(s1,set_node);
			int c = sc.nextInt();
			Edge ed1 = new Edge(edge1,c);
			Edge ed2 = new Edge(edge2,c);
			for(Node n1 : set_node)
			{
				if(n1.val.equalsIgnoreCase(s1))
				{
					n1.children.add(ed1);
				}
			}
			for(Node n1 : set_node)
			{
				if(n1.val.equalsIgnoreCase(s))
				{
					n1.children.add(ed2);
				}
			}
		}
		start = search(args[1],set_node);
		finish = search(args[2],set_node);
		start.cost = 0;
		UCS(start,finish);
		findFullPath(finish);
		sc.close();
	}
	public static Node search(String s1,Set<Node> set_node) 
	{
		for(Node n : set_node)
			{
				if(n.val.equalsIgnoreCase(s1))
				{
					return n;
				}
			}	
			return null;
	}
	public static void UCS(Node src2, Node dest2){
		HashSet<Node> traversed = new HashSet<Node>();
		boolean success = false;
        PriorityQueue<Node> frontier = new PriorityQueue<Node>(50, new Comparator<Node>()
        {
                public int compare(Node n1, Node n2)
                {
                	int c1 = n1.cost;
                	int c2 = n2.cost;
                	if(c1 > c2)
                    {
                        return 1;
                    }
                    else if (c1<c2)
                    {
                        return -1;
                    }
                    else
                    {
                        return 0;
                    }
                }
            }
        );
        frontier.add(src2);
		do {
			Node curr = frontier.poll();
			if (curr.val.equalsIgnoreCase(dest2.val)) 
			{
				success = true;
			}
			traversed.add(curr);
			for (Edge e : curr.children) 
			{
				Node ch = e.dest;
				int cost = e.cost;
				if (frontier.contains(ch) == false && traversed.contains(ch)== false) 
				{
					ch.cost = curr.cost + cost;
					ch.parent = curr;
					frontier.add(ch);
				}
				else if (frontier.contains(ch) == true && (ch.cost > (curr.cost + cost))) 
				{
					ch.parent = curr;
					ch.cost = curr.cost + cost;
					frontier.remove(ch);
					frontier.add(ch);
				}
			}
		} 
		while (frontier.isEmpty() == false && success == false);
	}
	public static void findFullPath(Node finish) 
	{
		ArrayList<Node> path = new ArrayList<Node>();
		for (Node node = finish; node != null; node = node.parent) 
		{
			path.add(node);
		}
		Collections.reverse(path);
		if (finish.cost == 0)
		{
			System.out.println("Distance : infinity");
		}	
		else
		{
			System.out.println("distance: "+finish.cost+" km");
		}
		System.out.println("route:");
		if (finish.cost == 0) 
		{
			System.out.println("none");
		} 
		else 
		{
			for (int i=0;i<=path.size()-1;i++) 
			{
				
				Node str1,end1;
				if (path.size() > i+1) 
				{
					str1 = path.get(i);
					end1 = path.get(i+1);
					int diff = end1.cost - str1.cost;
					System.out.println(""+str1.val+" to "+end1.val+", "+diff+" km");
				}
			}
		}
	}
}