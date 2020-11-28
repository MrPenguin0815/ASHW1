package com.example.homework1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {


    private EditText registerName;
    private EditText registerPassWord;
    private EditText confirmPassWord;
    private ArrayList<User> allUsers = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        if(savedInstanceState != null){
            allUsers = savedInstanceState.getParcelableArrayList("data_key");
        }


        registerName = (EditText) findViewById(R.id.edit_name_register);
        registerPassWord = (EditText) findViewById(R.id.edit_password_register);
        confirmPassWord = (EditText) findViewById(R.id.edit_password_confirm);


        //按钮“注册”的点击事件
        Button registerBt = (Button) findViewById(R.id.button_register);
        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registerName.getText().toString();
                String password = registerPassWord.getText().toString();
                String confirmedPassword = confirmPassWord.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmedPassword)) {
                    if (!isValid(password)) {
                        Toast.makeText(SecondActivity.this, "您输入的密码不符合规范哦", Toast.LENGTH_LONG).show();
                    } else if (!password.equals(confirmedPassword)) {
                        Toast.makeText(SecondActivity.this, "您两次输入的密码不一致哦", Toast.LENGTH_LONG).show();
                    } else {

                        Parcel parcel = Parcel.obtain();
                        User u = User.CREATOR.createFromParcel(parcel);
                        u.setName(name);
                        u.setPassword(password);
                        allUsers.add(u);
                        parcel.recycle();
                        Toast.makeText(SecondActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(SecondActivity.this, "输入信息不完整哦_(:з」∠)", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //按钮“<登录”的点击事件
        Button backToLogin = (Button) findViewById(R.id.button_register_back);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return", allUsers);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }





    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList("data_key",allUsers);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("data_return", allUsers);
        setResult(RESULT_OK, intent);
        finish();
    }



    // 判断密码格式是否合适
    public static boolean isValid(String password) {

        if (password.length() > 0) {
            //判断是否有空格字符串
            for (int t = 0; t < password.length(); t++) {
                String b = password.substring(t, t + 1);
                if (b.equals(" ")) {
                    return false;
                }
            }


            //判断是否是字母和数字
            int numberCounter = 0;
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                }
                if (Character.isDigit(c)) {
                    numberCounter++;
                }
            }

        } else {
            return false;
        }
        return true;
    }
}