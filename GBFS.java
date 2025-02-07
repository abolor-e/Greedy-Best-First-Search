import java.util.*;

/*
Problem Formulation:
State: All possible states from A - H.
Initial State: A
Actions: East, West, South, North

Transition Model: Where each action leads to
- A to B (West=2, South=1) SC=3
- A to C (East=3) SC=3
- B to D (South=2) SC=2
- C to F (South=3) SC=3
- D to E (East=4) SC=4
- F to G (South=2, West = 1) SC=3
- F to E (West=1) SC=1
- E to F (East=1) SC=1
- E to G (South=2) SC=2
- G to H (West=2) SC=2

Path Cost: ...

Goal State: State H 

Optimal Path: Cost 11
*/

//Transition model will be an array with all the possible transitions!
//For example, A to B can be coded inside an array as 12 and A to C as 13.
//HASH MAP

class Node
{
	String state_ID;//State name
	Node parent;
	String action;
	int pathCost;
	int heuristic;

	public Node(String state_ID, int h)
	{
		this.state_ID = state_ID;
		parent = null;
		action = null;
		pathCost = 0;
		this.heuristic = h;
	}

	public Node(Node other)
	{
		this.state_ID = other.state_ID;
		this.parent = other.parent;  // Reference copy (shallow)
		this.action = other.action;  // Reference copy (shallow)
		this.pathCost = other.pathCost;
		this.heuristic = other.heuristic;
	}
}

class Action_result
{
	String action;
	String result_state;
	int step_cost;
	int heuristic;

	public Action_result(String a, String rs, int step_cost, int h)
	{
		this.action = a;
		this.result_state = rs;
		this.step_cost = step_cost;
		this.heuristic = h;
	}
}

class TransitionModel
{
	private Map<String, List<Action_result>> transitionModel;

	public TransitionModel()
	{
		transitionModel = new HashMap<>();
	}

	public void addTransition(String state_ID, String action_name, String stateToMove, int step_cost, int heuristic)
	{
		transitionModel.putIfAbsent(state_ID, new ArrayList<>());
		transitionModel.get(state_ID).add(new Action_result(action_name, stateToMove, step_cost, heuristic));
	}

	public List<Action_result> getTransitions(String state_ID, List<Action_result> values)
	{
		return transitionModel.getOrDefault(state_ID, values);
	}
}

public class GBFS
{
	//Check state_ID in queue nodes
	public static boolean state_ID_checkQ(PriorityQueue<Node> queue, String ID)
	{
		for (Node node : queue)
		{
			if (node.state_ID. equals(ID))
				return (true);
		}
		return (false);
	}

	public static void solution(Node node)
	{
		Node ptr = node;
		System.out.println();
		System.out.println("\033[32mGOAL STATE REACHED\033[0m");
		while (true)
		{
			if (ptr == null)
				break ;
			System.out.print("\033[32m" + ptr.state_ID + "\033[0m");
			if (ptr.action == null)
				break ;
			System.out.print("<--");
			System.out.print("\033[36m" + ptr.action + "\033[0m");
			System.out.print("--");
			ptr = ptr.parent;
		}
		System.out.println();
	}

	public static boolean goal_test(Node node)
	{
		boolean solutionFound = false;
		if (node.state_ID.equals("H"))
			solutionFound = true;
		return (solutionFound);
	}
	public static void main(String[] args)
	{
		//ALL THE POSSIBLE TRANSITIONS FOR GRAPH SEARCH ONE WAY
		TransitionModel transitionModel = new TransitionModel();
		transitionModel.addTransition("A", "East", "C", 3, 8);
		transitionModel.addTransition("A", "South West", "B", 3, 6);
		transitionModel.addTransition("B", "South", "D", 2, 4);
		transitionModel.addTransition("C", "South", "F", 3, 5);
		transitionModel.addTransition("D", "East", "E", 4, 4);
		transitionModel.addTransition("F", "South West", "G",3, 2);
		transitionModel.addTransition("F", "West", "E", 1, 4);
		transitionModel.addTransition("E", "East", "F", 1, 5);
		transitionModel.addTransition("E", "South", "G", 2, 2);
		transitionModel.addTransition("G", "West", "H", 2, 0);

		//REVERSE TRANSITION MAKING BIDIRECTIONAL to see if algorithm will expand already expanded state
		transitionModel.addTransition("C", "East", "A", 3, 5);
		transitionModel.addTransition("B", "South West", "A", 3, 5);
		transitionModel.addTransition("D", "South", "B", 2, 6);
		transitionModel.addTransition("F", "South", "C", 3, 8);
		transitionModel.addTransition("E", "East", "D", 4, 4);
		transitionModel.addTransition("G", "South West", "F",3, 5);
		transitionModel.addTransition("G", "South", "E", 2, 4);
		transitionModel.addTransition("H", "West", "G", 2, 2);

		// List<Action_result> transitionOfA = transitionModel.getTransitions("A", new ArrayList<>());
		// System.out.println("Actions and Reachable states of state A");
		// for (Action_result ar : transitionOfA)
		// {
		//     System.out.println("Action: " + ar.action + "| Reaching state: " + ar.result_state + "| Step Cost: " + ar.step_cost);
		// }

		Node root = new Node("A", 5);
		Node node;
		PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2)
			{
				return Integer.compare(n1.heuristic, n2.heuristic);
			}
		});//priority queue which 	
				// has to priority queue
		if (goal_test(root))
		{
			solution(root);
			return ;
		}
		queue.add(root);//Frontier
		int a = 0;
		ArrayList<String> expandedSet = new ArrayList<>();//Expanded state_IDs gonna be saved in the memory
		while (true)
		{
			System.out.println();
			System.out.println("\033[32mLoop " + a + "\033[0m");
			if (queue.isEmpty())
				return ;
			for (Node element : queue)
				System.out.println("Frontier: " + element.state_ID);
			for (String state_ID : expandedSet)
				System.out.println("Expanded set: " + state_ID);
			node = queue.poll();//Will pull the highest priority element from the queue (least h)
			System.out.println("Chosen node to be expanded ID: " + node.state_ID);
			expandedSet.add(node.state_ID);//String state_ID is added
			for (String state_ID : expandedSet)
				System.out.println("Expanded set: " + state_ID);//to see if expandedSet is updated
			
			List<Action_result> reach = transitionModel.getTransitions(node.state_ID, new ArrayList<>());
			for (Action_result ar : reach)
			{
				Node new_node = new Node(node);
				new_node.state_ID = ar.result_state;
				new_node.action = ar.action;
				new_node.heuristic = ar.heuristic;
				new_node.parent = node;
				new_node.pathCost = new_node.pathCost + ar.step_cost;
			
				if (!expandedSet.contains(new_node.state_ID) && !state_ID_checkQ(queue, new_node.state_ID))
				{
					if (goal_test(new_node))
					{
						solution(new_node);
						System.out.println("Path Cost: " + new_node.pathCost);
						System.out.println();
						return ;
					}
					queue.add(new_node);
					System.out.println("Newly generated state added to the priority queue: " + new_node.state_ID);
				}
			}
			a++;
		}
	}
}