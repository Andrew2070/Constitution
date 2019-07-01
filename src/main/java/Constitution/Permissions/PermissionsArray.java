package Constitution.Permissions;

import java.util.ArrayList;
import java.util.List;

public class PermissionsArray {
	
	public static List<Group> ActiveGroups = new ArrayList<Group>();
	
	public static Group getActiveGroups() {
		return ActiveGroups.iterator().next();
	}
	
	public static Group getGroupFromName(String name) {
		for (Group group : ActiveGroups) {
			if (group.getName().equals(name)) {
				return group;
			}
		}
	return null;
	}

	public static void setActiveGroup(Group group) {
		ActiveGroups.add(group);
	}
}
