package com.weinba;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public abstract class ActivityBase extends Activity
{
  protected ActivityBase m_actThis;
  protected ImageButton m_btnAction;
  protected ImageButton m_btnHome;
  protected ImageButton m_btnReload;
  protected Boolean m_isReloadEnabled;
  protected Boolean m_isToolbarEnabled;
  protected LinearLayout m_layoutToolbar;
  protected RelativeLayout m_layoutToolbarContainer;
  protected Bundle m_savedInstanceState;
  protected View m_viewMain;

  protected void customAction()
  {
  }

  protected void onCreate(Bundle paramBundle)
  {
    onCreate(paramBundle, true, true);
  }

  public void onCreate(Bundle paramBundle, boolean paramBoolean)
  {
    onCreate(paramBundle, paramBoolean, true);
  }

  public void onCreate(Bundle paramBundle, boolean paramBoolean1, boolean paramBoolean2)
  {
    onCreate(paramBundle, paramBoolean1, paramBoolean2, true);
  }

  public void onCreate(Bundle paramBundle, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    super.onCreate(paramBundle);
    this.m_actThis = this;
    this.m_isToolbarEnabled = Boolean.valueOf(paramBoolean1);
    this.m_isReloadEnabled = Boolean.valueOf(paramBoolean2);
    this.m_savedInstanceState = paramBundle;
    if ((paramBoolean3) && (Settings.getConnector() == null))
        Settings.setConnector(Connector.restoreConnector());
  }

  protected void reloadRemoteData()
  {
  }

  public void setContentView(int paramInt)
  {
    this.m_viewMain = getLayoutInflater().inflate(paramInt, null);
    super.setContentView(this.m_viewMain);
  }

  protected void setTitleCaption(int paramInt)
  {
    setTitle(getString(paramInt));
  }

  public void setTitleCaption(String paramString)
  {
    setTitle(paramString);
  }
}
