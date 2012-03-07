
package com.weinba;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public abstract class ListActivityBase extends ListActivity {
    private static final String TAG = "ListActivityBase";
    protected ListActivityBase m_actThis;
    protected Button m_btnAction;
    protected ImageButton m_btnHome;
    protected ImageButton m_btnReload;
    protected Boolean m_isReloadEnabled;
    protected boolean m_isToolbarEnabled;
    protected LinearLayout m_layoutToolbar;
    protected RelativeLayout m_layoutToolbarContainer;
    protected Bundle m_savedInstanceState;
    protected View m_viewMain;

    protected void customAction() {
    }

    public void onCreate(Bundle paramBundle) {
        onCreate(paramBundle, false, true);
    }

    public void onCreate(Bundle paramBundle, boolean paramBoolean) {
        onCreate(paramBundle, paramBoolean, true);
    }

    public void onCreate(Bundle paramBundle, boolean paramBoolean1, boolean paramBoolean2) {
        onCreate(paramBundle, paramBoolean1, paramBoolean2, true);
    }

    public void onCreate(Bundle paramBundle, boolean paramBoolean1, boolean paramBoolean2,
            boolean paramBoolean3) {
        super.onCreate(paramBundle);
        
        this.m_actThis = this;
        this.m_isToolbarEnabled = Boolean.valueOf(paramBoolean1);
        this.m_isReloadEnabled = Boolean.valueOf(paramBoolean2);
        this.m_savedInstanceState = paramBundle;
        
    }

    protected void reloadRemoteData() {
    }

    public void setContentView(int paramInt) {
        this.m_viewMain = getLayoutInflater().inflate(paramInt, null);
        super.setContentView(this.m_viewMain);
        
        Log.d(TAG, "m_isToolbarEnable:" + m_isToolbarEnabled);
        if(m_isToolbarEnabled){
            
            this.m_btnAction = (Button) m_viewMain.findViewById(R.id.add_img);

            this.m_btnAction.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramView) {
                    ListActivityBase.this.m_actThis.customAction();
                }
            });
        }
       
        
    }

    protected void setTitleCaption(int paramInt) {
        setTitle(getString(paramInt));
    }

    protected void setTitleCaption(String paramString) {
        setTitle(paramString);
    }
}
