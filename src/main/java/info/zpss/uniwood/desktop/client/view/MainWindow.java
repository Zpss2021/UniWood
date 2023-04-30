package info.zpss.uniwood.desktop.client.view;

import javax.swing.*;

public class MainWindow extends JFrame {

    // 定义控件

    public MainWindow() {
        super();
        // 实例化控件
        this.initWindow();
        // 设置全局控件属性
        this.setTitle("UniWood - 桌面端");
    }

    private void initWindow() {
        // 设置布局及控件属性
    }

    public void showWindow() {
        this.setVisible(true);
        this.validate();
    }

}
