/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;


//import java.sql.Connection
import java.sql.Connection;

/**
 *
 * @author Administrator
 */
public abstract class DB {
    /*
    protected String url = "jdbc:derby://localhost:1527/";
    protected String db = "ISD";
    protected String dbUser = "root";
    protected String dbPassword = "root";
    protected String driver = "org.apache.derby.jdbc.ClientDriver";
    protected Connection connection = null;
    */
    
    protected String url;
    protected String db;
    protected String dbUser;
    protected String dbPassword;
    protected String driver;
    protected Connection connection;
    
    
    public DB() {
        this.url = "jdbc:derby://localhost:1527/";
        this.db = "ISD";
        this.dbUser = "root";
        this.dbPassword = "root";
        this.driver = "org.apache.derby.jdbc.ClientDriver";
        this.connection = null;
    }
    
    
    
    public DB(String url, String db, String dbuser, String dbpassword, String dbdriver) {
        this.url = url;
        this.db = db;
        this.dbUser = dbuser;
        this.dbPassword = dbpassword;
        this.driver = dbdriver;
        this.connection = null;
    }
    
    
}
