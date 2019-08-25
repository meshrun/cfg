package io.meshrun.cfg

import groovy.io.FileType
import groovy.transform.CompileStatic

@CompileStatic
class Runner {

    static require = { Class<Script> cls ->
        return cls.newInstance().run()
    }

    static json2hcl = { String json ->
        return Json2Hcl.Lib.INSTANCE.json_to_hcl(json, json.getBytes().size())
    }

    static void main(String[] args) {
        String dir = new File(args[0]).getParentFile().getAbsolutePath()
        String name = new File(args[0]).getName()
        def binding = new Binding()
        binding.setVariable("SCRIPT_HOME", dir)
        binding.setVariable("require", require)
        binding.setVariable("apply", require) // alias
        binding.setVariable("json2hcl", json2hcl)

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
        new GroovyScriptEngine(urls.toArray() as String[]).run(name, binding)
    }

}
