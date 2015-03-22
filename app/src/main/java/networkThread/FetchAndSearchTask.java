package networkThread;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wordquiz.R;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
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

/**
 * Created by tuwulisu on 2015/3/4.
 */
public class FetchAndSearchTask extends AsyncTask<Void,Void,JSONArray>
{
    private ListView wordList;
    private String query;
    private Context context;
    private JSONArray words;//this will be used in wordAdapter as itemlist input, directly use in onPostExecute()
    boolean isBackgroundTaskEnd;
    private Bundle LoginInfo;
    private String status;
    public FetchAndSearchTask(Context c,Bundle b,ListView lv,String query)
    {
        wordList = lv;
        this.query = query;
        context = c;
        isBackgroundTaskEnd = false;
        LoginInfo = b;
    }


    @Override
    protected JSONArray doInBackground(Void... voids)
    {
        try
        {
            JSONObject Info = new JSONObject();
            Info.put("word",query);
            Info.put("username", LoginInfo.getString("username"));
            Info.put("serialNum",LoginInfo.getString("serialNum"));
            Info.put("identifier",LoginInfo.getString("identifier"));
            StringEntity Content = new StringEntity(Info.toString(), HTTP.UTF_8);
            Content.setContentEncoding("UTF-8");
            Content.setContentType("application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInformation.serverSearchURL));
            httpost.setEntity(Content);
            httpost.addHeader("Accept", "text/plain");
            JSONObject response = new JSONObject(httpclient.execute(httpost, new BasicResponseHandler()).toString());
            status = response.getString("status");
            if(response.getString("status").equals("success")!=true)
                Log.i("FetchAndSearchTask", "response status : " + response.getString("status"));
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
        isBackgroundTaskEnd = true;
        return words;
    }

    @Override
    protected void onPostExecute(JSONArray words)
    {
        super.onPostExecute(words);
        if(taskTool.checkStatusAndReturnLogin(context,status))
            return;
        wordList.setAdapter(new WordAdapter(context));

    }
    private class WordAdapter extends BaseAdapter
    {
        private LayoutInflater mInflater;


        public WordAdapter(Context context)
        {
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount()
        {
            return words.length();
        }

        @Override
        public Object getItem(int i)
        {
            try
            {
                return words.get(i);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            view = mInflater.inflate(R.layout.word_list_item,null);
            TextView wordTV = (TextView)view.findViewById(R.id.wordTextView);
            TextView readingTV = (TextView)view.findViewById(R.id.readingTextView);
            TextView meaningTV = (TextView)view.findViewById(R.id.meaningTextView);
            TextView rateTV = (TextView)view.findViewById(R.id.rateTextView);
            final ImageButton deleteButton = (ImageButton)view.findViewById(R.id.wordDeleteButton);//prepare for adding to wordItem when long click
            final int itemId = i;
            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    try
                    {
                        JSONObject aWord = new JSONObject(words.get(itemId).toString());
                        deleteWordTask deleteTask = new deleteWordTask(context,LoginInfo,aWord.getInt("id"),wordList);
                        deleteTask.execute();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener()
            {
                private boolean state;
                {
                    state = false;
                }
                @Override
                public boolean onLongClick(View view)
                {
                    LinearLayout wordLayout = (LinearLayout)view.findViewById(R.id.wordLayout);
                    //LinearLayout wordListItemLayout = (LinearLayout)view.findViewById(R.id.wordListItemLayout);
                    if(state==false)//now this item hasn't delete button
                    {
                        wordLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3.0f));
                        deleteButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                        state = true;
                    }
                    else
                    {
                        deleteButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f));
                        wordLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4.0f));
                        state = false;
                    }
                    return true;
                }
            });
            try
            {
                JSONObject aWord = new JSONObject(words.get(i).toString());
                wordTV.setText(aWord.getString("word"));
                readingTV.setText(aWord.getString("reading"));
                meaningTV.setText(aWord.getString("meaning"));
                Double d = new Double( aWord.getDouble("rate")*100 );
                rateTV.setText(String.valueOf(d.intValue())+"%");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return view;
        }
    }
    public JSONArray getWords()
    {
        if(isBackgroundTaskEnd)
            return words;
        else
            return null;
    }

}
