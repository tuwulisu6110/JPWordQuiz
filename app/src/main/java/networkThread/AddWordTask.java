package networkThread;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
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
import java.util.ArrayList;

/**
 * Created by tuwulisu on 2015/3/1.
 */
public class AddWordTask extends AsyncTask<Bundle,Void,String>
{
    Context context;
    ArrayList<EditText> forEmptyList;
    ArrayList<String> info;
    private Bundle LoginInfo;
    private String status;
    public AddWordTask(Context context,Bundle b,ArrayList<EditText> ets,ArrayList<String> info)
    {
        this.context = context;
        forEmptyList = ets; // include all editTexts in addNewWordTab will be cleared about text
        this.info = info;
        LoginInfo = b;
    }

    @Override
    protected String doInBackground(Bundle... bundles)
    {
        String word = info.get(1);
        String reading = info.get(2);
        String meaning = info.get(3);
        String sourceId = info.get(4);
        String page = info.get(5);
        String sentence = info.get(6);
        String result = "FAIL";
        try
        {
            JSONObject Info = new JSONObject();

            Info.put("word",word);
            Info.put("reading",reading);
            Info.put("meaning",meaning);
            Info.put("sourceId",sourceId);
            Info.put("page",page);
            Info.put("sentence",sentence);
            Info.put("username", LoginInfo.getString("username"));
            Info.put("serialNum",LoginInfo.getString("serialNum"));
            Info.put("identifier",LoginInfo.getString("identifier"));
            StringEntity Content = new StringEntity(Info.toString(), HTTP.UTF_8);
            Content.setContentEncoding("UTF-8");
            Content.setContentType("application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInformation.serverAddWordURL));
            httpost.setEntity(Content);
            httpost.addHeader("Accept", "text/plain");
            ResponseHandler responseHandler = new BasicResponseHandler();
            JSONObject response = new JSONObject(httpclient.execute(httpost, responseHandler).toString());
            status = response.getString("status");
            if(response.getString("status").equals("success")!=true)
                Log.i("NewWordTask", "response status : " + response.getString("status"));
            else
                result="add word succeed";
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        if(taskTool.checkStatusAndReturnLogin(context,status))
            return;
        if(s.equals("FAIL"))
            Log.i("AddNewWordTask","result = FAIL in onPostExecute");
        else
        {
            for (EditText et : forEmptyList)
                et.setText("");
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }

    }
}
