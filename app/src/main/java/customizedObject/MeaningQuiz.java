package customizedObject;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wordquiz.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

import customizedView.AnswerButton;

/**
 * Created by tuwulisu on 2015/3/31.
 */
public class MeaningQuiz implements QuizGenerator
{
    LinearLayout answersRegionLayout;
    TextView questionTV;
    View activeView;
    TextView questionTypeTV;
    private JSONArray words;
    public MeaningQuiz(View v,JSONArray w)
    {
        activeView = v;
        answersRegionLayout = (LinearLayout)activeView.findViewById(R.id.answersRegionLayout);
        questionTV = (TextView)activeView.findViewById(R.id.questionTextView);
        questionTypeTV = (TextView)activeView.findViewById(R.id.questionTypeTextView);

        words = w;
    }
    @Override
    public void generateQuiz()
    {
        questionTypeTV.setText("意味");
        Random rand = new Random();
        int numOfAnswers = answersRegionLayout.getChildCount();
        int correctAnswerId = rand.nextInt(numOfAnswers);
        try
        {
            questionTV.setText(words.getJSONObject(correctAnswerId).getString("meaning"));
            for(int i = 0; i < numOfAnswers;i++)
            {
                AnswerButton ab = (AnswerButton)answersRegionLayout.getChildAt(i);
                ab.setText(words.getJSONObject(i).getString("word"));
                ab.setCorrectId(correctAnswerId);
                ab.setWordInfo(words.getJSONObject(i));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
