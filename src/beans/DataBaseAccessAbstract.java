/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bobmastrolilli
 */
public class DataBaseAccessAbstract implements IDataBaseAccess
{
    protected  Connection myConnection;
    
    @Override
    public  int Connect(ConnectionOptions options) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ResultSet GetTables(){
        ResultSet rs = null; 
        try {
            DatabaseMetaData md = myConnection.getMetaData();
            rs = md.getTables(myConnection.getCatalog(),null,null,null);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public int CountAll(String table) {
        int nbel = -1;
        System.out.println("quelque chose il y a");
        try {
            PreparedStatement ps = myConnection.prepareStatement("SELECT COUNT(*) FROM " + table );
            ResultSet rs = ps.executeQuery();
            if(rs.next())
               {
                   System.out.println("quelque chose il y a");
                nbel = rs.getInt(1);
            }
        } 
        catch (SQLException ex) { System.out.println("Err. while CountAll()");System.out.println("sql = " + ex.getMessage()); }
        return nbel;
    }
    
    @Override
    public ResultSet SelectAll(String table) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = myConnection.prepareStatement("SELECT * FROM " + table + ";", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = ps.executeQuery();
        } 
        catch (SQLException ex) { System.out.println("Err. while SelectAll()"); }
        return rs;
    }
    
    @Override
    public ResultSet SelectAll(String from_statetment, String where_statement){
        ResultSet rs = null;
        try {
            PreparedStatement ps = myConnection.prepareStatement("SELECT * FROM " + from_statetment + " WHERE " + where_statement + ";", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = ps.executeQuery();
        } 
        catch (SQLException ex) { System.out.println("Err. while SelectAll()"); }
        return rs;
    }
 
    @Override
    public void Close() {
        try {
            myConnection.close();
            System.out.println("Connection to database closed succesfully!");
        } 
        catch (SQLException ex) { System.out.println("Err. while closing()"); }
    }

    @Override
    public List<Object> SelectFromRequest(String requete,String table) 
    {
        synchronized(this)
        {
            ResultSet rs = null;
            try {
                PreparedStatement ps = myConnection.prepareStatement(requete);
                rs = ps.executeQuery();
            } 
            catch (SQLException ex) { System.out.println("Err. while SelectAll()"); }
            return null;
        }
    }

       @Override
    public Integer UpdateFromRequest(String requete)
    {
        synchronized(this)
        {
            PreparedStatement p;
            Vector v = new Vector();

            try {
                p = myConnection.prepareStatement(requete);
                return p.executeUpdate();
            } catch (SQLException ex) {
                System.err.println("Erreur SQL ! [" + ex + "]");
            } catch (Exception ex) {
                System.err.println("Erreur ! [" + ex + "]");
            }
            return null;
        }
    }
    
}
