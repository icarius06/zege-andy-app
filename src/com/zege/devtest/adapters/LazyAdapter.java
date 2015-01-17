package com.zege.devtest.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zege.devtest.R;
import com.zege.devtest.models.TransactionModel;

public class LazyAdapter extends BaseAdapter {
    private ArrayList<TransactionModel> data;
    private static LayoutInflater inflater=null;
    
	public LazyAdapter(Activity activity, ArrayList<TransactionModel> d){
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

        TextView amount = (TextView)vi.findViewById(R.id.amount); // amount
        TextView units = (TextView)vi.findViewById(R.id.units); // units
        TextView particulars = (TextView)vi.findViewById(R.id.particulars); // units
        TextView priority = (TextView)vi.findViewById(R.id.priority); // units
        TextView id = (TextView)vi.findViewById(R.id.transaction_id); // id
        //LinearLayout header = (LinearLayout)vi.findViewById(R.id.list_header);
        
        amount.setText("Amount:"+data.get(position).getAmount());
        units.setText("Units:"+data.get(position).getUnits()+",");
        particulars.setText("Particulars:"+data.get(position).getParticulars()+",");
        id.setText(String.valueOf(position));
        
        if(data.get(position).getTran_color()!=null){
        	if(data.get(position).getTran_color().equals("undefined")){
        		priority.setText("Low");
        		priority.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_low, 0, 0, 0);
        	}
        	else{
        		if(data.get(position).getTran_color().equals("Red")){
        			priority.setText("Very High");
        			priority.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_very_high, 0, 0, 0);
        		}
        		if(data.get(position).getTran_color().equals("Blue")){
        			priority.setText("High");
        			priority.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_high, 0, 0, 0);
        		}
        		if(data.get(position).getTran_color().equals("Green")){
        			priority.setText("Medium");
        			priority.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_medium, 0, 0, 0);
        		}
        		if(data.get(position).getTran_color().equals("Yellow")){
        			priority.setText("Low");
        			priority.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_low, 0, 0, 0);
        		}
        	}
        }
        else{
        	priority.setText("Low");
    		priority.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_low, 0, 0, 0);
        }
        
        
        /*if (position==0){
        	header.setVisibility(View.VISIBLE);
        }
        else{
        	header.setVisibility(View.GONE);
        }*/
        return vi;
    }


}
