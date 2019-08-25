package io.meshrun.cfg

import groovy.transform.CompileStatic
import io.meshrun.cfg.cmds.Generate

import picocli.CommandLine

@CompileStatic
@CommandLine.Command(name = "mrcfg",
        mixinStandardHelpOptions = true,
        version = "v0.1.1",
        description = "Configurations as Codes.",
        subcommands = [Generate.class])
class Runner implements Runnable {

    static require = { Class<Script> cls ->
        return cls.newInstance().run()
    }

    static json2hcl = { String json ->
        return Json2Hcl.Lib.INSTANCE.json_to_hcl(json, json.getBytes().size())
    }

    private CommandLine cmdline

    static void main(String[] args) {
        def app = new Runner()
        def cmdline = new CommandLine(app)
        app.cmdline = cmdline
        cmdline.execute(args)
    }

    @Override
    void run() {
        cmdline.usage(System.out)
    }

}
