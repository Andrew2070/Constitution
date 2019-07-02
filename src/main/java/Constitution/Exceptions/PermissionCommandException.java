package Constitution.Exceptions;
import Constitution.ConstitutionMain;
public class PermissionCommandException extends CommandException {
	public PermissionCommandException(String localKey, Object... args) {
		super(ConstitutionMain.instance.LOCAL.getLocalization(localKey, args).getUnformattedText());
	}
}