package sms.invoicing.payment.model;

import sms.language.Internationalized;

/**
 * @author Claive Monteza
 *
 */

public enum PayMethod implements Internationalized{
	
	
	CASH{
		public String getMessageKey() {
			return "cash.record";
		}

		@Override
		public String getVariant() {
			return "Cash";
		}
	},
	
	MOBILE{
		public String getMessageKey() {
			return "moble.record";
		}

		@Override
		public String getVariant() {
			return "Mobile";
		}
	},
	
	POS {
		public String getMessageKey() {
			return "pos.record";
		}

		@Override
		public String getVariant() {
			return "POS";
		}
	},
	
}
