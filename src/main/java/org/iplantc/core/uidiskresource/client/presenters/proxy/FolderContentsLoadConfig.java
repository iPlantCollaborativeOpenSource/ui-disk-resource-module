package org.iplantc.core.uidiskresource.client.presenters.proxy;

import org.iplantc.core.uicommons.client.models.diskresources.Folder;

import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;

public class FolderContentsLoadConfig extends FilterPagingLoadConfigBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Folder folder;
    
    public void setFolder(Folder folder) {
        this.folder = folder;
    }
    
    public Folder getFolder() {
        return folder;
    }

}