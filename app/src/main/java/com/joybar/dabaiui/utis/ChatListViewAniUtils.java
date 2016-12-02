 package com.joybar.dabaiui.utis;

 import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.RelativeLayout;

public class ChatListViewAniUtils {

	public static int DURATION_LONG = 200;
	public static int DURATION_SHORT = 250;

	public static void upAA(RelativeLayout lv, final RelativeLayout view) {
		if (lv.getTranslationY() == -view.getHeight()) {
			return;
		}
		ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(lv,
				"translationY", 0, -view.getHeight());
		fViewTransYAnim.setDuration(DURATION_SHORT);
		ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(view,
				"translationY", view.getHeight(), 0);
		sViewTransYAnim.setDuration(DURATION_LONG);
		sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				view.setVisibility(View.VISIBLE);
			}
		});

		AnimatorSet showAnim = new AnimatorSet();
		showAnim.playTogether(fViewTransYAnim, sViewTransYAnim);

		showAnim.start();
	}

	public static void upAA(RelativeLayout lv, final RelativeLayout view,
			int distance) {
		if (lv.getTranslationY() == -view.getHeight()) {
		}
		ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(lv,
				"translationY", 0, -distance);
		fViewTransYAnim.setDuration(DURATION_SHORT);
		ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(view,
				"translationY", distance, 0);
		sViewTransYAnim.setDuration(DURATION_LONG);
		sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				view.setVisibility(View.VISIBLE);
			}
		});

		AnimatorSet showAnim = new AnimatorSet();
		showAnim.playTogether(fViewTransYAnim, sViewTransYAnim);

		showAnim.start();
	}

	public static void hideAA(RelativeLayout lv, final RelativeLayout secondView) {

		ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(lv,
				"translationY", -secondView.getHeight(), 0);
		fViewTransYAnim.setDuration(DURATION_SHORT);

		ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(secondView,
				"translationY", 0, secondView.getHeight());
		sViewTransYAnim.setDuration(DURATION_LONG);
		sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationStart(animation);
				secondView.setVisibility(View.INVISIBLE);
			}
		});

		AnimatorSet showAnim = new AnimatorSet();
		showAnim.playTogether(fViewTransYAnim, sViewTransYAnim);

		showAnim.start();
	}

	public static void hideAA(RelativeLayout lv,
			final RelativeLayout secondView, int distance) {

		ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(lv,
				"translationY", -distance, 0);
		fViewTransYAnim.setDuration(DURATION_SHORT);

		ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(secondView,
				"translationY", 0, distance);
		sViewTransYAnim.setDuration(DURATION_LONG);
		sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationStart(animation);
				secondView.setVisibility(View.INVISIBLE);
			}
		});

		AnimatorSet showAnim = new AnimatorSet();
		showAnim.playTogether(fViewTransYAnim, sViewTransYAnim);

		showAnim.start();
	}

	public static void hideAAContent(int count,
			RelativeLayout rel_lv, final RelativeLayout rel_aa) {

		// 防止aa_Content内容为空
		if (count != 0) {
			if (count > 5) {
				ChatListViewAniUtils.hideAA(rel_lv, rel_aa,
						ScreenUtils.convertDipOrPx(40 * 5));
			} else {

				ChatListViewAniUtils.hideAA(rel_lv, rel_aa, ScreenUtils
						.convertDipOrPx(40 * count));
			}

		}

	}

	public static void showAAContent(int count,
			RelativeLayout rel_lv, final RelativeLayout rel_aa) {
		//防止aa_Content内容为空
		if(count!=0){
			if(count>5){
				//Item数量超过5，则为200dp高度
				ViewMPWHUtils.setHeightWidth(rel_aa, ScreenUtils.convertDipOrPx(200), 0);
				ChatListViewAniUtils.upAA(rel_lv,rel_aa,ScreenUtils.convertDipOrPx(40*5));
			}else{

				ViewMPWHUtils.setHeightWidth(rel_aa, ScreenUtils.convertDipOrPx(40*count), 0);
				ChatListViewAniUtils.upAA(rel_lv,rel_aa,ScreenUtils.convertDipOrPx(40*count));
			}

		}

	}

}
