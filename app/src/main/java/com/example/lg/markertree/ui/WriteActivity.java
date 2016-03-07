package com.example.lg.markertree.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lg.markertree.R;
import com.example.lg.markertree.network.MyService;
import com.example.lg.markertree.network.NetworkManager;
import com.example.lg.markertree.network.SignUpMark;
import com.example.lg.markertree.result.create_book_mark.CreateResult;
import com.example.lg.markertree.result.login.LoginResult;
import com.example.lg.markertree.session.Values;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class WriteActivity extends AppCompatActivity {
    EditText editComment,editURL;
    RadioGroup radioGroup;
    RadioButton radioGood,radioBad;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        mContext = this;
        //툴바 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //뒤로가기 버튼
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        init();

        // 인텐트를 얻어오고, 액션과 MIME 타입을 가져온다
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        // 인텐트 정보가 있는 경우 실행
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);    // 가져온 인텐트의 텍스트 정보
                Toast.makeText(WriteActivity.this, sharedText, Toast.LENGTH_SHORT).show();
                editURL.setText(sharedText);
            }
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.write_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(TextUtils.isEmpty(editURL.getText().toString())){
                Toast.makeText(WriteActivity.this, "URL을 입력해주세요.",Toast.LENGTH_SHORT).show();
            }
            else {
                showSingle(); //다이얼로그를 실행하여 카테고리 설정
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //다이얼로그 설정 , 카테고리 설정
    String[] favoriteList = {"운동","예술","게임","독서","음악","공부","기타"};
    String tmpFavorite="";
    String favorite="";

    public void showSingle(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("카테고리");

        builder.setSingleChoiceItems(favoriteList, 1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tmpFavorite = favoriteList[which];
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(tmpFavorite)) {
                    favorite = favoriteList[1]; //아무것도 클릭 안했을시에 기본값 예술로 지정
                } else {
                    favorite = tmpFavorite; //yes를 눌러야만 값 저장하도록 유도
                }
                sendServer(); //카테고리 설정이 끝나면 서버에 전송

            }
        });

        builder.create().show();
    }

    //초기화
    public void init(){
        editComment = (EditText)findViewById(R.id.editComment);
        editURL = (EditText)findViewById(R.id.editURL);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGood = (RadioButton)findViewById(R.id.radioGood);
        radioBad = (RadioButton)findViewById(R.id.radioBad);
        radioGood.setChecked(true);
    }


    //좋아요, 나빠요 설정
    public String getGoodBad(){
        String goodBad="";
        if(radioGood.isChecked()){
            goodBad="G";
        }
        if(radioBad.isChecked()){
            goodBad="B";
        }
        return goodBad;

    }

    //서버에 전송하기 위한 메소드
    public void sendServer(){


        //북마크 저장
        SignUpMark signUpMark = new SignUpMark(mContext,Values.setting.getString("email", ""),getGoodBad(),editURL.getText().toString(),editComment.getText().toString(),favorite);
        signUpMark.sendMark();
        finish();
    }
}
