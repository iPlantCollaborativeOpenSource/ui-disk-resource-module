package org.iplantc.core.uidiskresource.client.views.cells;

import static com.google.gwt.dom.client.BrowserEvents.CLICK;
import static com.google.gwt.dom.client.BrowserEvents.MOUSEOUT;
import static com.google.gwt.dom.client.BrowserEvents.MOUSEOVER;

import org.iplantc.core.uicommons.client.events.EventBus;
import org.iplantc.core.uidiskresource.client.events.DiskResourceSelectedEvent;
import org.iplantc.core.uidiskresource.client.models.autobeans.DiskResource;
import org.iplantc.core.uidiskresource.client.models.autobeans.File;
import org.iplantc.core.uidiskresource.client.models.autobeans.Folder;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.widget.core.client.tips.Tip;

/**
 * A cell for displaying the icons and names for <code>DiskResource</code> list items.
 * 
 * TODO JDS Implement preview tooltip.
 * Tooltip will probably have to be {@link Tip}, since this is a cell and not a widget.
 * 
 * @author jstroot
 * 
 */
public class DiskResourceNameCell extends AbstractCell<DiskResource> {

    interface DiskResourceNameCellStyle extends CssResource {

        String drFile();

        String drFolder();

        String nameStyle();
    }

    interface Resources extends ClientBundle {
        @Source("DiskResourceNameCell.css")
        DiskResourceNameCellStyle css();

        @Source("images/file.gif")
        ImageResource file();

        @Source("images/folder.gif")
        ImageResource folder();
    }

    /**
     * The HTML templates used to render the cell.
     */
    interface Templates extends SafeHtmlTemplates {

        @SafeHtmlTemplates.Template("<span class=\"{0}\"> </span>&nbsp;<span name=\"drName\" class=\"{1}\" >{2}</span>")
        SafeHtml cell(String imgClassName, String diskResourceClassName, SafeHtml diskResourceName);
    }

    final Resources res = GWT.create(Resources.class);
    final Templates templates = GWT.create(Templates.class);

    public DiskResourceNameCell() {
        super(CLICK, MOUSEOVER, MOUSEOUT);
        res.css().ensureInjected();
    }

    @Override
    public void render(Cell.Context context, DiskResource value, SafeHtmlBuilder sb) {
        if (value == null) {
            return;
        }

        if (value instanceof File) {
            sb.append(templates.cell(res.css().drFile(), res.css().nameStyle(),
                    SafeHtmlUtils.fromString(value.getName())));

        } else if (value instanceof Folder) {
            sb.append(templates.cell(res.css().drFolder(), res.css().nameStyle(),
                    SafeHtmlUtils.fromString(value.getName())));

        }
    }

    @Override
    public void onBrowserEvent(Cell.Context context, Element parent, DiskResource value,
            NativeEvent event, ValueUpdater<DiskResource> valueUpdater) {
        if (value == null) {
            return;
        }

        Element eventTarget = Element.as(event.getEventTarget());
        if (parent.isOrHasChild(eventTarget)) {

            switch (Event.as(event).getTypeInt()) {
                case Event.ONCLICK:
                    doOnClick(eventTarget, value, valueUpdater);
                    break;
                case Event.ONMOUSEOVER:
                    doOnMouseOver(eventTarget, value);
                    break;
                case Event.ONMOUSEOUT:
                    doOnMouseOut(eventTarget, value);
                    break;
                default:
                    break;
            }
        }
    }

    private void doOnMouseOut(Element eventTarget, DiskResource value) {
        if (eventTarget.getAttribute("name").equalsIgnoreCase("drName")) {
            eventTarget.getStyle().setTextDecoration(TextDecoration.NONE);
        }
    }

    private void doOnMouseOver(Element eventTarget, DiskResource value) {
        if (eventTarget.getAttribute("name").equalsIgnoreCase("drName")) {
            eventTarget.getStyle().setTextDecoration(TextDecoration.UNDERLINE);
        }
    }

    private void doOnClick(Element eventTarget, DiskResource value,
            ValueUpdater<DiskResource> valueUpdater) {

        EventBus.getInstance().fireEvent(new DiskResourceSelectedEvent(this, value));
    }

}