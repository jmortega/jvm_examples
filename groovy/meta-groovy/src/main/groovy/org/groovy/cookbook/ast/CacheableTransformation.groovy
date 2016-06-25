package org.groovy.cookbook.ast

import java.lang.reflect.Modifier

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation
class CacheableTransformation implements ASTTransformation {


  boolean doTransform(ASTNode[] astNodes) {

    if (!astNodes) return false
    if (!astNodes[0] || !astNodes[1]) return false
    if (!(astNodes[0] instanceof AnnotationNode)) return false
    if (!(astNodes[1] instanceof MethodNode)) return false
    return true
  }


  @Override
  void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

    if (!doTransform(astNodes)) return

    //validate AnnotationNode
    MethodNode annotatedMethod = astNodes[1]
    BlockStatement methodCode = annotatedMethod.code

    if (annotatedMethod.parameters.length != 1) return
    if (annotatedMethod.returnType.name == 'void') return

    def methodStatements = annotatedMethod.code.statements
    def parameterName = annotatedMethod.parameters[0].name
    def cachedFieldName = annotatedMethod.name
    def declaringClass = annotatedMethod.declaringClass

    FieldNode cachedField =
        new FieldNode("cache$cachedFieldName", Modifier.PRIVATE, new ClassNode(Map.class), new ClassNode(declaringClass.getClass()),
        new ConstructorCallExpression(new ClassNode(HashMap.class), new ArgumentListExpression()))

    declaringClass.addField(cachedField)

    Statement oldReturnStatement = methodCode.statements.last()

    def ex = oldReturnStatement.getExpression()

    def stats = """
            def cached = cache${cachedFieldName}.get(${parameterName})
            if (cached) {
                return cached
            }
        """

    List<ASTNode> checkMap = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS,true,stats)

    def putInMap = new AstBuilder().buildFromSpec {

      expression {
        declaration {
          variable "localCalculated$cachedFieldName"
          token "="
          {-> delegate.expression << ex }()
        }
      }
      expression {
        methodCall {
          variable "cache$cachedFieldName"
          constant 'put'
          argumentList {
            variable parameterName
            variable "localCalculated$cachedFieldName"
          }
        }
      }
      returnStatement { variable "localCalculated$cachedFieldName" }
    }
    methodStatements.remove(oldReturnStatement)
    methodStatements.add(0, checkMap[0])
    methodStatements.add(putInMap[0])
    methodStatements.add(putInMap[1])
    methodStatements.add(putInMap[2])


  }

}
