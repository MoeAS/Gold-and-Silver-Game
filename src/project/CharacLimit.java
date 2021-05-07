package project;


import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/* 
 * Code used to limit user input to 1 digit per text field
 * */
public class CharacLimit extends PlainDocument {
	
		private int limit;
		
		public CharacLimit(int limitation) {
			this.limit = limitation;
		}
		public void insertString(int offset, String str, AttributeSet set) throws BadLocationException{
			if (str == null) {
				return;
			}
			else if((getLength()+str.length()) <= limit) {
				str = str.toUpperCase();
				super.insertString(offset, str, set);
			}
		}
}
