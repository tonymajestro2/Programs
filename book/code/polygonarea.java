
static double areaPolygon(Point2D.Double p[]) {
    double area = 0.0;
    for (int i = 0; i < p.length; i++) {
        area += p[i].x * p[(i+1) % p.length].y - p[i].y * p[(i+1) % p.length].x;
    }
    return Math.abs(area/2.0);
}

