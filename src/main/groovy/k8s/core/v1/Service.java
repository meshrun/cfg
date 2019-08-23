package k8s.core.v1;

import groovy.lang.Closure;
import io.meshrun.cfg.BaseObject;

public class Service extends BaseObject {

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
