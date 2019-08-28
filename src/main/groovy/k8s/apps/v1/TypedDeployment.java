package k8s.apps.v1;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import k8s.typechecked.TypedMetadata;

import java.util.List;

public class TypedDeployment {

  /**
   * Kubernetes Object Metadata.
   * It may contain the resource name, the resource namespace, and labels.
   *
   * @param metadata body of metadata
   **/
  public void metadata(@DelegatesTo(TypedMetadata.class) Closure metadata) {
  }

  public void spec(@DelegatesTo(Spec.class) Closure spec) {
  }

  public class Spec {
    public void selector(@DelegatesTo(Selector.class) Closure selector) {
    }

    public void replicas(Integer replicas) {
    }

    public void template(@DelegatesTo(Template.class) Closure template) {
    }

    public void containers(@DelegatesTo(Container.class) Closure containers) {
    }

    public class Selector {
      public void matchLabels(Closure matchLabels) {
      }
    }

    public class Template {
      public void metadata(@DelegatesTo(TypedMetadata.class) Closure metadata) {
      }

      public void spec(@DelegatesTo(PodSpec.class) Closure podSpec) {
      }

      public class PodSpec {
        public void containers(List<Closure> containers) {
        }
      }
    }

    public class Container {
      public void name(String name) {
      }

      public void image(String imageName) {
      }

      public void resources(@DelegatesTo(Resource.class) Closure resources) {
      }

      public void ports(@DelegatesTo(Port.class) Closure port) {
      }

      public class Resource {
        public void cpu(String cpu) {
        }

        public void memory(String memory) {
        }
      }

      public class Port {
        public void containerPort(Integer port) {
        }
      }
    }
  }
}
