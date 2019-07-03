package constitution.exceptions;
import constitution.localization.LocalizationManager;
import net.minecraft.util.text.ITextComponent;

public abstract class FormattedException extends RuntimeException {

	public final ITextComponent message;

	public FormattedException(String localizationKey, Object... args) {
		message = LocalizationManager.get(localizationKey, args);
	}
}