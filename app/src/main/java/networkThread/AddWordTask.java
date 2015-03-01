package networkThread;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by tuwulisu on 2015/3/1.
 */
public class AddWordTask extends AsyncTask<String,Void,String>
{
    Context context;
    EditText[] forEmptyList;
    public AddWordTask(Context context,EditText[] ets)
    {
        this.context = context;
        forEmptyList = ets; // include all editTexts in addNewWordTab will be cleared about text
    }

    @Override
    protected String doInBackground(String... info)
    {
        String username = info[0];
        String word = info[1];
        String reading = info[2];
        String meaning = info[3];
        String sourceId = info[4];
        String page = info[5];
        String result = "FAIL";
        try
        {
            JSONObject Info = new JSONObject();
            Info.put("username",username);
            Info.put("word",word);
            Info.put("reading",reading);
            Info.put("meaning",meaning);
            Info.put("sourceId",sourceId);
            Info.put("page",page);
            StringEntity Content = new StringEntity(Info.toString());
            Content.setContentEncoding("UTF-8");
            Content.setContentType("application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInformation.serverAddWordURL));
            httpost.setEntity(Content);
            httpost.addHeader("Accept", "text/plain");
            ResponseHandler responseHandler = new BasicResponseHandler();
            JSONObject response = new JSONObject(httpclient.execute(httpost, responseHandler).toString());
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
