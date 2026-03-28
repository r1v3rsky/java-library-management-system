import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class Users {
    private String id;
    private String name;
    private String phone;
    private int penalty;
    private static final Dictionary<String, List<Object>> dictUsers = new Hashtable<>();

    public Users(String id, String name, String phone, int penalty) {
        this.setId(id);
        this.setName(name);
        this.setPhone(phone);
        this.setPenalty(penalty);
    }
    public static void createUsers(List<String[]> users) {
        for (String[] line : users) {
            switch (line[0]) {
                case "S":
                    Student.dictStudent(line);
                    break;
                case "A":
                    Academicians.dictAcademicians(line);
                    break;
                case "G":
                    Guest.dictGuest(line);
                    break;
            }
        }
    }

    public static Dictionary<String, List<Object>> getDictUsers() {
        return dictUsers;
    }
    public static void addDictUsers(String id, List<Object> userInfos) {
        dictUsers.put(id,userInfos);
    }
    public String getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getPenalty() {
        return penalty;
    }
    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
    public static boolean canAccess(String id, String bookId) {
        if (id.startsWith("1")) {
            return Student.canAccessStudent(bookId);
        } else if (id.startsWith("2")) {
            return Academicians.canAccessAcademicians(bookId);
        } else if (id.startsWith("3")) {
            return Guest.canAccessGuest(bookId);
        }else {
            return false;
        }
    }
}

class Student extends Users{
    private String department;
    private String faculty;
    private String degree;
    private int nowLimit;
    private static final List<String> cannotAccessStudent = new ArrayList<>();

    public Student(String id, String name, String phone, String department, String faculty, String degree, int penalty, int nowLimit){
        super(id, name, phone, penalty);
        this.setDepartment(department);
        this.setFaculty(faculty);
        this.setDegree(degree);
        this.setNowLimit(nowLimit);
    }
    public static Student createStudent(String[] line) {
        return new Student(line[2], line[1], line[3], line[4], line[5], line[6], 0, 0);
    }
    public static void printStudents(String id, BufferedWriter writer) throws IOException {
        List<Object> userInfos = Users.getDictUsers().get(id);
        // {id = '[name, penalty, maxlimit, maxday, nowlimit, phone, faculty, department, degree]'}
        writer.write("------ User Information for "+ id +" ------\n");
        writer.write("Name: " + userInfos.get(0) + " Phone: " + userInfos.get(5)+"\n");
        writer.write("Faculty: " + userInfos.get(6) + " Department: " + userInfos.get(7) + " Grade: " + userInfos.get(8) + "th\n");
        if ((int) userInfos.get(1) > 0) {
            writer.write("Penalty: " + userInfos.get(1) + "$\n");
        }
    }
    public static void dictStudent(String[] line) {
        Student student = Student.createStudent(line);
        ArrayList<Object> userInfos = new ArrayList<>();
        userInfos.add(student.getName());
        userInfos.add(student.getPenalty());
        userInfos.add(student.getMaxLimit());
        userInfos.add(student.getMaxDay());
        userInfos.add(student.getNowLimit());
        userInfos.add(student.getPhone());
        userInfos.add(student.getFaculty());
        userInfos.add(student.getDepartment());
        userInfos.add(student.getDegree());
        addDictUsers(student.getId(), userInfos);
    }
    public static boolean canAccessStudent(String bookId) {
        List<Object> idList = LibraryItems.getDictItems().get(bookId);
        String type = (String) idList.get(3);
        return !getCannotAccess().contains(type);
    }
    public String getFaculty(){
        return faculty;
    }
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDegree(){
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public static List<String> getCannotAccess() {
        cannotAccessStudent.clear();
        cannotAccessStudent.add("reference");
        return cannotAccessStudent;
    }
    public int getMaxLimit() {
        return 5;
    }
    public int getMaxDay() {
        return 30;
    }
    public int getNowLimit() {
        return nowLimit;
    }
    public void setNowLimit(int nowLimit) {
        this.nowLimit = nowLimit;
    }
}
class Academicians extends Users {
    private String department;
    private String faculty;
    private String title;
    private int nowLimit;
    private static final List<String> cannotAccessAcademicians = new ArrayList<>();

