package gwtquery.plugins.droppable.client.testoptionssample;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;
import static gwtquery.plugins.droppable.client.Droppable.Droppable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.droppable.client.DroppableOptions;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class TestOptionsSample implements EntryPoint {

  public static class SetupPopupPanel extends PopupPanel{

    private static SetupPopupPanelUiBinder uiBinder = GWT.create(SetupPopupPanelUiBinder.class);

    @UiTemplate(value = "SetupPopupPanel.ui.xml")
    interface SetupPopupPanelUiBinder extends UiBinder<Widget, SetupPopupPanel> {
    }
    
    @UiField
    Label header;
    @UiField
    SimplePanel popupContent;
    
    public SetupPopupPanel(String id) {
      setModal(true);
      setGlassEnabled(true);
      setAutoHideEnabled(true);
      super.add(uiBinder.createAndBindUi(this));
      initHeader(id);
    }
    
    private void initHeader(String id){
      header.setText("Available options for "+id);
    }
    
    @UiHandler(value = { "closeButton" })
    public void onCloseButtonClicked(ClickEvent e){
      hide();
    }
    
    @UiHandler(value = { "resetButton" })
    public void onResetButtonClicked(ClickEvent e){
      EVENT_BUS.fireEvent(new ResetOptionEvent(popupContent.getWidget()));
    }
    
    @Override
    public void add(Widget w) {
      popupContent.add(w);
    }
    
    
  }
  public static SimpleEventBus EVENT_BUS = new SimpleEventBus();
  
  public void onModuleLoad() {
    addSetupButton("draggable1", new DraggableOptionsPanel($("#draggable1")
        .get(0)));
    addSetupButton("draggable2", new DraggableOptionsPanel($("#draggable2")
        .get(0)));

    addSetupButton("mainDroppable1", new DroppableOptionsPanel($(
        "#mainDroppable1").get(0)));
    addSetupButton("secondDroppable1", new DroppableOptionsPanel($(
        "#secondDroppable1").get(0)));
    addSetupButton("mainDroppable2", new DroppableOptionsPanel($(
        "#mainDroppable2").get(0)));
    addSetupButton("secondDroppable2", new DroppableOptionsPanel($(
        "#secondDroppable2").get(0)));

    $("#draggable1").as(Draggable).draggable(EVENT_BUS);
    $("#draggable2").as(Draggable).draggable(EVENT_BUS);
    $("#secondDroppable1").as(Droppable).droppable(createDroppableOptions(),EVENT_BUS);
    $("#mainDroppable1").as(Droppable).droppable(createDroppableOptions(),EVENT_BUS);
    //$("#secondDroppable1").as(Droppable).droppable(createDroppableOptions(),EVENT_BUS);
    $("#mainDroppable2").as(Droppable).droppable(createDroppableOptions(),EVENT_BUS);
    $("#secondDroppable2").as(Droppable).droppable(createDroppableOptions(),EVENT_BUS);
    
    RootPanel.get("logEventWindow").add(new LogEventPanel());

  }

  private void addSetupButton(final String id, final Widget setupPanel) {
    Button setup = new Button("setup me!");
    setup.addStyleName("setupButton");
    setup.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        PopupPanel setupPopup = new SetupPopupPanel(id);
        setupPopup.add(setupPanel);
        setupPopup.center();

      }
    });
    RootPanel.get(id).add(setup);

  }
  
  /**
   * Create droppable options having some visual feedback on drop operation 
   * @return
   */
  private DroppableOptions createDroppableOptions(){
    DroppableOptions options = new DroppableOptions();
    options.setActiveClass("orange-background");
    options.setHoverClass("yellow-background");
    return options;
  }
}
