package k8s.core.v1;

import groovy.lang.Closure;
import k8s.K8sResource;

public class Secret extends K8sResource {

    @Override
    protected String apiVersion() {
        return "v1";
    }

    public Secret(Closure body) {
        super(body);
    }

    public Secret(String name, Closure body) {
        super(name, body);
    }

}
