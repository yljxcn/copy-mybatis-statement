package cn.lony;

import com.intellij.ide.CopyProvider;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;

public class CopyMyBatisStatementConsoleMenu extends AnAction {

    public static final String PREPARING_PREFIX = "==>  Preparing: ";
    public static final String PARAMETERS_PREFIX = "==> Parameters: ";

    @Override
    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        DataContext dataContext = e.getDataContext();
        System.out.println(dataContext);
        CopyProvider copyProvider = PlatformDataKeys.COPY_PROVIDER.getData(dataContext);
        boolean available = copyProvider != null && copyProvider.isCopyEnabled(dataContext) && copyProvider.isCopyVisible(dataContext);
        presentation.setEnabled(available);
        presentation.setVisible(available);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        String selectedText = editor.getSelectionModel().getSelectedText();

        String[] sections = selectedText.split("\\n");

        String statement = "";
        String[] paramters = null;

        for (String section : sections) {
            if (section.contains(PREPARING_PREFIX)) {
                statement = section.split(PREPARING_PREFIX)[1].replace("?", "'%s'");
            }
            if (section.contains(PARAMETERS_PREFIX)) {
                paramters = section.split(PARAMETERS_PREFIX)[1].split("\\(\\w*\\),?\\s?");
            }
        }
        ClipboardUtil.setClipboardString(String.format(statement, paramters));
    }
}
