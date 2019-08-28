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

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ComposeResource extends GroovyObjectSupport {

  protected Closure body;
  private Object json;

  protected abstract String version();

  protected ComposeResource() {
  }

  public ComposeResource(Closure body) {
    this();
    this.body = body;
    prepare();
  }

  private void prepare() {
    Map<String, Object> jsonMeta = new LinkedHashMap<String, Object>() {{
      put("version", version());
    }};
    Map<String, Object> jsonBody = JsonDelegate.cloneDelegateAndGetContent(body);
    jsonMeta.putAll(jsonBody);
    JsonBuilder builder = new JsonBuilder(jsonMeta);

    this.json = new JsonSlurper().parseText(builder.toString());
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
    if (name.equals("version")) {
      return;
    }

    InvokerHelper.setProperty(this.json, name, value);
  }

  public Object getProperty(String name) {
    return InvokerHelper.getProperty(this.json, name);
  }

}
