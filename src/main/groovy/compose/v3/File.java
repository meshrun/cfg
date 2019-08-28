package compose.v3;

import groovy.lang.Closure;
import io.meshrun.cfg.ComposeResource;

public class File extends ComposeResource {

  @Override
  protected String version() {
    return "3.6";
  }

  public File(Closure body) {
    super(body);
  }
}
