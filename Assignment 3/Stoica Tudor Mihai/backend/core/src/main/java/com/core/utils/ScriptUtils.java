package com.core.utils;

import javax.script.*;
import java.io.IOException;
import java.util.List;

public class ScriptUtils {

    public static void runJavaScript(String script, String functionToRun, Object... scriptParams) throws ScriptException, NoSuchMethodException, IOException {
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
//        engine.eval(script);
//        ((Invocable) engine).invokeFunction(functionToRun, scriptParams);
// --------------
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("Nashorn");
        engine.eval(script);
        Invocable inv = (Invocable) engine;
        inv.invokeFunction(functionToRun, scriptParams);
    }

    public static void listEngines() {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> engines = manager.getEngineFactories();

        for (ScriptEngineFactory engine : engines) {
            System.out.println(engine.getEngineName());
            System.out.println(engine.getEngineVersion());
            System.out.println(engine.getLanguageName());

            System.out.println("Short Names:");
            for (String names : engine.getNames()) {
                System.out.println(names);
            }
        }
    }
}
