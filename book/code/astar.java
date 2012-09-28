// shortest distance to start state
final Map<State, Integer> dist = new HashMap<State, Integer>();   
Queue<State> queue;
if (useAstar) {
    // sort by sum of distance to start state + mindist to goal
    queue = new PriorityQueue<State>(1000, new Comparator<State>() {
        @Override
        public int compare(State a, State b) {
            int ascore = dist.get(a) + a.mindistancetogoal();
            int bscore = dist.get(b) + b.mindistancetogoal();
            return Integer.valueOf(ascore).compareTo(bscore);
        }
    });
} else {
    queue = new ArrayDeque<State>();                 // BFS queue
}
queue.offer(start);
