package k8s.apps.v1;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import k8s.K8sResource;

public class Deployment extends K8sResource {
  @Override
  protected String apiVersion() {
    return "apps/v1";
  }

  public Deployment(Closure body) {
    super(body);
  }

  public Deployment(String name, @DelegatesTo(value = TypedDeployment.class) Closure body) {
    super(name, body);
  }
}
