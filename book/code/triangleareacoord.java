// special case of polygon area formula
static double triangle_area(P a, P b, P c) {
    return Math.abs(a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2.0;
}

