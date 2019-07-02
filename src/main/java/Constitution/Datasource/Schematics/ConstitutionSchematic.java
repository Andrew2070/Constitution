package Constitution.Datasource.Schematics;

import Constitution.Datasource.Bridge.BridgeSQL;

public class ConstitutionSchematic extends BaseSchematic {

    	  @Override
    	    public void initializeUpdates(BridgeSQL bridge) {
    	        updates.add(new DBUpdate("10.01.2019.1", "Add Updates Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "Updates (" +
    	                "id VARCHAR(20) NOT NULL," +
    	                "description VARCHAR(50) NOT NULL," +
    	                "PRIMARY KEY(id)" +
    	                ");"));
    	        
    	    /*/    updates.add(new DBUpdate("11.01.2019.1", "Add Groups Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "Groups (" +
    	        		"Name VARCHAR(20) NOT NULL," +
    	        		"Prefix VARCHAR(20)"
    	        		))
    	    }
    	    
    	    /*/
    	}
   }