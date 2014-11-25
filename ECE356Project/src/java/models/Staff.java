/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author stuart
 */
public class Staff {
    private String username;
    private String type;
    private Timestamp create_datetime;
    private Timestamp deleted_datetime;
    
    public Staff(String username, String type, Timestamp create_datetime, Timestamp deleted_datetime) {
        this.username = username;
        this.type = type;
        this.create_datetime = create_datetime;
        this.deleted_datetime = deleted_datetime;
    }
    
    public String get_username() {
        return username;
    }

    public String get_type() {
        return type;
    }

    public Timestamp getCreate_datetime() {
        return create_datetime;
    }
    
    public Timestamp getDeleted_datetime() {
        return deleted_datetime;
    }
}

