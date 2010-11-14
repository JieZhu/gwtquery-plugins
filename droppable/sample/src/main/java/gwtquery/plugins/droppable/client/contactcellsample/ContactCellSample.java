package gwtquery.plugins.droppable.client.contactcellsample;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.contactcellsample.ContactDatabase.Category;
import gwtquery.plugins.droppable.client.contactcellsample.ContactDatabase.ContactInfo;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.CellDragAndDropBehaviour;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellList;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellWidgetUtils;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

/**
 * Take the contact sample of the GWT showcase and make the contactcell draggable !
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class ContactCellSample implements EntryPoint {

  /**
   * The Cell used to render a {@link ContactInfo}.
   * Code coming from the GWT showcase
   * 
   */
  private static class ContactCell extends AbstractCell<ContactInfo> {

    /**
     * The html of the image used for contacts.
     * 
     */
    private final String imageHtml;

    public ContactCell(ImageResource image) {
      this.imageHtml = AbstractImagePrototype.create(image).getHTML();
    }

    @Override
    public void render(ContactInfo value, Object key, SafeHtmlBuilder sb) {
      // Value can be null, so do a null check..
      if (value == null) {
        return;
      }

      sb.appendHtmlConstant("<table>");

      // Add the contact image.
      sb.appendHtmlConstant("<tr><td rowspan='3'>");
      sb.appendHtmlConstant(imageHtml);
      sb.appendHtmlConstant("</td>");

      // Add the name and address.
      sb.appendHtmlConstant("<td style='font-size:95%;'>");
      sb.appendEscaped(value.getFullName());
      sb.appendHtmlConstant("</td></tr><tr><td>");
      sb.appendEscaped(value.getAddress());
      sb.appendHtmlConstant("</td></tr></table>");
    }
  }

  /**
   * The images used for this example.
   */
  static interface Images extends ClientBundle {
    public Images INSTANCE = GWT.create(Images.class);

    ImageResource contact();
  }

  /**
   * Object handling the drop event. 
   * @author Julien Dramaix (julien.dramaix@gmail.com)
   *
   */
  private class DropHandler implements DropEventHandler {

    @SuppressWarnings("unchecked")
    public void onDrop(DropEvent event) {
      //retrieve the category linked to panle where the draggable was dropped.
      DroppableWidget<ShowMorePagerPanel> droppabelWidget = (DroppableWidget<ShowMorePagerPanel>) event
          .getDroppableWidget();
      ShowMorePagerPanel dropPanel = droppabelWidget.getOriginalWidget();
      Category dropCategory = dropPanel.getCategory();

      //retrieve the ContactInfo associated with the draggable element
      ContactInfo draggedContact = event.getDraggableData();
      
      Category oldCategory = draggedContact.getCategory();
      
      if (oldCategory == dropCategory) {
        return;
      }

      //change the category of the contact that was being dragged and prevent the data source.
      draggedContact.setCategory(dropCategory);
      ContactDatabase.get().moveContact(draggedContact, oldCategory);

      contactForm.setContact(draggedContact);

    }

  }

  private ContactInfoForm contactForm;

  
  public void onModuleLoad() {
    //add the contact form
    contactForm = new ContactInfoForm();
    RootPanel.get("contactForm").add(contactForm);

    //add the 4 lists for the 4 different categories
    RootPanel.get("cell").add(createList(Category.OTHERS));
    RootPanel.get("cell").add(createList(Category.FAMILY));
    RootPanel.get("cell").add(createList(Category.FRIENDS));
    RootPanel.get("cell").add(createList(Category.BUSINESS));

  }

  private DraggableOptions createDraggableOptions() {

    DraggableOptions options = new DraggableOptions();
    options.setHelper(HelperType.CLONE);
    options.setOpacity((float) 0.9);
    // options.setScroll(false);
    options.setAppendTo("body");
    return options;

  }

  /**
   * Code coming from GWT showcase
   * We just wrap the ContactCell into a DraggableCell and make the pager panel droppable. 
   * 
   * @param contactForm
   * 
   * @return
   */
  private DroppableWidget<ShowMorePagerPanel> createList(final Category category) {

    // Create a ConcactCell and make it draggable
    ContactCell contactCell = new ContactCell(Images.INSTANCE.contact());
   /* DraggableCell<ContactCell, ContactInfo> draggableContactCell = new DraggableCell<ContactCell, ContactInfo>(
        contactCell, createDraggableOptions());*/
  

    DragAndDropCellList<ContactInfo> cellList = new DragAndDropCellList<ContactInfo>(
        contactCell, ContactDatabase.ContactInfo.KEY_PROVIDER);
    cellList.setCellDragAndDropBehaviour(new CellDragAndDropBehaviour<ContactInfo>() {

      public boolean isDraggable(ContactInfo value) {
        return true;
      }

      public boolean isDroppable(ContactInfo value) {
        return false;
      }
    });
    cellList.setDraggableOptions(createDraggableOptions());
    
    cellList.setPageSize(30);
    cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
    final SingleSelectionModel<ContactInfo> selectionModel = new SingleSelectionModel<ContactInfo>(
        ContactDatabase.ContactInfo.KEY_PROVIDER);
    cellList.setSelectionModel(selectionModel);
    selectionModel
        .addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
          public void onSelectionChange(SelectionChangeEvent event) {
            contactForm.setContact(selectionModel.getSelectedObject());
          }
        });

    ContactDatabase.get().addDataDisplay(cellList, category);

    ShowMorePagerPanel pagerPanel = new ShowMorePagerPanel(category);
    pagerPanel.setDisplay(cellList);

    // make the pager panel droppable.
    DroppableWidget<ShowMorePagerPanel> droppabelPanel = new DroppableWidget<ShowMorePagerPanel>(
        pagerPanel);
    droppabelPanel.setHoverClass("orange-border");
    droppabelPanel.setActiveClass("yellow-border");
    droppabelPanel.addDropHandler(new DropHandler());
    droppabelPanel.setAccept(new AcceptFunction() {

      public boolean acceptDrop(Element droppable, Element draggable) {
        ContactInfo draggedContact = (ContactInfo) $(draggable).data(DragAndDropCellWidgetUtils.VALUE_KEY);
        Category dragCategory = draggedContact.getCategory();
        //accept only contact coming from an other panel.
        return dragCategory != category;
      }
    });

    return droppabelPanel;
  }
}
