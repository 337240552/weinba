package com.weinba.media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ImagesAlbumsActivity extends MediaAlbumsActivity
{
  public ImagesAlbumsActivity()
  {
    this.m_sMethodXMLRPC = "dolphin.getImageAlbums";
    this.m_classFilesActivity = ImagesFilesActivity.class;
  }

  protected MediaAlbumsAdapter getAdapterInstance(Context paramContext, Object[] paramArrayOfObject)
  {
    return new ImagesAlbumsAdapter(paramContext, paramArrayOfObject);
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (this.m_oConnector.getAlbumsReloadRequired())
    {
      reloadRemoteData();
      this.m_oConnector.setAlbumsReloadRequired(false);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }
}
