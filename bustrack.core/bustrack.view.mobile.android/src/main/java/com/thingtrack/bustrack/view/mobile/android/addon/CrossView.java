package com.thingtrack.bustrack.view.mobile.android.addon;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class CrossView extends FrameLayout {
	private ActionBar actionBar;
	private MultiDirectionSlidingDrawer slidingDrawer;
	
	private Rect mFrame = new Rect();
	
	private SimpleDateFormat formatDate = new SimpleDateFormat("d MMM, yyyy");
	
	public CrossView(Context context) {
		super(context);
		this.setClickable(true);
				
	}
	
	public CrossView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setClickable(true);
			
	}
	
	public CrossView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setClickable(true);
	
	}
	
	public void setActionBar(final ActionBar actionBar) {
		this.actionBar = actionBar;
		
	}
	
	public void setSlidingDrawer(final MultiDirectionSlidingDrawer slidingDrawer) {
		this.slidingDrawer = slidingDrawer;
		
		slidingDrawer.setOnDrawerOpenListener(new MultiDirectionSlidingDrawer.OnDrawerOpenListener() {			
			@Override
			public void onDrawerOpened() {
				// date header text
				actionBar.setTitle(formatDate.format(new Date()));
				
				// set actions invisible
				actionBar.setActionVisibility(4);
			}
		});
		
		slidingDrawer.setOnDrawerCloseListener(new MultiDirectionSlidingDrawer.OnDrawerCloseListener() {		
			@Override
			public void onDrawerClosed() {
				// slider invisible
				slidingDrawer.setVisibility(4);	//invisible
				
				// default header text
				actionBar.setTitle("Home");
				
				// set actions
				actionBar.setActionVisibility(0); //visible
				
			}
		});
		
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {	
		if (slidingDrawer.isOpened()) { return false; }
		
		if (slidingDrawer.isLock()) { return false; }
		
		final int action = event.getAction();
		
		float x = event.getX();
		float y = event.getY();

		// get drawer rectangule and adapted the location
		final Rect frame = mFrame;

		slidingDrawer.getHandle().getHitRect(frame);
		frame.top = frame.top + slidingDrawer.getTop();
		frame.bottom = frame.bottom + slidingDrawer.getTop();
		
		if ( !slidingDrawer.isMoving() && !frame.contains( (int)x, (int)y ) ) { return false; }

		return slidingDrawer.onCrossViewInterceptTouchEvent(event, action, slidingDrawer.getHandle(), (int)x, (int)y);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		return slidingDrawer.onTouchEvent(event);

	}
	
}
