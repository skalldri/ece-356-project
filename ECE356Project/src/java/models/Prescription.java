/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author Bo
 */
public class Prescription {
    private String doctor_username;
    private String health_card;
    private String drug_name;
    private Integer refills;
    private String start_datetime;
    private String end_datetime;

    public String getDoctor_username() {
        return doctor_username;
    }

    public String getHealth_card() {
        return health_card;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public Integer getRefills() {
        return refills;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }

    public Prescription() {
    }

    public Prescription(String doctor_username, String health_card, String drug_name, Integer refills, String start_datetime, String end_datetime) {
        this.doctor_username = doctor_username;
        this.health_card = health_card;
        this.drug_name = drug_name;
        this.refills = refills;
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
    }
    
    
}
