package ohtu.util;

import java.sql.Date;
import ohtu.domain.Vinkki;

public class ISBNhandler {
    private boolean queue;
    private String isbn;

    public ISBNhandler() {
        this.isbn = "";
        this.queue = false;
    }

    public boolean isQueue() {
        return queue;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
        this.queue = true;
    }
    
    public Vinkki getData() {
        this.queue = false;
        
        if (isValidISBN()) {
            
            
            return new Vinkki(-1, "", "", "", "", new Date(1), null);
        } else {
            return new Vinkki(-1, "", "", "", "", new Date(1), null);
        }
    }
    
    /**
     * found logic from http://www.vinaysingh.info/validate-isbn-number/
     */
    private boolean isValidISBN(){
        int digit;
        int sum = 0;
        int len = this.isbn.length();
        
        if( len != 10) {
            return false;
        }
        
        for(int i = 0; i < len ; i++){
            digit = this.isbn.charAt(i) - '0';
            digit = (digit == 40 || digit == 72) ? 10 : digit;
            sum += digit * (10 - i);
        }
        
        return (sum % 11) == 0;
    }
}
