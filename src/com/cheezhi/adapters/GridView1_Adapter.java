package com.cheezhi.adapters;

import java.util.ArrayList;
import java.util.List;

import com.cheezhi.bean.MenuItem;
import com.cheezhi.cheezhi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridView1_Adapter extends BaseAdapter {
	Context context;
	LayoutInflater mInflater;
	List<MenuItem> list = new ArrayList<MenuItem>();
	ViewHolder mHolder;

	public GridView1_Adapter(Context context, List<MenuItem> list) {
		super();
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.gridview_item1, null);
			mHolder = new ViewHolder();
			mHolder.titleImg = (ImageView) arg1
					.findViewById(R.id.gridview_icon);
			mHolder.bigName = (TextView) arg1.findViewById(R.id.menu_item_big);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		mHolder.titleImg.setImageResource(list.get(arg0).getImgId());
		mHolder.bigName.setText(list.get(arg0).getBigName());

		return arg1;
	}

	class ViewHolder {
		ImageView titleImg;
		TextView bigName;
	}
}
