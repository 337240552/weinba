package com.weinba.media;

import com.weinba.Connector;
import com.weinba.ListActivityBase;
import com.weinba.R;
import com.weinba.Connector.Callback;
import com.weinba.R.id;
import com.weinba.R.layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Map;

public class MediaFilesActivity extends ListActivityBase
{
  private static final String TAG = "MediaFilesActivity";
  protected MediaFilesAdapter filesAdapter;
  protected Object[] m_aFiles;
  protected Connector m_oConnector;
  protected String m_sAlbumId;
  protected String m_sAlbumName;
  protected String m_sMediaId;
  protected String m_sMethodXMLRPC;
  protected String m_sUsername;

  protected MediaFilesAdapter getAdapterInstance(Context paramContext, Object[] paramArrayOfObject, String paramString)
  {
    return null;
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    Log.d("MediaFilesActivity", "onActivityResult | requestCode:" + paramInt1 + " | resultCode:" + paramInt2 + " | i:" + paramIntent);
    if (this.m_sMediaId != null)
      finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.video_file_listview);
    Intent localIntent = getIntent();
    this.m_sUsername = localIntent.getStringExtra("username");
    this.m_sAlbumId = localIntent.getStringExtra("album_id");
    this.m_sAlbumName = localIntent.getStringExtra("album_name");
    this.m_sMediaId = localIntent.getStringExtra("media_id");
    if (this.m_sMediaId != null)
      ((TextView)findViewById(R.id.empty)).setVisibility(View.GONE);
    this.m_oConnector = Connector.restoreConnector();
    reloadRemoteData();
  }

  protected void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    Object localObject = this.filesAdapter.getItem(paramInt);
    if (localObject != null)
    {
      localObject = (String)((Map)localObject).get("file");
      Log.e(TAG, "video file path:" + localObject);
      if ((((String)localObject).startsWith("http://")) || (((String)localObject).startsWith("https://")))
      {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setDataAndType(Uri.parse((String)localObject), "video/*");
        startActivityForResult(localIntent, 1);
      }
      else
      {
        startActivityForResult(new Intent("android.intent.action.VIEW", Uri.parse("http://www.youtube.com/watch?v=" + (String)localObject)), 2);
      }
    }
  }

  public void onRemoveFile(String paramString)
  {
  }

  public void onViewFile(String paramString)
  {
  }

  protected void reloadRemoteData()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this.m_oConnector.getUsername();
    arrayOfObject[1] = this.m_oConnector.getPassword();
    arrayOfObject[2] = this.m_sUsername;
    arrayOfObject[3] = this.m_sAlbumId;
    this.m_oConnector.execAsyncMethod(this.m_sMethodXMLRPC, arrayOfObject, new Connector.Callback()
    {
      public void callFinished(Object paramObject)
      {
        Log.d("MediaFilesActivity", MediaFilesActivity.this.m_sMethodXMLRPC + " result: " + paramObject.toString());
       
        MediaFilesActivity.this.m_aFiles = ((Object[])paramObject);
        Log.d("MediaFilesActivity", MediaFilesActivity.this.m_sMethodXMLRPC + " num: " + MediaFilesActivity.this.m_aFiles.length);
        
        MediaFilesActivity.this.filesAdapter = MediaFilesActivity.this.getAdapterInstance(MediaFilesActivity.this.m_actThis, MediaFilesActivity.this.m_aFiles, MediaFilesActivity.this.m_sUsername);
        if (MediaFilesActivity.this.m_sMediaId != null)
        {
          if (MediaFilesActivity.this.m_aFiles.length != 0)
          {
            int i = MediaFilesActivity.this.filesAdapter.getPositionByFileId(MediaFilesActivity.this.m_sMediaId);
            if (i < 0)
              i = 0;
            MediaFilesActivity.this.onListItemClick(null, null, i, 0L);
          }
          else
          {
            MediaFilesActivity.this.finish();
          }
        }
        else
          MediaFilesActivity.this.setListAdapter(MediaFilesActivity.this.filesAdapter);
      }
    }
    , this);
  }
}
