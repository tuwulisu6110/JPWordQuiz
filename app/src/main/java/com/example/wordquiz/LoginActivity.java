package com.example.wordquiz;

import networkThread.LoginTask;
import networkThread.RegisterTask;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity 
{

	EditText usernameET;
	EditText passwordET;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_login);
		usernameET = (EditText)findViewById(R.id.editTextUsername);
		passwordET = (EditText)findViewById(R.id.editTextPassword);
		Button loginButton = (Button)findViewById(R.id.buttonLogin);
		Button RegisterButton = (Button)findViewById(R.id.buttonRegister);
		
		loginButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				String[] loginInfo = new String[2];
				loginInfo[0] = usernameET.getText().toString();
				loginInfo[1] = passwordET.getText().toString();
				LoginTask lt = new LoginTask(context);
				lt.execute(loginInfo);
			}
		});
		
		RegisterButton.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				String[] loginInfo = new String[2];
				loginInfo[0] = usernameET.getText().toString();
				loginInfo[1] = passwordET.getText().toString();
				RegisterTask rt = new RegisterTask(context);
				rt.execute(loginInfo);
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
