package io.meshrun.cfg

class Runner {

    static require = { cls ->
        return cls.newInstance().run()
    }

    static void main(String[] args) {
        String dir = new File(args[0]).getParentFile().getAbsolutePath()
        String name = new File(args[0]).getName()
        def binding = new Binding()
        binding.setVariable("SCRIPT_HOME", dir)
        binding.setVariable("require", require)
        new GroovyScriptEngine(dir).run(name, binding)
    }

}
