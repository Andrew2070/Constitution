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
package constitution.datasource.bridge;


import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.sqlite.JDBC;

import constitution.ConstitutionMain;
import constitution.configuration.ConfigProperty;
import constitution.configuration.ConfigTemplate;
public class BridgeSQLite extends BridgeSQL {

	public ConfigProperty<String> dbPath = Config.instance.DBPath;

	public BridgeSQLite(ConfigTemplate config) {
		dbPath.set(ConstitutionMain.DATABASE_FOLDER + config.getModID() + "/data.db");
		config.addBinding(dbPath, true);
		initProperties();
		initConnection();
	}

	@Override
	protected void initConnection() {
		File file = new File(dbPath.get());
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		this.dsn = "jdbc:sqlite:" + dbPath.get();

		try {
			DriverManager.registerDriver(new JDBC());
		} catch (SQLException ex) {
			ConstitutionMain.logger.info("Failed to register driver for SQLite database." + " exception: " + ex);
		}

		try {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
				} catch (SQLException ex) {
				} // Ignore since we are just closing an old connection
				conn = null;
			}

			conn = DriverManager.getConnection(dsn, properties);
		} catch (SQLException ex) {
			ConstitutionMain.logger.info("Failed to get SQL connection! {}" + " dsn: " + dsn);
			ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));
		}
	}

	@Override
	protected void initProperties() {
		properties.put("foreign_keys", "ON");
	}
}
