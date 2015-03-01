package networkThread;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.wordquiz.R;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterTask extends AsyncTask<String, Void, String> 
{
	private Context context;
	public RegisterTask(Context context)
	{
		this.context = context;
	}
	@Override
	protected String doInBackground(String... loginInfo) 
	{
		// TODO Auto-generated method stub
		String result = new String("ERROR in do InBackground registerTask");
		try
		{
			JSONObject registerInfo = new JSONObject();
			registerInfo.put("username",loginInfo[0]);
			registerInfo.put("password",loginInfo[1]);
			StringEntity registerContent = new StringEntity(registerInfo.toString());
			registerContent.setContentEncoding("UTF-8");
			registerContent.setContentType("application/json");
			DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(new URI(ServerInfo.serverRegisterURL));
			httpost.setEntity(registerContent);
			httpost.addHeader("Accept", "text/plain");
			ResponseHandler responseHandler = new BasicResponseHandler();
			String inputLine = httpclient.execute(httpost, responseHandler).toString();
		    JSONObject response = new JSONObject(inputLine);
		    result = response.getString("status");
		    //La.toast(response.getString("status"));
		    
		}
		catch (JSONException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		catch (ClientProtocolException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@Override
	protected void onPostExecute(String result) 
	{
		// TODO Auto-generated method stub
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
	}
	
	
}
