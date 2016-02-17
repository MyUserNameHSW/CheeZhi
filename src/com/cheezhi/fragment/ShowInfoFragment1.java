package com.cheezhi.fragment;

import com.cheezhi.cheezhi.FixdetialActivity;
import com.cheezhi.cheezhi.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowInfoFragment1 extends Fragment {
	View view;
    TextView detail;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup p = (ViewGroup) view.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
		}
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.slide_fragment_one,
				(ViewGroup) getActivity().findViewById(R.id.vPager), false);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		detail = (TextView) view.findViewById(R.id.detial_infos);
		detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), FixdetialActivity.class);
				startActivity(intent);
			}
		});
	}
}
