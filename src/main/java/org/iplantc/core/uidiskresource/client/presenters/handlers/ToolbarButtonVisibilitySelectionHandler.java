package org.iplantc.core.uidiskresource.client.presenters.handlers;

import java.util.List;

import org.iplantc.core.uidiskresource.client.models.DiskResource;
import org.iplantc.core.uidiskresource.client.views.widgets.DiskResourceViewToolbar;

import com.google.common.collect.Lists;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

/**
 * A SelectionChangedHandler and SelectionHandler for DiskResources the Data Window.
 * 
 * @author jstroot
 * 
 */
public abstract class ToolbarButtonVisibilitySelectionHandler<R extends DiskResource> implements
        SelectionChangedHandler<R>, SelectionHandler<R> {
    protected final DiskResourceViewToolbar toolbar;

    public ToolbarButtonVisibilitySelectionHandler(final DiskResourceViewToolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public void onSelectionChanged(SelectionChangedEvent<R> event) {
        updateToolbar(event.getSelection());
    }

    @Override
    public void onSelection(SelectionEvent<R> event) {
        List<R> selection = Lists.newArrayList();
        selection.add(event.getSelectedItem());
        updateToolbar(selection);
    }

    /**
     * Update the appropriate toolbar buttons based on the given selection.
     * 
     * @param selection
     */
    protected abstract void updateToolbar(List<R> selection);
}