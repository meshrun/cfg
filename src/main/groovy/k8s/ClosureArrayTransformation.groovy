package k8s

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class ClosureArrayTransformation implements ASTTransformation {

  private void checkAndTransformCall(MethodCallExpression call) {
    // check only of the call looks like
    // ports { ... }
    // only the call with a single closure arg would do
    def ale = call.arguments as ArgumentListExpression
    if (ale.size() == 1 && (ale.getExpression(0) instanceof ClosureExpression)) {
      def ce = ale.getExpression(0) as ClosureExpression
      def blockStmt = ce.code as BlockStatement

      // if there's a closure call looks like:
      // {
      //   - { ... }
      //   - { ... }
      // }
      def allAreItems = blockStmt.statements.every {
        (it instanceof ExpressionStatement) &&
            (
                (it as ExpressionStatement).expression instanceof UnaryMinusExpression ||
                    (it as ExpressionStatement).expression instanceof UnaryPlusExpression
            )
      }
      if (allAreItems) {
        // convert it to
        // ([
        //   { ... },
        //   { ... }
        // ])
        //
        // so that it would be able to generate
        // as a proper yaml array
        def listExpr = new ListExpression()
        // init root
        Expression root = listExpr
        blockStmt.statements.each {
          def item = (it as ExpressionStatement).expression
          if (item instanceof UnaryMinusExpression) {
            listExpr.addExpression((item as UnaryMinusExpression).expression)
          } else if (item instanceof UnaryPlusExpression) {
            root = new BinaryExpression(
                root,
                Token.newSymbol(Types.PLUS, 0, 0),
                (item as UnaryPlusExpression).expression
            )
          }
        }
        call.arguments = new ArgumentListExpression(root)
      }
    }
  }

  @Override
  void visit(ASTNode[] nodes, SourceUnit source) {
    if (source.getAST() == null) {
      return
    }
    for (cn in source.getAST().getClasses()) {
      cn.addAnnotation(new AnnotationNode(new ClassNode(TypeChecked.class)))
      for (mn in cn.methods) {
        mn.code.visit(new CodeVisitorSupport() {
          @Override
          void visitMethodCallExpression(MethodCallExpression call) {
            checkAndTransformCall(call)
            super.visitMethodCallExpression(call)
          }
        })
      }
    }
  }

}
