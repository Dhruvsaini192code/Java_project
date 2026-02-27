package bank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class bankManagement {
    private static int NULL = 0;
    static Connection con = Connections.getConnection();

    // create account
    public static boolean createAccount(String name , int passCode)
    {
        if(name.isEmpty() || passCode == NULL)
        {
            System.out.println("all fields are required");
            return false;
        }

        try{
            String sql = "INSERT INTO customer(cname , balance ,pass_code) VALUES (? , 1000 , ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, passCode);

            int rows = ps.executeUpdate();
            if(rows == 1)
            {
                System.out.println("Account created successfull you can login");
                return true;
            }
        }catch (SQLIntegrityConstraintViolationException e)
        {
            System.out.println("username already exists , try another one");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // login account
    public static boolean loginAccount(String name, int passCode)
    {
        if(name.isEmpty() || passCode == NULL)
        {
            System.out.println("All fields are required");
            return false;
        }

        try{
            String sql = "SELECT * FROM customer WHERE cname = ? AND pass_code = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1 , name);
            ps.setInt(2 , passCode);
            ResultSet rs = ps.executeQuery();
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

            if(rs.next())
            {
                int senderAc =  rs.getInt("ac_no");
                int ch;

                while (true) {
                    System.out.println("\n Hello , " + rs.getString("cname") + "! what whould you like to do");
                    System.out.println("1) TRANSFER MONEY");
                    System.out.println("2) VIEW BALANCE");
                    System.out.println("3) LOGOUT");
                    System.out.println("Please enter you choice");
                    ch = Integer.parseInt(sc.readLine());

                    if (ch == 1) {
                        System.out.println("Enter your account no :");
                        int receiverAc = Integer.parseInt(sc.readLine());
                        System.out.println("Enter Amount :");
                        int amt = Integer.parseInt(sc.readLine());
                        if(transferMoney(senderAc , receiverAc , amt))
                        {
                            System.out.println("Transaction successfull");
                        }
                        else{
                            System.out.println("Transaction unsuccessfull");
                        }

                    } else if (ch == 2) {
                        getBalance(senderAc);
                    } else if (ch == 3) {
                        System.out.println("successfully logout");
                        break;
                    } else {
                        System.out.println("Invalid choice ! try again");
                    }
                }
                return true;
            }
            else {
                System.out.println("invalid username and password");
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }


    public static void getBalance(int actNo)
    {
        try{
            String sql = "SELECT * FROM customer WHERE ac_no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1 , actNo);
            ResultSet rs = ps.executeQuery();

            System.out.println("____________________________________________________________________");
            if (rs.next()) {
                System.out.println("____________________________________________________________________");
                System.out.printf("%12d %15s %10d.00 \n",
                        rs.getInt("ac_no"),
                        rs.getString("cname"),
                        rs.getInt("balance"));
            } else {
                System.out.println("No record found.");
            }
            System.out.println("____________________________________________________________________");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    //transfer money
    public static boolean transferMoney(int sender_ac , int receiver_ac , int amt)
    {
        if(receiver_ac == NULL || sender_ac == NULL)
        {
            System.out.println("All fields are required");
            return false;
        }

        try{
            con.setAutoCommit(false);

            String sql = "SELECT balance FROM customer WHERE ac_no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1 , sender_ac);
            ResultSet rs = ps.executeQuery();

            if(rs.next() && rs.getInt("balance") < amt)
            {
                System.out.println("Insufficiant balance");
                return false;
            }

            String debit = "UPDATE customer SET balance = balance - ? WHERE ac_no = ?";
            PreparedStatement psDebit = con.prepareStatement(debit);
            psDebit.setInt(2 , sender_ac);
            psDebit.setInt(1 , amt);
            psDebit.executeUpdate();

            String credit = "UPDATE customer SET balance = balance + ? WHERE ac_no = ?";
            PreparedStatement psCredit = con.prepareStatement(credit);
            psCredit.setInt(1 , amt);
            psCredit.setInt(2, receiver_ac);
            psCredit.executeUpdate();

            con.commit();
            return true;
        }
        catch (Exception e)
        {
            try{
                con.rollback();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    return false;
    }
}
