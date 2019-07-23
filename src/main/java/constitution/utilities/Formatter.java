package constitution.utilities;

import java.text.DateFormat;
import java.util.Date;

public class Formatter {
	private static final DateFormat dateFormatter = DateFormat.getDateTimeInstance(0, 0);
	
	private Formatter() {
	}

	public static String formatDate(Date date) {
		return dateFormatter.format(date);
	}
}
