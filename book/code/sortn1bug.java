ArrayList<String> sl = new ArrayList<String>(iWeight.keySet());
final HashMap<String, String> wLoss = new HashMap<String, String>();
Collections.sort(sl, new Comparator<String> () {
    public int compare(String t1, String t2) {
        double iw1 = iWeight.get(t1);
        double iw2 = iWeight.get(t2);
        double fw1 = fWeight.get(t1);
        double fw2 = fWeight.get(t2);
        double wl1 = (int)((1.0 - fw1/iw1) * 1000.0);
        double wl2 = (int)((1.0 - fw2/iw2) * 1000.0);
        wLoss.put(t1, String.format("%.1f", (1.0 - fw1/iw1) * 100.0));
        wLoss.put(t2, String.format("%.1f", (1.0 - fw2/iw2) * 100.0));
        if (wl1 == wl2) {
            return t1.compareTo(t2);
        } else {
            return Double.valueOf(wl2).compareTo(wl1);
        }
    }
});
System.out.printf("%s %s%%%n", sl.get(0), wLoss.get(sl.get(0)));
