package io.meshrun.cfg

class Globals {

  static String SCRIPT_HOME

  static require = { Class<Script> cls ->
    return cls.newInstance().run()
  }

  static apply = { Class<Script> cls ->
    return cls.newInstance().run()
  }

  static json2hcl = { String json ->
    return Json2Hcl.Lib.INSTANCE.json_to_hcl(json, json.getBytes().size())
  }

}
