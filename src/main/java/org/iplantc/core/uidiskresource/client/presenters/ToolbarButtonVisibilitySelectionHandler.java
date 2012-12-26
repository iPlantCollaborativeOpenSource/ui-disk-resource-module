package org.iplantc.core.uidiskresource.client.presenters;

import java.util.List;

import org.iplantc.core.uidiskresource.client.models.autobeans.DiskResource;
import org.iplantc.core.uidiskresource.client.util.DiskResourceUtil;
import org.iplantc.core.uidiskresource.client.views.widgets.DiskResourceViewToolbar;

import com.google.common.collect.Lists;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

final class ToolbarButtonVisibilitySelectionHandler<R extends DiskResource> implements
        SelectionChangedHandler<R>, SelectionHandler<R> {
    private final DiskResourceViewToolbar toolbar;

    ToolbarButtonVisibilitySelectionHandler(DiskResourceViewToolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public void onSelectionChanged(SelectionChangedEvent<R> event) {
        updateToolbar(event.getSelection());
    }

    @Override
    public void onSelection(SelectionEvent<R> event) {
        List<R>  selection = Lists.newArrayList();
        selection.add(event.getSelectedItem());
        updateToolbar(selection);
    }
    
    private void updateToolbar(List<R> selection){
        updateToolbarDownloadButtons(selection);
        updateToolbarUploadButtons(selection);

        if (selection.isEmpty()) {
            // Set all disabled
            toolbar.setNewFolderButtonEnabled(false);
            toolbar.setRefreshButtonEnabled(false);
            toolbar.setRenameButtonEnabled(false);
            toolbar.setDeleteButtonEnabled(false);
            toolbar.setShareButtonEnabled(false);
            toolbar.setMetadataButtonEnabled(false);
            toolbar.setDataQuotaButtonEnabled(false);
        } else if (selection.size() == 1) {
            toolbar.setRefreshButtonEnabled(true);
            // Check Ownership
            if (selection.get(0).getPermissions().isOwner()) {
                toolbar.setNewFolderButtonEnabled(true);
                toolbar.setRenameButtonEnabled(true);
                toolbar.setDeleteButtonEnabled(true);
                toolbar.setShareButtonEnabled(true);
                toolbar.setMetadataButtonEnabled(true);
                toolbar.setDataQuotaButtonEnabled(false);
            } else {
                toolbar.setNewFolderButtonEnabled(false);
                toolbar.setRenameButtonEnabled(false);
                toolbar.setDeleteButtonEnabled(false);
                toolbar.setShareButtonEnabled(false);
                toolbar.setMetadataButtonEnabled(false);
                toolbar.setDataQuotaButtonEnabled(false);
            }

        } else {
            toolbar.setNewFolderButtonEnabled(false);
            toolbar.setRefreshButtonEnabled(true);
            toolbar.setRenameButtonEnabled(false);
            toolbar.setDeleteButtonEnabled(true);
            toolbar.setShareButtonEnabled(true);
            toolbar.setMetadataButtonEnabled(false);
            toolbar.setDataQuotaButtonEnabled(false);
        }
    }

    private void updateToolbarDownloadButtons(List<R> selection) {
        if(selection.isEmpty()){
            toolbar.setDownloadsEnabled(false);
            toolbar.setSimpleDowloadButtonEnabled(false);
            toolbar.setBulkDownloadButtonEnabled(false);
        } else if(DiskResourceUtil.containsFolder(selection)){
            toolbar.setDownloadsEnabled(true);
            toolbar.setSimpleDowloadButtonEnabled(false);
            toolbar.setBulkDownloadButtonEnabled(true);
        }else{
            toolbar.setDownloadsEnabled(true);
            toolbar.setSimpleDowloadButtonEnabled(true);
            toolbar.setBulkDownloadButtonEnabled(true);
        }
    }

    private void updateToolbarUploadButtons(List<R> selection) {
        if((selection.size() == 1) && DiskResourceUtil.canUploadTo(selection.get(0))){
            toolbar.setUploadsEnabled(true);
            toolbar.setBulkUploadEnabled(true);
            toolbar.setSimpleUploadEnabled(true);
            toolbar.setImportButtonEnabled(true);
        } else {
            toolbar.setUploadsEnabled(false);
            toolbar.setBulkUploadEnabled(false);
            toolbar.setSimpleUploadEnabled(false);
            toolbar.setImportButtonEnabled(false);
        }        
    }
}