package io.meshrun.cfg;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class Json2Hcl {

    public static interface Lib extends Library {
        Lib INSTANCE = (Lib)Native.loadLibrary("json2hcl", Lib.class);
        String json_to_hcl(String json, int size);
    }

}
