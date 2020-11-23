package ui;

import exceptions.MultipleValidException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// runQuizApp() and its methods are similar to the code from TellerApp
public class QuizApp {
    private static final String JSON_STORE = "./data/quizList.json";
    private static final int MAX_ANSWERS = 4;
    private QuizList quizList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the application for the quizzes
    public QuizApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
            input.nextLine();
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
        System.out.println("Load a Quiz = v");
        System.out.println("Save a Quiz = s");
        System.out.println("Quit = q");
    }

    // MODIFIES: this
    // EFFECTS: initializes by adding a quiz list and one quiz instruction demo
    private void initial() {
        Quiz demoQuiz = new Quiz("Startup Quiz");
        demoQuiz.addQuestion(new Question("Select the answer that says 'true'"));
        try {
            demoQuiz.getQuestion(0).addAnswer(new Answer("false"));
        } catch (MultipleValidException e) {
            System.out.println("failed to add new answer: Question already had one valid answer");
        }
        try {
            demoQuiz.getQuestion(0).addAnswer(new Answer("true"));
        } catch (MultipleValidException e) {
            System.out.println("failed to add new answer: Question already had one valid answer");
        }
        demoQuiz.getQuestion(0).getAnswer(1).setValid();

        quizList = new QuizList("Remember to save!");
        quizList.addQuiz(demoQuiz);

        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: causes the effect of the command inputted if valid
    private void cmdValue(String cmd) {
        switch (cmd) {
            case "l":
                produceList();
                break;
            case "c":
                createQuiz();
                break;
            case "v":
                loadQuizList();
                break;
            case "s":
                saveQuizList();
                break;
            default:
                System.out.println("not a valid command input"); // change so code doesn't redisplay menu

                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: shows quiz list. Input matching integer to start quiz. Else sent to menu
    private void produceList() {
        int cmd;
        for (int i = 0; i < quizList.listSize(); i++) {
            System.out.println((i + 1) + " - " + quizList.getQuiz(i).getName());
        }
        System.out.println("enter quiz number to access quiz");
        System.out.println("enter an integer not in the list to go back to the menu");

        while (!input.hasNextInt()) {
            input.nextLine();
            System.out.println("Please input an integer");
        }
        cmd = input.nextInt();
        input.nextLine();
        if (cmd > 0 && cmd <= quizList.listSize()) {
            quizUser(quizList.getQuiz(cmd - 1));
        }
    }

    // MODIFIES: this
    // EFFECTS: starts the quiz for the user. After questions answered, sent to quiz list with score
    public void quizUser(Quiz quiz) {
        int correct = 0;
        for (int i = 0; i < quiz.listSize(); i++) {
            System.out.println(quiz.getQuestion(i).getQues());
            correct += quizAnswer(quiz.getQuestion(i));
        }
        System.out.println("Score: " + correct + "/" + quiz.listSize() + "\n");
        produceList();
    }

    // MODIFIES: this
    // EFFECTS: produces answer list. user chooses an answer. Adds a point to score if correct
    public int quizAnswer(Question question) {
        int cmd;

        for (int i = 0; i < question.listSize(); i++) {
            System.out.println((i + 1) + " - " + question.getAnswer(i).getStr());
        }
        do {
            System.out.println("Please input an answer integer from the list");
            while (!input.hasNextInt()) {
                input.nextLine();
                System.out.println("Please input an integer");
            }
            cmd = input.nextInt();
            input.nextLine();
        } while (cmd < 1 || cmd > question.listSize());

        if (question.getAnswer(cmd - 1).isValid()) {
            System.out.println("Correct!\n");
            return 1;
        }
        System.out.println("Incorrect\n");
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: creates a quiz. Allow creation of questions until input "done" is inputted
    private void createQuiz() {
        System.out.println("Welcome to the quiz creator! Please name the quiz: ");
        while (!input.hasNextLine()) {
            input.nextLine();
            System.out.println("Please input a string, not an integer");
        }
        String name = input.nextLine();
        Quiz quiz = new Quiz(name);

        System.out.println("This quiz's name will be " + quiz.getName() + "\n");

        System.out.println("Next we will create the questions");

        while (true) {

            System.out.println("Enter a new question");
            System.out.println("Enter 'done' to be done with question creation\n");
            while (!input.hasNextLine()) {
                input.nextLine();
                System.out.println("Please input a string, not an integer");
            }
            name = input.nextLine();
            if (name.equals("done")) {
                break;
            }
            quiz.addQuestion(createQuestion(name));
        }
        quizList.addQuiz(quiz);
    }

    // MODIFIES: this
    // EFFECTS: creates a question with answers and returns it to the quiz
    private Question createQuestion(String question) {
        String cmd;
        Question ques = new Question(question);

        System.out.println("Add some choices for answers to this question. Only one should be correct!\n");
        System.out.println("Input 'done' if done with adding answers");

        for (int i = 0; i < MAX_ANSWERS; i++) {
            while (!input.hasNextLine()) {
                input.nextLine();
                System.out.println("Please input a sentence, not an integer");
            }
            cmd = input.nextLine();
            if (cmd.equals("done")) {
                break;
            }
            try {
                ques.addAnswer((new Answer(cmd)));
            } catch (MultipleValidException e) {
                System.out.println("failed to add new answer: Question already had one valid answer");
            }
        }
        if (ques.listSize() > 0) {
            setTrueAnswer(ques);
        }
        return ques;
    }

    // MODIFIES: this
    // EFFECTS: sets one of the answers given by the user as the true answer
    private void setTrueAnswer(Question question) {
        int correct;
        for (int i = 0; i < question.listSize(); i++) {
            System.out.println((i + 1) + " - " + question.getAnswer(i).getStr());
        }
        System.out.println("Which answer is the correct one?");
        do {
            System.out.println("Please input the correct answer's integer from the list");
            while (!input.hasNextInt()) {
                input.nextLine();
                System.out.println("Please input an integer");
            }
            correct = input.nextInt();
            input.nextLine();
        } while (correct < 1 || correct > question.listSize());

        question.getAnswer(correct - 1).setValid();
        System.out.println("Answer " + correct + " has been chosen");
    }

    // EFFECTS: saves the QuizList to file
    private void saveQuizList() {
        try {
            jsonWriter.open();
            jsonWriter.write(quizList);
            jsonWriter.close();
            System.out.println("Saved " + quizList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads QuizList from file
    private void loadQuizList() {
        try {
            quizList = jsonReader.read();
            System.out.println("Loaded " + quizList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
