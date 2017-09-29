package org.kusovf.dublicator;

import org.kusovf.dublicator.interfaces.Replacer;

import java.util.*;

public class VariableReplacer implements Replacer {
    private final List<Variable> vars;

    VariableReplacer(Collection<Variable> variables) {
        this.vars = new ArrayList<>();
        vars.addAll(variables);
        Collections.sort(vars, new Comparator<Variable>() {
            @Override
            public int compare(Variable o1, Variable o2) {
                return o2.getName().length() - o1.getName().length();
            }
        });
    }

    @Override
    public String process(String string) {
        String result = string;
        for (Variable variable : vars) {
            result = result.replaceAll(VariablePattern.getPattern(variable.getName()), String.valueOf(variable.getValue()));
        }
        return result;
    }
}
