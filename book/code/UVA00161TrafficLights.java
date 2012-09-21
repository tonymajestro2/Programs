import java.util.*;
/**
 * Discrete Event Simulation.
 * UVA 00161 Traffic Lights
 *
 * @author Godmar Back
 */

public class Main
{
    /** Begin generic discrete event code. */
    Queue<Event> evQueue = new PriorityQueue<Event>();
    int currentTime;

    void schedule(Event e) {
        evQueue.offer(e);
    }

    int now() {
        return currentTime;
    }

    class Event implements Comparable<Event> {
        int time;
        Runnable what;

        Event(int time, Runnable what) {
            this.time = time;
            this.what = what;
        }

        @Override
        public int compareTo(Event that) {
            return this.time - that.time;
        }
    }

    /**
     * Event loop.
     *
     * Mostly generic, some problem-specific output interspersed.
     * Note that 'time' always jumps to the next event, rather than
     * being incremented one by one.
     */
    void simulate(int maxTime) {
        while (evQueue.peek().time <= maxTime) {
            Event e = evQueue.poll();
            currentTime = e.time;
            e.what.run();

            if (!allGreen())
                continue;

            System.out.printf("%02d:%02d:%02d%n", e.time/3600, (e.time % 3600)/60, e.time % 60);
            return;
        }
        System.out.println("Signals fail to synchronise in 5 hours");
    }

    /** End generic discrete event code. */

    class LightChange implements Runnable {
        int light;
        LightState state;

        LightChange(int light, LightState state) {
            this.light = light;
            this.state = state;
        }

        @Override
        public void run() {
            states[light] = state;
            switch (state) {
            case GREEN:
                schedule(new Event(now() + cycles[light] - 5, new LightChange(light, LightState.ORANGE)));
                break;
            case ORANGE:
                schedule(new Event(now() + 5, new LightChange(light, LightState.RED)));
                break;
            case RED:
                schedule(new Event(now() + cycles[light], new LightChange(light, LightState.GREEN)));
                break;
            }
        }
    }

    enum LightState {
        GREEN, ORANGE, RED;
    };

    LightState [] states;
    int [] cycles;

    Main(List<Integer> lightcycles) {
        states = new LightState[lightcycles.size()];
        cycles = new int[lightcycles.size()];
        for (int i = 0; i < lightcycles.size(); i++) {
            states[i] = LightState.GREEN;
            cycles[i] = lightcycles.get(i);
            /* prime event queue with when the traffic lights first turn orange */
            schedule(new Event(cycles[i] - 5, new LightChange(i, LightState.ORANGE)));
        }
    }

    boolean allGreen() {
        for (LightState s : states) {
            if (s != LightState.GREEN) {
                return false;
            }
        }
        return true;
    }

    public static void main(String []av) {
        Scanner s = new Scanner(System.in);
        for (;;) {
            ArrayList<Integer> lightcycles = new ArrayList<Integer>();
            int n;
            while ((n = s.nextInt()) != 0)
                lightcycles.add(n);

            if (lightcycles.size() == 0)
                return;

            new Main(lightcycles).simulate(5 * 60 * 60);
        }
    }
}