    public Academicians(String id, String name, String phone, String department, String faculty, String title, int penalty, int nowLimit){
        super(id, name, phone, penalty);
        this.setDepartment(department);
        this.setFaculty(faculty);
        this.setTitle(title);
        this.setNowLimit(nowLimit);
    }
    public static Academicians createAcademicians(String[] line) {
        return new Academicians(line[2], line[1], line[3], line[4], line[5], line[6], 0, 0);
    }
    public static void printAcademicians(String id, BufferedWriter writer) throws IOException {
        List<Object> userInfos = Users.getDictUsers().get(id);
        // {id = '[name, penalty, maxlimit, maxday, nowlimit, phone, faculty, department, title]'}
        writer.write("------ User Information for "+ id +" ------\n");
        writer.write("Name: " +userInfos.get(8) + " " + userInfos.get(0) + " Phone: " + userInfos.get(5)+"\n");
        writer.write("Faculty: " + userInfos.get(6) + " Department: " + userInfos.get(7)+"\n");
        if ((int) userInfos.get(1) > 0) {
            writer.write("Penalty: " + userInfos.get(1) + "$\n");
        }
    }
    public static void dictAcademicians(String[] line) {
        Academicians academicians = Academicians.createAcademicians(line);
        ArrayList<Object> userInfos = new ArrayList<>();
        userInfos.add(academicians.getName());
        userInfos.add(academicians.getPenalty());
        userInfos.add(academicians.getMaxLimit());
        userInfos.add(academicians.getMaxDay());
        userInfos.add(academicians.getNowLimit());
        userInfos.add(academicians.getPhone());
        userInfos.add(academicians.getFaculty());
        userInfos.add(academicians.getDepartment());
        userInfos.add(academicians.getTitle());
        addDictUsers(academicians.getId(), userInfos);
    }
    public static boolean canAccessAcademicians(String bookId) {
        List<Object> idList = LibraryItems.getDictItems().get(bookId);
        String type = (String) idList.get(3);
        return !getCannotAccess().contains(type);
    }
    public String getFaculty(){
        return faculty;
    }
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public static List<String> getCannotAccess() {
        cannotAccessAcademicians.clear();
        return cannotAccessAcademicians;
    }
    public static void addCannotAccessAcademicians(String cannot) {
        cannotAccessAcademicians.add(cannot);
    }
    public int getMaxLimit() {
        return 3;
    }
    public int getMaxDay() {
        return 15;
    }
    public int getNowLimit() {
        return nowLimit;
    }
    public void setNowLimit(int nowLimit) {
        this.nowLimit = nowLimit;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
class Guest extends Users {
    private String job;
    private int nowLimit;
    private static final List<String> cannotAccessGuest = new ArrayList<>();

    public Guest(String id, String name, String phone, String job, int penalty, int nowLimit) {
        super(id, name, phone, penalty);
        this.setJob(job);
        this.setNowLimit(nowLimit);
    }
    public static Guest createGuest(String[] line) {
        return new Guest(line[2], line[1], line[3], line[4], 0, 0);
    }
    public static void printGuest(String id, BufferedWriter writer) throws IOException {
        List<Object> userInfos = Users.getDictUsers().get(id);
        // {id = '[name, penalty, maxlimit, maxday, nowlimit, phone, occupation]'}
        writer.write("------ User Information for "+ id +" ------\n");
        writer.write("Name: " + userInfos.get(0) + " Phone: " + userInfos.get(5)+"\n");
        writer.write("Occupation: " + userInfos.get(6)+"\n");
        if ((int) userInfos.get(1) > 0) {
            writer.write("Penalty: " + userInfos.get(1) + "$\n");
        }
    }
    public static void dictGuest(String[] line) {
        Guest guest = Guest.createGuest(line);
        ArrayList<Object> userInfos = new ArrayList<>();
        userInfos.add(guest.getName());
        userInfos.add(guest.getPenalty());
        userInfos.add(guest.getMaxLimit());
        userInfos.add(guest.getMaxDay());
        userInfos.add(guest.getNowLimit());
        userInfos.add(guest.getPhone());
        userInfos.add(guest.getJob());
        addDictUsers(guest.getId(), userInfos);
    }
    public static boolean canAccessGuest(String bookId) {
        List<Object> idList = LibraryItems.getDictItems().get(bookId);
        String type = (String) idList.get(3);
        return !getCannotAccess().contains(type);
    }
    public static List<String> getCannotAccess() {
        cannotAccessGuest.clear();
        return cannotAccessGuest;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public int getNowLimit() {
        return nowLimit;
    }
    public void setNowLimit(int nowLimit) {
        this.nowLimit = nowLimit;
    }
    public int getMaxLimit() {
        return 1;
    }
    public int getMaxDay() {
        return 7;
    }
}

