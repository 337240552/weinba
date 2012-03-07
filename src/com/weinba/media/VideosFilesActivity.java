package com.weinba.media;

import android.content.Context;
import android.os.Bundle;

public class VideosFilesActivity extends MediaFilesActivity
{
  public VideosFilesActivity()
  {
    this.m_sMethodXMLRPC = "dolphin.getVideoInAlbum";
  }

  protected MediaFilesAdapter getAdapterInstance(Context paramContext, Object[] paramArrayOfObject, String paramString)
  {
    return new VideosFilesAdapter(paramContext, paramArrayOfObject, paramString);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }
}
