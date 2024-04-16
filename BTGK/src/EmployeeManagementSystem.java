import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class EmployeeManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private Connection con = null;
    private Statement stmt;
    private List<Employee> employees;


    public EmployeeManagementSystem() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ktxck;user=sa;password=123456");
            stmt = con.createStatement();
            System.out.println("connect completed");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        employees = new ArrayList<>();
    }
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nEmployee Management System Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Delete Employee");
            System.out.println("4. Save Employees to File");
            System.out.println("5. Load Employees from File");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    saveEmployeesToFile(employees, "employees.txt");

                    break;
                case 5:
                    loadEmployeesFromFile("employees.txt");

                    break;
                case 0:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println(" Please enter a number between 0 and 5.");
            }
        } while (choice != 0);
        scanner.close();
    }

    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        system.start();
    }
    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("There are no employees to display.");
        } else {
            System.out.println("List of employees:");
            for (Employee employee : employees) {
                employee.showInfo();
            }
        }
    }

    // Thêm một nhân viên vào cơ sở dữ liệu
    private static void addEmployee() {
        System.out.println("Enter employee details:");
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("BirthDay: ");
        String birthDay = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Employee Type: ");
        String employeeType = scanner.nextLine();

        if (employeeType.equalsIgnoreCase("Experience")) {
            // Nếu là nhân viên có kinh nghiệm
            System.out.print("ExpInYear: ");
            int expInYear = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            System.out.print("ProSkill: ");
            String proSkill = scanner.nextLine();

            // Tạo và thêm nhân viên kinh nghiệm vào cơ sở dữ liệu
            Experience employee = new Experience(fullName, birthDay, phone, email, employeeType, expInYear, proSkill);
            if (dataConnection.addEmployee(employee)) {
                System.out.println("Employee added successfully!");
            } else {
                System.out.println("Failed to add employee.");
            }
        } else if (employeeType.equalsIgnoreCase("Fresher")) {
            // Nếu là nhân viên mới ra trường
            System.out.print("Graduation Date: ");
            String graduationDate = scanner.nextLine();
            System.out.print("Graduation Rank: ");
            String graduationRank = scanner.nextLine();
            System.out.print("Education: ");
            String education = scanner.nextLine();

            // Tạo và thêm nhân viên mới ra trường vào cơ sở dữ liệu
            Fresher employee = new Fresher(fullName, birthDay, phone, email, employeeType, graduationDate, graduationRank, education);
            if (dataConnection.addEmployee(employee)) {
                System.out.println("Employee added successfully!");
            } else {
                System.out.println("Failed to add employee.");
            }
        } else if (employeeType.equalsIgnoreCase("Intern")) {
            // Nếu là nhân viên thực tập
            System.out.print("Majors: ");
            String majors = scanner.nextLine();
            System.out.print("Semester: ");
            int semester = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            System.out.print("University Name: ");
            String universityName = scanner.nextLine();

            // Tạo và thêm nhân viên thực tập vào cơ sở dữ liệu
            Intern employee = new Intern(fullName, birthDay, phone, email, employeeType, majors, semester, universityName);
            if (dataConnection.addEmployee(employee)) {
                System.out.println("Employee added successfully!");
            } else {
                System.out.println("Failed to add employee.");
            }
        } else {
            System.out.println("Invalid employee type.");
        }
    }

    // Xóa một nhân viên từ cơ sở dữ liệu
    private static void deleteEmployee() {
        System.out.print("Enter employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (dataConnection.deleteEmployee(id)) {
            System.out.println("Employee with ID " + id + " deleted successfully!");
        } else {
            System.out.println("Failed to delete employee with ID " + id + ".");
        }
    }

    // Cập nhật thông tin của một nhân viên trong cơ sở dữ liệu
    private static void updateEmployee() {
        // Nhập thông tin mới cho nhân viên
        System.out.print("Enter employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.println("Enter new details for employee with ID " + id + ":");
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("BirthDay: ");
        String birthDay = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Employee Type: ");
        String employeeType = scanner.nextLine();

        if (employeeType.equalsIgnoreCase("Experience")) {
            System.out.print("ExpInYear: ");
            int expInYear = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            System.out.print("ProSkill: ");
            String proSkill = scanner.nextLine();

            // Tạo và cập nhật thông tin nhân viên kinh nghiệm trong cơ sở dữ liệu
            Experience employee = new Experience(ID, fullName, birthDay, phone, email, employeeType, expInYear, proSkill);
            if (dataConnection.updateEmployee(employee)) {
                System.out.println("Employee with ID " + id + " updated successfully!");
            } else {
                System.out.println("Failed to update employee with ID " + id + ".");
            }
        } else if (employeeType.equalsIgnoreCase("Fresher")) {
            System.out.print("Graduation Date: ");
            String graduationDate = scanner.nextLine();
            System.out.print("Graduation Rank: ");
            String graduationRank = scanner.nextLine();
            System.out.print("Education: ");
            String education = scanner.nextLine();

            // Tạo và cập nhật thông tin nhân viên mới ra trường trong cơ sở dữ liệu
            Fresher employee = new Fresher(ID, fullName, birthDay, phone, email, employeeType, graduationDate, graduationRank, education);
            if (dataConnection.updateEmployee(employee)) {
                System.out.println("Employee with ID " + id + " updated successfully!");
            } else {
                System.out.println("Failed to update employee with ID " + id + ".");
            }
        } else if (employeeType.equalsIgnoreCase("Intern")) {
            System.out.print("Majors: ");
            String majors = scanner.nextLine();
            System.out.print("Semester: ");
            int semester = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            System.out.print("University Name: ");
            String universityName = scanner.nextLine();

            // Tạo và cập nhật thông tin nhân viên thực tập trong cơ sở dữ liệu
            Intern employee = new Intern(ID, fullName, birthDay, phone, email, employeeType, majors, semester, universityName);
            if (dataConnection.updateEmployee(employee)) {
                System.out.println("Employee with ID " + id + " updated successfully!");
            } else {
                System.out.println("Failed to update employee with ID " + id + ".");
            }
        } else {
            System.out.println("Invalid employee type.");
        }
    }
    private static void saveEmployeesToFile(List<Employee> employees, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Employee employee : employees) {
                writer.write(employee.toString());
                writer.write(System.lineSeparator());
            }
            System.out.println("Employee information written to file successfully!");
        } catch (IOException e) {
            System.out.println("Failed to write employee information to file: " + e.getMessage());
        }
    }

    // Đọc thông tin nhân viên từ file
    private void loadEmployeesFromFile(String fileName) {
        // Tạo danh sách mới để lưu trữ thông tin của các nhân viên từ file
        List<Employee> loadedEmployees = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                // Tạo đối tượng Employee từ dữ liệu trong file và thêm vào danh sách
                Employee employee = new Employee(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                loadedEmployees.add(employee);
            }
            System.out.println("Employee information read from file successfully!");
        } catch (IOException e) {
            System.out.println("Failed to read employee information from file: " + e.getMessage());
        }

        // Gán danh sách nhân viên đã tải từ file vào danh sách chính
        this.employees = loadedEmployees;
    }


}


