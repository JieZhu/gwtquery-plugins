/*
 * Copyright 2010 The gwtquery plugins team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwtquery.plugins.droppable.client.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;

/**
 * Event fired when an acceptable draggable is being dragged out of the droppable.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class OutDroppableEvent extends
    AbstractDroppableEvent<OutDroppableEvent.OutDroppableEventHandler> {

  public interface OutDroppableEventHandler extends EventHandler {
    public void onOutDroppable(OutDroppableEvent event);
  }

  public static Type<OutDroppableEventHandler> TYPE = new Type<OutDroppableEventHandler>();

  public OutDroppableEvent(DragAndDropContext ctx) {
    super(ctx);
  }

  public OutDroppableEvent(Element droppable, Element draggable) {
    super(droppable, draggable);
  }

  @Override
  public Type<OutDroppableEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(OutDroppableEventHandler handler) {
    handler.onOutDroppable(this);
  }

}
