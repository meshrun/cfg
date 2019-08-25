package k8s.core.v1;

import groovy.lang.Closure;
import k8s.K8sResource;

public class Service extends K8sResource {

    @Override
    protected String apiVersion() {
        return "v1";
    }

    public Service(Closure body) {
        super(body);
    }

    public Service(String name, Closure body) {
        super(name, body);
    }

}
