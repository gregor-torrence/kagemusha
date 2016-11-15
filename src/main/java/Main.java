package main.java;

public class Main {

    public static void main(String[] args) {
        String greeting = "Hello World!";
        System.out.printf(" English greeting: %s\n", greeting);
        System.out.printf("Japanese greeting: %s\n", Kagemusha.convert(greeting));
    }

}
