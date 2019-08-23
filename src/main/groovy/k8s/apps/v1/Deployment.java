package k8s.apps.v1;

import groovy.lang.Closure;
import io.meshrun.cfg.BaseObject;

public class Deployment extends BaseObject {
    @Override
    protected String apiVersion() {
        return "apps/v1";
    }

    public Deployment(Closure body) {
        super(body);
    }

    public Deployment(String name, Closure body) {
        super(name, body);
    }
}
