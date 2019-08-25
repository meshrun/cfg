package tf;

import groovy.json.JsonBuilder;
import groovy.json.JsonDelegate;
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import groovy.lang.Closure;
import io.meshrun.cfg.Json2Hcl;

import java.util.HashMap;
import java.util.Map;

public class Provider {

    private final Closure body;
    private String name;
    private Object json;

    public Provider(String name, Closure body) {
        this.name = name;
        this.body = body;
        prepare();
    }

    private void prepare() {
        Map<String, Object> jsonBody = JsonDelegate.cloneDelegateAndGetContent(body);
        Map<String, Object> name = new HashMap<String, Object>(){{
            put(Provider.this.name, jsonBody);
        }};
        Map<String, Object> provider = new HashMap<String, Object>(){{
            put("provider", name);
        }};
        JsonBuilder builder = new JsonBuilder(provider);
        this.json = new JsonSlurper().parseText(builder.toString());
    }

    @Override
    public String toString() {
        String input = JsonOutput.toJson(this.json);
        return Json2Hcl.Lib.INSTANCE.json_to_hcl(input, input.getBytes().length);
    }

    public String toJSON() {
        return JsonOutput.toJson(this.json);
    }

}