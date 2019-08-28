package compose.v2;

import groovy.lang.Closure;
import io.meshrun.cfg.ComposeResource;

public class File extends ComposeResource {

  @Override
  protected String version() {
    return "2";
  }

  public File(Closure body) {
    super(body);
  }
}
