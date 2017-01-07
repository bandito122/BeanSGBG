/*
 * To change this template, choose Tools | Templates
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
public class BeanBDAccessMySQL extends DataBaseAccessAbstract {

    private String host;
    private String port;
    private String db;
    private String user;
    private String passwd;
    int test = 0;

    public BeanBDAccessMySQL() {
    }

    @Override
    public int Connect(ConnectionOptions options) 
    {
        synchronized(this)
        {
            try 
            {
                test = 0;
                this.host = (String) options.getOption("host");
                this.port = (String) options.getOption("port");
                this.db = (String) options.getOption("database");
                this.user = (String) options.getOption("user");
                this.passwd = (String) options.getOption("passwd");
                String url = "jdbc:mysql://" + host + ":" + port + "/" + db;

                Class.forName("com.mysql.jdbc.Driver");
                myConnection = (Connection) DriverManager.getConnection(url, user, passwd);
                System.out.println("Connection to MySQL database success!");
                test = 1;
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(BeanBDAccessMySQL.class.getName()).log(Level.SEVERE, null, ex);
                test = 0;
            }
            return test;
        }
    }

    public String findPasswpordByLogin(String user) 
    {
        synchronized(this)
        {
            String s = null;
            PreparedStatement p;
            System.out.println("Recherche dans la BD....");
            try {
                p = myConnection.prepareStatement("select pwd from users where login=? ");
                p.setString(1, user);
                ResultSet rs = p.executeQuery();
                int cpt = 0;
                while (rs.next()) {
                    cpt++;
                    s = rs.getString(1);
                    System.out.println("Password = " + s);
                }
                if (cpt > 0) {
                    return s;
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                System.err.println("Erreur SQL ! [" + ex + "]");
            } catch (Exception ex) {
                System.err.println("Erreur ! [" + ex + "]");
            }

            return null;
        }
    }

    @Override
    public List<Object> SelectFromRequest(String requete, String table) 
    {
        synchronized(this)
        {
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
                    if (table.equals("APPAREILS".toLowerCase())) {
                        v.add(rs.getInt(1)); //NumSerie
                        try {
                            v.add(rs.getString(2)); //TypeGeneral
                            v.add(rs.getFloat(3)); //Prix de vente
                            v.add(rs.getInt(4)); //etatsituation
                            v.add(rs.getInt(5)); //etatpaiement
                            v.add(rs.getInt(6));  // typeachat
                        } catch (Exception ex) {
                            System.out.println("C'est un count...");
                        }
                        {

                        }
                    } else if (table.equals("TYPEAPPAREILS".toLowerCase())) {
                        v.add(rs.getString(1)); //NumSerie
                        try {
                            v.add(rs.getString(2)); //TypeGeneral

                            v.add(rs.getString(3)); //Prix de vente
                            v.add(rs.getInt(4)); //etatsituation
                            v.add(rs.getInt(5)); //etatpaiement
                            v.add(rs.getInt(6));  // typeachat
                            v.add(rs.getInt(7));  // typeachat
                        } catch (Exception ex) {System.out.println("C'est un count...");
                        }

                    } else if (table.equals("FOURNISSEURS".toLowerCase())) {
                        v.add(rs.getInt(1)); //NumSerie
                        try {
                            v.add(rs.getString(2)); //TypeGeneral
                        }catch (Exception ex) {System.out.println("C'est un count...");}

                    } else if (table.equals("CLIENTS".toLowerCase())) {
                        v.add(rs.getInt(1)); //NumSerie
                        try{
                        v.add(rs.getString(2)); //TypeGeneral
                        v.add(rs.getString(3)); //TypeGeneral
                        }catch (Exception ex) {System.out.println("C'est un count...");}
                    } else if (table.equals("VEHICULES".toLowerCase())) {
                        v.add(rs.getInt(1)); //NumSerie
                        try{
                        v.add(rs.getString(2)); //TypeGeneral
                        v.add(rs.getString(3)); //TypeGeneral
                        }catch (Exception ex) {System.out.println("C'est un count...");}
                    } else if (table.equals("PERSONNEL".toLowerCase())) {
                        v.add(rs.getInt(1)); //NumSerie
                        try{
                        v.add(rs.getString(2)); //TypeGeneral
                        }catch (Exception ex) {System.out.println("C'est un count...");}
                    } else if (table.equals("ITEMS_FACTURE".toLowerCase())) {
                        v.add(rs.getInt(1)); //NumSerie
                        try{
                        v.add(rs.getInt(2)); //NumSerie
                        v.add(rs.getInt(3)); //NumSerie
                        }catch (Exception ex) {System.out.println("C'est un count...");}
                    } else if (table.equals("FACTURES".toLowerCase())) {
                        v.add(rs.getInt(1)); //NumSerie
                        try{

                        v.add(rs.getInt(2)); //NumSerie
                        v.add(rs.getInt(3)); //NumSerie
                        v.add(rs.getInt(3)); //NumSerie
                        }catch (Exception ex) {System.out.println("C'est un count...");}
                    } else if (table.equals("SALAIRES".toLowerCase())) {
                        v.add(rs.getInt(1)); //NumSerie
                        try{
                        v.add(rs.getInt(2)); //NumSerie
                        v.add(rs.getInt(3)); //NumSerie
                        }catch (Exception ex) {System.out.println("C'est un count...");}
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

 
}
