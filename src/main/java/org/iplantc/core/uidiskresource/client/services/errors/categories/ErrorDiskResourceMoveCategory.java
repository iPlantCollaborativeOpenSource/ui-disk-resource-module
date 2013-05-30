package org.iplantc.core.uidiskresource.client.services.errors.categories;

import org.iplantc.core.uidiskresource.client.services.errors.DiskResourceErrorCode;
import org.iplantc.core.uidiskresource.client.services.errors.ErrorDiskResourceMove;

import com.google.common.base.Joiner;
import com.google.web.bindery.autobean.shared.AutoBean;

public class ErrorDiskResourceMoveCategory extends ErrorDiskResourceCategory {

    public static String generateErrorMsg(AutoBean<ErrorDiskResourceMove> instance) {
        ErrorDiskResourceMove moveErr = instance.as();
        String paths = Joiner.on(',').join(moveErr.getPaths());
        DiskResourceErrorCode errCode = getDiskResourceErrorCode(moveErr.getErrorCode());

        return getErrorMessage(errCode, paths);
    }
}