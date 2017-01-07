/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author bobmastrolilli
 */
abstract class BeanAbstract 
{
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    private String sampleProperty;
    private PropertyChangeSupport propertySupport;
    protected Class leDriver;
    protected ResultSet results;
    public String dataBase;
    public String driverJdbc;
    private Connection con;
    protected java.sql.Statement instruc;
    private String user;
    private String password;
    private String requete;
    
    
}
