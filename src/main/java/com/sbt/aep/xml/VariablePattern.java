package com.sbt.aep.xml;

/**
 * @author Ivan
 * @date 30.09.2017.
 */
public class VariablePattern {

    private final static String VARIABLE_PATTERN = "\\$\\{%s(=\\d+)?\\}";

    public String getPattern(Variable variable) {
        return getPattern(variable.getName());
    }

    public static String getPattern(String name) {
        return String.format(VARIABLE_PATTERN, name);
    }
}
