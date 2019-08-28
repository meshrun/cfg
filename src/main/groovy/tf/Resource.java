package tf;

import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import groovy.lang.Closure;
import io.meshrun.cfg.Json2Hcl;

import java.util.HashMap;
import java.util.Map;

public class Resource {

  private final Closure body;
  private final String group;
  private String name;
  private Object json;

  public Resource(String group, String name, Closure body) {
    this.group = group;
    this.name = name;
    this.body = body;
    prepare();
  }

  private void prepare() {
    Map<String, Object> jsonBody = JsonDelegate.cloneDelegateAndGetContent(body);
    Map<String, Object> name = new HashMap<String, Object>() {{
      put(Resource.this.name, jsonBody);
    }};
    Map<String, Object> group = new HashMap<String, Object>() {{
      put(Resource.this.group, name);
    }};
    Map<String, Object> provider = new HashMap<String, Object>() {{
      put("resource", group);
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
