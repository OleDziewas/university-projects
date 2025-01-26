package de.uniba.wiai.dsg.ajp.uebung1.fizzbuzz;

public class FizzBuzzImpl implements FizzBuzz{

    public void printGame(int limit) {
        String output;
        for (int i = 1; i <= limit; i++) {
            if (i%3==0 && i%5==0) {
                output = "FizzBuzz";
                System.out.println(output);
            }else if (i%3==0){
                output = "Fizz";
                System.out.println(output);
            }else if (i%5 ==0 ){
                output = "Buzz";
                System.out.println(output);
            }else{
                System.out.println(i);
            }
        }
    }
}
