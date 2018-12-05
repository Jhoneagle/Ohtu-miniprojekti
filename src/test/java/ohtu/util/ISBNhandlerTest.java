package ohtu.util;

import java.sql.Date;
import ohtu.domain.Vinkki;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class ISBNhandlerTest {
    private ISBNhandler isbn;
    
    @Before
    public void setUp() {
        this.isbn = new ISBNhandler();
    }
    
    @Test
    public void emptyISBN() {
        Vinkki data = isbn.getData();
        boolean same = areVinkitSame(data, new Vinkki(-1, "", "", "", "", new Date(1), null));
        assertTrue(same);
    }
    
    @Test
    public void setISBN() {
        assertFalse(this.isbn.isQueue());
        this.isbn.setIsbn("0545162076");
        assertTrue(this.isbn.isQueue());
    }
    
    @Test
    public void invalidISBN() {
        this.isbn.setIsbn("0735619571");
        Vinkki data = isbn.getData();
        
        boolean same = areVinkitSame(data, new Vinkki(-1, "", "", "", "", new Date(1), null));
        assertTrue(same);
    }
    
    @Test
    public void getData() {
        this.isbn.setIsbn("0545162076");
        Vinkki data = isbn.getData();
        
        String otsikko = "Harry Potter the Complete Series";
        String tekija = "J. K. Rowling";
        String kuvaus = "Collects the complete series that relates the adventures of young Harry Potter, who attends Hogwarts School of Witchcraft and Wizardry, where he and others of his kind learn their craft.";
        String linkki = "http://books.google.fi/books?id=h77PDQAAQBAJ&dq=isbn:0545162076&hl=&source=gbs_api";
        Vinkki vinkki = new Vinkki(-1, otsikko, tekija, kuvaus, linkki, new Date(1), null);
        
        assertTrue(areVinkitSame(data, vinkki));
    }
    
    private boolean areVinkitSame(Vinkki expected, Vinkki actual) {
        if (!expected.getOtsikko().contains(actual.getOtsikko())) {
            return false;
        }

        if (!expected.getTekija().contains(actual.getTekija())) {
            return false;
        }

        if (!expected.getKuvaus().contains(actual.getKuvaus())) {
            return false;
        }

        if (!expected.getLinkki().contains(actual.getLinkki())) {
            return false;
        }

        return true;
    }
}
