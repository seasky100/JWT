package com.bj.jwt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class AllpunishActivity extends Activity{

	Button btn_simple = null;
	Button btn_force=null;
	Button btn_unlocale=null;
	boolean isOpen = false;
	PopupWindow popupWindow = null;
	PopupWindow popupWindow_simple = null;
	PopupWindow popupWindow_force = null;
	PopupWindow popupWindow_unlocale = null;
	private boolean isPopwindowopen = false;
	Drawable btnicon_close = null;//this.getResources().getDrawable(R.drawable.close);
	Drawable btnicon_unfold =null;// this.getResources().getDrawable
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allpunish);	
		btn_simple = (Button) findViewById(R.id.btn_simple);
		btn_force =(Button) findViewById(R.id.btn_force);
		btn_unlocale=(Button)findViewById(R.id.btn_unlocale);
		btnicon_close = this.getResources().getDrawable(R.drawable.close);
		btnicon_unfold = this.getResources().getDrawable(R.drawable.unfold);
		
	}
	//简易处罚
	public void Onclicksimple(View v)
	{
		//btnPunish(v,R.layout.activity_simplepunish,btn_simple);
		
		if (isPopwindowopen) {
			if (popupWindow_simple != null) {
				if (popupWindow_simple.isShowing()) {
					popupWindow_simple.dismiss();
				}
			}
			btn_simple.setBackgroundDrawable(btnicon_close);
		} else {
			// 关闭状态
			
			if (popupWindow_simple == null) {
				popupWindow_simple = MakePopupWindow(R.layout.activity_simplepunish,btn_simple);
			} 
			int[] xy = new int[2];
			v.getLocationOnScreen(xy);
			// popupWindow.showAtLocation(v, Gravity.CENTER,-xy[0] / 2,
			// xy[1] + v.getWidth());
			// popupWindow.showAsDropDown(button,0, 0);
			popupWindow_simple.showAsDropDown(v);
			btn_simple.setBackgroundDrawable(btnicon_unfold);
		}
	
		isPopwindowopen = !isPopwindowopen;

	}
	//强制措施
	public void Onclickforce(View v)
	{
		//btnPunish(v,R.layout.activity_forcepunish,btn_force);
		
		if (isPopwindowopen) {
			if (popupWindow_force != null) {
				if (popupWindow_force.isShowing()) {
					popupWindow_force.dismiss();
				}
			}
			btn_force.setBackgroundDrawable(btnicon_close);
		} else {
			// 关闭状态
			
			if (popupWindow_force == null) {
				popupWindow_force = MakePopupWindow(R.layout.activity_forcepunish,btn_force);
			} 
			int[] xy = new int[2];
			v.getLocationOnScreen(xy);
			// popupWindow.showAtLocation(v, Gravity.CENTER,-xy[0] / 2,
			// xy[1] + v.getWidth());
			// popupWindow.showAsDropDown(button,0, 0);
			popupWindow_force.showAsDropDown(v);
			btn_force.setBackgroundDrawable(btnicon_unfold);
		}
	
		isPopwindowopen = !isPopwindowopen;
	}
	//非现场处理
	public void Onclickunlocale(View v)
	{
		//btnPunish(v,R.layout.activity_unlocale,btn_unlocale);
		if (isPopwindowopen) {
			if (popupWindow_unlocale != null) {
				if (popupWindow_unlocale.isShowing()) {
					popupWindow_unlocale.dismiss();
				}
			}
			btn_unlocale.setBackgroundDrawable(btnicon_close);
		} else {
			// 关闭状态
			
			if (popupWindow_unlocale == null) {
				popupWindow_unlocale = MakePopupWindow(R.layout.activity_unlocale,btn_unlocale);
			} 
			int[] xy = new int[2];
			v.getLocationOnScreen(xy);
			// popupWindow.showAtLocation(v, Gravity.CENTER,-xy[0] / 2,
			// xy[1] + v.getWidth());
			// popupWindow.showAsDropDown(button,0, 0);
			popupWindow_unlocale.showAsDropDown(v);
			btn_unlocale.setBackgroundDrawable(btnicon_unfold);
		}
	
		isPopwindowopen = !isPopwindowopen;
	}
	public void btnPunish(View v,int rlayout,Button rbtn)
	{
		if (isPopwindowopen) {
			if (popupWindow != null) {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}
			rbtn.setBackgroundDrawable(btnicon_close);
		} else {
			// 关闭状态
			
			if (popupWindow == null) {
				popupWindow = MakePopupWindow(rlayout,rbtn);
			} 
			int[] xy = new int[2];
			v.getLocationOnScreen(xy);
			// popupWindow.showAtLocation(v, Gravity.CENTER,-xy[0] / 2,
			// xy[1] + v.getWidth());
			// popupWindow.showAsDropDown(button,0, 0);
			popupWindow.showAsDropDown(v);
			rbtn.setBackgroundDrawable(btnicon_unfold);
		}
	
		isPopwindowopen = !isPopwindowopen;
	}
	//简易机动车
   public void OnClickSimpleVehicle(View v)
   {
	   
	   Intent intent =new Intent(AllpunishActivity.this,VehicleActivity.class);
	   intent.putExtra("punishtype", "s");
	   startActivity(intent);
	   
	   /*
	   Intent intent =new Intent(AllpunishActivity.this,driverinfo.class);
	   //intent.putExtra("punishtype", "s");
	   startActivity(intent);
	   */
   }
   /**
	 * 创建导航菜单的下拉菜单popwindow
	 * 
	 * @param cx
	 * @return
	 */
	PopupWindow MakePopupWindow(int rlayout,final Button rbtn) {

		PopupWindow window;
		View contentView = LayoutInflater.from(
				AllpunishActivity.this).inflate(
				rlayout, null);

		window = new PopupWindow(contentView, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		 
		// window.setContentView(contentView);

		 // window.setBackgroundDrawable(getResources().getDrawable(R.drawable.pop_bg));
		
		// window.setBackgroundDrawable(new ColorDrawable(R.color.xxxer));
		 
		// window.setWidth(150);
		// window.setHeight(150);

		window.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				//isPopwindowopen=false;
				//expandBtn.setText("打开");
				rbtn.setBackgroundDrawable(btnicon_close);
			}
		});

		// 设置PopupWindow外部区域是否可触摸
		//window.setFocusable(true); // 设置PopupWindow可获得焦点
		window.setTouchable(true); // 设置PopupWindow可触摸
		window.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
		
		return window;
	}
	
   public void OnClickSimpleNvehicle(View v)
   {
	   new AlertDialog.Builder(this)  
   	
       .setTitle("标题")

       .setMessage("暂无此功能")

       .setPositiveButton("确定", null)

       .show();
   }
   //强制
   public void OnClickForceVehicle(View v)
   {
	   Intent intent =new Intent(AllpunishActivity.this,VehicleActivity.class);
	   intent.putExtra("punishtype", "f");
	   startActivity(intent);
   }
   public void OnClickForceNvehicle(View v)
   {
	   new AlertDialog.Builder(this)  
   	
       .setTitle("标题")

       .setMessage("暂无此功能")

       .setPositiveButton("确定", null)

       .show();
   }
   public void OnClickForceNotification(View v)
   {
	   Intent intent =new Intent(AllpunishActivity.this,VehicleActivity.class);
	   intent.putExtra("punishtype", "n");
	   startActivity(intent);
   }
   public void OnClickUnlocale(View v)
   {
	   new AlertDialog.Builder(this)  
   	
       .setTitle("标题")

       .setMessage("暂无此功能")

       .setPositiveButton("确定", null)

       .show();
   }
   public void OnClickpunishback(View v)
   {
	  
	   AllpunishActivity.this.finish();
   }
}
