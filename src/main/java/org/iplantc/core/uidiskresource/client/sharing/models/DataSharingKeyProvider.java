/**
 * 
 */
package org.iplantc.core.uidiskresource.client.sharing.models;

import com.sencha.gxt.data.shared.ModelKeyProvider;

/**
 * @author sriram
 *
 */
public class DataSharingKeyProvider implements ModelKeyProvider<DataSharing> {
    @Override
    public String getKey(DataSharing item) {
        return item.getKey();
    }
}