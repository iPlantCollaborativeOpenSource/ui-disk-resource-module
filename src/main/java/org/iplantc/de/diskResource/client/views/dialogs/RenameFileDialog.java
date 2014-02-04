package org.iplantc.de.diskResource.client.views.dialogs;

import org.iplantc.de.resources.client.messages.I18N;
import org.iplantc.de.commons.client.models.diskresources.File;
import org.iplantc.de.commons.client.validators.DiskResourceNameValidator;
import org.iplantc.de.commons.client.views.gxt3.dialogs.IPlantPromptDialog;
import org.iplantc.de.diskResource.client.views.widgets.DiskResourceViewToolbar;

import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class RenameFileDialog extends IPlantPromptDialog {

    public RenameFileDialog(final File file, final DiskResourceViewToolbar.Presenter presenter) {
        super(I18N.DISPLAY.fileName(), -1, file.getName(), new DiskResourceNameValidator());
        setHeadingText(I18N.DISPLAY.rename());

        addOkButtonSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                presenter.doRename(file, getFieldText());
            }
        });
    }
}
