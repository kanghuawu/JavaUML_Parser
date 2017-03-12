package test_java_parser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;

class MyVisitor extends ModifierVisitor<Void> {
    @Override
    public Node visit(VariableDeclarator declarator, Void args) {
        if (declarator.getNameAsString().equals("a")
                // the initializer is optional, first check if there is one
                && declarator.getInitializer().isPresent()) {
            Expression expression = declarator.getInitializer().get();
            // We're looking for a literal integer.
            if (expression instanceof IntegerLiteralExpr) {
                // We found one. Is it literal integer 20?
                if (((IntegerLiteralExpr) expression).getValue().equals("20")) {
                    // Returning null means "remove this VariableDeclarator"
                    return null;
                }
            }
        }
        return declarator;
    }
}
