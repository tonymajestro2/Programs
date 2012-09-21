/** BFS Skeleton.
 * Assumes that 'State' implements equals() and hashCode()
 * according to contract.
 * State must also provide 'isfinal', and 'successors' methods
 */
void solve(State start) {
    Set<State> visited = new HashSet<State>();                  
    // has this state been visited?
    Map<State, State> pred = new HashMap<State, State>();       
    // predecessor on the shortest path to the start state
    Map<State, Integer> dist = new HashMap<State, Integer>();   
    // shortest distance to start state
    Deque<State> bfs = new ArrayDeque<State>();  // BFS queue
    bfs.offer(start);
    dist.put(start, 0);

    while (bfs.size() > 0) {
        State s = bfs.poll();
        int n = dist.get(s);
        visited.add(s);

        if (s.isfinal()) {
            output(n, s, pred);
            return;
        }

        for (State succ : s.successors()) {
            if (visited.contains(succ))
                continue;

            if (!pred.containsKey(succ))
                pred.put(succ, s);

            if (!dist.containsKey(succ)) {
                dist.put(succ, n+1);
                bfs.offer(succ);
            }
        }
    }
}

/* Compute and output path */
void output(int distToSolution, State finalState, Map<State, State> pred) {
    System.out.println("The distance to the solution is: " + distToSolution);

    List<State> revPath = new ArrayList<State>();
    State s = finalState;
    while (pred.containsKey(s)) {
        revPath.add(s);
        s = pred.get(s);
    }
    revPath.add(s);

    for (int i = 0; i < revPath.size(); i++) {
        System.out.printf("%3d %s%n", i, revPath.get(revPath.size() - 1 - i));
    }
}

