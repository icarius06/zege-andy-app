package com.zege.zegedevtest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zege.zegedevtest.adapters.HeaderAdapter;
import com.zege.zegedevtest.adapters.LazyAdapter;
import com.zege.zegedevtest.flatui.views.FlatButton;
import com.zege.zegedevtest.utils.Constants;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Constants.initializeFlatUi(this);
        
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TransactionsHomeFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class TransactionsHomeFragment extends Fragment {

        public TransactionsHomeFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
            FlatButton new_transactionBtn =  (FlatButton) rootView.findViewById(R.id.button_add_new);
            new_transactionBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(getActivity(),AddNewTransactionActivity.class);
					startActivity(i);
				}
			});		
			
            final Activity activity =getActivity();
            
            ListView header = (ListView)rootView.findViewById(R.id.headerList);
            header.setAdapter(new HeaderAdapter(activity));
            
            ListView transactionsList =(ListView) rootView.findViewById(R.id.transactions_list);
            
            final ArrayList<String> temp = new ArrayList<String>();
            
            for(int i=0;i<getResources().getStringArray(R.array.themes_array).length;i++){
            	temp.add(getResources().getStringArray(R.array.themes_array)[i]);
            }
            
            transactionsList.setAdapter(new LazyAdapter(activity,temp));
            transactionsList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					Intent i = new Intent(activity,SelectedTransactionActivity.class);
					i.putExtra("item", temp.get(position));
					
					startActivity(i);
				}
			});
            
            return rootView;
        }
    }

}
