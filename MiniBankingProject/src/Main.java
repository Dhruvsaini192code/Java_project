import bank.bankManagement;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main{
    public static void main(String[] args) {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String name ;
        int pass_code;
        int ch;
        while(true)
        {
            System.out.println("\n===============================");
            System.out.println(" Welcome to InBank ");
            System.out.println("===============================");
            System.out.println("1) Create Account");
            System.out.println("2) Login Account");
            System.out.println("3) Exit");

            try{
                System.out.print("\nEnter choice");
                ch = Integer.parseInt(sc.readLine());

                switch (ch)
                {
                    case 1 -> {
                        System.out.print("Enter unique username");
                        name = sc.readLine();
                        System.out.print("Enter password");
                        pass_code = Integer.parseInt(sc.readLine());
                        if(bankManagement.createAccount(name , pass_code))
                        {
                            System.out.println("you can login from main menu");
                        }
                    }

                    case 2 -> {
                        System.out.print("Enter unique username");
                        name = sc.readLine();
                        System.out.print("Enter password");
                        pass_code = Integer.parseInt(sc.readLine());
                        if(!bankManagement.loginAccount(name , pass_code))
                        {
                            System.out.println("please enter valid login information");
                        }
                    }

                    case 3 -> {
                        System.out.println("chal nikal");
                        System.exit(0);
                    }

                    default -> System.out.println("Invalid input ! please try again");
                }
            }
            catch(Exception e)
            {
                System.out.println("Please enter valid input");
            }
        }
    }
}