package k8s.core.v1;

import groovy.lang.Closure;
import k8s.K8sResource;

public class ConfigMap extends K8sResource {

    @Override
    protected String apiVersion() {
        return "v1";
    }

    public ConfigMap(Closure body) {
        super(body);
    }

    public ConfigMap(String name, Closure body) {
        super(name, body);
    }

}
