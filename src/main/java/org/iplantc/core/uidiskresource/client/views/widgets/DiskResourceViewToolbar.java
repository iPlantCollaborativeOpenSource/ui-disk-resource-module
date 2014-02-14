package org.iplantc.core.uidiskresource.client.views.widgets;

import org.iplantc.core.uicommons.client.events.diskresources.DiskResourceRefreshEvent;
import org.iplantc.core.uicommons.client.models.diskresources.DiskResource;
import org.iplantc.core.uicommons.client.models.diskresources.Folder;
import org.iplantc.core.uidiskresource.client.search.events.SaveDiskResourceQueryEvent.SaveDiskResourceQueryEventHandler;
import org.iplantc.core.uidiskresource.client.search.events.SubmitDiskResourceQueryEvent.SubmitDiskResourceQueryEventHandler;
import org.iplantc.core.uidiskresource.client.search.views.DiskResourceSearchField;

import com.google.gwt.user.client.ui.IsWidget;

import java.util.Set;

public interface DiskResourceViewToolbar extends IsWidget {

    public interface Presenter extends org.iplantc.core.uicommons.client.presenter.Presenter, SaveDiskResourceQueryEventHandler, SubmitDiskResourceQueryEventHandler {

        void doBulkUpload();

        void doSimpleUpload();

        void doImport();

        /**
         * Fires a {@link DiskResourceRefreshEvent} in order to reload the given folder in all available
         * views and the folder cache. Or reloads the current search results if no valid folder is given.
         * 
         * @param folder The folder to reload from the service, or null to refresh the current search
         *            results.
         */
        void doRefresh(Folder folder);

        void doSimpleDownload();

        void doBulkDownload();

        void doShare();

        void requestDelete();

        void doDelete();

        void doMetadata();

        void doDataQuota();

        Set<DiskResource> getSelectedDiskResources();

        Folder getSelectedFolder();

        void doRename(DiskResource dr, String newName);

        void doCreateNewFolder(Folder parentFolder, String folderName);

        void emptyTrash();

        void restore();

        void doDataLinks();

        void onMove();
        
        void onNewFile();

        /**
         * Reloads the given folder in the view's navigation tree, and if it's the currently selected
         * folder then the data grid is refreshed as well.
         * 
         * @param folder
         */
        void onFolderRefresh(Folder folder);
    }


    void setPresenter(Presenter presenter);
    
    void setUploadsEnabled(boolean enabled);

    void setBulkUploadEnabled(boolean enabled);

    void setSimpleUploadEnabled(boolean enabled);

    void setImportButtonEnabled(boolean enabled);

    void setNewFolderButtonEnabled(boolean enabled);

    void setRefreshButtonEnabled(boolean enabled);

    void setDownloadsEnabled(boolean enabled);
    
    void setSimpleDowloadButtonEnabled(boolean enabled);

    void setBulkDownloadButtonEnabled(boolean enabled);

    void setRenameButtonEnabled(boolean enabled);

    void setDeleteButtonEnabled(boolean enabled);

    void setShareButtonEnabled(boolean enabled);

    void setShareMenuItemEnabled(boolean enabled);

    void setDataLinkMenuItemEnabled(boolean enabled);

    void setRestoreMenuItemEnabled(boolean b);

    void setMetaDatMenuItemEnabled(boolean canEditMetadata);

    void setEditEnabled(boolean canEdit);

    void setMoveButtonEnabled(boolean enabled);

    void setNewFileButtonEnabled(boolean enabled);

    void setNewButtonEnabled(boolean enabled);

    DiskResourceSearchField getSearchField();
}
