package Constitution.Exceptions;
import Constitution.Constitution;
public class PermissionCommandException extends CommandException {
	public PermissionCommandException(String localKey, Object... args) {
		super(Constitution.instance.LOCAL.getLocalization(localKey, args).getUnformattedText());
	}
}