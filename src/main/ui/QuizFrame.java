package ui;

import exceptions.MultipleValidException;
import model.Answer;
import model.Question;
import model.Quiz;
import model.QuizList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class QuizFrame extends JFrame {

    private static final String JSON_STORE = "./data/quizList.json";
    private CardLayout cardLayout = new CardLayout();
    private JPanel cards;
    private QuizList quizList;
    private int marks;
    private int totalMarks;
    private String quizTitle;
    private int initial;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs a quiz frame of card layout. Begins with the main menu
    public QuizFrame() {
        initial();
        initial = 0;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        cards = new JPanel();
        cards.setLayout(cardLayout);


        cards.add(mainMenu(), "0");
        cards.add(chooseQuiz(), "1");
        cards.add(resultsPanel(), "2");
        cards.add(createQuizTitle(), "create");
        cards.add(createQuestion(), "createQuestion");
        while (initial < quizList.listSize()) {
            cards.add(quizTester(quizList.getQuiz(initial)), Integer.toString(initial + 3));
            initial++;
        }
        add(cards);
    }

    // MODIFIES: this
    // EFFECTS: initializes the first quiz and its objects.
    private void initial() {
        Quiz demoQuiz = new Quiz("Startup Quiz");
        createDemoQuestions(demoQuiz);

        demoQuiz.getQuestion(0).getAnswer(1).setValid();
        demoQuiz.getQuestion(1).getAnswer(0).setValid();

        quizList = new QuizList("Quiz List");
        quizList.addQuiz(demoQuiz);
    }

    private void createDemoQuestions(Quiz demoQuiz) {
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

        demoQuiz.addQuestion(new Question("yodel lay hee hoo"));
        try {
            demoQuiz.getQuestion(1).addAnswer(new Answer("stop it"));
        } catch (MultipleValidException e) {
            System.out.println("failed to add new answer: Question already had one valid answer");
        }
        try {
            demoQuiz.getQuestion(1).addAnswer(new Answer("No keep yodeling"));
        } catch (MultipleValidException e) {
            System.out.println("failed to add new answer: Question already had one valid answer");
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the main menu that the user will be using
    private JPanel mainMenu() {
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(0, 2));

        JLabel saveLoadConfirmLabel = new JLabel("");
        saveLoadConfirmLabel.setForeground(Color.BLUE);

        JButton quizButton = new JButton("Quiz List");
        quizButton.addActionListener(e -> cardLayout.show(cards, "1"));

        // create Swing components
        JButton createButton = new JButton("Create Quiz");
        createButton.addActionListener(e -> cardLayout.show(cards, "create"));

        JButton loadButton = new JButton("Load List");
        loadButton.addActionListener(loadActionListener(saveLoadConfirmLabel));
        JButton saveButton = new JButton("Save List");
        saveButton.addActionListener(saveActionListener(saveLoadConfirmLabel));

        // add Swing components
        mainMenu.add(quizButton);
        mainMenu.add(createButton);
        mainMenu.add(loadButton);
        mainMenu.add(saveButton);
        mainMenu.add(saveLoadConfirmLabel);

        return mainMenu;
    }

    // MODIFIES: this
    // EFFECTS: action listener for loading json file
    private ActionListener loadActionListener(JLabel saveLoadConfirmLabel) {
        return e -> {
            try {
                loadQuizList();
                saveLoadConfirmLabel.setText("Loaded up file from " + JSON_STORE);
            } catch (IOException ioException) {
                saveLoadConfirmLabel.setText("Unable to load file from " + JSON_STORE);
            }
        };
    }

    // MODIIES: this
    // EFFECTS: action listener for saving to json file
    private ActionListener saveActionListener(JLabel saveLoadConfirmLabel) {
        return e -> {
            try {
                saveQuizList();
                saveLoadConfirmLabel.setText("Saved file to " + JSON_STORE);
            } catch (FileNotFoundException fileNotFoundException) {
                saveLoadConfirmLabel.setText("Unable to save file to " + JSON_STORE);
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: returns the quiz panel where user chooses which quiz to take
    private JPanel chooseQuiz() {

        JPanel chooseQuiz = new JPanel();
        chooseQuiz.setLayout(new GridLayout(0, 1));

        // create Swing components
        for (int i = 0; i < quizList.listSize(); i++) {

            int finalI = i;

            JButton button = new JButton(quizList.getQuiz(i).getName());
            button.addActionListener(e -> {
                cardLayout.show(cards, Integer.toString(finalI + 3));
                totalMarks = quizList.getQuiz(finalI).listSize();
            });

            chooseQuiz.add(button);
        }

        return chooseQuiz;
    }

    // MODIFIES: this
    // EFFECTS: Creates quiz tester for a user. Rotates new question in after present question is answered
    private JPanel quizTester(Quiz quiz) {

        JPanel quizPanel = new JPanel();

        JLabel questionLabel = new JLabel();
        JButton submitButton = new JButton("Submit");
        final ButtonGroup[] buttonGroup = {new ButtonGroup()};
        JLabel resultsLabel = new JLabel("");
        JButton nextButton = new JButton("next");

        quizPanel.setLayout(new GridLayout(0, 1));
        questionLabel.setText(quiz.getQuestion(0).getQues());
        submitButton.setEnabled(false);
        nextButton.setEnabled(false);

        createQuizComponents(quiz, quizPanel, questionLabel, submitButton, buttonGroup, resultsLabel, nextButton);

        return quizPanel;
    }

    // MODIFIES: this
    // EFFECTS: helper function to construct the contents within the quiz tester
    private void createQuizComponents(Quiz quiz, JPanel chooseAnswer, JLabel questionLabel,
                                      JButton submitButton, ButtonGroup[] buttonGroup,
                                      JLabel resultsLabel, JButton nextButton) {
        final int[] questionNum = {0};
        final int[] i = {1};

        chooseAnswer.add(questionLabel);

        createChoices(quiz.getQuestion(0), chooseAnswer, buttonGroup[0], submitButton);


        chooseAnswer.add(submitButton);
        chooseAnswer.add(nextButton);
        chooseAnswer.add(resultsLabel);

        nextButton.addActionListener(e -> {
            if (i[0] < quiz.listSize()) {
                nextQuestion(quiz.getQuestion(i[0]), questionLabel, submitButton,
                        buttonGroup[0], resultsLabel, nextButton);
                i[0]++;
            } else {
                i[0] = 1;
                finishQuiz(quiz, questionLabel, submitButton, resultsLabel, nextButton, buttonGroup[0]);
                questionNum[0] = 0;
            }
        });



        submitButton.addActionListener(e -> {
            checkCorrectAns(buttonGroup[0], quiz.getQuestion(questionNum[0]),
                    resultsLabel, submitButton, nextButton);
            questionNum[0]++;
        });
    }

    // MODIFIES: this
    // EFFECTS: replaces old question with the next question in the quiz
    private void nextQuestion(Question question, JLabel questionLabel, JButton submitButton,
                              ButtonGroup buttonGroup, JLabel resultsLabel, JButton nextButton) {
        questionLabel.setText(question.getQues());
        replaceChoices(question, buttonGroup, resultsLabel);
        submitButton.setEnabled(false);
        nextButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: Sends user to results panel after setting the quiz to its default state
    private void finishQuiz(Quiz quiz, JLabel questionLabel, JButton submitButton,
                            JLabel resultsLabel, JButton nextButton, ButtonGroup buttonGroup2) {
        questionLabel.setText(quiz.getQuestion(0).getQues());
        replaceChoices(quiz.getQuestion(0), buttonGroup2, resultsLabel);
        submitButton.setEnabled(false);
        nextButton.setEnabled(false);
        cardLayout.show(cards, "2");
    }

    // MODIFIES: this
    // EFFECTS: produces the quiz choices for the user's quiz test
    public void createChoices(Question question, JPanel chooseAnswer, ButtonGroup buttonGroup, JButton submitButton) {
        for (int i = 0; i < question.listSize(); i++) {

            // add radioButton for choice on left
            JRadioButton radioButton = new JRadioButton(question.getAnswer(i).getStr());
            chooseAnswer.add(radioButton);
            buttonGroup.add(radioButton);

            radioButton.addActionListener(e -> submitButton.setEnabled(true));
        }
        for (int j = 0; j < 4 - question.listSize(); j++) {
            JRadioButton emptyRadioButton = new JRadioButton();
            emptyRadioButton.setEnabled(false);
            emptyRadioButton.setVisible(false);
            chooseAnswer.add(emptyRadioButton);
            buttonGroup.add(emptyRadioButton);
            emptyRadioButton.addActionListener(e -> submitButton.setEnabled(true));
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if answer is correct. If it is, produces a green "Correct!" Else produces red "Incorrect"
    public void checkCorrectAns(ButtonGroup buttonGroup,
                                Question question,
                                JLabel resultsLabel,
                                JButton submitButton,
                                JButton nextButton) {
        int i = 0;
        ArrayList<AbstractButton> buttonList = Collections.list(buttonGroup.getElements());
        for (AbstractButton button : buttonList) {

            if (button.isSelected()) {
                if (question.getAnswer(i).isValid()) {
                    resultsLabel.setForeground(Color.GREEN);
                    resultsLabel.setText("Correct!");
                    marks++;

                } else {
                    resultsLabel.setForeground(Color.RED);
                    resultsLabel.setText("Incorrect");
                }
                submitButton.setEnabled(false);
                nextButton.setEnabled(true);
                break;
            }
            i++;
        }
        for (AbstractButton button : buttonList) {
            button.setEnabled(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: replaces the previous question's answer list with the next question's answer list
    private void replaceChoices(Question question, ButtonGroup buttonGroup, JLabel resultsLabel) {
        int i = 0;
        buttonGroup.clearSelection();

        ArrayList<AbstractButton> buttonList = Collections.list(buttonGroup.getElements());
        for (AbstractButton button : buttonList) {
            if (i < question.listSize()) {
                button.setText(question.getAnswer(i).getStr());
                button.setEnabled(true);
                button.setVisible(true);
            } else {
                button.setText("");
                button.setEnabled(false);
                button.setVisible(false);
            }
            i++;
        }
        resultsLabel.setText("");
    }

    // MODIFIES: this
    // EFFECTS: creates a result panel that gives the user their mark and appears after the user finishes their quiz.
    private JPanel resultsPanel() {

        JPanel results = new JPanel();
        results.setLayout(new GridLayout(0, 1));
        JButton revealMark = new JButton("Click me to reveal your mark!");
        JLabel resultsLabel = new JLabel();
        JButton mainMenuButton = new JButton("Return to Main Menu");

        results.add(revealMark);
        results.add(resultsLabel);
        results.add(mainMenuButton);
        mainMenuButton.setVisible(false);

        revealMark.addActionListener(e -> {
            revealMark.setVisible(false);
            resultsLabel.setText(marks + " / " + totalMarks);
            mainMenuButton.setVisible(true);
        });

        mainMenuButton.addActionListener(e -> {
            marks = 0;
            totalMarks = 0;
            cardLayout.show(cards, "0");
            revealMark.setVisible(true);
            resultsLabel.setText("");
            mainMenuButton.setVisible(false);
        });

        return results;
    }

    // MODIFIES: this
    // EFFECTS: produces the quiz title creator that allows the user to name their new quiz
    private JPanel createQuizTitle() {

        JPanel quizCreator = new JPanel();

        JTextField titleField = new JTextField();
        titleField.setColumns(20);
        JButton confirmButton = new JButton("Confirm input");
        confirmButton.setEnabled(false);

        quizCreator.add(new JLabel("Input quiz title"));

        titleField.getDocument().addDocumentListener(titleFieldListener(titleField, confirmButton));
        quizCreator.add(titleField);


        quizCreator.add(confirmButton);
        confirmButton.addActionListener(e -> {
            quizTitle = titleField.getText();
            cardLayout.show(cards, "createQuestion");
        });

        return quizCreator;
    }

    // EFFECTS: checks if any text is within the quiz field before allowing the user to confirm the title
    private DocumentListener titleFieldListener(JTextField titleField, JButton confirmButton) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            private void changed() {
                confirmButton.setEnabled(!titleField.getText().equals(""));
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: accepts inputs for question and answer strings alongside which answer (singular) is the valid one
    private JPanel createQuestion() {
        ArrayList<JTextField> textFields = new ArrayList<>();
        Quiz quiz = new Quiz(quizTitle);

        JPanel createQuestion = new JPanel();
        createQuestion.setLayout(new GridLayout(0, 3));

        createQuestion.add(new JLabel("Question"));

        JTextField questionField = new JTextField();
        questionField.setColumns(20);
        textFields.add(questionField);
        createQuestion.add(questionField);

        createQuestion.add(new JLabel("True (On)/False (Off)"));

        ButtonGroup buttonGroup = new ButtonGroup();
        JLabel confirmLabel = new JLabel("");

        createAnswerFields(textFields, createQuestion, buttonGroup, confirmLabel);

        JButton addButton = new JButton("Add components");
        JButton finishButton = new JButton("Finish quiz (add last question before clicking!)");

        finishButton.addActionListener(finishListener(confirmLabel, quiz, buttonGroup, textFields));
        addButton.addActionListener(confirmActionListener(quiz, textFields, confirmLabel, buttonGroup));

        createQuestion.add(addButton);
        createQuestion.add(finishButton);
        createQuestion.add(confirmLabel);

        return createQuestion;
    }

    // MODIFIES: this
    // EFFECTS: produces the answer fields for the question creator panel
    private void createAnswerFields(ArrayList<JTextField> textFields,
                                    JPanel createQuestion,
                                    ButtonGroup buttonGroup,
                                    JLabel confirmLabel) {
        for (int i = 0; i < 4; i++) {
            createQuestion.add(new JLabel("Answer " + (i + 1)));
            JTextField answerField = new JTextField();
            JRadioButton radioButton = new JRadioButton();
            radioButton.setEnabled(false);
            answerField.setColumns(20);
            answerField.getDocument().addDocumentListener(answerFieldListener(confirmLabel, answerField, radioButton));
            textFields.add(answerField);
            createQuestion.add(answerField);
            createQuestion.add(radioButton);
            buttonGroup.add(radioButton);
        }
    }

    // EFFECTS: only allows an answer field to be the true answer if it contains a string
    private DocumentListener answerFieldListener(JLabel confirmLabel,
                                                 JTextField answerField,
                                                 JRadioButton radioButton) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                change();
            }

            private void change() {
                if (answerField.getText().equals("")) {
                    radioButton.setEnabled(false);
                } else {
                    radioButton.setEnabled(true);
                    confirmLabel.setText("");
                }
            }
        };
    }

    // EFFECTS: when confirm button is pressed, adds question with all of its answers to the quiz
    private ActionListener confirmActionListener(Quiz quiz,
                                                 ArrayList<JTextField> textFields,
                                                 JLabel confirmLabel,
                                                 ButtonGroup buttonGroup) {

        return e -> {
            int i = 1;
            Question question = new Question(textFields.get(0).getText());
            ArrayList<AbstractButton> buttonList = Collections.list(buttonGroup.getElements());
            for (AbstractButton button : buttonList) {
                Answer answer = new Answer(textFields.get(i).getText());
                if (button.isSelected()) {
                    answer.setValid();
                }
                checkAnswerThrowsException(question, answer);
                textFields.get(i).setText("");
                button.setEnabled(false);
                i++;
            }
            quiz.addQuestion(question);
            textFields.get(0).setText("");
            buttonGroup.clearSelection();
            confirmLabel.setForeground(Color.BLUE);
            confirmLabel.setText("Added question to the quiz");
        };
    }

    private void checkAnswerThrowsException(Question question, Answer answer) {
        if (!answer.equals(new Answer(""))) {
            try {
                question.addAnswer(answer);
            } catch (MultipleValidException multipleValidException) {
                System.out.println("failed to add new answer: Question already had one valid answer");
            }
        }
    }

    // EFFECTS: when finish button is pressed, quiz is added to the quiz list. Sent back to the main menu
    private ActionListener finishListener(JLabel confirmLabel,
                                          Quiz quiz,
                                          ButtonGroup buttonGroup,
                                          ArrayList<JTextField> textFields) {

        return e -> {
            int i = 1;
            ArrayList<AbstractButton> buttonList = Collections.list(buttonGroup.getElements());
            for (AbstractButton button : buttonList) {
                textFields.get(i).setText("");
                button.setEnabled(false);
                i++;
            }
            textFields.get(0).setText("");
            buttonGroup.clearSelection();
            confirmLabel.setText("");
            quiz.setName(quizTitle);
            quizList.addQuiz(quiz);
            cardLayout.show(cards, "0");
            cards.remove(chooseQuiz());
            cards.add(chooseQuiz(), "1");
            cards.add(quizTester(quizList.getQuiz(initial)), Integer.toString(initial + 3));
            initial++;
        };
    }

    // EFFECTS: saves quiz to json file
    private void saveQuizList() throws FileNotFoundException {
        try {
            jsonWriter.open();
            jsonWriter.write(quizList);
            jsonWriter.close();
            // System.out.println("Saved " + quizList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
            // System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads QuizList from file
    private void loadQuizList() throws IOException {
        try {
            cards.remove(chooseQuiz());
            initial = 0;
            while (initial < quizList.listSize()) {
                cards.remove(quizTester(quizList.getQuiz(initial)));
                initial++;
            }
            quizList = jsonReader.read();
            initial = 0;
            while (initial < quizList.listSize()) {
                cards.add(quizTester(quizList.getQuiz(initial)), Integer.toString(initial + 3));
                initial++;
            }
            add(cards);
            cards.add(chooseQuiz(), "1");
            // System.out.println("Loaded " + quizList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            throw new IOException();
            // System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
