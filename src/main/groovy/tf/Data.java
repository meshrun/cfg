package tf;

import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import groovy.lang.Closure;
import io.meshrun.cfg.Json2Hcl;

import java.util.HashMap;
import java.util.Map;

public class Data {

  private final Closure body;
  private final String group;
  private String name;
  private Object json;

  public Data(String group, String name, Closure body) {
    this.group = group;
    this.name = name;
    this.body = body;
    prepare();
  }

  private void prepare() {
    Map<String, Object> provider = mkMap("data", mkMap(this.group, mkMap(this.name, body)));

    JsonBuilder builder = new JsonBuilder(provider);
    this.json = new JsonSlurper().parseText(builder.toString());
  }

  private static Map<String, Object> mkMap(String key, Closure closure) {
    Map<String, Object> value = JsonDelegate.cloneDelegateAndGetContent(closure);
    return new HashMap<String, Object>() {{
      put(key, value);
    }};
  }

  private static Map<String, Object> mkMap(String key, Map<String, Object> value) {
    return new HashMap<String, Object>() {{
      put(key, value);
    }};
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
