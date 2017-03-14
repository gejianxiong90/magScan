package com.example.frank.magscan;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity{

 //   private Intent serviceIntent;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //    serviceIntent = new Intent(MainActivity.this, MainServer.class);
    //    startService(serviceIntent);
        button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent("com.urovo.posclient821.action.SALE");
                float aMount = (float) 0.1;
                intent.putExtra("AMOUNT", aMount);
                //	intent.putExtra("splitIdentity", "A00001");// 分账标识
                //	intent.putExtra("employeeNum", "0012");// 员工
                intent.putExtra("waybillNum", "DC03457622");// 单号
                //	intent.putExtra("mobile", "18682181854");// 手机号码
                startActivityForResult(intent, 1);
            }
        });

    }

    public void gotoTongLian(String waybillNum,String price){
        Intent intent = new Intent("com.urovo.posclient821.action.SALE");
        intent.putExtra("AMOUNT",price);
        //	intent.putExtra("splitIdentity", "A00001");// 分账标识
        //	intent.putExtra("employeeNum", "0012");// 员工
        intent.putExtra("waybillNum", waybillNum);// 单号
        //	intent.putExtra("mobile", "18682181854");// 手机号码
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(MainActivity.this,"返回code("+resultCode+")", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //stopService(serviceIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
