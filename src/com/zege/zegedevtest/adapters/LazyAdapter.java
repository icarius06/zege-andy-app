package com.zege.zegedevtest.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zege.zegedevtest.R;

public class LazyAdapter extends BaseAdapter {
    private ArrayList<String> data;
    private static LayoutInflater inflater=null;
    
	public LazyAdapter(Activity activity, ArrayList<String> d){
		data = d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView desc = (TextView)vi.findViewById(R.id.description); // title
        TextView id = (TextView)vi.findViewById(R.id.transaction_id); // id
        //LinearLayout header = (LinearLayout)vi.findViewById(R.id.list_header);
        
        String transaction = data.get(position);
        desc.setText(transaction);
        id.setText(String.valueOf(position));
        
        /*if (position==0){
        	header.setVisibility(View.VISIBLE);
        }
        else{
        	header.setVisibility(View.GONE);
        }*/
        return vi;
    }


}
