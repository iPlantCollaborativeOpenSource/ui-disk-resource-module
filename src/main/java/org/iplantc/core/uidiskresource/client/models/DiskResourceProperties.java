package org.iplantc.core.uidiskresource.client.models;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DiskResourceProperties extends PropertyAccess<DiskResource> {

    ModelKeyProvider<DiskResource> id();

    @Path("path")
    ValueProvider<DiskResource, String> path();

    @Path("name")
    LabelProvider<DiskResource> nameLabel();

    ValueProvider<DiskResource, String> name();

    ValueProvider<DiskResource, Date> dateCreated();

    ValueProvider<DiskResource, Date> lastModified();

}