package core;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;


/*Solution found on stackoverflow. 
 * Altered to fit our application. Filters out numerical characters from textboxs/areas
 */

//http://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers


class MyIntFilter extends DocumentFilter {
   @Override
   public void insertString(FilterBypass fb, int offset, String string,
         AttributeSet attr) throws BadLocationException {

      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.insert(offset, string);

      if (test(sb.toString())) {
         super.insertString(fb, offset, string, attr);
      } else {
         // warn the user and don't allow the insert
    	  JOptionPane.showMessageDialog(null, "Requires integer value!");
    	  //doc.getText(0, doc.getLength())
      }
   }

   private boolean test(String text) {
	   if(text.trim().isEmpty())
		   return true;
	   
	   try {
         Integer.parseInt(text);
         
       
         return true;
         
         
      } catch (NumberFormatException e) {
         return false;
      }
   }

   @Override
   public void replace(FilterBypass fb, int offset, int length, String text,
         AttributeSet attrs) throws BadLocationException {

      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.replace(offset, offset + length, text);

      if (test(sb.toString())) {
         super.replace(fb, offset, length, text, attrs);
      } else {
         // warn the user and don't allow the insert
      }

   }

   @Override
   public void remove(FilterBypass fb, int offset, int length)
         throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.delete(offset, offset + length);

      if (test(sb.toString())) {
         super.remove(fb, offset, length);
      } else {
         // warn the user and don't allow the insert
      }

   }
}