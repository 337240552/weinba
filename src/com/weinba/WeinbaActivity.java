
package com.weinba;

import com.weinba.R;
import com.weinba.media.ImagesAlbumsActivity;
import com.weinba.media.VideosAlbumsActivity;

import org.apache.http.conn.HttpHostConnectException;
import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;
import org.xmlrpc.android.XMLRPCFault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

public class WeinbaActivity extends Activity {
    private XMLRPCClient client;
    private URI uri;
    private Button login, videoBtn, imgBtn;
    private TextView result, callTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /*
         * uri = URI.create("http://buguke.com/xmlrpc/");//http://10.0.2.2:8888
         * client = new XMLRPCClient(uri);
         * 
         * result = (TextView) findViewById(R.id.tv); callTime = (TextView)
         * findViewById(R.id.callTime);
         */
        videoBtn = (Button) findViewById(R.id.video_info);
        imgBtn = (Button) findViewById(R.id.img_info);

        videoBtn.setOnClickListener(new BtnClickListener());
        imgBtn.setOnClickListener(new BtnClickListener());
    }

    private class BtnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.video_info){
                Intent localIntent = new Intent(WeinbaActivity.this, VideosAlbumsActivity.class);
                localIntent.putExtra("username", Settings.NICK_NAME);
                startActivityForResult(localIntent, 6);
            }else if(v.getId() == R.id.img_info){
                Intent localIntent = new Intent(WeinbaActivity.this, ImagesAlbumsActivity.class);
                localIntent.putExtra("username", Settings.NICK_NAME);
                startActivityForResult(localIntent, 5);
            }
            
        }
    } 
}

/*
 * class XMLRPCMethod extends Thread { private String method; private Object[]
 * params; private Handler handler; private XMLRPCMethodCallback callBack;
 * 
 * public XMLRPCMethod(String method, Object[] params, XMLRPCMethodCallback
 * callBack) { this.method = method; this.callBack = callBack; handler = new
 * Handler(); }
 * 
 * public void call(Object[] params) { result.setTextColor(0xff80ff80);
 * result.setText("Calling host " + uri.getHost()); this.params = params;
 * start(); }
 * 
 * @Override public void run() { try { final long t0 =
 * System.currentTimeMillis(); final Object retVal = client.callEx(method,
 * params); final long t1 = System.currentTimeMillis(); handler.post(new
 * Runnable() { public void run() { callTime.setText("XML-RPC call took " + (t1
 * - t0) + "ms"); callBack.callFinished(retVal); } }); } catch (final
 * XMLRPCFault e) { handler.post(new Runnable() { public void run() {
 * result.setText(""); result.setEnabled(true); result.setTextColor(0xffff8080);
 * result.setText("Fault message: " + e.getFaultString() + "\nFault code: " +
 * e.getFaultCode()); Log.d("Test", "error", e); } }); } catch (final
 * XMLRPCException e) { handler.post(new Runnable() { public void run() {
 * result.setText(""); result.setEnabled(true); result.setTextColor(0xffff8080);
 * Throwable couse = e.getCause(); if (couse instanceof
 * HttpHostConnectException) { result.setText("Cannot connect to " +
 * uri.getHost() +
 * "\nMake sure server.py on your development host is running !!!"); } else {
 * result.setText("Error " + e.getMessage()); } Log.d("Test", "error", e); } });
 * } } } }
 * 
 * interface XMLRPCMethodCallback { void callFinished(Object result); }
 */

