package com.study.movieland.entity;

public enum Role {

    GUEST("GUEST"),
    USER("USER"),
    ADMIN("ADMIN");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Role getByName(String name) {
        for (Role role : values()) {
            if (role.name.equals(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("The role is not found: " + name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
