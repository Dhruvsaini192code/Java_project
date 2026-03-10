package HospitalManagementSystem;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    public Patient(Connection connection , Scanner scanner)
    {
        this.connection = connection;
        this.scanner = scanner;
    }

    // method to add patient
    public void addPatient()
    {
        System.out.println("Enter Patient Name : ");
        String name = scanner.next();
        System.out.println("Enter Patient Age : ");
        int age = scanner.nextInt();;
        System.out.println("Enter Patient Gender : ");
        String gender = scanner.next();

        try {
           String query = "INSERT INTO patients(name , age , gender) VALUES (? , ? , ?)";
           PreparedStatement ps = connection.prepareStatement(query);
           ps.setString(1 , name);
           ps.setInt(2 , age);
           ps.setString(3 , gender);
           int affectedRows = ps.executeUpdate();
           if(affectedRows > 0)
           {
               System.out.println("Patient Added Successfully");
           }
           else {
               System.out.println("Failed to add patient");
           }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    // method to view patients
    public void viewPatient()
    {
        String query = "SELECT * FROM patients";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Patients : ");
            while(rs.next()) {
                System.out.printf(" %12d %15s %12d %15s \n", rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("gender"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method to get Patient
    public boolean getPatientById(int id)
    {
        String query = "select * from patients where id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1 , id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
