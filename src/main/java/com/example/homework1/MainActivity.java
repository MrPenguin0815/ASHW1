package com.example.homework1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<User> returnedAllUsers = new ArrayList<User>();

    private EditText loginName;
    private EditText loginPassWord;

    private boolean isAgreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginName = (EditText) findViewById(R.id.edit_name);
        loginPassWord = (EditText) findViewById(R.id.edit_password);


        Button goToRegister = (Button)findViewById(R.id.button_register);
        goToRegister.setOnClickListener(this);
        Button goToLogin = (Button) findViewById(R.id.button_login);
        goToLogin.setOnClickListener(this);



        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox_agree);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isAgreed = true;
                }else {
                    isAgreed = false;
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_register:
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivityForResult(intent,1);

            case R.id.button_login:
                String name = loginName.getText().toString();
                String password =loginPassWord.getText().toString();
                //判断是否输入了账户密码
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {



                    //判断是否已从注册的活动中取得用户信息
                    if(returnedAllUsers.isEmpty()){
                        Toast.makeText(MainActivity.this,"无法找到指定用户(；´д｀)ゞ",Toast.LENGTH_SHORT).show();
                   }


                    //判断是否和某位已注册的用户信息一致
                    for (User u: returnedAllUsers) {
                        //若一致，再判断是否同意协议
                        if(u.getName().equals(name) && u.getPassword().equals(password)){

                            if(isAgreed){
                                Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this,"请同意用户协议",Toast.LENGTH_SHORT).show();
                            }
                            return;

                        }
                        //若不一致
                        Toast.makeText(MainActivity.this, "输入信息有误哦*･ω-q) ", Toast.LENGTH_SHORT).show();

                    }


                }else {
                    Toast.makeText(MainActivity.this, "输入信息不完整哦_(:з」∠)", Toast.LENGTH_SHORT).show();
                }


        }


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    returnedAllUsers = data.getParcelableArrayListExtra("data_return");
                    if(!returnedAllUsers.isEmpty()){
                        String firstName = returnedAllUsers.get(0).getName();
                        Toast.makeText(MainActivity.this,"有效元素个数为" + returnedAllUsers.size(),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this,"尚未注册",Toast.LENGTH_LONG).show();
                    }

                }
        }
    }
}