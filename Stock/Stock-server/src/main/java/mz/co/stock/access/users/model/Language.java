package mz.co.stock.access.users.model;

import java.util.Locale;

/**
 * <code>Language</code> will define witch type the user will use on the system.
 * 
 * @see Internationalized
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public enum Language implements Internationalized {
	PORTUGUESE {
		@Override
		public Locale getLocale() {
			return PORTUGUESE_LOCALE;
		}

		public String getMessageKey() {
			return "portuguese.record";
		}

		@Override
		public String getVariant() {
			return "pt";
		}

	},
	ENGLISH {
		@Override
		public Locale getLocale() {
			return ENGLISH_LOCALE/* Locale.ENGLISH */;
		}

		public String getMessageKey() {
			return "english.record";
		}

		@Override
		public String getVariant() {
			return "en";
		}

	};

	private static final Locale PORTUGUESE_LOCALE = new Locale("pt");
	private static final Locale ENGLISH_LOCALE = new Locale("en");

	/**
	 * Deve retornar o Locale para a lingua que representa
	 * 
	 * @return
	 */
	abstract public Locale getLocale();

}
