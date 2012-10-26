package org.iplantc.core.uidiskresource.client.views;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.iplantc.core.uidiskresource.client.models.autobeans.DiskResource;
import org.iplantc.core.uidiskresource.client.models.autobeans.DiskResourceModelKeyProvider;
import org.iplantc.core.uidiskresource.client.models.autobeans.Folder;
import org.iplantc.core.uidiskresource.client.views.widgets.DiskResourceViewToolbar;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent.DndDragStartHandler;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.GridDragSource;
import com.sencha.gxt.dnd.core.client.GridDropTarget;
import com.sencha.gxt.dnd.core.client.TreeDragSource;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.TreeAppearance;
import com.sencha.gxt.widget.core.client.tree.Tree.TreeNode;
import com.sencha.gxt.widget.core.client.tree.TreeStyle;

public class DiskResourceViewImpl implements DiskResourceView {

    @UiTemplate("DiskResourceView.ui.xml")
    interface DiskResourceViewUiBinder extends UiBinder<Widget, DiskResourceViewImpl> {
    }

    private static DiskResourceViewUiBinder BINDER = GWT.create(DiskResourceViewUiBinder.class);

    private Presenter presenter;

    @UiField
    DiskResourceViewToolbar toolbar;

    @UiField
    BorderLayoutContainer con;

    @UiField
    ContentPanel westPanel;

    @UiField
    Tree<Folder, String> tree;

    @UiField(provided = true)
    final TreeStore<Folder> treeStore;

    @UiField
    ContentPanel centerPanel;

    @UiField
    Grid<DiskResource> grid;

    @UiField
    ColumnModel<DiskResource> cm;

    @UiField
    ListStore<DiskResource> listStore;

    @UiField
    GridView<DiskResource> gridView;

    @UiField
    ContentPanel eastPanel;

    @UiField
    BorderLayoutData westData;
    @UiField
    BorderLayoutData centerData;
    @UiField
    BorderLayoutData eastData;
    @UiField
    BorderLayoutData northData;
    @UiField
    BorderLayoutData southData;

    private final Widget widget;

    private TreeLoader<Folder> treeLoader;

    private final GridDropTarget<DiskResource> gridDropTarget;

