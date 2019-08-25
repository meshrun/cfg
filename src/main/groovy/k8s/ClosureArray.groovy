package k8s

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.PACKAGE, ElementType.TYPE])
@GroovyASTTransformationClass(classes = [ClosureArrayTransformation])
@interface ClosureArray {

}
