package me.mikheev;

import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 * @author Andrey_Mikheev
 */
public class InvokeClipboardAction extends EditorAction {

  public InvokeClipboardAction() {
    super(new PasteHandler(true));
  }

  protected InvokeClipboardAction(EditorActionHandler defaultHandler) {
    super(defaultHandler);
  }

}
