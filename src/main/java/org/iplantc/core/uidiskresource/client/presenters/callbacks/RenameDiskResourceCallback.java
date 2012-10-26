package org.iplantc.core.uidiskresource.client.presenters.callbacks;

import org.iplantc.core.uicommons.client.ErrorHandler;
import org.iplantc.core.uicommons.client.events.EventBus;
import org.iplantc.core.uicommons.client.views.IsMaskable;
import org.iplantc.core.uidiskresource.client.I18N;
import org.iplantc.core.uidiskresource.client.events.DiskResourceRenamedEvent;
import org.iplantc.core.uidiskresource.client.models.autobeans.DiskResource;
import org.iplantc.core.uidiskresource.client.models.autobeans.DiskResourceAutoBeanFactory;
import org.iplantc.core.uidiskresource.client.models.autobeans.errors.DiskResourceErrorAutoBeanFactory;
import org.iplantc.core.uidiskresource.client.models.autobeans.errors.DiskResourceRenameError;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.shared.Splittable;
import com.google.web.bindery.autobean.shared.impl.StringQuoter;

public class RenameDiskResourceCallback extends DiskResourceServiceCallback {

    private final DiskResource dr;
    private final DiskResourceAutoBeanFactory factory = GWT.create(DiskResourceAutoBeanFactory.class);

    public RenameDiskResourceCallback(DiskResource dr, IsMaskable maskable) {
        super(maskable);
        this.dr = dr;
    }

    @Override
    public void onSuccess(String result) {
        unmaskCaller();
        Splittable split = StringQuoter.split(result);

        Splittable encode = AutoBeanCodex.encode(AutoBeanUtils.getAutoBean(dr));
        AutoBean<DiskResource> newDr = AutoBeanCodex.decode(factory, DiskResource.class, encode);   // AutoBeanUtils.getAutoBean(dr);
        String newId = split.get("dest").asString();
        // dr.setId(newId);
        // dr.setName(newId.substring(newId.lastIndexOf("/") + 1));
        if (newDr.isWrapper()) {
            GWT.log("Is Wrapper");
        }
        newDr.as().setId(newId);
        newDr.as().setName(newId.substring(newId.lastIndexOf("/") + 1));
        EventBus.getInstance().fireEvent(new DiskResourceRenamedEvent(dr, newDr.as()));
    }

    @Override
    public void onFailure(Throwable caught) {
        unmaskCaller();
        DiskResourceErrorAutoBeanFactory factory = GWT.create(DiskResourceErrorAutoBeanFactory.class);
        if (JsonUtils.safeToEval(caught.getMessage())) {
            AutoBean<DiskResourceRenameError> errorBean = AutoBeanCodex.decode(factory,
                    DiskResourceRenameError.class, caught.getMessage());

            ErrorHandler.post(errorBean.as(), caught);
        } else {
            ErrorHandler.post(caught);
        }


    }

    @Override
    protected String getErrorMessageDefault() {
        return I18N.ERROR.renameFolderFailed();
    }

    @Override
    protected String getErrorMessageByCode(ErrorCode code, JSONObject jsonError) {
        // TODO Auto-generated method stub
        return null;
    }

}