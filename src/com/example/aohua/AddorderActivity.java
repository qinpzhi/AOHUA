package com.example.aohua;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddorderActivity extends Activity implements OnTouchListener{
	private AutoCompleteTextView autotext;
	private ArrayAdapter<String> arrayAdapter;
	private String parenturl="http://10.132.23.147:8080/AOHUAServlet/";
	private Calendar calendar;//����װ���ڵ�
	private DatePickerDialog dialog;
	private Integer DeptID;//����������洢���ŵ�id��
	private Integer TransportID;//����������洢���䷽ʽid��
	private Integer SettleID;//����������洢���㷽ʽ��id��
	private Integer CustID;//����������洢ѡ��Ŀͻ���id��
	private Integer SellerID;//����������洢ҵ��Ա��id��
	private AlertDialog alert;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addorder);
		//������������
		this.findViewById(R.id.relative_DeliveryDate).setOnTouchListener(this);
		//����ǩ������
		this.findViewById(R.id.relative_SignDate).setOnTouchListener(this);
		//��д�����ص�
		this.findViewById(R.id.relative_DeliveryAddr).setOnTouchListener(this);
		//��д�������
		this.findViewById(R.id.relative_Freight).setOnTouchListener(this);
		//SignAddr��дǩ����ַ
		this.findViewById(R.id.relative_SignAddr).setOnTouchListener(this);
		//ContractCode��д��ͬ��
		this.findViewById(R.id.relative_ContractCode).setOnTouchListener(this);
		//ReceDays��д�տ���
		this.findViewById(R.id.relative_ReceDays).setOnTouchListener(this);
		//DeptName������������ѡ���б�
		this.findViewById(R.id.relative_DeptName).setOnTouchListener(this);
		//TransportName�������䷽ʽѡ���б�
		this.findViewById(R.id.relative_TransportName).setOnTouchListener(this);
		//SettleName�������㷽ʽѡ���б�
		this.findViewById(R.id.relative_SettleName).setOnTouchListener(this);
		//CustName�����ͻ�ѡ���б�
		this.findViewById(R.id.relative_CustName).setOnTouchListener(this);
		//SellerName����ҵ��Աѡ���б�
		this.findViewById(R.id.relative_SellerName).setOnTouchListener(this);	
		
		//���ǶԿͻ����Զ���ʾ��
