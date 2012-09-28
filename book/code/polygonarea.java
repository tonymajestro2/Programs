
static double areaPolygon(Point2D.Double p[]) {
    double area = 0.0;
    int n = p.length;
    for (int i = 0; i < n; i++) {
        area += p[i].x * p[(i+1) % n].y - p[i].y * p[(i+1) % n].x;
    }
    return Math.abs(area/2.0);
}

