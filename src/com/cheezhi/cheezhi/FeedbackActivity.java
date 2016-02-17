package com.cheezhi.cheezhi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class FeedbackActivity extends Activity {
    EditText contact,message;
    Button submit;
    ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);
		initView();
	}
    private void initView(){
    	contact = (EditText) findViewById(R.id.contact_edit);
    	message = (EditText) findViewById(R.id.add_msg);
    	submit = (Button) findViewById(R.id.submit);
    	submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	back = (ImageView) findViewById(R.id.afb_back);
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }
}
