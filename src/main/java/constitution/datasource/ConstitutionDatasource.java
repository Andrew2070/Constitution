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
package Constitution.Datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;

import Constitution.Configuration.ConfigTemplate;
import Constitution.Datasource.Schematics.BaseSchematic;
import Constitution.Datasource.Schematics.DatasourceSQL;
import Constitution.Permissions.Group;

public class ConstitutionDatasource extends DatasourceSQL {

	public ConstitutionDatasource(Logger log, ConfigTemplate config, BaseSchematic schema) {
		super(log, config, schema);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean loadAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkAll() {
		// TODO Auto-generated method stub
		return false;
	}



	public boolean loadGroups() {
		try {
			PreparedStatement loadEmpiresStatement = prepare("SELECT * FROM " + prefix + "Groups", true);
			ResultSet rs = loadEmpiresStatement.executeQuery();
			while (rs.next()) {
				Group group;
				List<Group> parents = new ArrayList<Group>();
				group = new Group(rs.getString("name"));
				group.setDesc(rs.getString("desc"));
				group.setRank(rs.getInt("rank"));
				group.setPrefix(rs.getString("prefix"));
				group.setSuffix(rs.getString("suffix"));


			}
		} catch (SQLException e) {
			LOG.error("Failed to load Groups!");
			LOG.error(ExceptionUtils.getStackTrace(e));
			return false;
		}

		return true;
	}


}
