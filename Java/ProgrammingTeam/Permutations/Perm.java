import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;


public class Perm {
	
	public static void main(String[] args)
	{
		char[] s = new char[] {'A','B','1','2'};
		
		for (String perm : permute("AB12")) {
			char[] permChars = perm.toCharArray();
			char[] map = new char[100];
			for (int i = 0; i < permChars.length; i++) {
				map[s[i]] = permChars[i];
			}
			
			applyMap(map, permChars);
			
			int count = 1;
			do {
				applyMap(map, permChars);
				count++;
			} while (Arrays.equals(perm.toCharArray(), permChars));
			System.out.println(count);
		}
		
		
	}
	
	public static void applyMap(char[] map, char[] word) {
		for (int i = 0; i < word.length; i++) {
			word[i] = map[word[i]];
		}
	}
	
	
	public static Set<String> permute(String str) {
        Set<String> set = new TreeSet<String>();
        permute(str.toCharArray(), 0, str.length() - 1, set);
        return set;
    }

    private static void permute(char[] a, int i, int n, Set<String> set) {
        int j;
        if (i == n) {
            set.add(new String(a));
            a = null;
        } else {
            for (j = i; j <= n; j++) {
                a = swap(a, i, j);
                permute(a, i + 1, n, set);
                a = swap(a, i, j);
            }
        }
    }

    public static void permute(String str, BlockingQueue<String> buffer) throws InterruptedException {
        permute(str.toCharArray(), 0, str.length() - 1, buffer);
    }

    private static void permute(char[] a, int i, int n, BlockingQueue<String> buffer) throws InterruptedException {
        int j;
        if (i == n) {
            buffer.put(new String(a));
            a = null;
        } else {
            for (j = i; j <= n; j++) {
                a = swap(a, i, j);
                permute(a, i + 1, n, buffer);
                a = swap(a, i, j);
            }
        }
    }

    private static char[] swap(char[] arr, int a, int b) {
        char tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
        return arr;
    }
}
