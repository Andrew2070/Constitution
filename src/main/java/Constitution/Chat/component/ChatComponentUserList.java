package constitution.chat.component;

import constitution.permissions.User;

public class ChatComponentUserList extends ChatComponentMultiPage {
	private User.Container users;

	public ChatComponentUserList(User.Container users) {
		super(9);
		this.users = users;
		this.construct();
	}

	private void construct() {
		for (User user : users) {
			this.add(new ChatComponentFormatted("{7| --> }{%s}", user.toChatMessage()));
		}
	}

	@Override
	public ChatComponentContainer getHeader(int page) {
		ChatComponentContainer header = super.getHeader(page);

		header.add(new ChatComponentFormatted("{9| [All Presently Existing Users Listed Below]"));

		return header;
	}
}