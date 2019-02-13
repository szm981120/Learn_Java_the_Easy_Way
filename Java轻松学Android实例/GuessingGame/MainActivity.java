package com.example.shen.guessinggame;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    // 必要涉及的插件变量声明
    private EditText txtGuess;  // 输入框
    private Button btnGuess;    // 猜测按钮
    private TextView lblOutput; // 输出信息
    private Button btnPlayAgain;    // 再玩一次
    private TextView lblRange;
    private TextView lblMaxTries;

    private int theNumber;  // 答案
    private int numberOfTries;  // 尝试次数
    private int range = 100;    // 猜数范围
    private int maxTries;   // 获胜的最大猜测次数

    // 猜数
    public void checkGuess(){
        numberOfTries++;    // 每执行一次猜数，猜测次数加一
        String guessText = txtGuess.getText().toString();	// 获取猜测框中的文本内容
        String message = "";	// 输出信息
        String winOrLose = "";
        try {
            int guess = Integer.parseInt(guessText);	// 利用转换获得猜测框中的int
            if(guess < theNumber)	// 猜小了
                message = guess + "太小了，再试试吧！";
            else if(guess > theNumber)	// 猜大了
                message = guess + "太大了，再试试吧！";
            else {	// 猜对了
                // 猜对了之后，利用用户首选项来记录游戏数据
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);    // 用户首选项实例化
                SharedPreferences.Editor editor = preferences.edit();   // 实例化一个编辑器用来编辑存储数据
                if(numberOfTries <= maxTries){  // 获胜
                    winOrLose = "你赢啦！";
                    int gamesWon = preferences.getInt("gamesWon", 0) + 1;   // 胜利次数加一
                    editor.putInt("gamesWon", gamesWon);    // 更新获胜数据
                    editor.apply(); // 提交更新
                }
                else{   // 失败
                    winOrLose = "可是次数太多了，你输了(；′⌒`)";
                    int gamesLose = preferences.getInt("gamesLose", 0) + 1; // 失败次数加一
                    editor.putInt("gamesLose", gamesLose);  // 更新失败数据
                    editor.apply(); // 提交更新
                }
                message = guess + "猜对了，"+ winOrLose + "\n你用了 " + numberOfTries + "次。再玩一次吧！";
                btnPlayAgain.setVisibility(View.VISIBLE);   // 再玩一次按钮可见
            }
        }catch(Exception e) {
            message = "请输入1到" + range + "的整数！"; // 输入不合法处理
        }finally {
            lblOutput.setText(message);	// 总是设置输出信息
            txtGuess.requestFocus();	// 总是在猜完之后把光标定在输入框
            txtGuess.selectAll();	// 输入框全选，改善用户体验
        }
    }

    // 新一轮游戏
    public void newGame(){
        theNumber = (int)(Math.random() * range + 1);	// 随机生成0到range的整数作为答案
        maxTries = (int)(Math.log(range) / Math.log(2) + 1);    // 根据二分法设置获胜最大猜测次数
        lblRange.setText("请输入一个1到" + range + "之间的整数");  // 根据range修改提示信息
        lblMaxTries.setText("请在" + maxTries + "内猜中答案，才可获胜");  // 根据range修改提示信息
        btnPlayAgain.setVisibility(View.INVISIBLE); // 再玩一次按钮不可见
        numberOfTries = 0;  // 初始化猜测次数
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 下面的findViewById是根据组件的Id，来将代码中的变量和GUI布局中的组件相关联
        txtGuess = (EditText)findViewById(R.id.txtGuess);
        btnGuess = (Button)findViewById(R.id.btnGuess);
        lblOutput = (TextView)findViewById(R.id.lblOutput);
        btnPlayAgain = (Button)findViewById(R.id.btnPlayAgain);
        lblRange = (TextView)findViewById(R.id.lblRange);
        lblMaxTries = (TextView)findViewById(R.id.lblMaxTries);

        /*
        下面实例化了一个用户首选项，并在调用方法newGame()千检索共享首选项，
        确保每次应用重启后使用上次结束时的范围range，如果是第一次游戏，那么默认范围是100
         */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        range = preferences.getInt("range", 100);
        newGame();

        // 监听猜数按钮
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess();
            }
        });
        // 监听输入框回车
        txtGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                checkGuess();
                return true;
            }
        });
        // 监听再玩一次按钮
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
                lblOutput.setText("重新开始啦！继续猜吧！");
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    // 该方法告诉Android如何为应用创建选项菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*
        这里告诉Android要扩展文件menu_main.xml以用作选项菜单
        选项菜单需要在app\res\menu\menu_main.xml文件中修改，这里修改了三个菜单项
        menu_main.xml不是菜单，需要用MenuInflator类将其转换为菜单
         */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    // 该方法响应用户的菜单选择
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();  // id可以标识用户选择了哪个菜单项

        //noinspection SimplifiableIfStatement
        switch (id){    // 用switch来对用户的选择做判断
            case R.id.action_settings:
                /*
                接下来，在用户选中Settings后，创建一个对话框，该对话框中有提示信息和四个选项，
                四个选项在items规定，对这四个选项监听，根据用户的选择执行修改range的重启游戏的操作
                 */
                final CharSequence[] items = {"1到10", "1到100", "1到1000", "1到10000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("选择范围：");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                range = 10;
                                storeRange(10);
                                newGame();
                                break;
                            case 1:
                                range = 100;
                                storeRange(100);
                                newGame();
                                break;
                            case 2:
                                range = 1000;
                                storeRange(1000);
                                newGame();
                                break;
                            case 3:
                                range = 10000;
                                storeRange(10000);
                                newGame();
                                break;
                        }
                        dialogInterface.dismiss();  // 用户点击一个选项后，对话框消失
                    }
                });
                AlertDialog alert = builder.create();   // 创建对话框alert
                alert.show();   // 显示对话框
                return true;
            case R.id.action_gamestats:
                /*
                用户选择了Game Stats，执行如下操作：
                出现一个对话框，显示与游戏数据相关的信息，并提供两个按钮
                一个可以用来清除数据，另一个是取消
                清除数据按钮触发后，还要再生成一个对话框，以确定是否清除数据
                 */
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);    // 实例化
                int gamesWon = preferences.getInt("gamesWon", 0);   // 获取历史获胜次数
                int gamesLose = preferences.getInt("gamesLose", 0); // 获取历史失败次数
                int gamesTries = gamesWon + gamesLose;  // 获取历史游戏次数
                double winRate = gamesTries == 0 ? 0 : (double)gamesWon / gamesTries * 100; // 计算胜率百分比
                // 胜率保留2位小数
                BigDecimal bg = new BigDecimal(winRate);
                winRate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                // 生成数据显示框并设置相关参数信息
                AlertDialog statDialog = new AlertDialog.Builder(MainActivity.this).create();
                statDialog.setTitle("猜数游戏统计");  // 标题
                statDialog.setMessage("你的获胜次数：" + gamesWon +
                        "\n你的游戏次数：" + gamesTries +
                        "\n胜率：" + winRate + "%");
                // 设置OK按钮并监听
                statDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();  // 点击OK，关闭对话框
                            }
                        });
                // 设置清除数据按钮并监听
                statDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "清除数据",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 清除数据确认对话框参数设定
                                AlertDialog clearStatDialog = new AlertDialog.Builder(MainActivity.this).create();
                                clearStatDialog.setTitle("清楚统计数据");
                                clearStatDialog.setMessage("你确定要删除所有统计数据吗？");
                                // 清除数据确认对话框中有两个按钮，一个是确定，一个是大哥，算了吧，对这两个按钮分别监听
                                clearStatDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                clearStat();    // 确认清除数据
                                            }
                                        });
                                clearStatDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "大哥，算了吧",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();  // 放弃清除数据，直接关闭当前对话框
                                            }
                                        });
                                clearStatDialog.show();
                                dialogInterface.dismiss();  // 同时关闭数据显示框
                            }
                        });
                statDialog.show();  // 显示数据框
                return true;
            case R.id.action_about:
                /*
                这里用户选择了About来查看关于信息。也是弹出一个对话框，设置相关参数信息，
                并监听一个OK按钮，来关闭对话框。
                 */
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("关于猜数游戏");
                aboutDialog.setMessage("(c)2019 沈子鸣\nFrom Bryson Payne.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void storeRange(int newRange){
        /*
        实例化用户首选项和编辑器，将数据range更新为参数newRange
         */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("range", newRange);
        editor.apply();
    }
    public void clearStat(){
        /*
        清楚用户首选项中保存的所有数据
         */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
