package com.example.calculatorapp;
import java.lang.reflect.Method;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    private Button clear,back;
    private Button division,multiplication,subtraction,addition,equal;//÷,X,-,+,=
    private Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button0;
    private Button button_mins;
    private Button two_ze,take_over,Square;
    private EditText show1,show2;
    private String text="";
    private double count,count1=1,tag=0;
    private boolean flag=true;
    private boolean isOperation=false;
    private boolean isOver=false;                //等于之后就over
    private String tempnum="";
    private String lastSign="";
    //private String Back_test="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void setMyEditText1(String str) {
        show1.setText(str);
        show1.setSelection(str.length());//将光标移至文字末尾
        disableShowSoftInput();
    }
    private void setMyEditText2(String str) {
        show2.setText(str);
    }
    /**
     * 添加一个数字
     * @param str
     */
    //始终屏蔽软件盘，并且让光标跟随内容的走动而动
    public void disableShowSoftInput()
    {
        if (android.os.Build.VERSION.SDK_INT <= 10)
        {
            show1.setInputType(InputType.TYPE_NULL);
        }
        else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus",boolean.class);
                method.setAccessible(true);
                method.invoke(show1, false);
            }catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus",boolean.class);
                method.setAccessible(true);
                method.invoke(show1, false);
            }catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    private void addNum(String str) {
        isOver=false;
        tempnum += str;
        setMyEditText1(text + tempnum);
    }
    private void clear(){//清除
        clear2();
        setMyEditText1("0");
        setMyEditText2("");
    }
    private void clear2(){   //重新把系统初始化
        lastSign="";
//    	isOver=false;
        tempnum="";
        count=0;
        text="";
        flag=true;
    }
    private void back(){//后退
        if(!isOver){
            try{
                //String aString;
                //aString=show1.getText().toString();
                //StringBuffer buffer=new StringBuffer(aString);
                //if(buffer.length()!=0){
                //	buffer.deleteCharAt(buffer.length() - 1);
                //	aString=buffer.toString();
                //	setMyEditText1(aString);
                // }
                if((tempnum.substring(tempnum.length()-1,tempnum.length())).equals(".")){
                    flag=true;
                }
                    tempnum=tempnum.substring(0,tempnum.length()-1);
                    setMyEditText1(text+tempnum);
            }catch (Exception e) {
                e.printStackTrace();
                Log.e("异常:", e.getMessage());
            }
        }
    }
    private void equal() {//等于 
        if(tempnum==""){
            isOver=false;
        }
        if(!isOver){
            text+=tempnum+"=";
            isOver=true;//等于之后就over
            jisuan();
            setMyEditText1(text);
            setMyEditText2(""+count);
            clear2();
        }
    }
    private void jisuan() {
       // if(lastSign==""){return;}
        if(tempnum==""&&(lastSign=="+"||lastSign=="-")){
            tag=1;
            tempnum="0";
        }
        if(tempnum==""&&(lastSign=="x"||lastSign=="÷")){
            tag=1;
            tempnum="1";
        }
        double tem = Double.parseDouble(tempnum);
        flag=true;
        try{
            if("+".equals(lastSign)){
                count+=tem;
            }else if("-".equals(lastSign)){
                count=count-tem;
            }else if("x".equals(lastSign))
            {
                count=count*tem;
            }else if("÷".equals(lastSign)){
                if(tem!=0){
                    count=count/tem;
                }else{
                    count=0;
                    Toast.makeText(MainActivity.this,"除数不能为0",Toast.LENGTH_LONG).show();
                    show2.setText("0.0");
                }

            }else if("".equals(lastSign)){
                count=tem;
              }
            else if("%".equals(lastSign)){
                count=count%tem;
            }
            else if ("^".equals(lastSign)) {
                for(int i=1;i<=tem;i++){
                    count1=count1*count;
                }
                count=count1;
                count1=1;
            }
            if(tag==1)   tempnum="";
            tag=0;
        }
        catch(Exception e){
            //System.out.println("出现异常");
            Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
        }
    }
    private void jia() {
        if(!isOver){
            jisuan();
            lastSign="+";
            isOver=true;
            text+=tempnum+"+";
            tempnum="";
            setMyEditText1(text);
        }
    }
    private void jian() {   //减法运算
        if(!isOver){
            jisuan();
            lastSign="-";
            isOver=true;
            text+=tempnum+"-";
            tempnum="";
            setMyEditText1(text);
        }
    }
    private void cheng() {
        if(!isOver){
            jisuan();
            lastSign="x";
            isOver=true;
            text+=tempnum+"x";
            tempnum="";
            setMyEditText1(text);
        }
    }
    private void chu() {    //除法运算
        if(!isOver){
            jisuan();
            lastSign="÷";
            isOver=true;
            text+=tempnum+"÷";
            tempnum="";
            setMyEditText1(text);
        }
    }
    private void quyu() {    //取余运算
        if(!isOver){
            jisuan();
            lastSign="%";
            isOver=true;
            text+=tempnum+"%";
            tempnum="";
            setMyEditText1(text);
        }
    }
    private void square() {    //乘方运算
        if(!isOver){
            jisuan();
            lastSign="^";
            isOver=true;
            text+=tempnum+"^";
            tempnum="";
            setMyEditText1(text);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.two_ze:
                addNum("00");
                break;
            case R.id.btn1:
                addNum("1");
                break;
            case R.id.btn2:
                addNum("2");
                break;
            case R.id.btn3:
                addNum("3");
                break;
            case R.id.btn4:
                addNum("4");
                break;
            case R.id.btn5:
                addNum("5");
                break;
            case R.id.btn6:
                addNum("6");
                break;
            case R.id.btn7:
                addNum("7");
                break;
            case R.id.btn8:
                addNum("8");
                break;
            case R.id.btn9:
                addNum("9");
                break;
            case R.id.btn0:
                addNum("0");
                break;
            case R.id.btn_mins:
                if(flag){
                    addNum(".");
                    flag=false;
                }
                break;
            case R.id.clear:
                clear();
                break;
            case R.id.back:
                //if(text.){

                //}
                back();
                break;
            case R.id.division:
                if(tempnum==""){
                    setMyEditText1("");
                    return ;
                }
                if(tempnum.equals(".")){
                    setMyEditText2("0");
                    return;
                }
                chu();
                break;
            case R.id.multiplication:
                if(tempnum==""){
                    setMyEditText1("");
                    return ;
                }
                if(tempnum.equals(".")){
                    setMyEditText2("0");
                    return;
                }
                cheng();
                break;
            case R.id.subtraction:
                if(tempnum==""){
                    setMyEditText1("");
                    return ;
                }
                if(tempnum.equals(".")){
                    setMyEditText2("0");
                    return;
                }
                jian();
                break;
            case R.id.addition:
                if(tempnum==""){
                    setMyEditText1("");
                    return ;
                }
                if(tempnum.equals(".")){
                    setMyEditText2("0");
                    return;
                }
                jia();
                break;

//                if(tempnum==""&& (lastSign=="+"||lastSign=="-")) {
//                    tag=1;
//                    tempnum = "0";
//                    setMyEditText1( );
//                    return;
//                }else if(tempnum == "" && (lastSign == "x" || lastSign == "÷")) {
//                    tag=1;
//                    tempnum = "1";
//                    setMyEditText1("");
//                    return;
//                }
            case R.id.equal:
                if((tempnum=="")&&(lastSign=="")){
                    setMyEditText2("0");
                    return;
                }
                if(tempnum.equals(".")){
                    if(lastSign=="" || lastSign=="+" ||lastSign=="-" ||lastSign=="*" || lastSign=="/"){
                    clear();
                    setMyEditText2("0");
                    }
                    return;
                }
                equal();
                break;
            case R.id.take_over:
                if(tempnum==""){
                    setMyEditText1("");
                    return ;
                }
                if(tempnum.equals(".")){
                    setMyEditText2("0");
                    return;
                }
                    quyu();
                    break;
            case R.id.square:
                if(tempnum==""&&(flag==true)){
                    setMyEditText1("");
                    return ;
                }
                if(tempnum.equals(".")){
                    clear();
                    setMyEditText2("0");
                    return;
                }
                square();
                break;
            default:
                break;
        }
    }
    private void initView() {
        //编辑框的id操作
        show1=(EditText) findViewById(R.id.show1);
        //show1.setFocusable(false);//始终默认不弹出软键盘
        //show1.clearFocus();   //默认不弹出软键盘
        //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(show1.getWindowToken(),0);
        show2=(EditText) findViewById(R.id.show2);
        //÷,X,-,+,=的id操作
        clear=(Button) findViewById(R.id.clear);
        back=(Button) findViewById(R.id.back);
        division=(Button) findViewById(R.id.division);
        multiplication=(Button) findViewById(R.id.multiplication);
        subtraction=(Button) findViewById(R.id.subtraction);
        addition=(Button) findViewById(R.id.addition);
        equal=(Button) findViewById(R.id.equal);
        button1=(Button) findViewById(R.id.btn1);
        button2=(Button) findViewById(R.id.btn2);
        button3=(Button) findViewById(R.id.btn3);
        button4=(Button) findViewById(R.id.btn4);
        button5=(Button) findViewById(R.id.btn5);
        button6=(Button) findViewById(R.id.btn6);
        button6=(Button) findViewById(R.id.btn6);
        button7=(Button) findViewById(R.id.btn7);
        button8=(Button) findViewById(R.id.btn8);
        button9=(Button) findViewById(R.id.btn9);
        button0=(Button) findViewById(R.id.btn0);
        button_mins=(Button) findViewById(R.id.btn_mins);//小数点的查找Id
        two_ze=(Button) findViewById(R.id.two_ze);
        take_over=(Button) findViewById(R.id.take_over);
        Square=(Button) findViewById(R.id.square);
        //监听
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
        division.setOnClickListener(this);
        multiplication.setOnClickListener(this);
        subtraction.setOnClickListener(this);
        addition.setOnClickListener(this);
        equal.setOnClickListener(this);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button0.setOnClickListener(this);
        button_mins.setOnClickListener(this);

        two_ze.setOnClickListener(this);
        take_over.setOnClickListener(this);
        Square.setOnClickListener(this);
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
}
