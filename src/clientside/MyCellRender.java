package clientside;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author iLLuMinatTi
 */
class MyCellRender extends JLabel implements ListCellRenderer {
   public MyCellRender() {
    setOpaque(true);
    
    
  }

   @Override
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    NotificationClient entry = (NotificationClient) value;
    if(entry.isNew)
    {
        setText("New!!\t" + entry.content);
        setBackground(Color.WHITE);
        setForeground(Color.RED);
    }
    else
    {
        setText(entry.content);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
    }
    return this;
  }
}
