package com.weinba.media;

import com.weinba.Connector;
import com.weinba.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import java.io.Serializable;
import java.util.List;

public class ImagesFilesActivity extends MediaFilesActivity
{
  private static final String TAG = "ImagesFilesActivity";

  public ImagesFilesActivity()
  {
    this.m_sMethodXMLRPC = "dolphin.getImagesInAlbum";
  }

  protected void customAction() {
    Intent localIntent = new Intent(this, AddImageActivity.class);
    localIntent.putExtra("album_name", this.m_sAlbumName);
    startActivityForResult(localIntent, 0);
  }

  protected MediaFilesAdapter getAdapterInstance(Context paramContext, Object[] paramArrayOfObject, String paramString)
  {
    return new ImagesFilesAdapter(paramContext, paramArrayOfObject, paramString);
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (this.m_oConnector.getImagesReloadRequired())
    {
      reloadRemoteData();
      this.m_oConnector.setImagesReloadRequired(false);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitleCaption(R.string.img_view_title);
    
  }

  @Override
    public void setContentView(int paramInt) {
      this.m_isToolbarEnabled = true;
        super.setContentView(paramInt);
    }
  protected void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    if (this.filesAdapter.getItem(paramInt) != null)
    {
      List localList = this.filesAdapter.getListStorage();
      Intent localIntent = new Intent(this, ImagesGallery.class);
      localIntent.putExtra("username", this.m_sUsername);
      localIntent.putExtra("index", paramInt);
      localIntent.putExtra("list", (Serializable)localList);
      startActivityForResult(localIntent, 0);
    }
  }

  public void onRemoveFile(String paramString)
  {
    Log.d("ImagesFilesActivity", "onRemove: " + paramString);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.m_oConnector.getUsername();
    arrayOfObject[1] = this.m_oConnector.getPassword();
    arrayOfObject[2] = paramString;
    this.m_oConnector.execAsyncMethod("dolphin.removeImage", arrayOfObject, new Connector.Callback()
    {
      public void callFinished(Object paramObject)
      {
        Log.d("ImagesFilesActivity", "dolphin.removeImage result: " + paramObject.toString());
        if (paramObject.toString().equals("ok"))
        {
          ImagesFilesActivity.this.reloadRemoteData();
          Connector.restoreConnector().setAlbumsReloadRequired(true);
        }
      }
    }
    , this);
  }

  public void onViewFile(String paramString)  {
    int i = this.filesAdapter.getPositionByFileId(paramString);
    if (i >= 0)
      onListItemClick(null, null, i, 0L);
  }
}