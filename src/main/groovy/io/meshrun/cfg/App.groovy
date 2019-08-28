package io.meshrun.cfg

import groovy.transform.CompileStatic
import io.meshrun.cfg.cmds.GenerateCommand
import io.meshrun.cfg.cmds.TestCommand
import picocli.CommandLine

@CompileStatic
@CommandLine.Command(name = "mrcfg",
        mixinStandardHelpOptions = true,
        version = "v0.2.0",
        description = "Configurations as Codes.",
        subcommands = [GenerateCommand.class, TestCommand.class])
class App implements Runnable {

    private CommandLine cmdline

    static void main(String[] args) {
        def app = new App()
        def cmdline = new CommandLine(app)
        app.cmdline = cmdline
        cmdline.execute(args)
    }

    @Override
    void run() {
        cmdline.usage(System.out)
    }

}
