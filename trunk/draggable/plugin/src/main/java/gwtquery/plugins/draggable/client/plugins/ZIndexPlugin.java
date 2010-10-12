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
package gwtquery.plugins.draggable.client.plugins;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;

import gwtquery.plugins.draggable.client.DraggableHandler;
import gwtquery.plugins.draggable.client.DraggableOptions;

/**
 * This add-on manage the z-index for the helper while being dragged.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class ZIndexPlugin implements DraggablePlugin {

  private static String OLD_ZINDEX_KEY = "oldZIndex";
  private static String ZINDEX_CSS = "zIndex";

  public String getName() {
    return "zIndex";
  }

  public boolean hasToBeExecuted(DraggableOptions options) {
    return options.getZIndex() != null;
  }

  public void onDrag(DraggableHandler handler, Element draggableElement, Event e) {
  }

  public void onStart(DraggableHandler handler, Element draggableElement,
      Event e) {
    GQuery $helper = handler.getHelper();
    String oldZIndex = $helper.css(ZINDEX_CSS);
    if (oldZIndex != null) {
      $helper.data(OLD_ZINDEX_KEY, oldZIndex);
    }
    $helper.css(ZINDEX_CSS, handler.getOptions().getZIndex().toString());

  }

  public void onStop(DraggableHandler handler, Element draggableElement, Event e) {
    GQuery $helper = handler.getHelper();
    String oldZIndex = $helper.data(OLD_ZINDEX_KEY, String.class);
    $helper.css(ZINDEX_CSS, oldZIndex);
  }

}