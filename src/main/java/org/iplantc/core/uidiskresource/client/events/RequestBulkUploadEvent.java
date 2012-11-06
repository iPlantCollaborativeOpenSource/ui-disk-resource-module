package org.iplantc.core.uidiskresource.client.events;

import org.iplantc.core.uidiskresource.client.events.RequestBulkUploadEvent.RequestBulkUploadEventHandler;
import org.iplantc.core.uidiskresource.client.models.autobeans.Folder;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RequestBulkUploadEvent extends GwtEvent<RequestBulkUploadEventHandler> {
    public interface RequestBulkUploadEventHandler extends EventHandler {

        void onRequestBulkUpload(RequestBulkUploadEvent event);
    }

    public static final GwtEvent.Type<RequestBulkUploadEventHandler> TYPE = new GwtEvent.Type<RequestBulkUploadEventHandler>();
    private final Folder destinationFolder;

    public RequestBulkUploadEvent(Object source, final Folder destinationFolder) {
        setSource(source);
        this.destinationFolder = destinationFolder;
    }

    @Override
    public GwtEvent.Type<RequestBulkUploadEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RequestBulkUploadEventHandler handler) {
        handler.onRequestBulkUpload(this);
    }

    public Folder getDestinationFolder() {
        return destinationFolder;
    }

}