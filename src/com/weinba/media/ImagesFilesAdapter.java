package com.weinba.media;

import com.weinba.Connector;
import com.weinba.Settings;

import android.content.Context;
import android.view.View;
import java.util.List;
import java.util.Map;

public class ImagesFilesAdapter extends MediaFilesAdapter
{
  public ImagesFilesAdapter(Context paramContext, Object[] paramArrayOfObject, String paramString)
  {
    super(paramContext, paramArrayOfObject, paramString);
  }

  public View getViewReal(int paramInt)
  {
    Object localObject;
    if ((paramInt < 0) || (paramInt >= this.m_listViews.size()))
    {
      localObject = (Map)this.m_listFiles.get(paramInt);
      Connector localConnector = Settings.getConnector() == null ? 
              Connector.restoreConnector() : Settings.getConnector();
      
      if (!this.m_sUsername.equalsIgnoreCase(localConnector.getUsername()))
        localObject = new ThumbViewMedia(this.m_context, (Map)localObject, this.m_sUsername);
      else
        localObject = new ThumbViewMediaEdit(this.m_context, (Map)localObject, this.m_sUsername);
    }
    else
    {
      localObject = (View)this.m_listViews.get(paramInt);
    }
    return (View)localObject;
  }
}

/* Location:           D:\tools\new-version-dex2jar\dex2jar-0.0.9.7\classes_dex2jar.jar
 * Qualified Name:     com.boonex.oo.media.ImagesFilesAdapter
 * JD-Core Version:    0.6.0
 */