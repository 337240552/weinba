
package com.weinba.media;

import com.weinba.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.Map;

public class ThumbViewMediaEdit extends ThumbViewMedia {
    protected Button m_btnDelete;
    protected Button m_btnView;


    public ThumbViewMediaEdit(Context paramContext, Map<String, String> paramMap, String paramString) {
            super(paramContext, paramMap, paramString);
           
            viewListener.setFileId((String) this.m_map.get("id"));
            viewListener.setContext(getContext());
            this.m_btnView = (Button) m_viewInfoContainer.findViewById(R.id.view_img);
           
            this.m_btnView.setText(R.string.view);
            this.m_btnView.setVisibility(VISIBLE);
            this.m_btnView.setOnClickListener(viewListener);
        

            deleteListener.setFileId((String) this.m_map.get("id"));
            deleteListener.setContext(getContext());
            
            this.m_btnDelete = (Button) m_viewInfoContainer.findViewById(R.id.delete_img);
            this.m_btnDelete.setOnClickListener(deleteListener);
            this.m_btnDelete.setText(R.string.delete);
            this.m_btnDelete.setVisibility(VISIBLE);
    }

    private MyOnClickListener viewListener = new MyOnClickListener() {
        protected Context m_context;
        protected String m_sFileId;

        public void onClick(View paramView) {
            ((MediaFilesActivity) this.m_context).onViewFile(this.m_sFileId);
        }

        public void setContext(Context paramContext) {
            this.m_context = paramContext;
        }

        public void setFileId(String paramString) {
            this.m_sFileId = paramString;
        }
    };
    private MyOnClickListener deleteListener = new MyOnClickListener() {
        protected Context m_context;
        protected String m_sFileId;

        public void onClick(View paramView) {
            ((MediaFilesActivity) this.m_context).onRemoveFile(this.m_sFileId);
        }

        public void setContext(Context paramContext) {
            this.m_context = paramContext;
        }

        public void setFileId(String paramString) {
            this.m_sFileId = paramString;
        }
    };

    static abstract interface MyOnClickListener extends View.OnClickListener {
        public abstract void setContext(Context paramContext);

        public abstract void setFileId(String paramString);
    }
}
