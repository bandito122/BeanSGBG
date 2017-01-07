/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author bobmastrolilli
 */
public class DataBaseAccessFactory {
    
    private static DataBaseAccessFactory myInstance;
    
    public static DataBaseAccessFactory getInstance(){
        if(myInstance == null)
            myInstance = new DataBaseAccessFactory();
        return myInstance;    
    }
    
    public IDataBaseAccess getDataBaseAcces(String db){
     
        if(db.equals("MySQL")){
            return new BeanBDAccessMySQL();
        }
        if(db.equals("Oracle")){
            return new BeanBDAccessOracle();
        }
        return null;
    }
}
