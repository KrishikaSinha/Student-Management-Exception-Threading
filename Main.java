import java.io.*;
import java.util.*;

// =====================
// Custom Exception
// =====================
class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String msg) {
        super(msg);
    }
}

// =====================
// Abstract Class Person
// =====================
abstract class Person {
    protected String name;
    protected String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public abstract void displayInfo();
}

// =====================
// Student Class
// =====================
class Student extends Person {
    private int rollNo;
    private String course;
    private double marks;
    private char grade;

    public Student(int rollNo, String name, String email, String course, double marks) {
        super(name, email);
        this.rollNo = rollNo;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public double getMarks() { return marks; }

    public void setMarks(double marks) {
        this.marks = marks;
        calculateGrade();
    }

    private void calculateGrade() {
        if (marks >= 85) grade = 'A';
        else if (marks >= 70) grade = 'B';
        else if (marks >= 50) grade = 'C';
        else grade = 'D';
    }

    @Override
    public void displayInfo() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
        System.out.println("Email   : " + email);
        System.out.println("Course  : " + course);
        System.out.println("Marks   : " + marks);
        System.out.println("Grade   : " + grade);
    }

    public String toFileData() {
        return rollNo + "|" + name + "|" + email + "|" + course + "|" + marks;
    }

    public static Student fromFileData(String line) {
        String[] p = line.split("\\|");
        return new Student(
                Integer.parseInt(p[0].trim()),
                p[1].trim(),
                p[2].trim(),
                p[3].trim(),
                Double.parseDouble(p[4].trim())
        );
    }
}

// =====================
// Interface
// =====================
interface RecordActions {
    void addStudent(Student s) throws Exception;
    void deleteStudent(String name) throws Exception;
    Student searchStudent(String name) throws Exception;
    void viewAllStudents();
    void saveToFile(String file);
    void loadFromFile(String file);
}

// =============================
// Student Manager Implementation
// =============================
class StudentManager implements RecordActions {

    private List<Student> students = new ArrayList<>();

    @Override
    public void addStudent(Student s) throws Exception {
        for (Student st : students)
            if (st.getRollNo() == s.getRollNo())
                throw new Exception("Duplicate Roll Number!");
        students.add(s);
        System.out.println("Student added successfully!");
    }

    @Override
    public void deleteStudent(String name) throws StudentNotFoundException {
        boolean removed = students.removeIf(s -> s.getName().equalsIgnoreCase(name));
        if (!removed)
            throw new StudentNotFoundException("No student found with name: " + name);
        System.out.println("Student deleted successfully!");
    }

    @Override
    public Student searchStudent(String name) throws StudentNotFoundException {
        for (Student s : students)
            if (s.getName().equalsIgnoreCase(name))
                return s;
        throw new StudentNotFoundException("Student not found.");
    }

    @Override
    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student s : students) {
            s.displayInfo();
            System.out.println("---------------------------------");
        }
    }

    @Override
    public void saveToFile(String file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Student s : students) {
                bw.write(s.toFileData());
                bw.newLine();
            }
            System.out.println("Records saved to file.");
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    @Override
    public void loadFromFile(String file) {
        File f = new File(file);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null)
                students.add(Student.fromFileData(line));

            System.out.println(students.size() + " records loaded from file.");
        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }

    public void sortByMarks() {
        students.sort((a, b) -> Double.compare(b.getMarks(), a.getMarks()));
        System.out.println("Sorted by marks (descending).");
    }
}

// =====================
// Loader Thread
// =====================
class Loader implements Runnable {
    @Override
    public void run() {
        System.out.print("Loading");
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(300);
                System.out.print(".");
            }
        } catch (Exception e) {}
        System.out.println();
    }
}

// =====================
// Main Program
// =====================
public class Main {

    private static final String DATA_FILE = "students.txt";

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        manager.loadFromFile(DATA_FILE);

        int choice = 0;

        do {
            System.out.println("\n===== Capstone Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Sort by Marks");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Enter valid number!");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();

            Thread t = new Thread(new Loader());
            t.start();
            t.join();

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Enter Roll No: ");
                        int roll = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        System.out.print("Enter Marks: ");
                        double marks = Double.parseDouble(sc.nextLine());

                        if (marks < 0 || marks > 100)
                            throw new Exception("Marks must be 0–100.");

                        manager.addStudent(new Student(roll, name, email, course, marks));

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    manager.viewAllStudents();
                    break;

                case 3:
                    System.out.print("Enter name to search: ");
                    String sname = sc.nextLine();
                    try {
                        Student s = manager.searchStudent(sname);
                        s.displayInfo();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter name to delete: ");
                    String dname = sc.nextLine();
                    try {
                        manager.deleteStudent(dname);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    manager.sortByMarks();
                    break;

                case 6:
                    manager.saveToFile(DATA_FILE);
                    System.out.println("Saved. Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}
