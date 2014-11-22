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
public class Drug extends DatabaseObject{    
    private String drug_name;
    private Double cost;
    private Date created_datetime;

    public String getDrug_name() {
        return drug_name;
    }

    public Double getCost() {
        return cost;
    }

    public Date getCreated_datetime() {
        return created_datetime;
    }

    public Drug() {
    }

    public Drug(String drug_name, Double cost, Date created_datetime) {
        this.drug_name = drug_name;
        this.cost = cost;
        this.created_datetime = created_datetime;
    }

    
}
