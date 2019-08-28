package io.meshrun.cfg

import groovy.transform.CompileStatic
import io.meshrun.cfg.cmds.GenerateCommand
import io.meshrun.cfg.cmds.TestCommand
import picocli.CommandLine

@CompileStatic
@CommandLine.Command(name = "mrcfg",
    mixinStandardHelpOptions = true,
    version = "v0.3.2",
    description = "Configuration as Code.",
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
