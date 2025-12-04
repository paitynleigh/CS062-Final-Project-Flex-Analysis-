import javax.swing.*;
import java.awt.*;

public class UserInterface {
    private final Dimension screenDim = new Dimension(800,800);
    private JFrame frame = new JFrame("FLEX");
    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);
    JPanel mainScreen = new JPanel();
    JPanel chartScreen = new JPanel();
    JPanel overviewScreen = new JPanel();
    JPanel leastBusyScreen = new JPanel();
    
    public void initialize(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.add(cardPanel);

        cardPanel.add(mainScreen, "mainScreen");
        cardPanel.add(chartScreen, "chartScreen");
        cardPanel.add(overviewScreen, "overviewScreen");
        cardPanel.add(leastBusyScreen, "leastBusyScreen");

        mainScreen.add(new JLabel("Home"));
        chartScreen.add(new JLabel("Location Business Chart"));
        overviewScreen.add(new JLabel("Location Overview"));
        leastBusyScreen.add(new JLabel("Least Busy Locations"));

        JButton chartButton = new JButton("Chart");
        JButton overviewButton = new JButton("Overview");
        JButton leastBusyButton = new JButton("Least Busy");

        mainScreen.add(chartButton);
        mainScreen.add(overviewButton);
        mainScreen.add(leastBusyButton);

        chartButton.addActionListener(e -> cardLayout.show(cardPanel, "chartScreen"));
        overviewButton.addActionListener(e -> cardLayout.show(cardPanel, "overviewScreen"));
        leastBusyButton.addActionListener(e -> cardLayout.show(cardPanel, "leastBusyScreen"));


        frame.setVisible(true);
    }

    private void formatButton(JButton button){
        //button.setSize();
        //button.setFont();
        //button.setBackground();
    }

    private void createHistogram(){
        
    }


    public static void main(String[] args) {
        UserInterface  ui = new UserInterface();
        ui.initialize();
    }
}
