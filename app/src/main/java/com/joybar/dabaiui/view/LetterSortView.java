package com.joybar.dabaiui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.joybar.dabaiui.R;

import java.util.ArrayList;
import java.util.List;

public class LetterSortView extends View {
	// 触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26个字母
	// public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
	// "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
	// "W", "X", "Y", "Z", "#" };

	public static List<String> list = new ArrayList<String>();

	public static void setListIndex(List<String> mlist) {
		list = mlist;
	}

	public static String[] bb = { "A", "B", "#" };

	public static void setIndexLetter(String[] bl) {
		bb = bl;
	}

	private int choose = -1;// 选中
	private Paint paint = new Paint();

	private TextView mTextDialog;

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public LetterSortView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LetterSortView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LetterSortView(Context context) {
		super(context);
	}

	/**
	 * 重写这个方法
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		int singleHeight = 1;
		if(list.size()!=0){
			singleHeight = height / list.size();// 获取每一个字母的高度
		}
	

		for (int i = 0; i < list.size(); i++) {
			paint.setColor(Color.parseColor("#9da0a4"));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			// paint.setTextSize(PixelUtil.sp2px(12));
			paint.setTextSize(25);

			// 选中的状态
			if (i == choose) {
				paint.setColor(Color.parseColor("#CD302F"));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(list.get(i)) / 2;
		//	float yPos = singleHeight * i + singleHeight;
			float yPos = singleHeight * i+singleHeight*13/20;
			canvas.drawText(list.get(i), xPos, yPos, paint);
			paint.reset();// 重置画笔
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * list.size());// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			choose = -1;//
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			// 设置右侧字母列表[A,B,C,D,E....]的背景颜色
			setBackgroundResource(R.drawable.letter_sort_background);
			if (oldChoose != c) {
				if (c >= 0 && c < list.size()) {
					if (listener != null) {
						listener.onTouchingLetterChanged(list.get(c));
					}
					if (mTextDialog != null) {
						mTextDialog.setText(list.get(c));
						mTextDialog.setVisibility(View.VISIBLE);
					}

					choose = c;
					invalidate();
				}
			}

			break;
		}
		return true;
	}

	/**
	 * 向外公开的方法
	 * 
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}
