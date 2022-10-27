package sysmob.zad2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import sysmob.zad1.R;

public class MainActivity extends AppCompatActivity {
     private Button trueButton;
     private Button promptButton;
     private Button falseButton;
     private Button nextButton;

     private TextView questionTextView;

     private Question[] questions = new Question[] {
             new Question(R.string.q_activity, true),
             new Question(R.string.q_find_resources, false),
             new Question(R.string.q_listener, true),
             new Question(R.string.q_resources, true),
             new Question(R.string.q_version, false),
     };

     private int currentIndex = 0;
     private boolean answerWasShown;

     private static final String KEY_CURRENT_INDEX = "currentIndex";
     private static final int REQUEST_CODE_PROMPT = 0;
     public static final String KEY_EXTRA_ANSWER = "sysmob.zad2.correctAnswer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity","Wywyołanie metody OnCreate");

        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        promptButton = findViewById(R.id.prompt_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton.setOnClickListener(l -> checkAnswerCorrectness(true));

        promptButton.setOnClickListener(l -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });

        falseButton.setOnClickListener(l -> checkAnswerCorrectness(false));

        nextButton.setOnClickListener(l -> {
            currentIndex = (currentIndex + 1) % questions.length;
            answerWasShown = false;
            setNextQuestion();
        });
        setNextQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","Wywyołanie metody OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","Wywyołanie metody OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","Wywyołanie metody OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","Wywyołanie metody OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","Wywyołanie metody OnDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity","Wywyołanie metody OnSaveInstance");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) { return; }

        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null ) { return; }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;

        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion()  {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
}