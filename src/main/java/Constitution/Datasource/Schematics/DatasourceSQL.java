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
package Constitution.Datasource.Schematics;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;

import Constitution.ConstitutionMain;
import Constitution.Configuration.Config;
import Constitution.Configuration.ConfigProperty;
import Constitution.Configuration.ConfigTemplate;
import Constitution.Datasource.Bridge.BridgeMySQL;
import Constitution.Datasource.Bridge.BridgeSQL;
import Constitution.Datasource.Bridge.BridgeSQLite;

/**
 * Datasource class which contains most functionality needed for a database
 * connection. Database connection initialization is done on instantiation.
 * Extend this and add all the load/save methods you want right in the extended
 * class.
 */
public abstract class DatasourceSQL {

	protected Logger LOG;

	protected String prefix = "";
	protected BridgeSQL bridge;
	protected BaseSchematic schema;

	public ConfigProperty<String> databaseType = Config.instance.DBType;

	public DatasourceSQL(Logger log, ConfigTemplate config, BaseSchematic schema) {
		this.LOG = log;
		this.schema = schema;
		loadConfig(config);
		schema.initializeUpdates(bridge);
		try {
			doUpdates();
		} catch (SQLException ex) {
			LOG.error("Failed to run database updates!");
			LOG.error(ExceptionUtils.getStackTrace(ex));
		}
		loadAll();
		checkAll();
	}

	public abstract boolean loadAll();

	public abstract boolean checkAll();

	public boolean stop() {
		try {
			bridge.getConnection().close();
			return true;
		} catch (SQLException e) {
			ConstitutionMain.logger.info("Failed to close connection to database.");
			ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(e));
			return false;
		}
	}

	private void loadConfig(ConfigTemplate config) {
		config.addBinding(databaseType, true);
		if (databaseType.get().equalsIgnoreCase("sqlite")) {
			bridge = new BridgeSQLite(config);
		} else if (databaseType.get().equalsIgnoreCase("mysql")) {
			bridge = new BridgeMySQL(config);
		}
	}

	protected boolean hasTable(String tableName) {
		try {
			DatabaseMetaData meta = bridge.getConnection().getMetaData();
			ResultSet rs = meta.getTables(null, null, prefix + tableName, null);
			return rs.next();
		} catch (Exception ex) {
			LOG.error("Failed to check for table existence.");
			LOG.error(ExceptionUtils.getStackTrace(ex));
			return false;
		}
	}

	protected PreparedStatement prepare(String sql, boolean returnGenerationKeys) {
		try {
			return bridge.getConnection().prepareStatement(sql,
					returnGenerationKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		} catch (SQLException e) {
			LOG.fatal(sql);
			LOG.error(ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	protected void doUpdates() throws SQLException {
		List<String> ids = new ArrayList<String>();
		PreparedStatement statement;
		if (hasTable("Updates")) {
			statement = prepare("SELECT id FROM " + prefix + "Updates", false);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				ids.add(rs.getString("id"));
			}
		}

		for (BaseSchematic.DBUpdate update : schema.updates) {
			if (ids.contains(update.id)) {
				continue; // Skip if update is already done
			}

			try {
				LOG.info("Running update {} - {}", update.id, update.desc);
				statement = prepare(update.statement, false);
				statement.execute();

				// Insert the update key so as to not run the update again
				statement = prepare("INSERT INTO " + prefix + "Updates (id,description) VALUES(?,?)", true);
				statement.setString(1, update.id);
				statement.setString(2, update.desc);
				statement.executeUpdate();
			} catch (SQLException e) {
				LOG.error("Update ({} - {}) failed to apply!", update.id, update.desc);
				LOG.error(ExceptionUtils.getStackTrace(e));
				throw e; // Throws back up to force safemode
			}
		}
	}

	public BridgeSQL getBridge() {
		return this.bridge;
	}
}
