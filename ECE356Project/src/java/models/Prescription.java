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
    private Date start_datetime;
    private Date end_datetime;
    private Date created_datetime;

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

    public Date getStart_datetime() {
        return start_datetime;
    }

    public Date getEnd_datetime() {
        return end_datetime;
    }

    public Date getCreated_datetime() {
        return created_datetime;
    }

    public Prescription() {
    }

    public Prescription(String doctor_username, String health_card, String drug_name, Integer refills, Date start_datetime, Date end_datetime, Date created_datetime) {
        this.doctor_username = doctor_username;
        this.health_card = health_card;
        this.drug_name = drug_name;
        this.refills = refills;
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.created_datetime = created_datetime;
    }
    
    
}
