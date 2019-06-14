package com.example.administrator.huarongdao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button Qz[] = new Button[10];
    int BG[][] = new int[5][4];
    TextView txt1;
    float SW;
    float x1, x2, y1, y2;
    int Step,Mode;

    {
        Step = 0;
        Mode = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Mode = intent.getIntExtra("Mode",0);

        Qz[0] = (Button) findViewById(R.id.Qz1);  //卒
        Qz[1] = (Button) findViewById(R.id.Qz2);  //卒
        Qz[2] = (Button) findViewById(R.id.Qz3);  //卒
        Qz[3] = (Button) findViewById(R.id.Qz4);  //卒
        Qz[4] = (Button) findViewById(R.id.Qz5);  //张飞
        Qz[5] = (Button) findViewById(R.id.Qz6);  //赵云
        Qz[6] = (Button) findViewById(R.id.Qz7);  //马超
        Qz[7] = (Button) findViewById(R.id.Qz8);  //黄忠
        Qz[8] = (Button) findViewById(R.id.Qz9);  //关羽
        Qz[9] = (Button) findViewById(R.id.Qz10); //曹操

        txt1 = (TextView) findViewById(R.id.Text1);

        for (int i = 0; i < 10; i++)
            Qz[i].setOnTouchListener(new mTouch());

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 4; j++)
                BG[i][j] = 1;


        boolean post = txt1.post(new Runnable() {
            @Override
            public void run() {
                txt1.setText(" ");
                SW = txt1.getWidth();
                init();
            }
        });

    }

    private void showNormalDialog(){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("成功");
        normalDialog.setMessage("恭喜您过关啦！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
        // 显示
        normalDialog.show();
    }

    private void resetDialog(){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("重置");
        normalDialog.setMessage("是否重置？");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        init();
                        txt1.setText("");
                        Step = 0;

                    }
                });
        normalDialog.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();
    }

    private void backDialog(){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("退出");
        normalDialog.setMessage("是否退出本关？");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
        normalDialog.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();
    }

    public void reset(View view) {
        resetDialog();
    }

    public void back(View view){
        backDialog();
    }

    public class mTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            String string_step = getResources().getString(R.string.step);
            String string_win = getResources().getString(R.string.win);
            int type;
            int r, c;
            if (v.getWidth() == v.getHeight()) {
                if (v.getHeight() > 300)
                    type = 4;//曹操
                else
                    type = 1;//小卒

            } else {
                if (v.getHeight() > v.getWidth())
                    type = 2;//2*1 如张飞、马超
                else
                    type = 3;//1*2 关羽
            }

            r = (int) (v.getY() / 270f);
            c = (int) (v.getX() / 270f);
            //继承了Activity的onTouchEvent方法，直接监听点击事件
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //当手指按下的时候
                x1 = event.getX();
                y1 = event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //当手指离开的时候
                x2 = event.getX();
                y2 = event.getY();
                if (y1 - y2 > 30) //"向上滑:"
                {
                    switch (type) {
                        case 1:
                            if (r > 0 && BG[r - 1][c] == 0) {
                                SetPois(v, r - 1, c);
                                BG[r - 1][c] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 2:
                            if (r > 0 && BG[r - 1][c] == 0) {
                                SetPois(v, r - 1, c);
                                BG[r - 1][c] = 1;
                                BG[r + 1][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 3:
                            if (r > 0 && BG[r - 1][c] == 0 && BG[r - 1][c + 1] == 0) {
                                SetPois(v, r - 1, c);
                                BG[r - 1][c] = BG[r - 1][c + 1] = 1;
                                BG[r][c] = BG[r][c + 1] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 4:
                            if (r > 0 && BG[r - 1][c] == 0 && BG[r - 1][c + 1] == 0) {
                                SetPois(v, r - 1, c);
                                BG[r - 1][c] = BG[r - 1][c + 1] = 1;
                                BG[r + 1][c] = BG[r + 1][c + 1] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;

                    }

                } else if (y2 - y1 > 30) //向下滑
                {
                    switch (type) {
                        case 1:
                            if (r < 4 && BG[r + 1][c] == 0) {
                                SetPois(v, r + 1, c);
                                BG[r + 1][c] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 2:
                            if (r < 3 && BG[r + 2][c] == 0) {
                                SetPois(v, r + 1, c);
                                BG[r + 2][c] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }

                            break;
                        case 3:
                            if (r < 4 && BG[r + 1][c] == 0 && BG[r + 1][c + 1] == 0) {
                                SetPois(v, r + 1, c);
                                BG[r + 1][c] = BG[r + 1][c + 1] = 1;
                                BG[r][c] = BG[r][c + 1] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 4:
                            if (r < 3 && BG[r + 2][c] == 0 && BG[r + 2][c + 1] == 0) {
                                SetPois(v, r + 1, c);
                                BG[r + 2][c] = BG[r + 2][c + 1] = 1;
                                BG[r][c] = BG[r][c + 1] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                                if (r + 1 == 3 && c == 1) {
                                    txt1.setText(string_win + Step);
                                    showNormalDialog();
                                }
                            }
                            break;
                    }
                } else if (x1 - x2 > 30) //向左滑
                {
                    switch (type) {
                        case 1:
                            if (c > 0 && BG[r][c - 1] == 0) {
                                SetPois(v, r, c - 1);
                                BG[r][c - 1] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 2:
                            if (c > 0 && BG[r][c - 1] == 0 && BG[r + 1][c - 1] == 0) {
                                SetPois(v, r, c - 1);
                                BG[r][c - 1] = 1;
                                BG[r + 1][c - 1] = 1;
                                BG[r][c] = 0;
                                BG[r + 1][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 3:
                            if (c > 0 & BG[r][c - 1] == 0) {
                                SetPois(v, r, c - 1);
                                BG[r][c - 1] = 1;
                                BG[r][c + 1] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 4:
                            if (c > 0 && BG[r][c - 1] == 0 && BG[r + 1][c - 1] == 0) {
                                SetPois(v, r, c - 1);
                                BG[r][c - 1] = BG[r + 1][c - 1] = 1;
                                BG[r][c + 1] = BG[r + 1][c + 1] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                                if (r == 3 && c - 1 == 1) {
                                    txt1.setText(string_win + Step);
                                    showNormalDialog();
                                }
                            }
                            break;
                    }
                } else if (x2 - x1 > 30) //向右滑
                {
                    switch (type) {
                        case 1:
                            if (c < 3 && BG[r][c + 1] == 0) {
                                SetPois(v, r, c + 1);
                                BG[r][c + 1] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 2:
                            if (c < 3 & BG[r][c + 1] == 0 && BG[r + 1][c + 1] == 0) {
                                SetPois(v, r, c + 1);
                                BG[r][c + 1] = 1;
                                BG[r + 1][c + 1] = 1;
                                BG[r][c] = 0;
                                BG[r + 1][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 3:
                            if (c < 2 && BG[r][c + 2] == 0) {
                                SetPois(v, r, c + 1);
                                BG[r][c + 2] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                            }
                            break;
                        case 4:
                            if (c < 2 && BG[r][c + 2] == 0 && BG[r + 1][c + 2] == 0) {
                                SetPois(v, r, c + 1);
                                BG[r][c + 2] = BG[r + 1][c + 2] = 1;
                                BG[r][c] = BG[r + 1][c] = 0;
                                Step++;
                                txt1.setText(string_step + Step);
                                if (r == 3 && c + 1 == 1) {
                                    txt1.setText(string_win + Step);
                                    showNormalDialog();
                                }
                            }
                            break;
                    }
                }
            }
            return true;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    void SetSize(Button v, int w, int h, String txt) {
        v.setWidth(240);
        v.setHeight(h * dip2px(getApplicationContext(), SW / 4));
        v.setText(txt);
    }

    void SetPois(View v, int r, int c) {
        v.setX(c * SW / 4f);
        v.setY(r * SW / 4f);
    }

    void init() {
        if(Mode == 0){
            //横刀立马
            BG[4][1] = 0;
            BG[4][2] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 4, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 3, 1);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 3, 2);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 4, 3);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 0, 0);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 0, 3);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 2, 0);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 2, 3);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 2, 1);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 0, 1);

        } else if(Mode == 1){
            //层拦叠障
            BG[4][1] = 0;
            BG[4][2] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 0, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 0, 1);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 0, 2);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 0, 3);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 1, 0);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 1, 3);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 3, 0);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 3, 3);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 1, 1);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 2, 1);
        }
        else if(Mode == 2){
            //一路进军
            BG[4][0] = 0;
            BG[4][3] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 0, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 1, 0);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 0, 3);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 1, 3);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 3, 1);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 3, 2);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 2, 0);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 2, 3);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 2, 1);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 0, 1);
        }else if(Mode == 3){
            //一步之遥
            BG[4][0] = 0;
            BG[4][1] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 2, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 2, 1);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 3, 0);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 3, 1);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 0, 0);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 0, 1);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 3, 2);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 3, 3);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 2, 2);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 0, 2);
        }else if(Mode == 4){
            //千里独行
            BG[4][1] = 0;
            BG[4][2] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 2, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 3, 1);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 3, 2);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 2, 3);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 0, 0);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 0, 3);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 3, 0);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 3, 3);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 2, 1);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 0, 1);
        }else if(Mode == 5){
            //天降奇兵
            BG[0][0] = 0;
            BG[0][3] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 3, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 3, 3);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 4, 2);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 4, 3);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 1, 0);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 1, 3);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 2, 1);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 2, 2);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 4, 0);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 0, 1);
        }else if(Mode == 6){
            //危急存亡
            BG[4][1] = 0;
            BG[4][2] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 2, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 2, 1);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 2, 2);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 2, 3);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 0, 0);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 0, 3);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 3, 0);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 3, 3);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 3, 1);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 0, 1);
        }else if(Mode == 7){
            //山水几程
            BG[3][2] = 0;
            BG[4][2] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 4, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 0, 3);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 1, 3);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 4, 3);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 0, 0);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 3, 1);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 2, 0);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 2, 3);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 2, 1);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 0, 1);
        }else if(Mode == 8){
            //伏击朔月
            BG[4][0] = 0;
            BG[4][3] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 0, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 0, 3);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 1, 0);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 1, 3);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 2, 0);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 2, 1);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 2, 2);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 2, 3);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 4, 1);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 0, 1);
        }else if(Mode == 9){
            //万户千门
            BG[4][0] = 0;
            BG[4][3] = 0;
            SetSize(Qz[0], 1, 1, "");
            SetPois(Qz[0], 0, 0);
            SetSize(Qz[1], 1, 1, "");
            SetPois(Qz[1], 0, 3);
            SetSize(Qz[2], 1, 1, "");
            SetPois(Qz[2], 3, 0);
            SetSize(Qz[3], 1, 1, "");
            SetPois(Qz[3], 3, 3);

            SetSize(Qz[4], 1, 2, "");
            SetPois(Qz[4], 1, 0);
            SetSize(Qz[5], 1, 2, "");
            SetPois(Qz[5], 1, 3);
            SetSize(Qz[6], 1, 2, "");
            SetPois(Qz[6], 2, 1);
            SetSize(Qz[7], 1, 2, "");
            SetPois(Qz[7], 2, 2);

            SetSize(Qz[8], 2, 1, "");
            SetPois(Qz[8], 4, 1);
            SetSize(Qz[9], 2, 2, "");
            SetPois(Qz[9], 0, 1);
        }
    }
}
