package FunctionalTab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wordquiz.R;

import org.w3c.dom.Text;

import customizedView.AnswerButton;
import networkThread.GenerateQuizTask;
import networkThread.RecordAnswerResultTask;

/**
 * Created by tuwulisu on 2015/3/23.
 */
public class QuizTab extends Fragment
{
    private final int numOfAnswers = 4;
    private LinearLayout answersRegionLayout;
    private TextView answerResultTV;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View v = inflater.inflate(R.layout.quiz_tab,container,false);
        answersRegionLayout = (LinearLayout)v.findViewById(R.id.answersRegionLayout);
        Button nextQuestionButton = (Button)v.findViewById(R.id.nextQuestionButton);
        TextView questionTypeTV = (TextView)v.findViewById(R.id.questionTypeTextView);
        questionTypeTV.setTextSize(30);
        answerResultTV = (TextView)v.findViewById(R.id.answerResultTextView);
        answerResultTV.setTextSize(30);
        nextQuestionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                resetQuizPage();
                GenerateQuizTask generateQuizTask = new GenerateQuizTask(getActivity(),v,getArguments());
                generateQuizTask.execute();

            }
        });
        for(int i=0;i<numOfAnswers;i++)
        {
            AnswerButton ab = new AnswerButton(getActivity(), i );
            answersRegionLayout.addView(ab);
            ab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    freezeAllAnswerButton();
                    AnswerButton itself =(AnswerButton) view;
                    int result;
                    if(itself.checkItsCorrect())
                    {
                        result = 1;
                        answerResultTV.setText("正解");
                    }
                    else
                    {
                        result = 0;
                        answerResultTV.setText("不正解");
                        itself.setTextColor(Color.BLUE);
                    }
                    int wordId = showCorrectAnswer();
                    RecordAnswerResultTask recordTask = new RecordAnswerResultTask(getActivity(),wordId,result,getArguments());
                    recordTask.execute();
                }
            });
        }
        GenerateQuizTask generateQuizTask = new GenerateQuizTask(getActivity(),v,getArguments());
        generateQuizTask.execute();
        return v;
    }
    private void freezeAllAnswerButton()
    {
        int numOfAnswers = answersRegionLayout.getChildCount();
        for(int i = 0; i < numOfAnswers;i++)
            ((AnswerButton)answersRegionLayout.getChildAt(i)).setClickable(false);
    }
    private int showCorrectAnswer()
    {
        int numOfAnswers = answersRegionLayout.getChildCount();
        for(int i = 0; i < numOfAnswers;i++)
        {
            AnswerButton ab = (AnswerButton)answersRegionLayout.getChildAt(i);
            if(ab.checkItsCorrect())
            {
                ab.setTextColor(Color.RED);
                return ab.getWordId();
            }
        }
        Log.d("showCorrectAnswer()","No one is correct !!");
        return -1;
    }
    private void resetQuizPage()
    {
        int numOfAnswers = answersRegionLayout.getChildCount();
        for(int i = 0; i < numOfAnswers;i++)
        {
            AnswerButton ab = (AnswerButton)answersRegionLayout.getChildAt(i);
            ab.setTextColor(Color.BLACK);
            ab.setClickable(true);
        }
        answerResultTV.setText("");
    }
}
