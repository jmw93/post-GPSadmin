package com.example.dbhelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    TextView textView1;
    TextView textView2;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);
        editText4=findViewById(R.id.editText4);
        editText5=findViewById(R.id.editText5);
        textView1 = findViewById(R.id.textView1);

        Button button = (Button)findViewById(R.id.button1);
        Button button2= (Button)findViewById(R.id.button2);
        Button button3 =(Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String databaseName = editText1.getText().toString();
                openDatabase(databaseName);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = editText2.getText().toString();
                createTable(tableName);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText3.getText().toString();
                String ageStr = editText4.getText().toString();
                String mobile = editText5.getText().toString();

                int age=-1;
                try {
                    age = Integer.parseInt(ageStr);
                }catch(Exception e){}

                insertData(name, age, mobile);

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = editText2.getText().toString();
                selectData(tableName);
            }
        });
    }

    public void openDatabase(String databaseName){
        println("openDatabase() 호출됨");

         database = openOrCreateDatabase(databaseName,MODE_PRIVATE,null);
        if(database !=null){
            println("데이터베이스 오픈됨.");
        }
    }

    public void println(String data){    //로그 찍기위한 메소드
        textView1.append(data+"\n");
    }

    public void createTable(String tableName){
        println("createTable()호출됨");
        if(database !=null) {
            String sql = "create table " + tableName + "(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text)"; // 띄어쓰기 조심하기
            database.execSQL(sql);

            println("테이블생성됨");
            }else {
            println("오류! 데이터베이스를 오픈하세요");
            }
        }
     public void insertData(String name, int age, String mobile){
        println("insertDate()호출됨");

        if(database !=null){
            String sql = "insert into customer(name, age, mobile) values(?,?,?)";
            Object[] params = {name, age, mobile};
            database.execSQL(sql, params);
            println("데이터 추가함");
        }else{
            println("오류!데이터베이스를 오픈하세요");
        }
     }

     public void selectData(String tableName){
        println("selectData()호출됨");
        if(database!=null){
            String sql ="select name, age, mobile from " + tableName;
            Cursor cursor = database.rawQuery(sql,null);
            println("조회된 데이터 개수:" + cursor.getCount());

            for ( int i=0; i<cursor.getCount(); i++){
                cursor.moveToNext();
                String name= cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                println ("#" + i + " -> " + name + ", " + age +", " + mobile);

            }
            cursor.close();
        }else{

        }
     }

}

