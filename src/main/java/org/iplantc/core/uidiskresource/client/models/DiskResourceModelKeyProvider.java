package org.iplantc.core.uidiskresource.client.models;

import com.sencha.gxt.data.shared.ModelKeyProvider;

public class DiskResourceModelKeyProvider implements ModelKeyProvider<DiskResource> {
    @Override
    public String getKey(DiskResource item) {
        return item.getId();
    }
}