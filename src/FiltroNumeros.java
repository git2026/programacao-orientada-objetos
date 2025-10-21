import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FiltroNumeros extends DocumentFilter {
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
            throws BadLocationException {
        if (isInteger(text)) {
            super.insertString(fb, offset, text, attr);
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (isInteger(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    /**
     * Metodo para verificar se um texto dado é um int ou não
     * @param texto - texto a ser verificado
     * @return <code>true</code> se o texto for um int, <code>false</code> em caso contrario
     */
    private boolean isInteger(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}