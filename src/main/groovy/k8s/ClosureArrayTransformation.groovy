package k8s

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class ClosureArrayTransformation implements ASTTransformation {

    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {
        for(cn in source.getAST().getClasses()) {
            for(mn in cn.methods) {
                mn.code.visit(new CodeVisitorSupport(){
                    @Override
                    void visitMethodCallExpression(MethodCallExpression call) {
                        def ale = call.arguments as ArgumentListExpression
                        if (ale.size() == 1 && (ale.getExpression(0) instanceof ClosureExpression)) {
                            def ce = ale.getExpression(0) as ClosureExpression
                            def blockStmt = ce.code as BlockStatement
                            def allAreItems = blockStmt.statements.every {
                                (it instanceof ExpressionStatement) && ((it as ExpressionStatement).expression instanceof UnaryMinusExpression)
                            }
                            if (allAreItems) {
                                def listExpr = new ListExpression()
                                blockStmt.statements.each {
                                    def e = ((it as ExpressionStatement).expression as UnaryMinusExpression).expression
                                    listExpr.addExpression(e)
                                }
                                call.arguments = new ArgumentListExpression(listExpr)
                            }
                        }
                        super.visitMethodCallExpression(call)
                    }
                })
            }
        }
    }

}
