package io.meshrun.cfg.cmds

import groovy.io.FileType
import groovy.transform.CompileStatic
import io.meshrun.cfg.App
import io.meshrun.cfg.Globals
import k8s.ClosureArray
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.control.customizers.ImportCustomizer
import picocli.CommandLine

@CompileStatic
@CommandLine.Command(name = "generate",
                     description = "GenerateCommand configurations from a script.",
                     mixinStandardHelpOptions = true)
class GenerateCommand implements Runnable {

    @CommandLine.Parameters(arity = "1..1", paramLabel = "SCRIPT", description = "The script file to run configurations from.")
    private String scriptFile

    @Override
    void run() {
        String dir = new File(this.scriptFile).getParentFile().getAbsolutePath()
        String name = new File(this.scriptFile).getName()

        /*
        def binding = new Binding()
        binding.setVariable("SCRIPT_HOME", dir)
        binding.setVariable("require", App.require)
        binding.setVariable("apply", App.require) // alias
        binding.setVariable("json2hcl", App.json2hcl)
        */

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
        engine.run(name, new Binding())
    }
}
