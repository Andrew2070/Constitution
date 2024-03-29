/*******************************************************************************
 * Copyright (C) July/14/2019, Andrew2070
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement:
 *    This product includes software developed by Andrew2070.
 * 
 * 4. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package constitution.datasource.schematics;

import constitution.datasource.bridge.BridgeSQL;

public class ConstitutionSchematic extends BaseSchematic {

    	  	@Override
    	    public void initializeUpdates(BridgeSQL bridge) {
    	        updates.add(new DBUpdate("07.04.2019.1", "Add Updates Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "Updates ("
    	               + "id VARCHAR(20) NOT NULL,"
    	               + "description VARCHAR(50) NOT NULL,"
    	               + "PRIMARY KEY(id)"
    	               + ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add Groups Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "Groups ("
    	        		+ "name VARCHAR(50) NOT NULL," 
    	        		+ "rank INT NOT NULL,"
    	        		+ "description VARCHAR(50) NOT NULL,"
    	        		+ "prefix VARCHAR(50) NOT NULL,"
    	        		+ "suffix VARCHAR(50) NOT NULL," 
    	        		+ "permissions VARCHAR(100) NOT NULL,"
    	        		+ "meta VARCHAR(50) NOT NULL,"
    	        		+ "PRIMARY KEY(name),"
    	        		+ "FOREIGN KEY(uuid) REFERENCES " + bridge.prefix + "Users(uuid) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add GroupsToParents Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "GroupsToParents ("
    	        		+ "group VARCHAR(50) NOT NULL, "
    	        		+ "parent VARCHAR(50) NOT NULL, "
    	        		+ "PRIMARY KEY(group, parent),"
    	        		+ "FOREIGN KEY(group) REFERENCES " + bridge.prefix + "Groups(name) ON DELETE CASCADE ON UPDATE CASCADE,"
    	        		+ "FOREIGN KEY(parent) REFERENCES " + bridge.prefix + "Groups(name) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add UsersToGroups Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "UsersToGroups ("
    	        		+ "uuid CHAR(36) NOT NULL, "
    	        		+ "group VARCHAR(50) NOT NULL, "
    	        		+ "PRIMARY KEY(uuid, group)"
    	        		+ "FOREIGN KEY(parent) REFERENCES " + bridge.prefix + "Users(uuid) ON DELETE CASCADE ON UPDATE CASCADE,"
    	        		+ "FOREIGN KEY(group) REFERENCES " + bridge.prefix + "Groups(name) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add Users Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "Users ("
    	        		+ "uuid CHAR(36) NOT NULL," 
    	        		+ "userName VARCHAR(32) NOT NULL,"
    	        		+ "lastName VARCHAR(50) NOT NULL,"
    	        		+ "nickName VARCHAR(50) NOT NULL,"
    	        		+ "prefix VARCHAR(100) NOT NULL,"
    	        		+ "suffix VARCHAR(100) NOT NULL,"
    	        		+ "dominantGroup VARCHAR(50) NOT NULL,"
    	        		+ "balance VARCHAR(100) NOT NULL,"
    	        		+ "joinDate BIGINT NOT NULL," 
    	        		+ "lastOnline BIGINT NOT NULL,"
    	        		+ "lastActivity VARCHAR(100) NOT NULL,"
    	        		+ "ipAddress VARCHAR(100) NOT NULL,"
    	        		+ "location VARCHAR(100) NOT NULL,"
    	        		+ "dimension INT NOT NULL,"
    	        		+ "operator BOOLEAN NOT NULL,"
    	        		+ "fakePlayer BOOLEAN NOT NULL,"
    	        		+ "invincible VARCHAR(100) NOT NULL,"
    	        		+ "flight BOOLEAN,"
    	        		+ "creative BOOLEAN,"
    	        		+ "health VARCHAR(100) NOT NULL,"
    	        		+ "xpTotal INTEGER(1000) NOT NULL,"
    	        		+ "permissions VARCHAR(100) NOT NULL,"
    	        		+ "meta VARCHAR(100) NOT NULL,"
    	        		+ "PRIMARY KEY(uuid, userName),"
    	        		+ ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add 'Homes' table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "Homes(" 
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + "," 
    	        		+ "uuid CHAR(36) NOT NULL,"
    	        		+ "player VARCHAR(36) NOT NULL,"
    	        		+ "name VARCHAR(30) NOT NULL,"
    	        		+ "dim INT NOT NULL,"
    	                + "x FLOAT NOT NULL,"
    	                + "y FLOAT NOT NULL,"
    	                + "z FLOAT NOT NULL,"
    	                + "yaw FLOAT NOT NULL,"
    	                + "pitch FLOAT NOT NULL,"
    	                + "PRIMARY KEY(uuid, id, name),"
    	                + "FOREIGN KEY(uuid, player) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	                + ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add 'Teleports' table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "Teleports(" 
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + "," 
    	        		+ "uuid1 CHAR(36) NOT NULL,"
    	        		+ "player1 VARCHAR(36) NOT NULL,"
    	        		+ "dim1 INT NOT NULL,"
    	                + "x1 FLOAT NOT NULL,"
    	                + "y1 FLOAT NOT NULL,"
    	                + "z1 FLOAT NOT NULL,"
    	                + "yaw1 FLOAT NOT NULL,"
    	                + "pitch1 FLOAT NOT NULL,"
    	        		+ "uuid2 CHAR(36) NOT NULL,"
    	        		+ "player2 VARCHAR(36) NOT NULL,"
    	        		+ "dim2 INT NOT NULL,"
    	                + "x2 FLOAT NOT NULL,"
    	                + "y2 FLOAT NOT NULL,"
    	                + "z2 FLOAT NOT NULL,"
    	                + "yaw2 FLOAT NOT NULL,"
    	                + "pitch2 FLOAT NOT NULL,"
    	                + "PRIMARY KEY(id),"
    	                + "FOREIGN KEY(uuid1, player1) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	                + "FOREIGN KEY(uuid2, player2) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	                + ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add 'Warps' table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "Warps(" 
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + "," 
    	        		+ "name VARCHAR(32) NOT NULL,"
    	        		+ "dim INT NOT NULL,"
    	                + "x FLOAT NOT NULL,"
    	                + "y FLOAT NOT NULL,"
    	                + "z FLOAT NOT NULL,"
    	                + "yaw FLOAT NOT NULL,"
    	                + "pitch FLOAT NOT NULL,"
    	                + "PRIMARY KEY(id, name),"
    	                + ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add BanManagerHistory Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "BanManagerHistory ("
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + ","
    	        		+ "uuid CHAR(36) NOT NULL," 
    	        		+ "userName VARCHAR(32) NOT NULL,"
    	        		+ "warns INTEGER NOT NULL,"
    	        		+ "kicks INTEGER NOT NULL,"
    	        		+ "mutes INTEGER NOT NULL,"
    	        		+ "bans INTEGER NOT NULL,"
    	        		+ "PRIMARY KEY(id, uuid),"
    	        		+ "FOREIGN KEY(uuid, userName) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add BanManagerWarnings Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "BanManagerWarnings ("
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + ","
    	        		+ "uuid CHAR(36) NOT NULL," 
    	        		+ "userName VARCHAR(32) NOT NULL,"
    	        		+ "reason VARCHAR(50) NOT NULL,"
    	        		+ "staffID CHAR(36) NOT NULL,"
    	        		+ "occasion BIGINT NOT NULL,"
    	        		+ "location VARCHAR(36) NOT NULL,"
    	        		+ "PRIMARY KEY(id),"
    	        		+ "FOREIGN KEY(uuid, userName) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ "FOREIGN KEY(staffID) REFERENCES " + bridge.prefix + "Users(uuid) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add BanManagerKicks Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "BanManagerKicks ("
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + ","
    	        		+ "uuid CHAR(36) NOT NULL,"
    	        		+ "userName VARCHAR(32) NOT NULL,"
    	        		+ "reason VARCHAR(50) NOT NULL,"
    	        		+ "staffID CHAR(36) NOT NULL,"
    	        		+ "occasion BIGINT NOT NULL,"
    	        		+ "location VARCHAR(36) NOT NULL,"
    	        		+ "PRIMARY KEY(id),"
    	        		+ "FOREIGN KEY(uuid, userName) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ "FOREIGN KEY(staffID) REFERENCES " + bridge.prefix + "Users(uuid) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add BanManagerMutes Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "BanManagerMutes ("
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + ","
    	        		+ "uuid CHAR(36) NOT NULL,"
    	        		+ "userName VARCHAR(32) NOT NULL,"
    	        		+ "reason VARCHAR(50) NOT NULL,"
    	        		+ "staffID CHAR(36) NOT NULL,"
    	        		+ "occasion BIGINT NOT NULL,"
    	        		+ "location VARCHAR(36) NOT NULL,"
    	        		+ "PRIMARY KEY(id),"
    	        		+ "FOREIGN KEY(uuid, userName) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ "FOREIGN KEY(staffID) REFERENCES " + bridge.prefix + "Users(uuid) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add BanManagerTempBans Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "BanManagerTempBans ("
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + ","
    	        		+ "uuid CHAR(36) NOT NULL,"
    	        		+ "userName VARCHAR(32) NOT NULL,"
    	        		+ "reason VARCHAR(50) NOT NULL,"
    	        		+ "staffID CHAR(36) NOT NULL,"
    	        		+ "occasion BIGINT NOT NULL,"
    	        		+ "duration BIGINT NOT NULL,"
    	        		+ "location VARCHAR(36) NOT NULL,"
    	        		+ "PRIMARY KEY(id),"
    	        		+ "FOREIGN KEY(uuid, userName) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ "FOREIGN KEY(staffID) REFERENCES " + bridge.prefix + "Users(uuid) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");"));
    	        updates.add(new DBUpdate("07.04.2019.1", "Add BanManagerBans Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "BanManagerBans ("
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + ","
    	        		+ "uuid CHAR(36) NOT NULL,"
    	        		+ "userName VARCHAR(32) NOT NULL,"
    	        		+ "reason VARCHAR(50) NOT NULL,"
    	        		+ "staffID CHAR(36) NOT NULL,"
    	        		+ "occasion BIGINT NOT NULL,"
    	        		+ "location VARCHAR(36) NOT NULL,"
    	        		+ "PRIMARY KEY(id),"
    	        		+ "FOREIGN KEY(uuid, userName) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ "FOREIGN KEY(staffID) REFERENCES " + bridge.prefix + "Users(uuid) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");")); 
    	        updates.add(new DBUpdate("07.04.2019.1", "Add BanManagerIPBans Table", "CREATE TABLE IF NOT EXISTS " + bridge.prefix + "BanManagerIPBans ("
    	        		+ "id INTEGER NOT NULL " + bridge.getAutoIncrement() + ","
    	        		+ "uuid CHAR(36) NOT NULL,"
    	        		+ "userName VARCHAR(32) NOT NULL,"
    	        		+ "reason VARCHAR(50) NOT NULL,"
    	        		+ "staffID CHAR(36) NOT NULL,"
    	        		+ "occasion BIGINT NOT NULL,"
    	        		+ "location VARCHAR(36) NOT NULL,"
    	        		+ "PRIMARY KEY(id),"
    	        		+ "FOREIGN KEY(uuid, userName) REFERENCES " + bridge.prefix + "Users(uuid, userName) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ "FOREIGN KEY(staffID) REFERENCES " + bridge.prefix + "Users(uuid) ON DELETE CASCADE ON UPDATE CASCADE"
    	        		+ ");"));
    	}
   }
