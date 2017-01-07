/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.HashMap;

/**
 *
 * @author bobmastrolilli
 */
public class ConnectionOptions {
    private HashMap options;
    
    public ConnectionOptions(){
        options = new HashMap();
    }
    
    public void addOption(String key, Object value){
        options.put(key, value);
    }
    
    public Object getOption(String key){
        return options.get(key);
    }
}
