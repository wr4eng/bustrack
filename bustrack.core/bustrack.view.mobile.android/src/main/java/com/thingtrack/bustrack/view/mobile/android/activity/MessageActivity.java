package com.thingtrack.bustrack.view.mobile.android.activity;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.thingtrack.bustrack.view.mobile.android.R;
import com.thingtrack.bustrack.view.mobile.android.addon.ActionBar;
import com.thingtrack.bustrack.view.mobile.android.addon.ActionBar.IntentAction;
import com.thingtrack.bustrack.view.mobile.android.database.Message;
import com.thingtrack.bustrack.view.mobile.android.database.MessageDao;

public class MessageActivity extends Activity {
	private ActionBar actionBar;
	
	private MessageAdapter adapter;
	private ListView messageList;
	
	private EditText messageTxt;
	private Button sendButton;
	
	//private int odd_resID, even_resID; 
	
	private List<Message> messages;
	
	private MessageDao messageDao;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 Log.i(getClass().getSimpleName(), "onCreate");
		 
		 setContentView(R.layout.message);
		 		 
	     // create native action bar
	     loadActionBar();
	     
	     // create message list view
	     loadMessageList();
	     
		 // load messages data from SQLite and refresh adapter
		 loadMessagesData();
		 
		 adapter.notifyDataSetChanged();
	}
	
	private void loadActionBar() {
	     actionBar = (ActionBar) findViewById(R.id.actionbar);
		     	     
	     actionBar.setHomeAction(new IntentAction(this, MainActivity.createIntent(this), R.drawable.ic_title_home_default));
         actionBar.setDisplayHomeAsUpEnabled(true);
         
         messageTxt = (EditText) findViewById(R.id.msg);
         messageTxt.setOnKeyListener(onclick_listener);
         
         sendButton = (Button) findViewById(R.id.send);
         sendButton.setOnClickListener(send_listener);
    }
	
	private void loadMessageList() {
		//finding the list view
		messageList = (ListView)findViewById(R.id.messageList);
		adapter = new MessageAdapter();
		messageList.setAdapter(adapter);
		messageList.setCacheColorHint(0);
		
	}
	
	private void loadMessagesData() {
		// create dao and open SQLite
		messageDao = new MessageDao(this);
		messageDao.open();
		
		// retrive all messages
		messages = messageDao.getAll();
	}
		
    private void postMessage() {
    	// add message to SQLite
    	Message message = messageDao.create("XXX", new Date(), messageTxt.getText().toString());
    	
    	// add message to the active list
    	messages.add(message);
    	
    	// refresh listview
    	adapter.notifyDataSetChanged();
    	
    	// initialize message textbox
    	messageTxt.setText("");
    }
    
    // On click listener for the button
    private OnClickListener send_listener = new OnClickListener() {
        public void onClick(View v) {
        	postMessage();
        	
        }
    };
    
    // On click Enter listener for the edit box
    private OnKeyListener onclick_listener = new OnKeyListener() {
    	public boolean onKey(View v, int keyCode, KeyEvent event) {
    		// If the event is a key-up event on the "enter" button
    		if ((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
    			postMessage();
    			return true;
    		}
    		
    		return false;
    	}
    };
        
	/**
	 * This class serves as the adapter that draws rows and provides data to the list 
	 * @author 
	 *
	 */
	class MessageAdapter extends BaseAdapter {
		/**
		 * returns the count of elements in the Array that is used to draw the text in rows 
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			return messages.size();
		}

		/**
		 * Get the data item associated with the specified position in the data set.
		 * (not Implemented at this point)
		 * @param position The position of the row that was clicked (0-n)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			return messages.get(position);
		}

		/**
		 * Get the row id associated with the specified position in the list.
		 * (not implemented at this point)
		 * @param position The position of the row that was clicked (0-n)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * Returns the complete row that the System draws.
		 * It is called every time the System needs to draw a new row;
		 * You can control the appearance of each row inside this function.
		 * @param position The position of the row that was clicked (0-n)
		 * @param convertView The View object of the row that was last created. null if its the first row
		 * @param parent The ViewGroup object of the parent view
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row;
			
//			String even_color,odd_color;
//			SharedPreferences prefList = getSharedPreferences("PrefsFile", MODE_PRIVATE);
//			even_color = prefList.getString("even_bubble_color","pink");
//			odd_color = prefList.getString("odd_bubble_color","green");
//			
//			int even_color_id=getResources().getIdentifier(even_color,"drawable","com.teks.chilltwit"),
//				odd_color_id=getResources().getIdentifier(odd_color,"drawable","com.teks.chilltwit");
//			
//			ImageView even_view,odd_view;
			
			if(messages.get(position).getUser() != "XXX"){
				row = inflater.inflate(R.layout.list_row_layout_even, parent, false);
				TextView textLabel = (TextView) row.findViewById(R.id.text);
				
				textLabel.setText(messages.get(position).getMessage());
				
			}else{
				row = inflater.inflate(R.layout.list_row_layout_odd, parent, false);
				TextView textLabel = (TextView) row.findViewById(R.id.text);
				
				textLabel.setText(messages.get(position).getMessage());
			}

			return (row);
		}
		
	}
	
}
