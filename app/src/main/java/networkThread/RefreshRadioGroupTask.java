package networkThread;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tuwulisu on 2015/2/28.
 */
//Note that it get sources on server and refresh radio buttons
public class RefreshRadioGroupTask extends AsyncTask<Void , Void, JSONObject>
{
    private Context context;
    private RadioGroup sourceGroup;
    private Button addSourceBtn;
    private String username;
    private JSONObject sourceTable;
    public RefreshRadioGroupTask(Context context, RadioGroup rg,Button btn,String un)
    {
        this.context = context;
        sourceGroup = rg;
        addSourceBtn = btn;
        username = un;
    }
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        addSourceBtn.setText("Loading Sources");
        addSourceBtn.setEnabled(false);
    }
    @Override
    protected JSONObject doInBackground(Void ... aVoid)
    {
        try
        {
            JSONObject accountInfo = new JSONObject();
            accountInfo.put("username",username);
            StringEntity accountContent = new StringEntity(accountInfo.toString());
            accountContent.setContentEncoding("UTF-8");
            accountContent.setContentType("application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInfo.serverListSourceURL));
            httpost.setEntity(accountContent);
            httpost.addHeader("Accept", "text/plain");
            ResponseHandler responseHandler = new BasicResponseHandler();
            JSONObject response = new JSONObject(httpclient.execute(httpost, responseHandler).toString());
            sourceTable = response.getJSONObject("sources");


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
        return sourceTable;
    }

    @Override
    protected void onPostExecute(JSONObject sources)
    {
        super.onPostExecute(sources);
        while(sourceTable.keys().hasNext())
        {
            RadioButton rBtn = new RadioButton(context);
            String key = sourceTable.keys().next().toString();
            try
            {
                rBtn.setText(sourceTable.getString(key));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            rBtn.setId(Integer.valueOf(key));
            sourceGroup.addView(rBtn);
        }
        addSourceBtn.setEnabled(true);
        addSourceBtn.setText("Add Source");
    }
}
