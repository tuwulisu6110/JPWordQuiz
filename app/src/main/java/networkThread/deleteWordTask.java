package networkThread;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by tuwulisu on 2015/3/22.
 */
public class deleteWordTask extends AsyncTask<Void,Void,Boolean> // delete word then refresh word list
{
    private Bundle LoginInfo;
    private int wordId;
    private Context context;
    ListView wordList;
    public deleteWordTask(Context c,Bundle b,int wordId,ListView lv)
    {
        this.wordId = wordId;
        LoginInfo = b;
        context = c;
        wordList = lv;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        try
        {
            JSONObject Info = new JSONObject();
            Info.put("wordId",wordId);
            Info.put("username", LoginInfo.getString("username"));
            Info.put("serialNum",LoginInfo.getString("serialNum"));
            Info.put("identifier",LoginInfo.getString("identifier"));
            StringEntity Content = new StringEntity(Info.toString(), HTTP.UTF_8);
            Content.setContentEncoding("UTF-8");
            Content.setContentType("application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInformation.serverDeleteWordURL));
            httpost.setEntity(Content);
            httpost.addHeader("Accept", "text/plain");
            JSONObject response = new JSONObject(httpclient.execute(httpost, new BasicResponseHandler()).toString());
            String status = response.getString("status");
            if(status.equals("success")!=true)
                Log.i("DeleteWordTask", "response status : " + response.getString("status"));
            else
                return true;

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
        return false;
    }

    @Override
    protected void onPostExecute(Boolean b)
    {
        super.onPostExecute(b);
        if(b==false)
            return;
        else
        {
            FetchAndSearchTask listTask = new FetchAndSearchTask(context,LoginInfo,wordList,"");
            listTask.execute();
        }
    }
}
