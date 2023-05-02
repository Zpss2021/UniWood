package info.zpss.uniwood.desktop.client.view.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SearchPanel extends JPanel {
    public final JPanel searchPanel;
    public final JPanel ctrlPanel;
    public final JTextField searchField;
    public final JButton searchBtn;
    public final JPanel radioPane;
    public final JRadioButton postRadio;
    public final JRadioButton zoneRadio;
    public final JRadioButton userRadio;

    public SearchPanel() {
        super();

        this.searchPanel = new JPanel(new GridBagLayout());
        this.ctrlPanel = new JPanel();
        this.searchField = new JTextField();
        this.searchBtn = new JButton("搜索");
        this.radioPane = new JPanel(new GridLayout(3, 1));
        this.postRadio = new JRadioButton("贴子");
        this.zoneRadio = new JRadioButton("分区");
        this.userRadio = new JRadioButton("用户");

        searchBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(20, 20, Image.SCALE_FAST)));  // TODO

        radioPane.add(postRadio);
        radioPane.add(zoneRadio);
        radioPane.add(userRadio);

        searchField.setText("输入关键词搜索贴子/分区/用户"); // TODO：根据不同的搜索类型，显示不同的提示
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

        searchField.setPreferredSize(new Dimension(250, 60));
        radioPane.setPreferredSize(new Dimension(60, 60));
        searchBtn.setPreferredSize(new Dimension(80, 60));


        // TODO：字体
        // TODO：为按钮添加图标
        // TODO：为按钮和搜索框添加事件

        searchPanel.add(searchField, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        ctrlPanel.add(radioPane);
        ctrlPanel.add(searchBtn);

        this.setLayout(new BorderLayout());
        this.add(searchPanel, BorderLayout.CENTER);
        this.add(ctrlPanel, BorderLayout.EAST);

        this.setBorder(BorderFactory.createTitledBorder("搜索"));
    }
}
