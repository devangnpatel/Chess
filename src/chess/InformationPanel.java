package chess;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * not really used yet
 * - would hold a label with text that can be easily updated
 * @author devang
 */
public class InformationPanel extends JPanel {
    private String informationText;
    private JLabel informationLabel;

    public InformationPanel(String informationText)
    {
        this.informationText = informationText;
        informationLabel = new JLabel(informationText,SwingConstants.CENTER);
        informationLabel.setVisible(true);
        add(informationLabel);
        setVisible(true);
    }
    
    public InformationPanel()
    {
        this("");
    }
    
    public void setInformationText(String informationText)
    {
        this.informationText = informationText;
        informationLabel.setText(informationText);
        repaint();
    }
    
    public String getinformationText()
    {
        return informationText;
    }
}
