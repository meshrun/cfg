package k8s.core.v1;

import k8s.K8sResource;

public class Namespace extends K8sResource {

    @Override
    protected String apiVersion() {
        return "v1";
    }

    public Namespace(String name) {
        super(name, null);
    }

}

/*
apiVersion: v1
kind: Namespace
  metadata:
    name: <insert-namespace-name-here>
*/
