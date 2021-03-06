package networkThread;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

/**
 * Created by tuwulisu on 2015/2/28.
 */
public class NewSourceTask extends AsyncTask<Void,Void,Integer>
{
    private Context context;
    private String sourceName;
    private RadioGroup sourceGroup;
    private Button addSourceBtn;
    private Bundle LoginInfo;
    private String status;
    public NewSourceTask(Context c,Bundle b,String sourceName, RadioGroup rg,Button btn)
    {
        context = c;
        this.sourceName = sourceName;
        sourceGroup = rg;
        addSourceBtn = btn;
        LoginInfo = b;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        addSourceBtn.setText("Loading Sources");
        addSourceBtn.setEnabled(false);
    }

    @Override
    protected Integer doInBackground(Void... voids)
    {
        int lastId = 0;
        try
        {
            JSONObject Info = new JSONObject();
            Info.put("source",sourceName);
            Info.put("username", LoginInfo.getString("username"));
            Info.put("serialNum",LoginInfo.getString("serialNum"));
            Info.put("identifier",LoginInfo.getString("identifier"));
            StringEntity Content = new StringEntity(Info.toString(), HTTP.UTF_8);
            Content.setContentEncoding("UTF-8");
            Content.setContentType("application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInformation.serverAddSourceURL));
            httpost.setEntity(Content);
            httpost.addHeader("Accept", "text/plain");
            ResponseHandler responseHandler = new BasicResponseHandler();
            JSONObject response = new JSONObject(httpclient.execute(httpost, responseHandler).toString());
            status = response.getString("status");
            if(response.getString("status").equals("success")!=true)
                Log.i("NewSourceTask","response status : "+response.getString("status"));
            else
                lastId = response.getInt("lastId");
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
        return lastId;
    }

    @Override
    protected void onPostExecute(Integer lastId)
    {
        super.onPostExecute(lastId);
        if(taskTool.checkStatusAndReturnLogin(context,status))
            return;
        if(lastId==0)
            Log.i("NewSourceTask","lastId == 0");
        else
        {
            RadioButton rBtn = new RadioButton(context);
            rBtn.setText(sourceName);
            rBtn.setId(lastId);
            sourceGroup.addView(rBtn);
            addSourceBtn.setEnabled(true);
            addSourceBtn.setText("Add Source");
        }
    }
}
