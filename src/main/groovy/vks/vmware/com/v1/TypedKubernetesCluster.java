package vks.vmware.com.v1;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import k8s.typechecked.TypedMetadata;

public class TypedKubernetesCluster {

  public void metadata(@DelegatesTo(TypedMetadata.class) Closure metadata) {
  }

  public void spec(@DelegatesTo(Spec.class) Closure spec) {
  }

  public class Spec {

    public class Topology {

      public class Workers {
        public void count(Integer value) {
        }

        public void class_(String name) {
        }
      }

      public void workers(@DelegatesTo(Workers.class) Closure workers) {
      }
    }

    public void topology(@DelegatesTo(Topology.class) Closure topology) {
    }

    public void distribution(String version) {
    }
  }

}
