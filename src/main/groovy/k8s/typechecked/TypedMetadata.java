package k8s.typechecked;

import groovy.lang.Closure;

public class TypedMetadata {

  public void labels(Closure map) {
  }

  /**
   * Kubernetes resource's name
   *
   * @param value value of the resource's name
   */
  public void name(String value) {
  }

  public void namespace(String value) {
  }

}
