package networkThread;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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
 * Created by tuwulisu on 2015/3/23.
 */
public class RecordAnswerResultTask extends AsyncTask<Void,Void,Void>
{
    private int wordId;
    private int result;
    private Bundle LoginInfo;
    private Context context;
    private String status;
    public RecordAnswerResultTask(Context c,int wordId,int r,Bundle b)
    {
        super();
        this.wordId = wordId;
        result = r;
        LoginInfo = b;
        context = c;
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        try
        {
            JSONObject Info = new JSONObject();
            Info.put("wordId",String.valueOf(wordId));
            Info.put("result",result);
            Info.put("username", LoginInfo.getString("username"));
            Info.put("serialNum",LoginInfo.getString("serialNum"));
            Info.put("identifier",LoginInfo.getString("identifier"));
            StringEntity Content = new StringEntity(Info.toString(), HTTP.UTF_8);
            Content.setContentEncoding("UTF-8");
            Content.setContentType("application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInformation.serverRecordAnswerResultURL));
            httpost.setEntity(Content);
            httpost.addHeader("Accept", "text/plain");
            JSONObject response = new JSONObject(httpclient.execute(httpost, new BasicResponseHandler()).toString());
            status = response.getString("status");
            if(status.equals("success")!=true)
                Log.i("DeleteWordTask", "response status : " + response.getString("status"));


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
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        if(taskTool.checkStatusAndReturnLogin(context,status))
            return;
    }
}
