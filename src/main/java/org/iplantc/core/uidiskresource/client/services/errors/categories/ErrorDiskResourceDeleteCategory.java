package org.iplantc.core.uidiskresource.client.services.errors.categories;

import org.iplantc.core.uicommons.client.util.DiskResourceUtil;
import org.iplantc.core.uidiskresource.client.services.errors.ErrorDiskResourceDelete;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.web.bindery.autobean.shared.AutoBean;

public class ErrorDiskResourceDeleteCategory {

    public static SafeHtml generateErrorMsg(AutoBean<ErrorDiskResourceDelete> instance) {
        ErrorDiskResourceDelete error = instance.as();

        return ErrorDiskResourceCategory.getErrorMessage(
                ErrorDiskResourceCategory.getDiskResourceErrorCode(error.getErrorCode()),
                DiskResourceUtil.asCommaSeperatedNameList(error.getPaths()));
    }
}