ASTNode parseEquation() {
    ASTNode lhs = parseExpression();
    if (lookahead() != '=')
        throw new Error("Missing =");
    tokens.poll();  // consume token
    ASTNode rhs = parseExpression();
    return new Equation(lhs, rhs);
}
