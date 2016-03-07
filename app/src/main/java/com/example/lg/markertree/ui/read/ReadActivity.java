package com.example.lg.markertree.ui.read;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.swipe.SwipeLayout;
import com.example.lg.markertree.R;
import com.example.lg.markertree.holder.ImageDecoration;
import com.example.lg.markertree.network.MyService;
import com.example.lg.markertree.network.NetworkManager;
import com.example.lg.markertree.network.SignUpMark;
import com.example.lg.markertree.result.create_book_mark.CreateResult;
import com.example.lg.markertree.result.read_comm.CommItem;
import com.example.lg.markertree.result.read_comm.ReadCommResult;
import com.example.lg.markertree.session.Values;
import com.example.lg.markertree.ui.LoginActivity;
import com.example.lg.markertree.ui.MarkerMainActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ReadActivity extends AppCompatActivity {
    TextView txtBookName, txtBookUrl, txtCommCount, txtFavorite;
    String bookIdx;
    Button sendBtn;
    EditText editComm;
    ImageView thumbImg;
    int idx;
    String bookName, bookUrl,favorite,imgUrl;
    RecyclerView recyclerView;
    CommItemAdapter mAdapter;
    Context mContext;
    RadioGroup radioGroup;
    RadioButton radioGood,radioBad;
    CardView markCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        init(); // 위젯 초기화
        readComm(); //댓글 읽어오기 메서드

        //공유버튼
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareMark(); //북마크 공유
            }
        });

        //북마크 카드
        markCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://"+bookUrl;
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
    }

    public void shareMark(){
        //북마크 저장
        SignUpMark signUpMark = new SignUpMark(mContext,Values.setting.getString("email", ""),getGoodBad(),bookUrl,editComm.getText().toString(),favorite);
        signUpMark.sendMark();
        finish();
    }

    //서버에서 댓글 정보 읽어옴
    // input : 북마크 인덱스
    // output : 카테고리,총 댓글 수, 유저이름,등록 날짜,댓글 내용, 좋아요
    public void readComm() {
        MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
        Call<ReadCommResult> call = myService.readComm(idx);
        call.enqueue(new Callback<ReadCommResult>() {
            @Override
            public void onResponse(Response<ReadCommResult> response, Retrofit retrofit) {
                favorite = response.body().favorite;
                txtFavorite.setText("카테고리 : " + favorite);

                txtCommCount.setText("총 댓글 수 : " + response.body().count);
                mAdapter.clear();
                for (CommItem commItem : response.body().item) {
                    mAdapter.add(commItem);
                }

                int height = mAdapter.getItemCount() * 170; //리사이클러뷰 높이 조정 (댓글
                recyclerView.getLayoutParams().height = height;
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
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

    public void init() {
        Intent intent = getIntent();
        bookIdx = intent.getExtras().getString("bookPos");
        idx = Integer.parseInt(bookIdx);
        bookName = intent.getExtras().getString("bookName");
        bookUrl = intent.getExtras().getString("bookUrl");
        imgUrl = intent.getExtras().getString("imgUrl");

        thumbImg=(ImageView)findViewById(R.id.thumbImg);
        markCard = (CardView)findViewById(R.id.markCard); //북마크가 담겨있는 CardView
        txtFavorite = (TextView) findViewById(R.id.txtFavorite); //카테고리
        txtCommCount = (TextView) findViewById(R.id.txtCommCount); //글의 댓글 수
        txtBookName = (TextView) findViewById(R.id.txtBookName); //북마크 이름
        txtBookUrl = (TextView) findViewById(R.id.txtBookUrl); // 북마크 URL
        editComm = (EditText)findViewById(R.id.editComm); //댓글 edit
        sendBtn = (Button)findViewById(R.id.sendBtn); // 공유 버튼
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGood = (RadioButton)findViewById(R.id.radioGood); // 좋아요 라디오
        radioBad = (RadioButton)findViewById(R.id.radioBad); // 싫어요 라디오
        radioGood.setChecked(true);

        //data 입력
        txtBookUrl.setText(bookUrl);
        txtBookName.setText(bookName);
        Glide.with(mContext)
                .load(imgUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(thumbImg);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new ImageDecoration(mContext));
        mAdapter = new CommItemAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
    }
}
