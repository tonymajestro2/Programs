
static double det(double x1, double y1, double x2, double y2) {
    return x1 * y2 - y1 * x2;
}

static Point2D.Double intersects(Point2D.Double p1, Point2D.Double p2, 
                                 Point2D.Double p3, Point2D.Double p4) {
    double d = det(p1.x - p2.x, p1.y - p2.y, p3.x - p4.x, p3.y - p4.y);
    double x12 = det(p1.x, p1.y, p2.x, p2.y);
    double x34 = det(p3.x, p3.y, p4.x, p4.y);

    // assert d != 0 (lines are known to intersect)
    double x = det(x12, p1.x-p2.x, x34, p3.x-p4.x) / d;
    double y = det(x12, p1.y-p2.y, x34, p3.y-p4.y) / d;
    return new Point2D.Double(x, y);
}

