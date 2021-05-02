# My Quiz Creator

## Task 2

Make a kahoot-like app that holds a list of Questions, with the Question having a list of answers. Only one answer per question can be correct. When started, the quiz application will send the user to the menu, which holds a list of quizzes that can be run, the option to create a quiz, and the option to add to a quiz.

The user can create a quiz and will be asked to add questions with up to four answers related to each question (labelled 1, 2, 3, 4). After the four answers are typed in, the app will ask to assign a choice from 1 to 4 as the correct answer. The user will type in the integer 1, 2, 3, or 4 to assign which choice is the true answer. After this, another question can be added if the user wants to. Once the user is done, they can type 'q' as specified by the program anytime between question additions. The user will be sent back to the menu.

When the user selects a quiz, the question will pop up, and the user must type 1, 2, 3, or 4 to answer the question correctly. If right, the quiz will congratulate the user. If wrong, the quiz will tell them it was incorrect. Once the quiz is done, the user will be sent back to the list of quizzes.

This is being made with students in mind in order to create quick and effective quizzes that can be stored for later use. This is interesting to me because I want to be able to develop a useful application that can be applied to multiple classes.

## Task 3

As a user, I want to be able to create a quiz

As a user, I want to be able to create a multiple choice question

As a user, I want to be able to set an answer to be the correct answer

As a user, I want to be able to do a quiz and answer questions

## Phase 2 Task 2

As a user, I want to be able to save quizzes into a file

As a user, I want to be able to load a file with quizzes

## Phase 4 Task 2

I have chosen to make my Question class (specifically the addAnswer method) more robust by making it throw a 
checked exception if more than one Answer object added to it is set to true. This way it catches if one too many answers 
can be true, which isn't expected for this quiz application, since multiple choices cannot be chosen at the same time.

## Phase 4 Task 3

- I would change the QuizFrame class by refactoring many of its methods for each Panel to smaller classes
- I would create more of the panel's items as fields of the class so that helper methods wouldn't become convoluted.
- These changes would also help decrease the amount of coupling that was created, since lots of bugs appear if one
panel is changed.
- Finally, I would delete the QuizList class and just make a field of List of quizzes when needed to decrease the 
amount of convoluted code present in the ui.
  
## Dependencies

- JSON library (https://github.com/stleary/JSON-java)
- JUnit Jupiter 5.4.2