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

import gwtquery.plugins.draggable.client.events.DragContext;

/**
 * Event fired when an acceptable draggable is being dragged over a droppable.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com, @jdramaix)
 * 
 */
public class OverDroppableEvent extends
    AbstractDroppableEvent<OverDroppableEvent.OverDroppableEventHandler> {

  public interface OverDroppableEventHandler extends EventHandler {
    public void onOverDroppable(OverDroppableEvent event);
  }

  public static Type<OverDroppableEventHandler> TYPE = new Type<OverDroppableEventHandler>();

  public OverDroppableEvent(Element droppable, DragContext dragCtx) {
    super(droppable, dragCtx);
  }

  public OverDroppableEvent(DragAndDropContext ctx) {
    super(ctx);
  }

  @Override
  public Type<OverDroppableEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(OverDroppableEventHandler handler) {
    handler.onOverDroppable(this);
  }

}
