package org.gfg;

public class OS {

    private String name;

    public OS() { // Tight Coupling
        name = "Windows";
    }

    public OS(String name) { // loose coupling
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OS{" +
                "name='" + name + '\'' +
                '}';
    }
}
