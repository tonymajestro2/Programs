import java.util.*;

public class Pi
{
    public static void main(String []av) {
        Random r = new Random();
        int incircle = 0, tries = 0;
        for (int i = 0; i < 20000000; i++) {
            double x = r.nextDouble();
            double y = r.nextDouble();
            if (x * x + y * y <= 1.0)
                incircle++;
            tries++;
        }
        System.out.printf("Pi is %f (exact value is %f)%n", 
                4*incircle/(double)tries, Math.PI);
    }
}
