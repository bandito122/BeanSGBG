/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author bobmastrolilli
 */
public class DataBaseAccessCSV extends DataBaseAccessAbstract{
    private String directry; 
    private int test=0;
    public DataBaseAccessCSV(){
    }
    
    @Override
    public int Connect(ConnectionOptions options) 
    {
        try 
        {
            
            this.directry = (String) options.getOption("directory");
            
            Properties props = new java.util.Properties();
            props.put("separator", ",");
            props.put("suppressHeaders", "false");
            props.put("fileExtension", ".csv");

            Class.forName("jstels.jdbc.csv.CsvDriver2");
            myConnection = DriverManager.getConnection("jdbc:jstels:csv:" + directry, props); 
            test=1;
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Err. whil Connection()");
            test=0;
        }
        return test;
    }
}
