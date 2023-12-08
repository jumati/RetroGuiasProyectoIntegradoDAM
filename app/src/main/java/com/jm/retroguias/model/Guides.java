package com.jm.retroguias.model;

import java.io.File;

public class Guides {
    private String guide_id;
    private String guide_name;
    private String platform_id;
    private String company_id;
    private String guide_pdf;

    public Guides() {}

    public Guides(String guide_id, String guide_name, String platform_id, String company_id)
    {
        this.guide_id = guide_id;
        this.guide_name = guide_name;
        this.platform_id = platform_id;
        this.company_id = company_id;
        //this.guide_pdf = guide_pdf;
    }

    public String getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(String guide_id) {
        this.guide_id = guide_id;
    }

    public String getGuide_name() {
        return guide_name;
    }

    public void setGuide_name(String guide_name) {
        this.guide_name = guide_name;
    }

    public String getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(String platform_id) {
        this.platform_id = platform_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getGuide_pdf() {
        return guide_pdf;
    }

    public void setGuide_pdf(String guide_pdf) {
        this.guide_pdf = guide_pdf;
    }

    @Override
    public String toString() {
        return "Guide{" +
                "guide_id='" + guide_id + '\'' +
                ", guide_name='" + guide_name + '\'' +
                ", platform_id='" + platform_id + '\'' +
                ", company_id='" + company_id + '\'' +
                /* ", guide_pdf=" + guide_pdf + */
                '}';
    }
}
