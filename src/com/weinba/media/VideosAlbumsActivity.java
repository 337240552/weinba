package com.weinba.media;

import android.content.Context;
import android.os.Bundle;

public class VideosAlbumsActivity extends MediaAlbumsActivity
{
  public VideosAlbumsActivity()
  {
    this.m_sMethodXMLRPC = "dolphin.getVideoAlbums";
    this.m_classFilesActivity = VideosFilesActivity.class;
  }

  protected MediaAlbumsAdapter getAdapterInstance(Context paramContext, Object[] paramArrayOfObject)
  {
    return new VideosAlbumsAdapter(paramContext, paramArrayOfObject);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitleCaption("Video");
  }
}