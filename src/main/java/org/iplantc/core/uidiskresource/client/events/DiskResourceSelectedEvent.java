package org.iplantc.core.uidiskresource.client.events;

import org.iplantc.core.uidiskresource.client.events.DiskResourceSelectedEvent.DiskResourceSelectedEventHandler;
import org.iplantc.core.uidiskresource.client.models.DiskResource;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DiskResourceSelectedEvent extends GwtEvent<DiskResourceSelectedEventHandler> {

    public interface DiskResourceSelectedEventHandler extends EventHandler {

        void onSelect(DiskResourceSelectedEvent event);

    }

    public static final GwtEvent.Type<DiskResourceSelectedEventHandler> TYPE = new GwtEvent.Type<DiskResourceSelectedEventHandler>();
    private final DiskResource selectedItem;

    public DiskResourceSelectedEvent(final Object source, final DiskResource selectedItem) {
        setSource(source);
        this.selectedItem = selectedItem;
    }

    public DiskResource getSelectedItem() {
        return selectedItem;
    }

    @Override
    public GwtEvent.Type<DiskResourceSelectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DiskResourceSelectedEventHandler handler) {
        handler.onSelect(this);
    }
}
