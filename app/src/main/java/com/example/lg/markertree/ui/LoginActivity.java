package com.example.lg.markertree.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lg.markertree.R;
import com.example.lg.markertree.network.MyService;
import com.example.lg.markertree.network.NetworkManager;
import com.example.lg.markertree.result.login.LoginResult;
import com.example.lg.markertree.session.Values;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn,signUpBtn;
    Context mContext;
    EditText editPw,editEmail;
    String email,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;

        init();


        //로그인 버튼 눌렀을 시에
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(TextUtils.isEmpty(editEmail.getText().toString()) || TextUtils.isEmpty(editEmail.getText().toString())){
                        Toast.makeText(mContext,"이메일/비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        email = URLEncoder.encode(editEmail.getText().toString(), "UTF-8");
                        pw = URLEncoder.encode(editPw.getText().toString(), "UTF-8");

                        MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
                        Call<LoginResult> call = myService.login(email, pw);

                        call.enqueue(new Callback<LoginResult>() {
                            @Override
                            public void onResponse(Response<LoginResult> response, Retrofit retrofit) {
                                if (response.body().success == 1) { //로그인 성공시 1 반환

                                    Values.editor.putString("email", email);
                                    Values.editor.commit(); //email 저장

                                    Intent intent = new Intent(mContext, MarkerMainActivity.class);
                                    startActivity(intent);

                                } else { //실패시 0 반환
                                    Toast.makeText(mContext, "아이디 or 비밀번호를 확인 해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        });

        //회원가입 버튼
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(mContext,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void init(){
        //sharedPreference를 위한 작업
        Values.setting = getSharedPreferences("setting",0);
        Values.editor = Values.setting.edit();

        editEmail = (EditText)findViewById(R.id.editEmail);
        editPw = (EditText)findViewById(R.id.editPw);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        signUpBtn = (Button)findViewById(R.id.signUpBtn);
    }




}
