package com.bj.jwt;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity 
{
	 EditText etpolicenum,etpolicepw;
	 String spolicenum,spolicepw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		 etpolicenum=(EditText)findViewById(R.id.login_user_edit);
		 etpolicepw=(EditText)findViewById(R.id.login_passwd_edit);
	}
	public void OnClickLogin(View v)
	{
		
		spolicenum=etpolicenum.getText().toString().trim();
		spolicepw=etpolicepw.getText().toString().trim();
		if(("".equals(spolicenum)||etpolicenum==null)||("".equals(spolicepw)||etpolicepw==null))
		{
			new AlertDialog.Builder(this)
			.setIcon(getResources().getDrawable(R.drawable.error))
			.setTitle("提示")
			.setMessage("用户名或密码不能为空")
			.setPositiveButton("确定", null)
			.create()
			.show();
		}
		
				
	}
	public void OnClickLoginback(View v)
	{
		LoginActivity.this.finish();
		
	}

}
