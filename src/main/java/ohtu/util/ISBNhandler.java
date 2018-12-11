package ohtu.util;

import java.sql.Date;
import ohtu.domain.Vinkki;

import com.google.gson.JsonArray;
import java.io.IOException;
import org.apache.http.client.fluent.Request;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
        this.isbn = isbn.replace("-", "");
        this.queue = true;
    }
    
    public Vinkki getData() {
        this.queue = false;
        
        if (isValidISBN()) {
            try {
                String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + this.isbn;
                String isbnResult = Request.Get(url).execute().returnContent().asString();
                
                JsonParser parser = new JsonParser();
                JsonObject parsedISBN = parser.parse(isbnResult).getAsJsonObject();
                JsonObject book = parsedISBN.get("items").getAsJsonArray().get(0).getAsJsonObject().get("volumeInfo").getAsJsonObject();
                
                String otsikko = book.get("title").getAsString();
                JsonArray tekijatList = book.get("authors").getAsJsonArray();
                String kuvaus = book.get("description").getAsString();
                String linkki = book.get("infoLink").getAsString();
                String tekijat = tekijatList.get(0).getAsString();;
                
                for (int i = 1; i < tekijatList.size(); i++) {
                    tekijat += ", " + tekijatList.get(i).getAsString();
                }
                
                Vinkki vinkki = new Vinkki(-1, otsikko, tekijat, kuvaus, linkki, new Date(1), this.isbn);
                vinkki.setTagit("kirja, book");
                return vinkki;
            } catch (IOException ex) {
                System.out.println("failed json fetch with isbn!" + "\n" + ex);
                return new Vinkki(-1, "", "", "", "", new Date(1), null);
            }
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
