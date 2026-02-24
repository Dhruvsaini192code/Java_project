import java.util.Scanner;

class Main{
    public static void main(String[] args) {
        System.out.println("This is the Number Guessing Game");
        System.out.println("Rules:");
        System.out.println("You have 10 attempts to guess the number");
        System.out.println("The number must be in range of 1 to 100");
        int num = (int)(Math.random() * 100) + 1;
        Scanner sc = new Scanner(System.in);
        boolean win = false;
        for(int i = 1 ; i <= 10 ; i++)
        {
            System.out.println("Please enter the number");
            int a = sc.nextInt();
            if(a == num)
            {
                System.out.println("You guess is correct");
                win = true;
                break;   
            }
            else if(a < num)
            {
                System.out.println("think something big");
            }
            else{
                System.out.println("think something small");
            }
        }

        sc.close();

        if(win)
        {
            System.out.println("congratualtions you won the game");
        }
        else{
            System.out.println("you lose the game");
        }
    }
}