//        autotext =(AutoCompleteTextView) findViewById(R.id.addorder_CustName);
//        String [] arr={"�㽭��ҵ�о�Ժ","���ݿƼ�","�㽭��ѧ","�������޹�˾"};
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
//        autotext.setAdapter(arrayAdapter);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//�����ѡ��ʱ���
		if(v.getId()==R.id.relative_DeliveryDate){
			calendar=Calendar.getInstance();
			dialog=new DatePickerDialog(AddorderActivity.this,new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					 ((TextView)findViewById(R.id.addorder_DeliveryDate)).setText(year+"-"+(month+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}else if(v.getId()==R.id.relative_SignDate){
			calendar=Calendar.getInstance();
			dialog=new DatePickerDialog(AddorderActivity.this,new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					 ((TextView)findViewById(R.id.addorder_SignDate)).setText(year+"-"+(month+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}else if(v.getId()==R.id.relative_DeliveryAddr){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
			final EditText edit_text=new EditText(this);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					 ((TextView)findViewById(R.id.addorder_DeliveryAddr)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.relative_Freight){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
			final EditText edit_text=new EditText(this);
			//ֻ���������ֺ�С����
			edit_text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					 ((TextView)findViewById(R.id.addorder_Freight)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.relative_SignAddr){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
			final EditText edit_text=new EditText(this);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					 ((TextView)findViewById(R.id.addorder_SignAddr)).setText(edit_text.getText().toString().trim());
				}
			});
			alert.show();
		}else if(v.getId()==R.id.relative_ContractCode){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
			final EditText edit_text=new EditText(this);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					 ((TextView)findViewById(R.id.addorder_ContractCode)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.relative_ReceDays){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
			final EditText edit_text=new EditText(this);
			//ֻ����������
			edit_text.setInputType( InputType.TYPE_CLASS_NUMBER);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					 ((TextView)findViewById(R.id.addorder_ReceDays)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}//����ѡ����������Ҫѡ���
		else if(v.getId()==R.id.relative_DeptName){
				new Thread(){
					public void run() {
						String target=parenturl+"getdepartment";
						HttpClient httpClient=new DefaultHttpClient();
						HttpPost httpRequest=new HttpPost(target);
						try{
							HttpResponse httpResponse=httpClient.execute(httpRequest);
							if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
								//����ɹ�
								Message message=Message.obtain();
								message.what=1;
								message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
								handler1.sendMessage(message);
							}else{
								//�����������ʧ��
								Message message=Message.obtain();
								message.what=0;
								handler1.sendMessage(message);
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					};
				}.start();
		}else if(v.getId()==R.id.relative_TransportName){
			new Thread(){
				public void run() {
					String target=parenturl+"gettransportmode";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					try{
						HttpResponse httpResponse=httpClient.execute(httpRequest);
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							//����ɹ�
							Message message=Message.obtain();
							message.what=1;
							message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
							handler2.sendMessage(message);
						}else{
							//�����������ʧ��
							Message message=Message.obtain();
							message.what=0;
							handler2.sendMessage(message);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();
		}else if(v.getId()==R.id.relative_SettleName){
			new Thread(){
				public void run() {
					String target=parenturl+"getsettlemode";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					try{
						HttpResponse httpResponse=httpClient.execute(httpRequest);
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							//����ɹ�
							Message message=Message.obtain();
							message.what=1;
							message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
							handler3.sendMessage(message);
						}else{
							//�����������ʧ��
							Message message=Message.obtain();
							message.what=0;
							handler3.sendMessage(message);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();
		}else if(v.getId()==R.id.relative_CustName){
			new Thread(){
				public void run() {
					String target=parenturl+"getse_customer";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					try{
						HttpResponse httpResponse=httpClient.execute(httpRequest);
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							//����ɹ�
							Message message=Message.obtain();
							message.what=1;
							message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
							handler4.sendMessage(message);
						}else{
							//�����������ʧ��
							Message message=Message.obtain();
							message.what=0;
							handler4.sendMessage(message);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();
		}else if(v.getId()==R.id.relative_SellerName){
			new Thread(){
				public void run() {
					String target=parenturl+"getemployee";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					try{
						HttpResponse httpResponse=httpClient.execute(httpRequest);
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							//����ɹ�
							Message message=Message.obtain();
							message.what=1;
							message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
							handler5.sendMessage(message);
						}else{
							//�����������ʧ��
							Message message=Message.obtain();
							message.what=0;
							handler5.sendMessage(message);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();
		}

		return false;
	}
	//����ѡ���
	private Handler handler1=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity.this,"��������ʧ�ܣ�", Toast.LENGTH_LONG).show();
			}else{
				try {
					List<Integer> codelist=new ArrayList<Integer>();
					List<String> list=new ArrayList<String>();
					JSONArray json = new JSONArray( msg.obj.toString());
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
					//	departMentCode.add((Integer) temp.get("DeptID"));
						list.add(temp.getString("DeptName")); 
						codelist.add(temp.getInt("DeptID"));
					}
					Builder builder =new AlertDialog.Builder(AddorderActivity.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
				       {  
				           @Override  
				           public void onClick(DialogInterface dialog, int which)  
				           {  
				               ((TextView)findViewById(R.id.addorder_DeptName)).setText(items[which].toString().trim());
				               DeptID=codeitems[which];
				               Toast.makeText(AddorderActivity.this, "ѡ��Ĳ���idΪ��" + DeptID, Toast.LENGTH_SHORT).show();
				           }  
				       });  
					builder.create().show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	//���䷽ʽѡ���
	private Handler handler2=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity.this,"��������ʧ�ܣ�", Toast.LENGTH_LONG).show();
			}else{
				try {
					List<Integer> codelist=new ArrayList<Integer>();
					List<String> list=new ArrayList<String>();
					JSONArray json = new JSONArray( msg.obj.toString());
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
					//	departMentCode.add((Integer) temp.get("DeptID"));
						list.add(temp.getString("TransportName")); 
						codelist.add(temp.getInt("TransportID"));
					}
					Builder builder =new AlertDialog.Builder(AddorderActivity.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
				       {  
				           @Override  
				           public void onClick(DialogInterface dialog, int which)  
				           {  
				               ((TextView)findViewById(R.id.addorder_TransportName)).setText(items[which].toString().trim());
				               TransportID=codeitems[which];
				               Toast.makeText(AddorderActivity.this, "ѡ������䷽ʽidΪ��" + TransportID, Toast.LENGTH_SHORT).show();
				           }  
				       });  
					builder.create().show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	//���㷽ʽѡ���
		private Handler handler3=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==0){
					Toast.makeText(AddorderActivity.this,"��������ʧ�ܣ�", Toast.LENGTH_LONG).show();
				}else{
					try {
						List<Integer> codelist=new ArrayList<Integer>();
						List<String> list=new ArrayList<String>();
						JSONArray json = new JSONArray( msg.obj.toString());
						for(int i=0;i<json.length();i++){
							JSONObject temp = (JSONObject) json.get(i);  
						//	departMentCode.add((Integer) temp.get("DeptID"));
							list.add(temp.getString("SettleName")); 
							codelist.add(temp.getInt("SettleID"));
						}
						Builder builder =new AlertDialog.Builder(AddorderActivity.this);
						final String[] items=list.toArray(new String[list.size()]);
						final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
						builder.setItems(items, new DialogInterface.OnClickListener()  
					       {  
					           @Override  
					           public void onClick(DialogInterface dialog, int which)  
					           {  
					               ((TextView)findViewById(R.id.addorder_SettleName)).setText(items[which].toString().trim());
					               SettleID=codeitems[which];
					               Toast.makeText(AddorderActivity.this, "ѡ��Ľ��㷽ʽidΪ��" + SettleID, Toast.LENGTH_SHORT).show();
					           }  
					       });  
						builder.create().show();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.handleMessage(msg);
			}
		};
		//�ͻ�ѡ���
		private Handler handler4=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==0){
					Toast.makeText(AddorderActivity.this,"��������ʧ�ܣ�", Toast.LENGTH_LONG).show();
				}else{
					try {
						List<Integer> codelist=new ArrayList<Integer>();
						List<String> list=new ArrayList<String>();
						JSONArray json = new JSONArray( msg.obj.toString());
						for(int i=0;i<json.length();i++){
							JSONObject temp = (JSONObject) json.get(i);  
						//	departMentCode.add((Integer) temp.get("DeptID"));
							list.add(temp.getString("CustName")); 
							codelist.add(temp.getInt("CustID"));
						}
						Builder builder =new AlertDialog.Builder(AddorderActivity.this);
						final String[] items=list.toArray(new String[list.size()]);
						final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
						builder.setItems(items, new DialogInterface.OnClickListener()  
					       {  
					           @Override  
					           public void onClick(DialogInterface dialog, int which)  
					           {  
					               ((TextView)findViewById(R.id.addorder_CustName)).setText(items[which].toString().trim());
					               CustID=codeitems[which];
					               Toast.makeText(AddorderActivity.this, "ѡ��Ŀͻ�idΪ��" + CustID, Toast.LENGTH_SHORT).show();
					           }  
					       });  
						builder.create().show();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.handleMessage(msg);
			}
		};	
		//ҵ��Աѡ���
		private Handler handler5=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==0){
					Toast.makeText(AddorderActivity.this,"��������ʧ�ܣ�", Toast.LENGTH_LONG).show();
				}else{
					try {
						List<Integer> codelist=new ArrayList<Integer>();
						List<String> list=new ArrayList<String>();
						JSONArray json = new JSONArray( msg.obj.toString());
						for(int i=0;i<json.length();i++){
							JSONObject temp = (JSONObject) json.get(i);  
						//	departMentCode.add((Integer) temp.get("DeptID"));
							list.add(temp.getString("EmpName")); 
							codelist.add(temp.getInt("EmpID"));
						}
						Builder builder =new AlertDialog.Builder(AddorderActivity.this);
						final String[] items=list.toArray(new String[list.size()]);
						final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
						builder.setItems(items, new DialogInterface.OnClickListener()  
					       {  
					           @Override  
					           public void onClick(DialogInterface dialog, int which)  
					           {  
					               ((TextView)findViewById(R.id.addorder_SellerName)).setText(items[which].toString().trim());
					               SellerID=codeitems[which];
					               Toast.makeText(AddorderActivity.this, "ѡ���ҵ��ԱidΪ��" + SellerID, Toast.LENGTH_SHORT).show();
					           }  
					       });  
						builder.create().show();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.handleMessage(msg);
			}
		};
}