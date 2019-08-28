package k8s.typechecked;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

public class TypedResource {

  public void metadata(@DelegatesTo(value = TypedMetadata.class) Closure metadata) {
  }

  public void spec(@DelegatesTo(value = TypedSpec.class) Closure spec) {
  }

}
