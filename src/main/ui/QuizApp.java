package ui;

import model.*;

import java.util.Scanner;

// runQuizApp() and its methods are similar to the code from TellerApp
public class QuizApp {
    private static final int MAX_ANSWERS = 4;
    private QuizList quizList;
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
        System.out.println("Quit = q");
    }

    // MODIFIES: this
    // EFFECTS: initializes by adding a quiz list and one quiz instruction demo
    private void initial() {
        Quiz demoQuiz = new Quiz("Startup Quiz");
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
        } else {
            System.out.println("not a valid command input"); // change so code doesn't redisplay menu
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
        for (int i = 0; i < quiz.getQuestionList().listSize(); i++) {
            System.out.println(quiz.getQuestionList().getQuestion(i).getQues());
            correct += quizAnswer(quiz.getQuestionList().getQuestion(i));
        }
        System.out.println("Score: " + correct + "/" + quiz.getQuestionList().listSize() + "\n");
        produceList();
    }

    // MODIFIES: this
    // EFFECTS: produces answer list. user chooses an answer. Adds a point to score if correct
    public int quizAnswer(Question question) {
        int cmd;

        for (int i = 0; i < question.getAnswerList().listSize(); i++) {
            System.out.println((i + 1) + " - " + question.getAnswerList().getAnswer(i).getStr());
        }
        do {
            System.out.println("Please input an answer integer from the list");
            while (!input.hasNextInt()) {
                input.nextLine();
                System.out.println("Please input an integer");
            }
            cmd = input.nextInt();
            input.nextLine();
        } while (cmd < 1 || cmd > question.getAnswerList().listSize());

        if (question.getAnswerList().getAnswer(cmd - 1).isValid()) {
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
            quiz.getQuestionList().addQuestion(createQuestion(name));
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
            ques.getAnswerList().addAnswer((new Answer(cmd)));
        }
        if (ques.getAnswerList().listSize() > 0) {
            setTrueAnswer(ques.getAnswerList());
        }
        return ques;
    }

    // MODIFIES: this
    // EFFECTS: sets one of the answers given by the user as the true answer
    private void setTrueAnswer(AnswerList answerList) {
        int correct;
        for (int i = 0; i < answerList.listSize(); i++) {
            System.out.println((i + 1) + " - " + answerList.getAnswer(i).getStr());
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
        } while (correct < 1 || correct > answerList.listSize());

        answerList.getAnswer(correct - 1).setValid();
        System.out.println("Answer " + correct + " has been chosen");
    }
}
