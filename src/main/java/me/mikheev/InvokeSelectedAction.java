package me.mikheev;

import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 * @author Andrey_Mikheev
 */
public class InvokeSelectedAction extends EditorAction {

  public InvokeSelectedAction() {
    super(new PasteHandler(false));
  }

  protected InvokeSelectedAction(EditorActionHandler defaultHandler) {
    super(defaultHandler);
  }

}
