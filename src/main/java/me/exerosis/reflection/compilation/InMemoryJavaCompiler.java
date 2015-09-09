package me.exerosis.reflection.compilation;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.Collections;

public class InMemoryJavaCompiler {
    static JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

    public static Class<?> compile(String className, String sourceCodeInText) throws Exception {

        SourceCode sourceCode = new SourceCode(className, sourceCodeInText);
        CompiledCode compiledCode = new CompiledCode(className);
        Iterable<? extends JavaFileObject> compilationUnits = Collections.singletonList(sourceCode);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        DynamicClassLoader cl = new DynamicClassLoader(classLoader);
        ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(javac.getStandardFileManager(null, null, null), compiledCode, cl);
        JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, null, null, null, compilationUnits);
        task.call();

        return cl.loadClass(className);
    }


}
