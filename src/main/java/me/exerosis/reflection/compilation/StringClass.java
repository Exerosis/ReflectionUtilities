package me.exerosis.reflection.compilation;


import java.io.File;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class StringClass {
    private String code = "";
    private ArrayList<String> imports;

    public StringClass() {
        imports = new ArrayList<>();
    }

    public void importClass(ArrayList<String> imports, String code) {
        this.imports.addAll(imports);
        append(code);
    }

    public void addImport(String importLocation) {
        imports.add(importLocation);
    }

    public void removeImport(String importLocation) {
        if (imports.contains(importLocation))
            imports.remove(importLocation);
    }

    public boolean containsImport(String importLocation) {
        return imports.contains(importLocation);
    }

    public void removeLine(String line) {
        code.replace(line, "");
    }

    public void replaceLine(String line, String replacementLine) {
        code.replace(line, replacementLine);
    }

    public Class<?> compile() {
        StringBuilder finalClass = new StringBuilder();
        finalClass.append("package src.classes;\n");
        finalClass.append("import me.exerosis.sql.database.management.table.SQLResult;\n");
        for (String importLocation : imports)
            finalClass.append("import ").append(importLocation).append(";\n");
        finalClass.append("public class Environment {\n");
        finalClass.append("		public static void runNewThread(Object object, SQLResult result) {\n");
        finalClass.append(code);
        finalClass.append("		}\n");
        finalClass.append("}\n");
        try {
            System.out.println(finalClass.toString());
            new File("src/classes/Environment.java").createNewFile();
            new File("src/classes").mkdirs();
            return InMemoryJavaCompiler.compile("src.classes.Environment", finalClass.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void append(String newLine) {
        code = code + newLine + "\n";
    }

    public ArrayList<String> getImports() {
        return imports;
    }

    public String getCode() {
        return code;
    }
}
