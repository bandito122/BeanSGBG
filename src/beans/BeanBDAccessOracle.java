/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bobmastrolilli
 */
public class BeanBDAccessOracle extends DataBaseAccessAbstract
{
    private String host;
    private String port;
    private String db;
    private String user;
    private String passwd;
    private int    test ; 
    public BeanBDAccessOracle() {
    }
    
    
    
    @Override
    public int Connect(ConnectionOptions options)
    {
        try {
            test=0;
            this.host = (String) options.getOption("host");
            this.port = (String) options.getOption("port");
            this.db = (String) options.getOption("database");
            this.user = (String) options.getOption("user");
            this.passwd = (String) options.getOption("passwd");
            String url = "jdbc:oracle:thin:@" + host + ":" + port + "/orcl "  ;
            System.out.println("String = " + url);
            //jdbc:oracle:thin:@myserver:1521/XE
            Class.forName("oracle.jdbc.driver.OracleDriver");
            myConnection = (Connection)DriverManager.getConnection(
					url, user,
					passwd);
            
            
           
            myConnection = (Connection) DriverManager.getConnection(url, user, passwd);
            System.out.println("Connection to MySQL database success!");
            test=1;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BeanBDAccessOracle.class.getName()).log(Level.WARNING, null, ex);
            test=0;
        }
        return test;
    }
       @Override
    public List<Object> SelectFromRequest(String requete, String table) {
        List<Object> res = new ArrayList<Object>();
        PreparedStatement p = null;
        table = table.toLowerCase();
        try {

            p = myConnection.prepareStatement(requete);

            ResultSet rs = p.executeQuery();
            int cpt = 0;
            Integer r = null;
            while (rs.next()) {
                cpt++;
                Vector v = new Vector();
                if (table.equals("ACTIVITES".toLowerCase())) {
                    v.add(rs.getInt(1)); //NumSerie

                    try {
                        v.add(rs.getInt(2)); 
                        v.add(rs.getString(3)); 
                        v.add(rs.getString(4)); 
                        v.add(rs.getString(5)); 
                        v.add(rs.getInt(6));
                        v.add(rs.getInt(7));
                    } catch (Exception ex) 
                    {
                        System.out.println("C'est un count...");
                    }
                    {

                    }
                } else if (table.equals("INTERVENANTS".toLowerCase())) {
                    v.add(rs.getInt(1)); //NumSerie
                    try {
                        v.add(rs.getString(2)); 

                        v.add(rs.getString(3)); 
                        v.add(rs.getInt(4)); 
                        
                    } catch (Exception ex) {System.out.println("C'est un count...");
                    }

                } 

                System.out.println("Vector " + v);
                res.add(v);
            }
            if (cpt > 0) {
                System.out.println("Quelque chose!!!");
                System.out.println("res " + res);
                return res;
            } else {
                return res;
            }
        } catch (SQLException ex) {
            System.err.println("Erreur SQL ! [" + ex + "]");
        } catch (Exception ex) {
            System.err.println("Erreur ! [" + ex + "]");
        }
        return null;
    }
}
