package info.zpss.uniwood.desktop.client.view.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SearchPanel extends JPanel {
    public final JTextField searchField;
    public final JButton searchBtn;

    public SearchPanel() {
        super();

        this.searchField = new JTextField();
        this.searchBtn = new JButton("搜索");

        this.setLayout(new FlowLayout());

        searchField.setPreferredSize(new Dimension(420, 40));
        searchBtn.setPreferredSize(new Dimension(80, 45));

        searchField.setText("输入关键词搜索贴子/分区/用户");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (searchField.getText().equals("输入关键词搜索贴子/分区/用户")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (searchField.getText().equals("")) {
                    searchField.setText("输入关键词搜索贴子/分区/用户");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // TODO：字体
        // TODO：为按钮添加图标
        // TODO：为按钮和搜索框添加事件

        // TODO：添加三个单选框，用来选择搜索范围

        this.add(searchField);
        this.add(searchBtn);

        this.setBorder(BorderFactory.createTitledBorder("搜索"));
    }

    // 测试用入口函数
    public static void main(String[] args) {
    }
}
