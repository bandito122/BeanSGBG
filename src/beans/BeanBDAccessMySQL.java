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
                System.out.println("URL = " + url);
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

    public String findPasswpordByLoginHashCode(String user) 
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
    public int traiteRequeteInsertLogin(String login,String pass) throws SQLException
    {
        PreparedStatement p  ;
        Vector v = new Vector();
        test=0;
         try{
             p = myConnection.prepareStatement(" insert into USERS values(?,?)");
             p.setString(1, login);
             p.setString(2,pass);
             p.executeUpdate();
             test=1;
        }
        catch (SQLException ex) { System.err.println("Erreur SQL ! [" + ex + "]");  }
        catch (Exception ex) { System.err.println("Erreur ! [" + ex + "]"); }
         return test;
    }
        public List<Object> getListSearchGoods(String type) throws SQLException
    {
        List<Object> res = new ArrayList<Object>();
        PreparedStatement p = null  ;
        
        p = myConnection.prepareStatement("SELECT numSerie,typeGeneral,typePrecis,typeappareils.marque,appareils.prixvente,etatsituation FROM Appareils,typeappareils  where Appareils.numSerie = Typeappareils.numSérie and typeappareils.typeprecis =? and appareils.etatPaiement = 0 ; ");
        p.setString(1, type);

             ResultSet rs = p.executeQuery();
             int cpt = 0;
             Integer r = null;
             while(rs.next())
             {
                 cpt ++ ;
                 Vector v = new Vector();
                 v.add(rs.getInt(1)); 
                 v.add(rs.getString(2)); 
                 v.add(rs.getString(3)); 
                 v.add(rs.getString(4)); 
                 v.add(rs.getFloat(5)); 
                 v.add(rs.getInt(6)); 
    
                 System.out.println("Vector " + v ) ;
                 res.add(v);
             }
             if(cpt>0)
             {
                System.out.println("Quelque chose!!!");
                System.out.println("res " + res ) ;
                return res ;
             }
             else
             {
                 return res ;
             }
        }
    public boolean UpdateAppareilEtat(int etat,int numSerie) throws SQLException
    {
        PreparedStatement p  ;
        Vector v = new Vector();
        
         try{
             p = myConnection.prepareStatement(" update APPAREILS set etatpaiement=? where numSerie = ? and etatpaiement=0");
             p.setInt(1, etat);
             p.setInt(2, numSerie);
             int test = p.executeUpdate();
             if (test ==1)
             {
                 return true;
             }
             else
                 return false;
             
             
             
            
        }
        catch (SQLException ex) { System.err.println("Erreur SQL ! [" + ex + "]"); }
        catch (Exception ex) { System.err.println("Erreur ! [" + ex + "]"); }
         return false;
    }
    public float findFinalPriceBySerialNum(int NumSerie)
    {
        float s = 0 ;
        PreparedStatement p  ;
        System.out.println("Recherche dans la BD....");
        try{
             p = myConnection.prepareStatement("select prixvente from appareils where numserie=? ");
             p.setInt(1, NumSerie);
             ResultSet rs = p.executeQuery();
             int cpt = 0;
             while(rs.next())
             {
                 cpt ++ ;
                 s=rs.getFloat(1); 
                 System.out.println("prix = " + s );
             }
             if(cpt>0)
             {
                return s ;
             }
             else
             {
                 return 0 ;
             }
        }
        catch (SQLException ex) { System.err.println("Erreur SQL ! [" + ex + "]"); }
        catch (Exception ex) { System.err.println("Erreur ! [" + ex + "]"); }
        
        return 0;
    }
    public boolean traiteRequeteInsertClient(String NomClient,String adresseClient) throws SQLException
    {
        PreparedStatement p  ;
        Vector v = new Vector();
        boolean bool=false;
         try{
             p = myConnection.prepareStatement(" insert into CLIENTS values(null,?,?)");
             p.setString(1, NomClient);
             p.setString(2,adresseClient);
             p.executeUpdate();
             
             bool=true;
        }
        catch (SQLException ex) { System.err.println("Erreur SQL ! [" + ex + "]");  }
        catch (Exception ex) { System.err.println("Erreur ! [" + ex + "]"); }
         return bool;
    }
    public int FindIdClientByName(String NomClient) throws SQLException
    {
        System.out.println("NOm client= " + NomClient);
        synchronized(this)
        {
            int s =0;
            PreparedStatement p;
            System.out.println("Recherche dans la BD....");
            try {
                p = myConnection.prepareStatement("select IdClient from CLIENTS where nomClient=? ");
                p.setString(1, NomClient);
                ResultSet rs = p.executeQuery();
                int cpt = 0;
                while (rs.next()) {
                    cpt++;
                    s = rs.getInt(1);
                    System.out.println("idClient = " + s);
                }
                if (cpt > 0) {
                    return s;
                } else {
                    return 0;
                }
            } catch (SQLException ex) {
                System.err.println("Erreur SQL ! [" + ex + "]");
            } catch (Exception ex) {
                System.err.println("Erreur ! [" + ex + "]");
            }

            return 0;
        }
    }
    public String FindNameClientByidClient(int idclient) throws SQLException
    {
        System.out.println("id client= " + idclient);
        synchronized(this)
        {
            String s =null;
            PreparedStatement p;
            System.out.println("Recherche dans la BD....");
            try {
                p = myConnection.prepareStatement("select nomclient from CLIENTS where idclient=? ");
                p.setInt(1, idclient);
                ResultSet rs = p.executeQuery();
                int cpt = 0;
                while (rs.next()) {
                    cpt++;
                    s = rs.getString(1);
                    System.out.println("nomclient = " + s);
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
    public boolean traiteRequeteInsertFacture(int numSerie,float Prix,int idClient,int etatPaiement) throws SQLException
    {
        System.out.println("NUM SERIE = " + numSerie);
        System.out.println("PRIX = " + Prix);
        System.out.println("idClient" + idClient);
        System.out.println("etatPaiement= " + etatPaiement);
        PreparedStatement p  ;
        Vector v = new Vector();
        boolean bool=false;
         try{
             p = myConnection.prepareStatement(" insert into FACTURES values(null,?,?,?,?)");
             p.setInt(1, numSerie);
             p.setFloat(2,Prix);
             p.setInt(3, idClient);
             p.setInt(4, etatPaiement);
             p.executeUpdate();
             test=1;
             bool=true;
        }
        catch (SQLException ex) { System.err.println("Erreur SQL ! [" + ex + "]");  }
        catch (Exception ex) { System.err.println("Erreur ! [" + ex + "]"); }
         return bool;
    }
    public Vector<Object> getFactureByNumSerie(int numSerie) throws SQLException
    {
        Vector v = null;
        PreparedStatement p = null  ;
        
        p = myConnection.prepareStatement("SELECT numserie,prix,idclient FROM FACTURES where numSerie=? ");
        p.setInt(1, numSerie);

             ResultSet rs = p.executeQuery();
             int cpt = 0;
             Integer r = null;
             while(rs.next())
             {
                 cpt ++ ;
                 v = new Vector();
                 v.add(rs.getInt(1)); 
                 v.add(rs.getFloat(2));
                 v.add(FindNameClientByidClient(rs.getInt(3))); 

             }
             if(cpt>0)
             {
                System.out.println("Quelque chose!!!");

                return v ;
             }
             else
             {
                 return null ;
             }
        }
     public List<Object> getFacturesByIds(Vector<Object> id) throws SQLException
    {
        List<Object> res = new ArrayList<Object>();
        PreparedStatement p = null  ;
        
        System.out.println("VentesRealiseeSession SIZE = " + id.size());
        for(int i=0;i<id.size();i++)
        {
            Vector v = new Vector();
            
            System.out.println("getFactureByNumSerie((int)id.get(i)" + getFactureByNumSerie((int)id.get(i)));
            v=getFactureByNumSerie((int)id.get(i));
            res.add(v);
        }
        return res;
    }
    

 
}
