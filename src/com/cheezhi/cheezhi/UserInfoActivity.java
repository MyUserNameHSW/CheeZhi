package com.cheezhi.cheezhi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity implements OnClickListener {
    ImageView back,head;
    TextView username,editUserName,editCode,editPhone,aboutUs,feedback;
    Button exitButton;
    Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_info);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.userinfo_back);
		head = (ImageView) findViewById(R.id.user_head);
		username = (TextView) findViewById(R.id.username_info);
		editUserName = (TextView) findViewById(R.id.edit_username);
		editCode = (TextView) findViewById(R.id.edit_user_code);
		editPhone = (TextView) findViewById(R.id.edit_user_phone);
		aboutUs = (TextView) findViewById(R.id.edit_aboutus);
		feedback = (TextView) findViewById(R.id.edit_messageback);
		back.setOnClickListener(this);
		head.setOnClickListener(this);
		editPhone.setOnClickListener(this);
		editUserName.setOnClickListener(this);
		editCode.setOnClickListener(this);
		aboutUs.setOnClickListener(this);
		feedback.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.userinfo_back:
			finish();
			break;
		case R.id.user_head:
			intent = new Intent(UserInfoActivity.this,LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.edit_messageback:
			intent = new Intent(UserInfoActivity.this,FeedbackActivity.class);
			startActivity(intent);
			break;
		case R.id.edit_username:
			
			break;
		case R.id.edit_user_code:
			
			break;
		case R.id.edit_user_phone:
			
			break;
		case R.id.edit_aboutus:
			intent = new Intent(UserInfoActivity.this,AboutusActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
