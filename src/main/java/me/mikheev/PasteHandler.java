package me.mikheev;

import ch.digitalfondue.jfiveparse.Converter;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.openapi.ide.CopyPasteManager;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.DataFlavor;
import java.util.Optional;

/**
 * @author Andrey_Mikheev
 */
public class PasteHandler extends EditorWriteActionHandler {

  private final boolean fromClipboard;

  PasteHandler(boolean fromClipboard) {
    this.fromClipboard = fromClipboard;
  }

  @Override
  public void executeWriteAction(Editor editor, @Nullable Caret caret, DataContext dataContext) {
    Document document = editor.getDocument();

    if(!document.isWritable()) {
      return;
    }

    String html;
    if(fromClipboard) {
      html = CopyPasteManager.getInstance().getContents(DataFlavor.stringFlavor);
    } else {
      html = editor.getSelectionModel().getSelectedText();
    }

    String indent = calcIndent(editor);
    String code = Optional.ofNullable(html).orElse("");
    String dsl = Converter.convert(code, indent);

    EditorModificationUtil.insertStringAtCaret(editor, dsl);
  }

  private String calcIndent(Editor editor) {
    Caret currentCaret = editor.getCaretModel().getCurrentCaret();
    int offset = currentCaret.getSelectionStartPosition().getColumn();
    String indent = "";
    if(offset > 0) {
      indent = String.format("%-" + offset + "s", " ");
    }
    return indent;
  }
}
