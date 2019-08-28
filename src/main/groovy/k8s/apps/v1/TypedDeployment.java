package k8s.apps.v1;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import k8s.typechecked.TypedMetadata;

public class TypedDeployment {

    /**
     * Kubernetes Object Metadata.
     * It may contain the resource name, the resource namespace, and labels.
     *
     * @param metadata body of metadata
     *
     * **/
    public void metadata(@DelegatesTo(TypedMetadata.class) Closure metadata) {}
    public void spec(@DelegatesTo(TypedDeploymentSpec.class) Closure spec) {}

}
