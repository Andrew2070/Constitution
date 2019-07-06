package constitution.datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils
import constitution.configuration.ConfigTemplate;
import constitution.datasource.schematics.BaseSchematic;
import constitution.datasource.schematics.DatasourceSQL;
import constitution.permissions.Group;

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
