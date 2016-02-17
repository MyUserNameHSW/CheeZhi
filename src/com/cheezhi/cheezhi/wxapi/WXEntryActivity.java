package com.cheezhi.cheezhi.wxapi;

import org.json.JSONException;
import org.json.JSONObject;

import com.cheezhi.cheezhi.AboutusActivity;
import com.cheezhi.utils.Methods;
import com.google.gson.JsonObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class WXEntryActivity extends Activity {
	String WEIXIN_APP_ID = "wx2c2489e42549ea70";
	String WEIXIN_APP_SECRET = "7b3ba5c20921492f95a8687e6666251c";
	//getType值 1为登录 2为分享
	final int RETURN_MSG_TYPE_LOGIN = 1;
	final int RETURN_MSG_TYPE_SHARE = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {
		SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
		if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
			if (resp.getType() == RETURN_MSG_TYPE_SHARE) {
				Methods.showToast(WXEntryActivity.this, "分享成功");
			}else {
			getResultCodes(WEIXIN_APP_ID,WEIXIN_APP_SECRET,resp.code);
			Methods.showToast(getApplicationContext(), "---");
		    intent = new Intent(this,AboutusActivity.class);
		    startActivity(intent);
		    finish();
		    }
		}
	}
	private void getResultCodes(String key,String secret,String code){
		String weixinUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+key+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, weixinUrl, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				try {
					JSONObject object = new JSONObject(result);
					//获取用户唯一标识
					String openid = object.getString("openid");
//					String access_token = object.getString("access_token");
//					String refresh_token = object.getString("refresh_token");
//					Log.e("refresh_token", "--->"+refresh_token);
					Log.e("openid", "--->"+openid);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
