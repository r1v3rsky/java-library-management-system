import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LibraryManagement {
    public static void LibraryManagements(String[] args) throws IOException {
        String usersfile = args[1];
        String commandfile = args[2];
        String itemsFile = args[0];
        String output = args[3];
        List<String[]> command = readTxt(commandfile);
        List<String[]> users = readTxt(usersfile);
        List<String[]> items = readTxt(itemsFile);
        Users.createUsers(users);  // first I took args and created users then I added users to dictionary like {"id" = [name, penalty, etc.]}
        LibraryItems.createItems(items);  // {"id" = [name, author, etc.]}
        BufferedWriter writerdelete = new BufferedWriter(new FileWriter(output));
        writerdelete.write("");  // this is for at the first to clear the file
        writerdelete.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(output, true));

        for (String[] line : command){
            switch (line[0]) {
                case "displayUsers":
                    writer.write("\n");
                    displayUsers(users, writer);
                    break;
                case "borrow":
                    canBorrow(line[1], line[3], line[2], writer);
                    break;
                case "pay":
                    pay(line[1]);
                    writer.write(Users.getDictUsers().get(line[1]).get(0)+ " has paid penalty\n");
                    break;
                case "return":
                    returnItem(line[1], line[2], command);
                    writer.write(Users.getDictUsers().get(line[1]).get(0) + " successfully returned " + LibraryItems.getDictItems().get(line[2]).get(0));
                    writer.write("\n");
                    break;
                case "displayItems":
                    writer.write("\n");
                    displayItems(items, writer);
                    break;
            }
        }
        writer.flush();
        writer.close();
    }
    public static void pay(String id) {
        List<Object> userInfos = Users.getDictUsers().get(id);
        userInfos.set(1, 0);
        Users.addDictUsers(id, userInfos);
    }
    public static void returnItem(String id, String bookId, List<String[]> command) {
        List<Object> userInfos = Users.getDictUsers().get(id);
        List<Object> itemInfos = LibraryItems.getDictItems().get(bookId);
        try {
            for (String[] line : command) {
                if (line[1].equals(id) && line[2].equals(bookId) && (int) userInfos.get(1) > 0) {
                    userInfos.set(1, (int) userInfos.get(1) - 2); // if someone return some items there is no need to extra penalty bcs automatically +2 penalty when exceeds
                }
            }
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }
        userInfos.set(4, (int) userInfos.get(4) - 1);  // this is number of items that user has
        itemInfos.set(4, "Available");  // this is item's status
        Users.addDictUsers(id, userInfos);  // I added new values
        LibraryItems.addDictItems(bookId, itemInfos);
    }
    public static void displayUsers(List<String[]> users, BufferedWriter writer) throws IOException{
        for (String[] line : users) {
            writer.write("\n");
            switch (line[0]) {
                case "S":
                    Student.printStudents(line[2], writer);
                    break;
                case "A":
                    Academicians.printAcademicians(line[2], writer);
                    break;
                case "G":
                    Guest.printGuest(line[2], writer);
                    break;}}}
    public static void displayItems(List<String[]> items, BufferedWriter writer) throws IOException {
        for (String[] line : items) {
            writer.write("\n");
            switch (line[0]) {
                case "B":
                    Books.printBooks(line[1], writer);
                    break;
                case "M":
                    Magazines.printMagazines(line[1], writer);
                    break;
                case "D":
                    DVDs.printDVDs(line[1], writer);
                    break;}}
        }

    public static int calculate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(date, formatter);
        LocalDate endDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }
    public static void canBorrow(String id, String date, String bookId, BufferedWriter writer) throws IOException {
        String userName = (String) Users.getDictUsers().get(id).get(0);
        String bookName = (String) LibraryItems.getDictItems().get(bookId).get(0);
        int maxDay = (Integer) Users.getDictUsers().get(id).get(3);
        int maxLimit = (Integer) Users.getDictUsers().get(id).get(2);
        int nowLimit = (Integer) Users.getDictUsers().get(id).get(4);
        List<Object> itemInfos = LibraryItems.getDictItems().get(bookId);
        List<Object> userInfos = Users.getDictUsers().get(id);
        int daysBetween = calculate(date);
        int penalty = (Integer)Users.getDictUsers().get(id).get(1);
        if (penalty < 6) {
            if (LibraryItems.getDictItems().get(bookId).get(4).equals("Available")) {
                if(Users.canAccess(id, bookId) == Boolean.TRUE){
                    if (nowLimit < maxLimit) {
                        writer.write(userName + " successfully borrowed! " + bookName+ "\n");
                        itemInfos.set(4, "Borrowed");  // updating some values
                        itemInfos.set(5, date);
                        itemInfos.set(6, userName);
                        LibraryItems.addDictItems(bookId, itemInfos);
                        userInfos.set(4, nowLimit + 1);
                        if (daysBetween > maxDay) {
                            itemInfos.set(4, "Available");  // automatically returned
                            userInfos.set(1, penalty + 2);
                            LibraryItems.addDictItems(bookId, itemInfos);
                            Users.addDictUsers(id, userInfos);
                            if (nowLimit > 0){
                                userInfos.set(4, nowLimit - 1);
                            }else{
                                userInfos.set(4,0);
                            }
                        }

                    }else{
                        writer.write(userName+" cannot borrow "+bookName+", since the borrow limit has been reached!\n");
                    }
                }else {
                    String type = (String) LibraryItems.getDictItems().get(bookId).get(3);
                    writer.write(userName+" cannot borrow "+type+" item!\n");
                }
            }else{
                writer.write(userName+" cannot borrow "+bookName+", it is not available!\n");
            }
        }else {
            writer.write(userName+" cannot borrow "+bookName+", you must first pay the penalty amount! "+penalty+"$\n");
        }

    }
    public static List<String[]> readTxt(String fileName) {
        List<String[]> commands = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                commands.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commands;
    }
}
