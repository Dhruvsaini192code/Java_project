package HospitalManagementSystem;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hospital";
    private static final String username = "root";
    private static final String password = "root" ;

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url , username , password);
            Patient patient = new Patient(connection , scanner);
            Doctor doctor = new Doctor(connection);
            while(true)
            {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1) Add Patient");
                System.out.println("2) View Patients");
                System.out.println("3) View Doctors");
                System.out.println("4) Book Appointment");
                System.out.println("5) Exit");
                System.out.println("Enter Your Choice : ");
                int choice = scanner.nextInt();

                switch (choice)
                {
                    case 1 :
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2 :
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3 :
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4 :
                        bookAppointment(patient , doctor , connection , scanner);
                        System.out.println();
                        break;
                    case 5 :
                        return ;
                    default:
                        System.out.println("Enter valid choice");

                }

            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient , Doctor doctor , Connection connection , Scanner scanner)
    {
        System.out.println("Enter patient id : ");
        int patient_id = scanner.nextInt();
        System.out.println("Enter Doctor id : ");
        int doctor_id = scanner.nextInt();
        System.out.printf("Enter appointment date (YYYY-MM-DD)");
        String appointmentDate = scanner.next();
        if(patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id))
        {
            if(checkDoctorAvailablity(doctor_id , appointmentDate , connection))
            {
                String appointmentQuery = "INSERT INTO appointments(patient_id , doctor_id , appointment_date) VALUES(? , ? , ?)";
                try{
                    PreparedStatement ps = connection.prepareStatement(appointmentQuery);
                    ps.setInt(1 , patient_id);
                    ps.setInt(2 , doctor_id);
                    ps.setString(3 , appointmentDate);
                    int rowAffected = ps.executeUpdate();
                    if(rowAffected > 0)
                    {
                        System.out.println("Appointment booked");
                    }
                    else {
                        System.out.println("Failed to book Appointment");
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Doctor is not available at this date");
            }
        }else {
            System.out.println("Either doctor or patient doesn't exist");
        }

    }


    public static boolean checkDoctorAvailablity(int doctor_id , String appointmentDate , Connection connection)
    {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1 ,doctor_id);
            ps.setString(2 , appointmentDate);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                int count = rs.getInt(1);
                if(count == 0)
                {
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
