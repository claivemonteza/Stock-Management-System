/**
 * 
 */
package sms.item.product.model;

import sms.language.Internationalized;
import sms.util.Extra;

/**
 * <code>Unity</code> will be which type of packaging the product will
 * be in.
 * 
 * @see Extra
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public enum Unity implements Internationalized{

	UNITY {
		public String getMessageKey() {
			return "unity.record";
		}

		@Override
		public String getVariant() {
			return "Uni";
		}
	},
	
	CX{
		public String getMessageKey() {
			return "box.record";
		}

		@Override
		public String getVariant() {
			return "Cx";
		}
	},
	
	TABLE{
		public String getMessageKey() {
			return "table.record";
		}

		@Override
		public String getVariant() {
			return "Tab";
		}
	},
	
	KG{
		public String getMessageKey() {
			return "kilogram.record";
		}

		@Override
		public String getVariant() {
			return "Kg";
		}
	},
	
	L{
		public String getMessageKey() {
			return "liter.record";
		}

		@Override
		public String getVariant() {
			return "L";
		}
	},
	
	MG{
		public String getMessageKey() {
			return "milligrams.record";
		}

		@Override
		public String getVariant() {
			return "Mg";
		}
	},
}
