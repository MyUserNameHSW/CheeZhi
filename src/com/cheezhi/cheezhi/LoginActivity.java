package com.cheezhi.cheezhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity implements OnClickListener {
	EditText usernameEditText, passwordEditText;
	Button loginButton;
	ImageView back;
	RelativeLayout login_other, register;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.login_back);
		usernameEditText = (EditText) findViewById(R.id.username_text);
		passwordEditText = (EditText) findViewById(R.id.password_text);
		loginButton = (Button) findViewById(R.id.login_btn);
		login_other = (RelativeLayout) findViewById(R.id.login_more_way);
		register = (RelativeLayout) findViewById(R.id.registe);
		loginButton.setOnClickListener(this);
		login_other.setOnClickListener(this);
		register.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.login_btn:

			break;
		case R.id.login_more_way:
            intent = new Intent(this,LoginOthersActivity.class);
            startActivity(intent);
			break;
		case R.id.registe:
            intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
			break;
		case R.id.login_back:
            finish();
			break;
		default:
			break;
		}
	}
}
