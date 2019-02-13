package com.example.shen.secretmessages;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    // 组件变量声明
    EditText txtIn;
    EditText txtOut;
    EditText txtKey;
    SeekBar sb;
    Button btn;

    public String encode(String message, int keyVal) {
        String output = "";	// 加密结果
        char key = (char)keyVal;	// 为了对信息文本凯撒加密，将密钥转成字符型作为偏移量
        /* 反向凯撒加密 */
        for(int x=message.length()-1; x>=0; x--) {
            /*
             * 反向凯撒加密
             * 对明文反向取字符作为密文的正向输出，同时根据密钥偏移得到加密字符
             * 大段的明文，还要判断字符是大小写，还是数字
             */
            char input = message.charAt(x);
            // 大写字符
            if(input >= 'A' && input <= 'Z') {
                input += key;
                if(input > 'Z')
                    input -= 26;
                if(input < 'A')
                    input += 26;
            }
            // 小写字符
            else if(input >= 'a' && input <= 'z') {
                input += key;
                if(input > 'z')
                    input -= 26;
                if(input < 'a')
                    input += 26;
            }
            // 数字
            else if(input >= '0' && input <= '9') {
                input += keyVal;
                if(input > '9')
                    input -= 10;
                if(input < '0')
                    input += 10;
            }
            output += input;	// 构造密文
        }
        return output;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 变量和GUI布局中的组件关联
        txtIn = (EditText)findViewById(R.id.txtIn);
        txtOut = (EditText)findViewById(R.id.txtOut);
        txtKey = (EditText)findViewById(R.id.txtKey);
        sb = (SeekBar)findViewById(R.id.seekBar);
        btn = (Button)findViewById(R.id.button);

        // 下面四行是用来接收来自其他应用的信息的
        Intent receiveIntent = getIntent(); // 该变量用来接收其他应用分享的Intent消息
        String receivedText = receiveIntent.getStringExtra(Intent.EXTRA_TEXT);  // 调用方法来获取Intent消息中的文本
        if(receivedText != null)    // 文本不为空，设置应用的源文本为此文本信息就好了
            txtIn.setText(receivedText);
        // 按钮监听，编码解码
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int keyVal = Integer.parseInt(txtKey.getText().toString()); // 获取密钥
                String message = txtIn.getText().toString();    // 获取源文本
                String output = encode(message, keyVal);    // 得到目标文本
                txtOut.setText(output); // 输出
            }
        });
        // 滑条监听，调节密钥
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                /* SeekBar滑条的值都是正数，如果要获得负值密钥，需要做好偏移 */
                int keyVal = sb.getProgress() - 12;
                String message = txtIn.getText().toString();    // 获取源文本
                String output = encode(message, keyVal);    // 得到目标文本
                txtOut.setText(output); // 输出
                txtKey.setText("" + keyVal);    // 输入框随滑条变化而变化
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // 下面是定制浮动操作按钮，即fab图标
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // 监听fab点击，用所需代码替换onClick中原代码
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                先实例化一个Intent对象
                在Android中，Intent是我们要启动的活动，如E-mail、QQ或相机
                 */
                // ACTION_SEND 意味着要向Android设备中的另一个应用发送信息
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                // 设置类型为"text/plain"，即Email和社交媒体帖子的类型
                shareIntent.setType("text/plain");
                // 设置主题行，如果通过使用主题行的应用分享消息，那么主题行就是这个"Secret Message"+当前日期
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "秘密信息 "
                + DateFormat.getDateTimeInstance().format(new Date()));
                // 根据用户选择来分享消息的应用，将文本框中的密文作为分享消息正文
                shareIntent.putExtra(Intent.EXTRA_TEXT, txtOut.getText().toString());
                // 防止分享出错，用try catch处理异常
                try{
                    startActivity(Intent.createChooser(shareIntent, "分享密文..."));    // 尝试启动用户选择的活动，并传递信息
                    finish();   //  结束这个活动
                }catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(MainActivity.this, "错误：分享失败。",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
