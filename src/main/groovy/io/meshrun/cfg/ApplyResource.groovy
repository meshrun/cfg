package io.meshrun.cfg

import groovy.transform.CompileStatic

@CompileStatic
class ApplyResource {

    static Object apply(Object self, Class<Script> cls) {
        return cls.newInstance().run()
    }

}
