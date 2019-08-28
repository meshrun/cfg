package io.meshrun.cfg.cmds

import groovy.io.FileType
import groovy.transform.CompileStatic
import io.meshrun.cfg.App
import io.meshrun.cfg.ApplyResource
import io.meshrun.cfg.Globals
import k8s.ClosureArray
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.MultipleCompilationErrorsException
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.junit.runner.Result
import org.junit.runner.RunWith
import org.junit.runners.model.MultipleFailureException
import org.spockframework.runtime.SpecUtil
import picocli.CommandLine
import spock.util.EmbeddedSpecRunner

@CommandLine.Command(name = "test",
        description = "Run test spec.",
        mixinStandardHelpOptions = true)
class TestCommand implements Runnable {

    @CommandLine.Parameters(arity = "1..1", paramLabel = "SPEC", description = "The spec file to test.")
    private String specFile

    private boolean isJUnitTest(Class clazz) {
        clazz.isAnnotationPresent(RunWith) || clazz.methods.any { it.getAnnotation(org.junit.Test) }
    }

    @Override
    void run() {
        String dir = new File(this.specFile).getParentFile().getAbsolutePath()
        String name = new File(this.specFile).getName()
        String className = name - ".groovy"

        def urls = new ArrayList<String>()
        urls.add(dir)
        try {
            new File(dir + "/libs").eachFileRecurse(FileType.FILES, {
                String path = it.getAbsolutePath()
                if (path.endsWith(".jar")) {
                    urls.add(path)
                }
            })
        }catch(ignored) {}
        def engine = new GroovyScriptEngine(urls.toArray() as String[])
        def config = new CompilerConfiguration()

        Globals.SCRIPT_HOME = dir
        ImportCustomizer imports = new ImportCustomizer()
        imports.addStaticStars("io.meshrun.cfg.Globals")
        config.addCompilationCustomizers(imports)
        config.addCompilationCustomizers(new ASTTransformationCustomizer(ClosureArray))
        engine.setConfig(config)

        def body = new File(this.specFile).text
        // def source = "class ${className} extends spock.lang.Specification { ${body} \n}"
        def loader = engine.getGroovyClassLoader()

        def unwrapCompileException = true
        try {
            loader.parseClass(body.trim())
        } catch (MultipleCompilationErrorsException e) {
            def errors = e.errorCollector.errors
            if ( unwrapCompileException && errors.every { it.hasProperty("cause") } ) {
                if (errors.size() == 1)
                    throw errors[0].cause
                else
                    throw new MultipleFailureException(errors.cause)
            }
            throw e
        }

        def classes = loader.loadedClasses.findAll {
            SpecUtil.isSpec(it) || isJUnitTest(it)
        } as List

        classes*.mixin(ApplyResource)
        def runner = new EmbeddedSpecRunner(throwFailure: false)
        Result result = runner.runClasses(classes)
        if(result.failureCount != 0) {
            result.failures.each {
                println it
            }
        }
        println("Run Time:      ${result.runTime}s")
        println("Run Count:     ${result.runCount}")
        println("Ignore Count:  ${result.ignoreCount}")
        println("Failure Count: ${result.failureCount}")
    }

}
