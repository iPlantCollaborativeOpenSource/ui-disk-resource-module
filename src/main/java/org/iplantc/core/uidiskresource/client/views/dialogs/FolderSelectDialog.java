package org.iplantc.core.uidiskresource.client.views.dialogs;

import org.iplantc.core.uicommons.client.views.gxt3.dialogs.IPlantDialog;
import org.iplantc.core.uidiskresource.client.I18N;
import org.iplantc.core.uidiskresource.client.gin.DiskResourceInjector;
import org.iplantc.core.uidiskresource.client.models.autobeans.Folder;
import org.iplantc.core.uidiskresource.client.views.DiskResourceView;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * An <code>IPlantDialog</code> which wraps the standard <code>DiskResourceView</code> for folder
 * selection.
 * 
 * Users of this class are responsible adding hide handlers to get the selected folder.
 * 
 * @author jstroot
 * 
 */
public class FolderSelectDialog extends IPlantDialog {

    private final DiskResourceView.Presenter presenter;
    private final TextField selectedFileField = new TextField();
    private String selectedFolderId;

    public FolderSelectDialog() {
        setResizable(true);
        setSize("640", "480");
        setHeadingText(I18N.DISPLAY.selectAFolder());

        presenter = DiskResourceInjector.INSTANCE.getDiskResourceViewPresenter();

        final FieldLabel fl = new FieldLabel(selectedFileField, I18N.DISPLAY.selectedFolder());

        presenter.getView().setSouthWidget(fl);
        presenter.addFolderSelectionHandler(new FileSelectionChangedHandler(selectedFileField));

        // Tell the presenter to add the view with the north, east, and center widgets hidden.
        presenter.go(this, false, true, true, true);

    }

    private final class FileSelectionChangedHandler implements SelectionHandler<Folder> {
        private final TextField selectedFileField;

        private FileSelectionChangedHandler(final TextField selectedFileField) {
            this.selectedFileField = selectedFileField;
        }

        @Override
        public void onSelection(SelectionEvent<Folder> event) {
            if (event.getSelectedItem() == null) {
                return;
            }
            Folder diskResource = event.getSelectedItem();
            selectedFileField.setValue(diskResource.getName());
            setSelectedFolderId(diskResource.getId());
        }
    }

    public String getSelectedFolderId() {
        return selectedFolderId;
    }

    private void setSelectedFolderId(String id) {
        this.selectedFolderId = id;
    }

}