    public DiskResourceViewImpl(final TreeStore<Folder> treeStore) {
        this.treeStore = treeStore;
        widget = BINDER.createAndBindUi(this);

        // Set Leaf icon to a folder
        TreeStyle treeStyle = tree.getStyle();
        TreeAppearance appearance = tree.getAppearance();
        treeStyle.setLeafIcon(appearance.closeNodeIcon());
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.getSelectionModel().addSelectionHandler(new SelectionHandler<Folder>() {

            @Override
            public void onSelection(SelectionEvent<Folder> event) {
                if (event.getSelectedItem() != null) {
                    onFolderSelected(event.getSelectedItem());
                }
            }

        });

        grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<DiskResource>() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent<DiskResource> event) {
                if ((event.getSelection() != null) && !event.getSelection().isEmpty()) {
                    presenter.onDiskResourceSelected(Sets.newHashSet(event.getSelection()));
                }
            }

        });

        gridDropTarget = new GridDropTarget<DiskResource>(grid);
        GridDragSource<DiskResource> gridDragSource = new GridDragSource<DiskResource>(grid);
        // TreeDropTarget<Folder> treeDropTarget = new TreeDropTarget<Folder>(tree);
        TreeDragSource<Folder> treeDragSource = new TreeDragSource<Folder>(tree);
        treeDragSource.addDragStartHandler(new DndDragStartHandler() {

            @Override
            public void onDragStart(DndDragStartEvent event) {
                // Transform drag start data from ArrayList<TreeModel> to ArrayList<Folder>
                if ((event.getData() instanceof List)
                        && !((List<?>)event.getData()).isEmpty()
                        && (((List<?>)event.getData()).get(0) instanceof TreeStore.TreeNode<?>)
                        && (((TreeStore.TreeNode<?>)(((List<?>)event.getData()).get(0))).getData() instanceof Folder)) {
                    GWT.log("derp");
                    // ArrayList<Folder> folders = Lists.newArrayList();
                    // for (TreeStore.TreeNode<Folder> f : (List<TreeStore.TreeNode<Folder>>)event
                    // .getData()) {
                    // folders.add(f.getData());
                    // }
                    // event.setData(folders);

                }

            }
        });

    }

    @Override
    public void onDiskResourceSelected(Set<DiskResource> selection) {
        onDiskResourceSelected(selection);
    }

    @Override
    public void onFolderSelected(Folder folder) {
        presenter.onFolderSelected(folder);
    }

    @UiFactory
    TreeStore<Folder> createTreeStore() {
        return new TreeStore<Folder>(new DiskResourceModelKeyProvider());
    }

    @UiFactory
    ListStore<DiskResource> createListStore() {
        return new ListStore<DiskResource>(new DiskResourceModelKeyProvider());
    }

    @UiFactory
    public ValueProvider<Folder, String> createValueProvider() {
        return new ValueProvider<Folder, String>() {

            @Override
            public String getValue(Folder object) {
                return object.getName();
            }

            @Override
            public void setValue(Folder object, String value) {}

            @Override
            public String getPath() {
                return "name";
            }
        };
    }

    @UiFactory
    ColumnModel<DiskResource> createColumnModel() {
        return new DiskResourceColumnModel();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        toolbar.setPresenter(presenter);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void setTreeLoader(TreeLoader<Folder> treeLoader) {
        tree.setLoader(treeLoader);
        this.treeLoader = treeLoader;
    }

    @Override
    public Folder getSelectedFolder() {
        return tree.getSelectionModel().getSelectedItem();
    }

    @Override
    public Set<DiskResource> getSelectedDiskResources() {
        return Sets.newHashSet(grid.getSelectionModel().getSelectedItems());
    }

    @Override
    public void setRootFolders(Set<Folder> rootFolders) {
        treeStore.add(Lists.newArrayList(rootFolders));
    }

    @Override
    public TreeStore<Folder> getTreeStore() {
        return treeStore;
    }

    @Override
    public boolean isLoaded(Folder folder) {
        TreeNode<Folder> findNode = tree.findNode(folder);
        return findNode.isLoaded();
    }

    @Override
    public void setDiskResources(Set<DiskResource> folderChildren) {
        grid.getStore().clear();
        grid.getStore().addAll(folderChildren);
    }

    @Override
    public void setWestWidgetHidden(boolean hideWestWidget) {
        westData.setHidden(hideWestWidget);
    }

    @Override
    public void setCenterWidgetHidden(boolean hideCenterWidget) {
        // If we are hiding the center widget, update west data to fill available space.
        if (hideCenterWidget) {
            westData.setSize(1);
        }
        centerData.setHidden(hideCenterWidget);
    }

    @Override
    public void setEastWidgetHidden(boolean hideEastWidget) {
        eastData.setHidden(hideEastWidget);
    }

    @Override
    public void setNorthWidgetHidden(boolean hideNorthWidget) {
        northData.setHidden(hideNorthWidget);
    }

    @Override
    public void setSouthWidget(IsWidget widget) {
        southData.setHidden(false);
        con.setSouthWidget(widget, southData);
    }

    @Override
    public void addDiskResourceSelectChangedHandler(SelectionChangedHandler<DiskResource> selectionChangedHandler) {
        grid.getSelectionModel().addSelectionChangedHandler(selectionChangedHandler);
    }

    @Override
    public void addFolderSelectionHandler(SelectionHandler<Folder> selectionHandler) {
        tree.getSelectionModel().addSelectionHandler(selectionHandler);
    }

    @Override
    public void setSelectedFolder(Folder folder) {

        if (treeStore.findModelWithKey(folder.getId()) != null) {
            tree.getSelectionModel().setSelection(
                    Lists.newArrayList(treeStore.findModelWithKey(folder.getId())));
        }
    }

    @Override
    public void addFolder(Folder parent, Folder newChild) {
        treeStore.add(parent, newChild);
    }

    @Override
    public Folder getFolderById(String folderId) {
        return treeStore.findModelWithKey(folderId);
    }

    @Override
    public void expandFolder(Folder folder) {
        tree.setExpanded(folder, true);
    }

    @Override
    public void deSelectDiskResources() {
        grid.getSelectionModel().deselectAll();
    }

    @Override
    public Set<Folder> getRootFolders() {
        return Sets.newHashSet(treeStore.getRootItems());
    }

    @Override
    public void refreshAll() {
        treeStore.clear();
        treeLoader.load(null);
    }

    @Override
    public void refreshFolder(Folder folder) {
        if (folder == null) {
            return;
        }

        if (!isLoaded(folder)) {
            treeLoader.load(folder);
        } else {
            treeStore.removeChildren(folder);
            treeLoader.load(folder);
        }
    }

    @Override
    public DiskResourceViewToolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void mask(String loadingMask) {
        con.mask(loadingMask);
    }

    @Override
    public void unmask() {
        con.unmask();
    }

    @Override
    public <D extends DiskResource> void removeDiskResources(Collection<D> resources) {
        for (DiskResource dr : resources) {
            listStore.remove(dr);
            if (dr instanceof Folder) {
                treeStore.remove((Folder)dr);
            }
        }
    }

    @Override
    public void addGridDropHandler(DndDropHandler dndDropHandler) {
        gridDropTarget.addDropHandler(dndDropHandler);
    }

    @Override
    public void updateDiskResource(DiskResource originalDr, DiskResource newDr) {
        // Check each store for for existence of original disk resource
        Folder treeStoreModel = treeStore.findModelWithKey(originalDr.getId());
        if (treeStoreModel != null) {

            // Grab original disk resource's parent, then remove original from tree store
            Folder parentFolder = treeStore.getParent(treeStoreModel);
            treeStore.remove(treeStoreModel);

            treeStoreModel.setId(newDr.getId());
            treeStoreModel.setName(newDr.getName());
            treeStore.add(parentFolder, treeStoreModel);
        }

        DiskResource listStoreModel = listStore.findModelWithKey(originalDr.getId());
        if (listStoreModel != null) {
            listStore.remove(listStoreModel);
            listStore.add(newDr);
        }
    }

}