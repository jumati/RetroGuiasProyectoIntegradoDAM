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

    public void setPlatform_id(String platform_id) {
        this.platform_id = platform_id;
    }

    public String getPlatform_name() {
        return platform_name;
    }

    public Platforms(String platform_id, String platform_name) {
        this.platform_id = platform_id;
        this.platform_name = platform_name;
    }

    @Override
    public String toString() {
        return platform_name;
    }
}
