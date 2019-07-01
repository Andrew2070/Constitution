package Constitution.Exceptions;
import Constitution.Localization.LocalizationManager;
import net.minecraft.util.text.ITextComponent;

public abstract class FormattedException extends RuntimeException {

	public final ITextComponent message;

	public FormattedException(String localizationKey, Object... args) {
		message = LocalizationManager.get(localizationKey, args);
	}
}