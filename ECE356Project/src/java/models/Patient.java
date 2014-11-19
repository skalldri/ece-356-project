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
public class Patient {
    private String health_card;
    private String name;
    private String address;
    private String phone_number;
    private String sin;
    private String default_doctor_username;
    private String patient_health;
    private Timestamp create_datetime;
    private String comments;
    private String password;
    
    public Patient() {
        this.health_card = null;
        this.name = null;
        this.address = null;
        this.phone_number = null;
        this.sin = null;
        this.default_doctor_username = null;
        this.patient_health = null;
        this.create_datetime = null;
        this.comments = null;
        this.password = null;
    }
    
    public Patient(String health_card, String name, String address, String phone_number, String sin, String default_doctor_username, String patient_health, Timestamp create_datetime, String comments, String password) {
        this.health_card = health_card;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.sin = sin;
        this.default_doctor_username = default_doctor_username;
        this.patient_health = patient_health;
        this.create_datetime = create_datetime;
        this.comments = comments;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public String getHealth_card() {
        return health_card;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getSin() {
        return sin;
    }

    public String getDefault_doctor_username() {
        return default_doctor_username;
    }

    public String getPatient_health() {
        return patient_health;
    }

    public Timestamp getCreate_datetime() {
        return create_datetime;
    }

    public String getComments() {
        return comments;
    }
    
}
