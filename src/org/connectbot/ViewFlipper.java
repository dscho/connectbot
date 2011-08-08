/*
 * ConnectBot: simple, powerful, open-source SSH client for Android
 * Copyright 2007 Kenny Root, Jeffrey Sharkey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.connectbot;

import android.app.Activity;

import android.content.Context;

import android.graphics.Rect;

import android.util.AttributeSet;

/**
 * A ViewFlipper that keeps tabs on whether the soft keyboard is displayed.
 *
 * @author dscho
 */
public class ViewFlipper extends android.widget.ViewFlipper {
	public ViewFlipper(Context context) {
		super(context);
	}

	public ViewFlipper(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public interface Listener {
		public void onSoftKeyboardShown(boolean isShowing);
	}

	private Listener listener;

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = MeasureSpec.getSize(heightMeasureSpec);
		Activity activity = (Activity)getContext();
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top;
		int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
		int diff = (screenHeight - statusBarHeight) - height;
		if (listener != null) {
			// assume all soft keyboards are at least 128 pixels high
			listener.onSoftKeyboardShown(diff > 128);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);       
	}
}
