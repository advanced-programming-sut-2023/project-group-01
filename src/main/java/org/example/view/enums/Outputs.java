package org.example.view.enums;

public enum Outputs {

    Temp("sd");
    private String output;

    Outputs(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return this.output;
    }
}
