package org.iplantc.core.uidiskresource.client.models;

import org.iplantc.core.uicommons.client.models.diskresources.DiskResourceMetadata;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DiskResourceMetadataProperties extends PropertyAccess<DiskResourceMetadata> {

    ValueProvider<DiskResourceMetadata, String> attribute();

    ValueProvider<DiskResourceMetadata, String> value();

    ValueProvider<DiskResourceMetadata, String> unit();

}
