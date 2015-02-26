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

import com.example.wordquiz.LoginActivity;
import com.example.wordquiz.R;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


public class LoginTask extends AsyncTask<String, Void, String> 
{
	private Context context;
	private String[] loginInfo;
	public LoginTask(Context context)
	{
		this.context = context;
	}
	@Override
	protected String doInBackground(String... arg0) 
	{
		// TODO Auto-generated method stub
		String result = new String("ERROR in doInBackground loginTask");
		loginInfo = arg0;
		try 
		{
			JSONObject loginInfo = new JSONObject();
			loginInfo.put("username",arg0[0]);
			loginInfo.put("password",arg0[1]);
			StringEntity loginContent = new StringEntity(loginInfo.toString());
			loginContent.setContentEncoding("UTF-8");
			loginContent.setContentType("application/json");
			DefaultHttpClient httpclient = new DefaultHttpClient();
			URI website;
			website = new URI("http://220.135.188.70:5000/login");
			HttpPost httpost = new HttpPost(website);
			httpost.setEntity(loginContent);
			httpost.addHeader("Accept", "text/plain");
			ResponseHandler responseHandler = new BasicResponseHandler();
			String inputLine = httpclient.execute(httpost, responseHandler).toString();
			
		    JSONObject response = new JSONObject(inputLine);
		    result = response.getString("status");
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
		} 
		catch (URISyntaxException e) 
		{
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
		if(result.equals("success"))
		{
            Intent switchIntent = new Intent(context,com.example.wordquiz.MainActivity.class);
            Bundle b = new Bundle();
            b.putString("username",loginInfo[0]);
            switchIntent.putExtras(b);
            context.startActivity(switchIntent);
		}
	}
	


}
