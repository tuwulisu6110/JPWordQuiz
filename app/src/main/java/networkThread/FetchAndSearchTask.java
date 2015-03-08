package networkThread;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wordquiz.R;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
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
    public FetchAndSearchTask(Context c,ListView lv,String query)
    {
        wordList = lv;
        this.query = query;
        context = c;
        isBackgroundTaskEnd = false;
    }


    @Override
    protected JSONArray doInBackground(Void... voids)
    {
        try
        {
            JSONObject Info = new JSONObject();
            Info.put("word",query);
            StringEntity Content = new StringEntity(Info.toString());
            Content.setContentEncoding("UTF-8");
            Content.setContentType("application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInformation.serverSearchURL));
            httpost.setEntity(Content);
            httpost.addHeader("Accept", "text/plain");
            JSONObject response = new JSONObject(httpclient.execute(httpost, new BasicResponseHandler()).toString());
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
            TextView meaningTV = (TextView)view.findViewById(R.id.meaningText);
            try
            {
                wordTV.setText(new JSONObject(words.get(i).toString()).getString("word"));
                readingTV.setText(new JSONObject(words.get(i).toString()).getString("reading"));
                meaningTV.setText(new JSONObject(words.get(i).toString()).getString("meaning"));
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