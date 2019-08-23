package io.meshrun.cfg

class Runner {

    static void main(String[] args) {
        String dir = new File(args[0]).getParentFile().getAbsolutePath()
        String name = new File(args[0]).getName()
        new GroovyScriptEngine(dir).run(name, new Binding())
    }

}
