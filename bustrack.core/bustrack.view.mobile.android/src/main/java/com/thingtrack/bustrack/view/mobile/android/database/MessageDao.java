package com.thingtrack.bustrack.view.mobile.android.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MessageDao {
	// Database fields
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allColumns = { SQLiteHelper.COLUMN_ID,
									SQLiteHelper.COLUMN_USER,
									SQLiteHelper.COLUMN_DATE,
									SQLiteHelper.COLUMN_MESSAGE};
	
	public MessageDao(Context context) {
		dbHelper = new SQLiteHelper(context);
		
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		
	}
	
	public void close() {
		dbHelper.close();
		
	}
	
	public Message create(String user, Date date, String message) {
		// prepare contents for insert
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COLUMN_USER, user);
		DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		values.put(SQLiteHelper.COLUMN_DATE, iso8601Format.format(date));
		values.put(SQLiteHelper.COLUMN_MESSAGE, message);
		
		// insert new register
		long insertId = database.insert(SQLiteHelper.TABLE_MESSAGES, null, values);
		
		// get the new inserted register from id
		Cursor cursor = database.query(SQLiteHelper.TABLE_MESSAGES, allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		
		Message newMessage = cursorToMessage(cursor);
		
		// close cursor
		cursor.close();
		
		return newMessage;
	}
	
	public void delete(Message message) {
		long id = message.getId();
		System.out.println("Comment deleted with id: " + id);
		
		database.delete(SQLiteHelper.TABLE_MESSAGES, SQLiteHelper.COLUMN_ID + " = " + id, null);
		
	}
	
	public List<Message> getAll() {
		List<Message> messages = new ArrayList<Message>();

		Cursor cursor = database.query(SQLiteHelper.TABLE_MESSAGES, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Message comment = cursorToMessage(cursor);
			messages.add(comment);
			
			cursor.moveToNext();
		}
		
		// Make sure to close the cursor
		cursor.close();
		
		return messages;
	}
	
	private Message cursorToMessage(Cursor cursor) {
		Message message = new Message();
		
		message.setId(cursor.getLong(0));
		message.setUser(cursor.getString(1));
		String dateString = cursor.getString(2);
		DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			message.setDate(iso8601Format.parse(dateString));
		} catch (ParseException e) {
			Log.e(getClass().getSimpleName(), "Parsing ISO8601 datetime failed", e);
		}
		message.setMessage(cursor.getString(3));
		
		return message;
	}
}
