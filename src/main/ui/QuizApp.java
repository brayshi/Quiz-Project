package ui;

import model.*;

import java.util.Scanner;

//Lots of code is similar to the code from TellerApp
public class QuizApp {
    private static final int MAX_ANSWERS = 4;
    private QuizList quizList;
    private Quiz demoQuiz;
    private Scanner input;

    // EFFECTS: runs the application for the quizzes
    public QuizApp() {
        runQuizApp();
    }

    // MODIFIES: this
    // EFFECTS: checks what is inputted by the quiz application
    private void runQuizApp() {
        String cmd;
        boolean cont = true;

        initial();

        while (cont) {
            menu();
            cmd = input.next();
            cmd = cmd.toLowerCase();

            if (cmd.equals("q")) {
                cont = false;
            } else {
                cmdValue(cmd);
            }
        }
    }

    // EFFECTS: creates console application's main menu for the quiz application
    private void menu() {
        System.out.println("Welcome to the main menu!");
        System.out.println("Choose from the following choices:");
        System.out.println("List of Quizzes = l");
        System.out.println("Create a Quiz = c");
        System.out.println("Edit a Quiz = e");
        System.out.println("Quit = q");
    }

    // MODIFIES: this
    // EFFECTS: initializes by adding a quiz list and one quiz instruction demo
    private void initial() {
        demoQuiz = new Quiz("Startup Quiz");
        demoQuiz.getQuestionList().addQuestion(new Question("Select the answer that says 'true'"));
        demoQuiz.getQuestionList().getQuestion(0).getAnswerList().addAnswer(new Answer("false"));
        demoQuiz.getQuestionList().getQuestion(0).getAnswerList().addAnswer(new Answer("true"));
        demoQuiz.getQuestionList().getQuestion(0).getAnswerList().getAnswer(1).setValid();

        quizList = new QuizList();
        quizList.addQuiz(demoQuiz);

        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: causes the effect of the command inputted if valid
    private void cmdValue(String cmd) {
        if (cmd.equals("l")) {
            produceList();
        } else if (cmd.equals("c")) {
            createQuiz();
        } else if (cmd.equals("e")) {
            editQuiz();
        } else {
            System.out.println("not a valid command input");
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the list of quizzes. Input integer matching quiz to start quiz. Other inputs send back to menu
    private void produceList() {
        for (int i = 1; i < quizList.listSize(); i++) {
            System.out.println(i + ": " + quizList.getQuiz(i).getName());
        }
        System.out.println("enter quiz number to access quiz");
        System.out.println("enter 'b' to go back to the menu");

        input = new Scanner(System.in);
        int cmd = input.nextInt();

        if (cmd > 0 && cmd <= quizList.listSize()) {
            //stub
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a quiz. Allow creation of questions until input "done" is inputted
    private void createQuiz() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: removes specified quiz questions, question answers, and then adds quiz questions, question answers
    private void editQuiz() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: creates a question
    private void createQuestion(String question) {
        Question ques = new Question(question);
    }

    // MODIFIES: this
    // EFFECTS: creates an answer
    private void createAnswer(String answer) {
        Answer ans = new Answer(answer);
    }
}
