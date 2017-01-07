/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Bandito
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //TEST
        DataBaseAccessFactory dbaf = DataBaseAccessFactory.getInstance();
        IDataBaseAccess db = dbaf.getDataBaseAcces("Oracle");

        ConnectionOptions options = new ConnectionOptions();
        options.addOption("host", "127.0.0.1");
        options.addOption("port", "1521");
        options.addOption("database", "BD_JOURNALDEBORD");
        options.addOption("user", "bob");
        options.addOption("passwd", "bob"); 
        db.Connect(options);
        
        System.out.println("Tables : " + db.CountAll("INTERVENANTS"));
        
    }
    
}
