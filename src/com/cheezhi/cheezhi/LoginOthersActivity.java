package com.cheezhi.cheezhi;

import java.text.SimpleDateFormat;

import com.cheezhi.utils.Methods;
import com.cheezhi.weibo.AccessTokenKeeper;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LoginOthersActivity extends Activity implements OnClickListener, Response {
	ImageView back;
	RelativeLayout weibo, weixin;
	private IWXAPI mWeixinAPI;
	String WEIXIN_APP_ID = "wx2c2489e42549ea70";
	String WEIXIN_APP_SECRET = "12a645ad747e73e27420cdf3a8c59d47";
	public static final String WEIBP_APP_KEY = "3394795257"; // 应用的APP_KEY
	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";// 应用的回调页
	AuthInfo mAuthInfo;
	Oauth2AccessToken mAccessToken;
	SsoHandler mSsoHandler;
    IWeiboShareAPI mWeiboShareAPI;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_others);
		initView();
		initWeibo();
	}

	private void initWeibo() {
		// TODO Auto-generated method stub
		mAuthInfo = new AuthInfo(LoginOthersActivity.this, WEIBP_APP_KEY,
				REDIRECT_URL, "-->");
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, WEIBP_APP_KEY);
		mWeiboShareAPI.registerApp();	// 将应用注册到微博客户端

	}

	private void initView() {
		back = (ImageView) findViewById(R.id.alo_back);
		weibo = (RelativeLayout) findViewById(R.id.weibo_login);
		weixin = (RelativeLayout) findViewById(R.id.weixin_login);
		back.setOnClickListener(this);
		weibo.setOnClickListener(this);
		weixin.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.alo_back:
			finish();
			break;
		case R.id.weibo_login:
//			gotoSina();
			SinaShare();
			break;
		case R.id.weixin_login:
//			goToWeixin();
			if (mWeixinAPI == null) {
				mWeixinAPI = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, true);
			}
			mWeixinAPI.registerApp(WEIXIN_APP_ID);
			weixinShare();
			break;

		default:
			break;
		}
	}
    private void SinaShare() {
		// TODO Auto-generated method stub
    	ImageObject imageobj = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),  
                R.drawable.ic_launcher);;
		if (bitmap != null) {
			imageobj.setImageObject(bitmap);
		}
		WeiboMultiMessage multmess = new WeiboMultiMessage();
		TextObject textobj = new TextObject();
		textobj.text = "测试";

		multmess.textObject = textobj;
		multmess.imageObject = imageobj;
		/*微博发送的Request请求*/
		SendMultiMessageToWeiboRequest multRequest = new SendMultiMessageToWeiboRequest();
		multRequest.multiMessage = multmess;
		//以当前时间戳为唯一识别符
		multRequest.transaction = String.valueOf(System.currentTimeMillis());
		mWeiboShareAPI.sendRequest(this, multRequest);

	}

	private void weixinShare() {
		// TODO Auto-generated method stub
    	WXWebpageObject webpage = new WXWebpageObject();  
        webpage.webpageUrl = "http://baidu.com";  
        WXMediaMessage msg = new WXMediaMessage(webpage);  
      
        msg.title = "title";  
        msg.description = getResources().getString(  
                R.string.aboutus_ch);  
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),  
                R.drawable.ic_launcher);  
        msg.setThumbImage(thumb);  
        SendMessageToWX.Req req = new SendMessageToWX.Req();  
        req.transaction = String.valueOf(System.currentTimeMillis());  
        req.message = msg;  
        req.scene = 1;  //0-->发给好友  1-->发到朋友圈
        mWeixinAPI.sendReq(req); 
	}

	//使用微信登录
	private void goToWeixin() {

		if (!mWeixinAPI.isWXAppInstalled()) {
			Methods.showToast(getApplicationContext(), "no weixin");
			return;
		}

		SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "wechat_sdk_demo";
		mWeixinAPI.sendReq(req);
	}

	//微博登录回调
	class AuthListener implements WeiboAuthListener {
		@Override
		public void onComplete(Bundle values) {
			//获取微博用户登录信息
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			String access_token = values.getString("access_token");  
            String expires_in = values.getString("expires_in");
            final String idstr = values.getString("uid");
		    Log.e("access_token", access_token);												// 中解析
		    Log.e("expires_in", expires_in);	
		    Log.e("idstr", idstr);
		    mAccessToken = new Oauth2AccessToken(access_token, expires_in);
		    
			if (mAccessToken.isSessionValid()) {
				AccessTokenKeeper.writeAccessToken(LoginOthersActivity.this, mAccessToken);
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")  
                .format(new java.util.Date(mAccessToken.getExpiresTime()));
				AccessTokenKeeper.writeAccessToken(LoginOthersActivity.this, mAccessToken);
//				AccessTokenKeeper.readAccessToken(LoginOthersActivity.this);
			} else {
				// 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
				String code = values.getString("code", "");
				Log.e("ErrorCode", code+"------------");
			}
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub
            
		}
	}

	private void gotoSina() {
		mSsoHandler = new SsoHandler(this, mAuthInfo);
		mSsoHandler.authorize(new AuthListener());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
    @Override
    protected void onNewIntent(Intent intent) {
    	// TODO Auto-generated method stub
    	super.onNewIntent(intent);
    	mWeiboShareAPI.handleWeiboResponse(intent, this); //当前应用唤起微博分享后，返回当前应用
    }

	@Override
	public void onResponse(BaseResponse arg0) {
		// TODO Auto-generated method stub
		switch (arg0.errCode) {
	    case WBConstants.ErrorCode.ERR_OK:
	    	Methods.showToast(LoginOthersActivity.this, "微博分享成功");
	        break;
	    case WBConstants.ErrorCode.ERR_CANCEL:
	        break;
	    case WBConstants.ErrorCode.ERR_FAIL:
	        break;
	    }

	}
}
