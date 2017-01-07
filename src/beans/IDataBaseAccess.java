/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author bobmastrolilli
 */
public interface IDataBaseAccess 
{
    public int Connect(ConnectionOptions options);
    public int CountAll(String table);
    public ResultSet GetTables();
    public ResultSet SelectAll(String table);
    public ResultSet SelectAll(String table, String where);
    public List<Object> SelectFromRequest(String requete,String table);
    public Integer UpdateFromRequest(String requete) ;
    public void Close();
}
