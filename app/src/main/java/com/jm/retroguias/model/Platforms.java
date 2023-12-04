package com.jm.retroguias.model;

public class Platforms {
    private String platform_id;
    private String platform_name;

    public Platforms() {}

    public Platforms(String company_name) {
        this.platform_name = company_name;
    }

    public String getPlatform_id() {
        return platform_id;
    }

    public String getPlatform_name() {
        return platform_name;
    }

    public void setPlatform_name(String company_name) {
        this.platform_name = company_name;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "platform_id='" + platform_id + '\'' +
                ", platform_name='" + platform_name + '\'' +
                '}';
    }
}
