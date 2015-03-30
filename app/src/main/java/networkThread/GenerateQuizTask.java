package networkThread;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wordquiz.R;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import customizedObject.MeaningQuiz;
import customizedObject.QuizGenerator;
import customizedObject.ReadingQuiz;
import customizedView.AnswerButton;

/**
 * Created by tuwulisu on 2015/3/23.
 */
public class GenerateQuizTask extends AsyncTask<Void,Void,String>
{
    View activeView;
    Context context;
    Bundle LoginInfo;
    LinearLayout answersRegionLayout;
    TextView questionTV;
    private JSONArray words;//this will be used for receive data from server randomWord, directly use in onPostExecute()
    Button nextQuestionButton;
    public GenerateQuizTask(Context c,View v,Bundle b)
    {
        context = c;
        activeView = v;
        LoginInfo = b;
        answersRegionLayout = (LinearLayout)activeView.findViewById(R.id.answersRegionLayout);
        questionTV = (TextView)activeView.findViewById(R.id.questionTextView);
        nextQuestionButton = (Button)activeView.findViewById(R.id.nextQuestionButton);
    }
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        nextQuestionButton.setEnabled(false);
        questionTV.setText("Loading");
        for(int i = 0; i < answersRegionLayout.getChildCount();i++)
        {
            AnswerButton ab = (AnswerButton)answersRegionLayout.getChildAt(i);
            ab.setText("Loading");
        }
    }

    @Override
    protected String doInBackground(Void... voids)
    {
        String status = "";
        try
        {
            JSONObject Info = new JSONObject();
            Info.put("num",answersRegionLayout.getChildCount());
            Info.put("username", LoginInfo.getString("username"));
            Info.put("serialNum",LoginInfo.getString("serialNum"));
            Info.put("identifier",LoginInfo.getString("identifier"));
            StringEntity Content = new StringEntity(Info.toString(), HTTP.UTF_8);
            Content.setContentEncoding("UTF-8");
            Content.setContentType("application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInformation.serverRandomWordURL));
            httpost.setEntity(Content);
            httpost.addHeader("Accept", "text/plain");
            JSONObject response = new JSONObject(httpclient.execute(httpost, new BasicResponseHandler()).toString());
            status = response.getString("status");
            if(status.equals("success")!=true)
                Log.i("DeleteWordTask", "response status : " + response.getString("status"));
            else
                words = response.getJSONArray("words");

        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    protected void onPostExecute(String status)
    {
        super.onPostExecute(status);
        Random rand = new Random();
        if(taskTool.checkStatusAndReturnLogin(context,status))
            return;
        nextQuestionButton.setEnabled(true);
        QuizGenerator quizGenerator;
        if(rand.nextBoolean())
            quizGenerator = new ReadingQuiz(activeView,words);
        else
            quizGenerator = new MeaningQuiz(activeView,words);
        quizGenerator.generateQuiz();
        /*int numOfAnswers = answersRegionLayout.getChildCount();
        int correctAnswerId = rand.nextInt(numOfAnswers);
        try
        {
            questionTV.setText(words.getJSONObject(correctAnswerId).getString("word"));
            for(int i = 0; i < numOfAnswers;i++)
            {
                AnswerButton ab = (AnswerButton)answersRegionLayout.getChildAt(i);
                ab.setText(words.getJSONObject(i).getString("reading"));
                ab.setCorrectId(correctAnswerId);
                ab.setWordInfo(words.getJSONObject(i));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }*/
    }
}
