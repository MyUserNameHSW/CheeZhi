package com.cheezhi.fragment;

import com.cheezhi.cheezhi.AboutusActivity;
import com.cheezhi.cheezhi.FeedbackActivity;
import com.cheezhi.cheezhi.LoginActivity;
import com.cheezhi.cheezhi.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MenuFragment extends Fragment implements OnClickListener {
	View view;
	LinearLayout login_lin,feedback_lin,aboutus_lin,signout_lin;
	Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.menu_fragment, null);
		initView();
		return view;
	}

	private void initView() {
		login_lin = (LinearLayout) view.findViewById(R.id.goto_login);
		feedback_lin = (LinearLayout) view.findViewById(R.id.feedback_lin);
		aboutus_lin = (LinearLayout) view.findViewById(R.id.aboutus_lin);
		signout_lin = (LinearLayout) view.findViewById(R.id.signout_lin);
		login_lin.setOnClickListener(this);
		feedback_lin.setOnClickListener(this);
		aboutus_lin.setOnClickListener(this);
		signout_lin.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.goto_login:
			intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.feedback_lin:
			intent = new Intent(getActivity(), FeedbackActivity.class);
			startActivity(intent);
			break;
		case R.id.aboutus_lin:
			intent = new Intent(getActivity(), AboutusActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
