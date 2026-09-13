import java.util.Scanner;
import javax.swing.*;

String currentInput = "";
float firstNumber = 0;
String operator = "";

void main() {
    JFrame frame = new JFrame("My First Java App");
    frame.setSize(900, 900);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quits app when closed
    frame.setLayout(null);

    for (int i = 0; i <= 10; i++) {
        createbutton(frame, 50, 50, (i * 60), 50, String.valueOf(i), i);
    }

    createOperatorButton(frame, 50, 50, 50, 150, "+");

    createOperatorButton(frame, 50, 50, 150, 150, "-");

    createOperatorButton(frame, 50, 50, 250, 150, "/");

    createOperatorButton(frame, 50, 50, 350, 150, "*");


    createEqualsButton(frame, 50, 50, 550, 150, "=");

    createDeleteButton(frame, 50, 150, 200, 200, "Delete");

    createReverseButton(frame, 50, 150, 300, 400, "<----");


    frame.setVisible(true);
}

public void createReverseButton(JFrame frame, int height, int width, int xaxis, int yaxis, String text) {
    JButton btn = new JButton(text);
    btn.setBounds(xaxis, yaxis, width, height);
    frame.add(btn);

    btn.addActionListener(e -> {
        try {
            currentInput = currentInput.substring(0, currentInput.length() -1);
            System.out.println(currentInput);
        } catch (Exception ex) {
            System.out.println("Nothing to delete");
        }
    });
}

public void createbutton(JFrame frame, int height, int width, int xaxis, int yaxis, String text, float value) {

    //boolean[] hasBeenClicked = { false };
    JButton button2 = new JButton(text);

    button2.setBounds(xaxis, yaxis, width, height);
    frame.add(button2);

    button2.addActionListener(e -> {
        currentInput = currentInput + text;
        System.out.println("Screen: " + currentInput);

    });

    frame.revalidate();
    frame.repaint();
}

public void createDeleteButton(JFrame frame, int height, int width, int xaxis, int yaxis, String opText) {
    JButton btn = new JButton(opText);
    btn.setBounds(xaxis, yaxis, width, height);
    frame.add(btn);

    btn.addActionListener(e -> {
        currentInput = "";
    });
}

public void createOperatorButton(JFrame frame, int height, int width, int xaxis, int yaxis, String opText) {
    JButton btn = new JButton(opText);
    btn.setBounds(xaxis, yaxis, width, height);
    frame.add(btn);

    btn.addActionListener(e -> {
        firstNumber = Float.parseFloat(currentInput);
        operator = opText;

        System.out.println("Saved first number: " + firstNumber + " | Operator: " + operator);

        // Clear currentInput
        currentInput = "";
    });
}

public void createEqualsButton(JFrame frame, int height, int width, int xaxis, int yaxis, String text) {
    JButton btn = new JButton(text);
    btn.setBounds(xaxis, yaxis, width, height);
    frame.add(btn);

    btn.addActionListener(e -> {
        float secondNumber = Float.parseFloat(currentInput);

        System.out.println("Calculating: " + firstNumber + " " + operator + " " + secondNumber);

        calculate(firstNumber, secondNumber, operator);

        // Clear memory dont forget
        currentInput = "";
    });
}

void calculate(float firstnumber, float secondnumber, String operator) {
    System.out.println("Working as intented");
    switch (operator) {

        case "+":
            System.out.println(firstnumber + secondnumber);
        break;


        case "-":
            System.out.println(firstnumber - secondnumber);
            break;


        case "/":
            System.out.println(firstnumber / secondnumber);
            break;


        case "*":
            System.out.println(firstnumber * secondnumber);
            break;
    }
}

/*
public void calculator() {
    IO.println(String.format("Hello and welcome!"));

    Scanner s = new Scanner(System.in);

    System.out.println("Welcome to the best Calculator. Enter you first Number: ");

    try {
        float firstnum = s.nextFloat();

        System.out.println("Enter your operator (+, *, - or /: ");

        s.nextLine();

        String operator = s.nextLine();


        System.out.println("Enter your second number: ");

        float secondnum = s.nextFloat();

        calculate(firstnum, secondnum, operator);
    } catch (Exception e) {
        System.out.println("Error please enter correct numbers");
        calculator();
    }


}
public void calculate(float firstnum1, float secondnum1, String operator1) {
    if (operator1.equals("+")) {
        float ergebnis = firstnum1 += secondnum1;

    System.out.println(ergebnis);
    }
    if (operator1.equals("-")) {
        float ergebnis = firstnum1 -= secondnum1;

        System.out.println(ergebnis);
    }
    if (operator1.equals("/")) {
        float ergebnis = firstnum1 /= secondnum1;

        System.out.println(ergebnis);
    }
    if (operator1.equals("*")) {
        float ergebnis = firstnum1 *= secondnum1;

        System.out.println(ergebnis);
    }
    else {
        System.out.println("Something went wrong. You didnt input a correct operator try again.");
        calculator();
    }
}

public void calculateoop(float firstnum2, float secondnum2, String operator2) {
    switch (operator2) {
        case "+":
            System.out.println("Test successfull");

        case "-":
            System.out.println("Test -");

        case "/":
            System.out.println("Test /");

        case "*":
            System.out.println("TEST *");
            break;
    }
}
public void methodsoop() {

}
*/
