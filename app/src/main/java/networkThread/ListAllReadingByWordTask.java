package networkThread;

import android.app.Dialog;
import android.content.Context;
import android.opengl.ETC1;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.List;

import FunctionalTab.AddNewWordTab;

/**
 * Created by tuwulisu on 2015/6/21.
 */
public class ListAllReadingByWordTask extends AsyncTask<Void,Void,JSONArray>
{
    private Bundle LoginInfo;
    private ListView readingList;
    private Context context;
    private String query;
    private EditText readingET;
    private Dialog dialog;
    String status;
    public ListAllReadingByWordTask(Context c,Bundle b,EditText readingET,Dialog dialog,String q)
    {
        context = c;
        LoginInfo = b;
        readingList = (ListView)dialog.findViewById(R.id.readingSelectionList);
        query = q;
        this.readingET = readingET;
        this.dialog = dialog;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        dialog.setTitle("Loading");
    }

    @Override
    protected JSONArray doInBackground(Void... voids)
    {

        JSONArray readings = new JSONArray();
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
            HttpPost httpost = new HttpPost(new URI(ServerInformation.serverListAllReadingByWordURL));
            httpost.setEntity(Content);
            httpost.addHeader("Accept", "text/plain");
            JSONObject response = new JSONObject(httpclient.execute(httpost, new BasicResponseHandler()).toString());
            status = response.getString("status");
            if(response.getString("status").equals("success")!=true)
                Log.i("FetchAndSearchTask", "response status : " + response.getString("status"));
            else
                readings = response.getJSONArray("readingList");
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
        return readings;
    }

    @Override
    protected void onPostExecute(JSONArray rawReadings)
    {
        super.onPostExecute(rawReadings);
        dialog.setTitle("select reading");
        if(taskTool.checkStatusAndReturnLogin(context,status))
            return;
        readingList.setAdapter(new ReadingAdapter(rawReadings,context));
    }
    private class ReadingAdapter extends BaseAdapter
    {
        JSONArray rawReadings;
        private LayoutInflater mInflater;
        ReadingAdapter(JSONArray rR,Context c)
        {
            rawReadings = rR;
            mInflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount()
        {
            return rawReadings.length();
        }

        @Override
        public Object getItem(int i)
        {
            try
            {
                return rawReadings.get(i);
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
            view = mInflater.inflate(R.layout.reading_selection_item,null);
            TextView readingTV = (TextView)view.findViewById(R.id.readingSelectionTextView);

            try
            {
                final String reading = rawReadings.getString(i);
                readingTV.setText(reading);
                readingTV.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        readingET.setText(reading);
                        dialog.dismiss();
                    }
                });
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return view;
        }
    }
}
