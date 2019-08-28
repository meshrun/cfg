package compose.v24;

import groovy.lang.Closure;
import io.meshrun.cfg.ComposeResource;

public class File extends ComposeResource {

  @Override
  protected String version() {
    return "2.4";
  }

  public File(Closure body) {
    super(body);
  }
}
