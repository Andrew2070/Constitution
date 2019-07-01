package Constitution.Chat;

import Constitution.Permissions.Group;

public class ChatComponentGroupList extends ChatComponentMultiPage {
	private Group.Container groups;

	public ChatComponentGroupList(Group.Container groups) {
		super(9);
		this.groups = groups;
		this.construct();
	}

	private void construct() {
		for (Group group : groups) {
 
		}
	}

	@Override
	public ChatComponentContainer getHeader(int page) {
		ChatComponentContainer header = super.getHeader(page);

		header.add(new ChatComponentFormatted("{9| [All Presently Existing Groups Listed Below]"));

		return header;
	}
}