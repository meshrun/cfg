package io.meshrun.cfg;

import groovy.json.JsonBuilder;
import groovy.json.JsonDelegate;
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;
import java.util.TreeMap;

public abstract class BaseObject extends GroovyObjectSupport {

    protected Closure body;
    protected String name;
    private Object json;

    protected BaseObject(){
    }

    protected abstract String apiVersion();
    protected String kind() {
        return this.getClass().getSimpleName();
    }

    public BaseObject(Closure body) {
        this();
        this.body = body;
        prepare();
    }

    public BaseObject(String name, Closure body) {
        this(body);
        this.name = name;
        prepare();
    }

    public void prepare() {
        Map<String, Object> jsonTypeMeta = new TreeMap<String, Object>(){{
            put("apiVersion", apiVersion());
            put("kind", kind());
        }};
        Map<String, Object> jsonBody = JsonDelegate.cloneDelegateAndGetContent(body);
        jsonTypeMeta.putAll(jsonBody);
        JsonBuilder builder = new JsonBuilder(jsonTypeMeta);

        this.json = new JsonSlurper().parseText(builder.toString());
        Object metadata = InvokerHelper.getProperty(json, "metadata");
        if(metadata == null) {
            metadata = new TreeMap<String, Object>();
            InvokerHelper.setProperty(json, "metadata", metadata);
        }
        InvokerHelper.setProperty(metadata, "name", this.name);
    }

    @Override
    public String toString() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        return yaml.dump(yaml.load(JsonOutput.toJson(this.json)));
    }

    public void setProperty(String name, Object value) {
        if(name.equals("apiVersion")) {
            return;
        }
        if(name.equals("kind")) {
            return;
        }

        InvokerHelper.setProperty(this.json, name, value);
    }

    public Object getProperty(String name) {
        return InvokerHelper.getProperty(this.json, name);
    }

}
