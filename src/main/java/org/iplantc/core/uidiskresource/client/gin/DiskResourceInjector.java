package org.iplantc.core.uidiskresource.client.gin;

import org.iplantc.core.uicommons.client.services.DiskResourceServiceFacade;
import org.iplantc.core.uidiskresource.client.views.DiskResourceView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(DiskResourceGinModule.class)
public interface DiskResourceInjector extends Ginjector {
    public static final DiskResourceInjector INSTANCE = GWT.create(DiskResourceInjector.class);

    public DiskResourceView.Presenter getDiskResourceViewPresenter();

    public DiskResourceServiceFacade getDiskResourceServiceFacade();

}
