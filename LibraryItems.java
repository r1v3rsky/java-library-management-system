import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class LibraryItems {
    private String id;
    private String name;
    private String type;
    private String status;
    private static final Dictionary<String, List<Object>> dictItems = new Hashtable<>();

    public LibraryItems(String id, String name, String type, String status) {
        this.setId(id);
        this.setName(name);
        this.setType(type);
        this.setStatus(status);
    }
    public static void createItems(List<String[]> items) {
        for (String[] line : items) {
            switch (line[0]) {
                case "B":
                    Books.dictBooks(line);
                    break;
                case "M":
                    Magazines.dictMagazines(line);
                    break;
                case "D":
                    DVDs.dictDVDs(line);
                    break;
            }
        }
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public static Dictionary<String, List<Object>> getDictItems() {
        return dictItems;
    }
    // {id = '[name, author, genre, type, status, borrowedDate, borrowedby]'} for books for exp
    public static void addDictItems(String id, List<Object> infos) {
        dictItems.put(id, infos);
    }

}
class Books extends LibraryItems {
    private String author;
    private String genre;
    private String date;
    private String by;

    public Books(String id, String name, String author, String genre, String type, String status, String date, String by){
        super(id, name, type, status);
        this.setAuthor(author);
        this.setGenre(genre);
        this.setDate(date);
        this.setBy(by);
    }
    public static Books createBooks(String[] line) {
        return new Books(line[1], line[2], line[3], line[4], line[5], "Available", "dd/MM/yyyy", "borrowedby");
    }
    public static void printBooks(String bookId, BufferedWriter writer) throws IOException {
        List<Object> itemInfos = LibraryItems.getDictItems().get(bookId);
        // {bookId = '[name, author, genre, type, status, borrowedDate, borrowedby]'}
        writer.write("------ Item Information for " + bookId + " ------\n");
        writer.write("ID: "+bookId+" Name: "+itemInfos.get(0)+" Status: "+itemInfos.get(4));
        if (itemInfos.get(4).equals("Borrowed")) {
            writer.write(" Borrowed Date: "+itemInfos.get(5)+" Borrowed by: "+ itemInfos.get(6));
        }
        writer.write("\n");
        writer.write("Author: "+itemInfos.get(1)+ " Genre: "+itemInfos.get(2)+"\n");
    }
    public static void dictBooks(String[] line) {
        Books books = Books.createBooks(line);
        ArrayList<Object> bookInfos = new ArrayList<>();
        bookInfos.add(books.getName());
        bookInfos.add(books.getAuthor());
        bookInfos.add(books.getGenre());
        bookInfos.add(books.getType());
        bookInfos.add(books.getStatus());
        bookInfos.add(books.getDate());
        bookInfos.add(books.getBy());
        addDictItems(books.getId(), bookInfos);
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getBy() {
        return by;
    }
    public void setBy(String by) {
        this.by = by;
    }
}
class Magazines extends LibraryItems {
    private String publisher;
    private String category;
    private String date;
    private String by;
    public Magazines(String id, String name, String publisher, String category, String type, String status, String date, String by) {
        super(id, name, type, status);
        this.setPublisher(publisher);
        this.setCategory(category);
        this.setDate(date);
        this.setBy(by);
    }
    public static Magazines createMagazines(String[] line) {
        return new Magazines(line[1], line[2], line[3], line[4], line[5], "Available", "dd/MM/yyyy", "borrowedby");
    }
    public static void printMagazines(String magazineId, BufferedWriter writer) throws IOException {
        List<Object> itemInfos = LibraryItems.getDictItems().get(magazineId);
        // {magazineId = '[name, publisher, category, type, status, borrowedDate, borrowedby]'}
        writer.write("------ Item Information for " + magazineId + " ------\n");
        writer.write("ID: "+magazineId+" Name: "+itemInfos.get(0)+" Status: "+itemInfos.get(4));
        if (itemInfos.get(4).equals("Borrowed")) {
            writer.write(" Borrowed Date: "+itemInfos.get(5)+" Borrowed by: "+ itemInfos.get(6));
        }
        writer.write("\n");
        writer.write("Publisher: "+itemInfos.get(1)+ " Category: "+itemInfos.get(2)+"\n");
    }
    public static void dictMagazines(String[] line) {
        Magazines magazines = Magazines.createMagazines(line);
        ArrayList<Object> magazinesInfos = new ArrayList<>();
        magazinesInfos.add(magazines.getName());
        magazinesInfos.add(magazines.getPublisher());
        magazinesInfos.add(magazines.getCategory());
        magazinesInfos.add(magazines.getType());
        magazinesInfos.add(magazines.getStatus());
        magazinesInfos.add(magazines.getDate());
        magazinesInfos.add(magazines.getBy());
        addDictItems(magazines.getId(), magazinesInfos);
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getBy() {
        return by;
    }
    public void setBy(String by) {
        this.by = by;
    }
}
class DVDs extends LibraryItems {
    private String directory;
    private String category;
    private String runtime;
    private String date;
    private String by;

    public DVDs(String id, String name, String directory, String category, String runtime, String type, String status, String date, String by) {
        super(id, name, type, status);
        this.setDirectory(directory);
        this.setCategory(category);
        this.setRuntime(runtime);
        this.setDate(date);
        this.setBy(by);
    }
    public static DVDs createDVDs(String[] line) {
        return new DVDs(line[1], line[2], line[3], line[4], line[5], line[6], "Available", "dd/MM/yyyy", "borrowedby");
    }
    public static void printDVDs(String dvdId, BufferedWriter writer) throws IOException {
        List<Object> itemInfos = LibraryItems.getDictItems().get(dvdId);
        // {dvdId = '[name, director, category, type, status, borrowedDate, borrowedby, runtime]'}
        writer.write("------ Item Information for " + dvdId + " ------\n");
        writer.write("ID: "+dvdId+" Name: "+itemInfos.get(0)+" Status: "+itemInfos.get(4));
        if (itemInfos.get(4).equals("Borrowed")) {
            writer.write(" Borrowed Date: "+itemInfos.get(5)+" Borrowed by: "+ itemInfos.get(6));
        }
        writer.write("\n");
        writer.write("Director: "+itemInfos.get(1)+" Category: "+itemInfos.get(2)+" Runtime: "+itemInfos.get(7)+"\n");
    }
    public static void dictDVDs(String[] line) {
        DVDs dvds = DVDs.createDVDs(line);
        ArrayList<Object> dvdsInfos = new ArrayList<>();
        dvdsInfos.add(dvds.getName());
        dvdsInfos.add(dvds.getDirectory());
        dvdsInfos.add(dvds.getCategory());
        dvdsInfos.add(dvds.getType());
        dvdsInfos.add(dvds.getStatus());
        dvdsInfos.add(dvds.getDate());
        dvdsInfos.add(dvds.getBy());
        dvdsInfos.add(dvds.getRuntime());
        addDictItems(dvds.getId(), dvdsInfos);
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDirectory() {
        return directory;
    }
    public void setDirectory(String directory) {
        this.directory = directory;
    }
    public String getRuntime() {
        return runtime;
    }
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getBy() {
        return by;
    }
    public void setBy(String by) {
        this.by = by;
    }
}