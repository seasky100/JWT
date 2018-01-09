package com.bj.jwt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


public class myDBAdapter {
	//private static final String DATABASE_NAME="loginfo.db";
	private static final String DATABASE_TABLE="maintable";
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_NAME="db_code.db";
	public  static final String PACKAGE_NAME="com.bj.jwt";
	public  static final String PATH_DB="/data"
	+Environment.getDataDirectory().getAbsolutePath()+"/"
	+PACKAGE_NAME;  //��ȡ�洢λ�õ�ַ
	private final int BUFFER_SIZE =400000;
	
			
	
	//where �Ӿ���ʹ�õ��������е�����
	public static final String KEY_ID="_id";
	//���ݿ���ÿ���е�����������
	public static final String KEY_NAME="name";
	public static final int NAME_COLUMN=1;
	//���������ݿ��sql���
	private static final String DATABASE_CREATE ="create table "+
	DATABASE_TABLE+"("+KEY_ID+" integer primary key , "+
			KEY_NAME+" text not null);";
	//�����������ݿ�ʵ���ı���
	private SQLiteDatabase db;
	//ʹ�����ݿ��Ӧ�ó���������
	private Context context;
	//���ݿ�Ĵ�/����helper
	private myDBHelper dbhelper;
	public myDBAdapter(Context _context)
	{
		context=_context;
		dbhelper= new myDBHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	public myDBAdapter(Context _context ,String dbname)
	{
		context=_context;
		dbhelper= new myDBHelper(context,dbname,null,DATABASE_VERSION);
	}
	//�򿪵����db�ļ�
	public  Boolean openImportDB()
	{
		db=this.opendatabase(PATH_DB+"/"+DATABASE_NAME);
		if (db==null) return false;
		else return true;
	
	}
	private SQLiteDatabase opendatabase(String dbfile)
	{
		
		try {
			
			if(!new File(dbfile).exists())
			{
				InputStream is =this.context.getResources().openRawResource(R.raw.db_code);//�������ݿ�
				FileOutputStream fos =new FileOutputStream(dbfile);
				byte[]buffer =new byte[BUFFER_SIZE];
				int count =0;
				while((count=is.read(buffer))>0)
				{
					fos.write(buffer,0,count);
				}
				is.close();
				fos.close();
			}
		SQLiteDatabase sdb =SQLiteDatabase.openOrCreateDatabase(dbfile, null);
		return sdb;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
		return null;
	}
	//���´��������ݿ�
	public myDBAdapter open()throws SQLException
	{
		try {
			db=dbhelper.getWritableDatabase();
		} catch (SQLiteException e) {
			// TODO: handle exception
			db=dbhelper.getReadableDatabase();
		}
		return this;
	}
	public void close()
	{
		if(db.isOpen())
		{
			db.close();
		}
		
	}
	//��������
	public long insertentry()
	{
		ContentValues newValues =new ContentValues();
		newValues.put(KEY_ID, 1);
		newValues.put(KEY_NAME, "��");
		return db.insert(DATABASE_TABLE, null, newValues);
	}
	public Cursor queryentry(String sqlcmd)
	{
		//db.query(DATABASE_TABLE, new String[]{KEY_ID,KEY_NAME}, KEY_ID+"="+_rowIndex, null, null, null, orderBy)
		
		return db.rawQuery(sqlcmd, null);
	}
	private static class myDBHelper extends SQLiteOpenHelper
	{

		public myDBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
		//	db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			/*
			Log.w("TaskDBAdapter", "Upgrading from version "+
			oldVersion+" to "+
			newVersion+",which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			onCreate(db);
			*/
		}
		
	}
}
