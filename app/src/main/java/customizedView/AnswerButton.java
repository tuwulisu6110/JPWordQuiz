package customizedView;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.wordquiz.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tuwulisu on 2015/3/23.
 */
public class AnswerButton extends Button
{
    private int id;
    private int correctId;
    private JSONObject wordInfo;
    public AnswerButton(Context context, int id)
    {
        super(context);
        this.id = id;
        //answersRegionLayout = (LinearLayout) v.findViewById(R.id.answersRegionLayout);
    }
    public void setWordInfo(JSONObject wi)
    {
        wordInfo = wi;
    }
    public void setCorrectId(int correctId)
    {
        this.correctId = correctId;
    }

    public boolean checkItsCorrect()
    {
        return correctId==id;
    }
    public int getWordId()
    {
        int wordId=-1;
        try
        {
            wordId  = wordInfo.getInt("id");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return wordId;
    }
}
