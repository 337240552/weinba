
package com.weinba;

import com.weinba.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.security.MessageDigest;
import java.util.Map;
import org.xmlrpc.android.XMLRPCClient;

public class Connector
        implements Serializable {
    private static final String TAG = "Connector";
    private static final long serialVersionUID = 1L;
    protected boolean m_bAlbumsReloadRequired;
    protected boolean m_bImagesReloadRequired;
    protected Context m_context;
    protected transient ProgressDialog m_dialogProgress;
    protected int m_iFriendRequests;
    protected int m_iProtocolVer;
    protected int m_iUnreadLetters;
    protected transient XMLRPCClient m_oClient;
    protected String m_sPwd;
    protected String m_sPwdClear;
    protected String m_sUrl;
    protected String m_sUsername;

    public Connector(String url, String username, String pwd) {
        this.m_sUrl = url;
        this.m_sUsername = username;
        this.m_sPwd = pwd;
        this.m_iProtocolVer = 2;
        this.m_oClient = new XMLRPCClient(URI.create(this.m_sUrl));
    }

   /* public static Connector restoreConnector(Context paramContext) {
        return new Connector(Settings.URL, Settings.USERNAME, Settings.PASSWORD);
    }*/

    public static Connector restoreConnector() {
        return new Connector(Settings.URL, Settings.USERNAME, Settings.PASSWORD);
    }
    // ERROR //
    public static void saveConnector(Context paramContext, Connector paramConnector) {

    }

    private void writeObject(ObjectOutputStream paramObjectOutputStream)
            throws IOException {
        paramObjectOutputStream.defaultWriteObject();
    }

    public void execAsyncMethod(String paramString, Object[] params,
            Callback paramCallback, Context paramContext) {
        this.m_context = paramContext;
        String str = this.m_context.getResources().getString(R.string.loading);
        if (paramContext == null)
            this.m_dialogProgress = null;
        else
            this.m_dialogProgress = ProgressDialog.show(paramContext, "", str, true, false);
        new XMLRPCMethod(paramString, paramCallback).call(params);
    }

    public boolean getAlbumsReloadRequired() {
        return this.m_bAlbumsReloadRequired;
    }

    public int getFriendRequestsNum() {
        return this.m_iFriendRequests;
    }

    public boolean getImagesReloadRequired() {
        return this.m_bImagesReloadRequired;
    }

    public String getPassword() {
        return this.m_sPwd;
    }

    public String getPasswordClear() {
        return this.m_sPwdClear;
    }

    public int getProtocolVer() {
        return this.m_iProtocolVer;
    }

    public String getSiteUrl() {
        return this.m_sUrl;
    }

    public int getUnreadLettersNum() {
        return this.m_iUnreadLetters;
    }

    public String getUsername() {
        return this.m_sUsername;
    }

    public String md5(String str) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte bytes[] = md5.digest();
            for (int i = 0; i < bytes.length; i++) {
                String s = Integer.toHexString(bytes[i] & 0xff);
                if (s.length() == 1) {
                    buf.append("0");
                }
                buf.append(s);
            }

        } catch (Exception ex) {
        }
        return buf.toString();
    }

    public boolean setAlbumsReloadRequired(boolean paramBoolean) {
        this.m_bAlbumsReloadRequired = paramBoolean;
        return paramBoolean;
    }

    public int setFriendRequestsNum(int paramInt) {
        this.m_iFriendRequests = paramInt;
        return paramInt;
    }

    public boolean setImagesReloadRequired(boolean paramBoolean) {
        this.m_bImagesReloadRequired = paramBoolean;
        return paramBoolean;
    }

    public String setPassword(String paramString) {
        this.m_sPwd = paramString;
        return paramString;
    }

    public String setPasswordClear(String paramString) {
        this.m_sPwdClear = paramString;
        return paramString;
    }

    public int setProtocolVer(int paramInt) {
        this.m_iProtocolVer = paramInt;
        return paramInt;
    }

    public int setUnreadLettersNum(int paramInt) {
        this.m_iUnreadLetters = paramInt;
        return paramInt;
    }

    public static class Callback {
        public boolean callFailed(Exception e) {
            Log.e(TAG, e.getMessage());
            return true;
        }

        public void callFinished(Object paramObject) {
        }
    }

    class XMLRPCMethod extends Thread {
        private Callback callBack;
        private Handler handler;
        private String method;
        private Object[] params;

        public XMLRPCMethod(String method, Callback callBack) {
            this.method = method;
            this.callBack = callBack;
            this.handler = new Handler();
        }

        public void call() {
            call(null);
        }

        public void call(Object[] params) {
            this.params = params;
            start();
        }

        @Override
        public void run() {
                try {
                    final long l1 = System.currentTimeMillis();
                    final Object result = Connector.this.m_oClient.call(this.method, this.params);
                    final long l2 = System.currentTimeMillis();
                    this.handler.post(new Runnable(){
                        public void run(){
                            Log.i("OO Connector", "XML-RPC call took " + (l2 - l1)
                                    + "ms");
                            if (Connector.this.m_dialogProgress != null){
                                Connector.this.m_dialogProgress.dismiss();
                            }                                
                            
                            if ((!(result instanceof Map))
                                || (((Map) result).get("error") == null)){//success?
                                    Connector.XMLRPCMethod.this.callBack.callFinished(result);
                                    return;
                            }else{
                                AlertDialog.Builder localBuilder = new AlertDialog.Builder(
                                        Connector.this.m_context);
                                localBuilder.setTitle(Connector.this.m_context.getResources().getString(
                                        R.string.load_error));
                                localBuilder.setMessage(Connector.this.m_context.getResources().getString(
                                        R.string.load_error_msg));
                                localBuilder.setNegativeButton("cancel", null);
                                localBuilder.show();
                            }
                       }
                    });
                } catch (final Exception e) {
                        this.handler.post(new Runnable(){
                            public void run(){
                            Log.w("Connector", "Error: " + e.getMessage());
                            if (Connector.this.m_dialogProgress != null)
                                Connector.this.m_dialogProgress.dismiss();
                                if (Connector.XMLRPCMethod.this.callBack.callFailed(e)){
                                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(
                                            Connector.this.m_context);
                                    localBuilder.setTitle(Connector.this.m_context.getResources()
                                            .getString(R.string.exception));
                                    localBuilder.setMessage(e.getMessage());
                                    localBuilder.setNegativeButton("cancel", null);
                                    localBuilder.show();
                                }
                            }
                        });
                    }
        }
    }
}