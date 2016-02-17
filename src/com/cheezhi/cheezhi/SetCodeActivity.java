package com.cheezhi.cheezhi;

import java.io.File;

import com.cheezhi.utils.Config;
import com.cheezhi.utils.Methods;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SetCodeActivity extends Activity implements OnClickListener {
	ImageView head, back;
	EditText username, code1, code2;
	Button confirm;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_set_code);
		initView();
	}

	private void initView() {
		head = (ImageView) findViewById(R.id.user_headimg);
		back = (ImageView) findViewById(R.id.asc_back);
		username = (EditText) findViewById(R.id.user_name);
		code1 = (EditText) findViewById(R.id.first_code);
		code2 = (EditText) findViewById(R.id.second_code);
		confirm = (Button) findViewById(R.id.submit_info);
		head.setOnClickListener(this);
		back.setOnClickListener(this);
		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.asc_back:
			finish();
			break;
		case R.id.user_headimg:
			showPhotoDialog();
			break;
		case R.id.submit_info:

			break;
		case R.id.openCamera:// 打开相机
			openCamera();
			break;
		case R.id.openPhones:// 打开图库
			openPhones();
			break;
		case R.id.cancel:// 取消
			dialog.cancel();
			break;
		default:
			break;
		}
	}

	private void openPhones() {
		// TODO Auto-generated method stub
		dialog.dismiss();
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, Config.REQUEST_CODE_OPENPICS);
	}

	private void openCamera() {
		// TODO Auto-generated method stub
		dialog.dismiss();
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				SetCodeActivity.this.getExternalCacheDir(), "temp_photo.png")));
		startActivityForResult(intent, Config.REQUEST_CODE_CAMERA);
	}

	// 弹出选择对话框
	private void showPhotoDialog() {
		// TODO Auto-generated method stub
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,
				null);
		view.findViewById(R.id.openCamera).setOnClickListener(this);
		view.findViewById(R.id.openPhones).setOnClickListener(this);
		view.findViewById(R.id.cancel).setOnClickListener(this);
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		// 如果是直接从相册获取
		case Config.REQUEST_CODE_OPENPICS:
			if (data != null)
				startPhotoZoom(data.getData());
			break;
		// 如果是调用相机拍照时
		case Config.REQUEST_CODE_CAMERA:
			System.out.println("----" + data);
			if (resultCode == RESULT_OK) {
				File file = new File(this.getExternalCacheDir(),
						"temp_photo.png");
				startPhotoZoom(Uri.fromFile(file));
			}
			break;
		// 取得裁剪后的图片
		case Config.REQUEST_CODE_ZOOM:
			if (resultCode == RESULT_OK) {
				if (data != null) {
					// dataIntent = data;
					setPicToView(data);
					uploadPhoto();
				}
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			head.setImageBitmap(photo);
		}
	}

	private void uploadPhoto() {
		String url = "http://192.168.1.53:8080/ImageText/myimg";
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("msg", "1");
		params.addBodyParameter("img1", new File(this.getExternalCacheDir(),
				"temp.png"));
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				Methods.showToast(getApplicationContext(), "上传成功");
			}
		});
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 500);
		intent.putExtra("outputY", 500);
		intent.putExtra("return-data", true);
		intent.putExtra("output",
				Uri.fromFile(new File(this.getExternalCacheDir(), "temp.png")));
		startActivityForResult(intent, Config.REQUEST_CODE_ZOOM);
	}
}
