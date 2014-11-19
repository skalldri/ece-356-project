/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author Bo
 */
public class Visit {
    private String doctor_username;
    private Timestamp start_datetime;
    private Timestamp end_datetime;
    private String health_card;
    private String diagnosis;
    private String procedure_description;
    private Double procedure_cost;
    private String scheduling_of_treatment;
    private Timestamp created_datetime;

    public Visit(String doctor_username, Timestamp start_datetime, Timestamp end_datetime, String health_card, String diagnosis, String procedure_description, Double procedure_cost, String scheduling_of_treatment, Timestamp created_datetime) {
        this.doctor_username = doctor_username;
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.health_card = health_card;
        this.diagnosis = diagnosis;
        this.procedure_description = procedure_description;
        this.procedure_cost = procedure_cost;
        this.scheduling_of_treatment = scheduling_of_treatment;
        this.created_datetime = created_datetime;
    }
    
    
    public String getDoctor_username() {
        return doctor_username;
    }

    public Timestamp getStart_datetime() {
        return start_datetime;
    }

    public Timestamp getEnd_datetime() {
        return end_datetime;
    }

    public String getHealth_card() {
        return health_card;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getProcedure_description() {
        return procedure_description;
    }

    public Double getProcedure_cost() {
        return procedure_cost;
    }

    public String getScheduling_of_treatment() {
        return scheduling_of_treatment;
    }

    public Timestamp getCreated_datetime() {
        return created_datetime;
    }    
}
