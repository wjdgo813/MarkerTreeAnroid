package com.example.lg.markertree.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lg.markertree.R;
import com.example.lg.markertree.result.signUp.SignupResult;
import com.example.lg.markertree.network.MyService;
import com.example.lg.markertree.network.NetworkManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    EditText email,pw,name,birth;
    String realEmail,realPw,realName,realBirth,realGender;
    CheckBox checkAth,checkMusic,checkArt,checkCar,checkRead,checkGame;
    Button signUpBtn;
    Context mContext;
    RadioButton radioMan,radioGirl;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        init();
        mContext = this;
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(checkSign()) {
                        String gender = "";
                        if (radioGirl.isChecked()) {
                            gender = "G";
                        }
                        if (radioMan.isChecked()) {
                            gender = "M";
                        }

                        String favorite = getCheckBox();
                        favorite = favorite.trim();
                        realEmail = URLEncoder.encode(email.getText().toString(), "UTF-8");
                        realPw = URLEncoder.encode(pw.getText().toString(), "UTF-8");
                        realName = URLEncoder.encode(name.getText().toString(), "UTF-8");
                        realBirth = URLEncoder.encode(birth.getText().toString(), "UTF-8");
                        realGender = URLEncoder.encode(gender, "UTF-8");
                        String realFav = URLEncoder.encode(favorite, "UTF-8");


                        MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
                        Call<SignupResult> call = myService.signUp(realEmail, realPw, realName, realFav, realBirth, realGender);

                        call.enqueue(new Callback<SignupResult>() {
                            @Override
                            public void onResponse(Response<SignupResult> response, Retrofit retrofit) {
                                Toast.makeText(mContext, response.body().result.message, Toast.LENGTH_SHORT).show();
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


    }

    public void init(){
        email= (EditText)findViewById(R.id.editEmail);
        pw= (EditText)findViewById(R.id.editPasswd);
        name= (EditText)findViewById(R.id.editName);
        birth= (EditText)findViewById(R.id.editbirth);
        //gender= (EditText)findViewById(R.id.editGender);
        signUpBtn = (Button)findViewById(R.id.signUpBtn); // 회원가입 버튼

        //체크박스 초기화
        checkCar = (CheckBox)findViewById(R.id.checkCar);
        checkGame = (CheckBox)findViewById(R.id.checkGame);
        checkArt = (CheckBox)findViewById(R.id.checkArt);
        checkMusic = (CheckBox)findViewById(R.id.checkMusic);
        checkRead = (CheckBox)findViewById(R.id.checkRead);
        checkAth = (CheckBox)findViewById(R.id.checkAth);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioMan = (RadioButton)findViewById(R.id.radioMan);
        radioGirl = (RadioButton)findViewById(R.id.radioGirl);
        radioMan.setChecked(true);
    }

    public String getCheckBox(){
        String fav =""; //관심사
        if(checkCar.isChecked()){
            fav+=" "+checkCar.getText().toString();
        }
        if(checkGame.isChecked()){
            fav+=" "+checkGame.getText().toString();
        }
        if(checkArt.isChecked()){
            fav+=" "+checkArt.getText().toString();
        }
        if(checkMusic.isChecked()){
            fav+=" "+checkMusic.getText().toString();
        }
        if(checkRead.isChecked()){
            fav+=" "+checkRead.getText().toString();
        }
        if(checkAth.isChecked()){
            fav+=" "+checkAth.getText().toString();
        }
        return fav;
    }

    public Boolean checkSign(){

        if(TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(mContext,"Email을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(pw.getText().toString())){
            Toast.makeText(mContext,"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(mContext,"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(birth.getText().toString())){
            Toast.makeText(mContext,"생년월일을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
