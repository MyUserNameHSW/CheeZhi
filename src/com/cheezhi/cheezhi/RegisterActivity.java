package com.cheezhi.cheezhi;

import com.cheezhi.utils.CheezhiApplication;
import com.cheezhi.utils.Methods;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class RegisterActivity extends Activity implements OnClickListener {
	int i = 60;
	Button validate, submit;
	EditText phoneNum_text, codeText;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		initSDK();
		initView();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.arg_back);
		codeText = (EditText) findViewById(R.id.validate_num);
		phoneNum_text = (EditText) findViewById(R.id.phoneNum_text);
		validate = (Button) findViewById(R.id.sendMsg);
		submit = (Button) findViewById(R.id.submit_val);
		validate.setOnClickListener(this);
		submit.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String phoneString = phoneNum_text.getText().toString();
		switch (arg0.getId()) {
		case R.id.sendMsg:
			if (Methods.judgePhoneNums(phoneString, getApplicationContext())) {
				sendValidate();
			} else {
				Methods.showToast(getApplicationContext(), "��������ȷ�ֻ���");
				return;
			}
			break;
		case R.id.submit_val:
//			SMSSDK.submitVerificationCode("86", phoneString, codeText.getText()
//					.toString().trim());
			Intent intent = new Intent(RegisterActivity.this,SetCodeActivity.class);
			startActivity(intent);
			break;
		case R.id.arg_back:
			finish();
			break;
		default:
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				validate.setText("���·���(" + i + ")");
				validate.setClickable(false);
			} else if (msg.what == -8) {
				validate.setText("���·���");
				validate.setClickable(true);
				i = 60;
			} else {
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event=" + event);
				if (result == SMSSDK.RESULT_COMPLETE) {

					// ����ע��ɹ��󣬷���MainActivity,Ȼ����ʾ
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// �ύ��֤��ɹ�
						Toast.makeText(RegisterActivity.this, "success",
								Toast.LENGTH_LONG).show();
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						Toast.makeText(getApplicationContext(), "��֤���Ѿ�����",
								Toast.LENGTH_SHORT).show();
					} else {
						((Throwable) data).printStackTrace();
					}
				}
			}
		}
	};

	private void initSDK() {
		SMSSDK.initSDK(this, CheezhiApplication.SMSSDK_APPKEY,
				CheezhiApplication.SMSSDK_SECRET);
		EventHandler eventHandler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eventHandler);
	}

	private void sendValidate() {
		SMSSDK.getVerificationCode("86", phoneNum_text.getText().toString());
		validate.setClickable(false);
		validate.setText("���·���(" + i + ")");
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (; i > 0; i--) {
					handler.sendEmptyMessage(-9);
					if (i <= 0) {
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				handler.sendEmptyMessage(-8);
			}
		}).start();
	}
}
