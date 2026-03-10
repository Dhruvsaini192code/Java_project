package HospitalManagementSystem;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner scanner;

    public Doctor(Connection connection)
    {
        this.connection = connection;
        this.scanner = scanner;
    }

    //method to view doctor by id
    public void viewDoctor()
    {
        String query = "select * from doctors";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Doctors : ");
            while(rs.next())
            {
                System.out.printf("%12d %15s %20s \n" , rs.getInt("id") , rs.getString("name") , rs.getString("specialization"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // method to check doctors
    public boolean getDoctorById(int id)
    {
        String query = "select * from doctors where id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1 ,id);
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
