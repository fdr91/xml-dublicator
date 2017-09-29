package org.kusovf.dublicator;

public class Variable {

    private final String name;
    private final int start;
    private int value;

    public Variable(String name, int start) {
        this.name = name;
        this.value = this.start = start;

    }

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }


    public int getValue() {
        return value;
    }

    public void inc(int step) {
        value += step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (start != variable.start) return false;
        if (value != variable.value) return false;
        return name != null ? name.equals(variable.name) : variable.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + start;
        result = 31 * result + value;
        return result;
    }
}
