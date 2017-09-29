package org.kusovf.dublicator;

public class VariablePattern {

    private final static String VARIABLE_PATTERN = "\\$\\{%s(=\\d+)?\\}";

    public String getPattern(Variable variable) {
        return getPattern(variable.getName());
    }

    public static String getPattern(String name) {
        return String.format(VARIABLE_PATTERN, name);
    }
}
