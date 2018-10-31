package cn.lony;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class CopyMyBatisStatementConsoleMenu extends AnAction {

    public static final String PREPARING_PREFIX = "==>  Preparing: ";
    public static final String PARAMETERS_PREFIX = "==> Parameters: ";

    @Override
    public void actionPerformed(AnActionEvent e) {
        String content = ClipboardUtil.getClipboardString();
        String[] sections = content.split("\\n");

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
