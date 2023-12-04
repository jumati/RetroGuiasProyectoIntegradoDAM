package com.jm.retroguias.model;

public class Companies {
    private String company_id;
    private String company_name;

    public Companies() {}

    public Companies(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String toString() {
        return "Company{" +
                "company_id='" + company_id + '\'' +
                ", company_name='" + company_name + '\'' +
                '}';
    }
}
