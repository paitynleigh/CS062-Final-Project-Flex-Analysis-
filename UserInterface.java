import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserInterface {
    private JFrame frame;
    private JPanel cardPanel;
    private JPanel mainScreen;
    private JPanel chartScreen;
    private JPanel overviewScreen;
    private JPanel leastBusyScreen;
    
    public UserInterface(){
        initialize();
    }

    public void initialize(){
        // instantiate JFrame with title FLEX
        frame = new JFrame("FLEX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        frame.setResizable(false);
        
        CardLayout cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        mainScreen = new JPanel(new BorderLayout(10,40));
        chartScreen = new JPanel(new BorderLayout(10,40));
        overviewScreen = new JPanel(new BorderLayout(10,40));
        leastBusyScreen = new JPanel(new BorderLayout(10,40));
        frame.add(cardPanel);

        cardPanel.add(mainScreen, "mainScreen");
        cardPanel.add(chartScreen, "chartScreen");
        cardPanel.add(overviewScreen, "overviewScreen");
        cardPanel.add(leastBusyScreen, "leastBusyScreen");

        // add titles to all of the screens
        JLabel mainTitle = new JLabel("Home Screen");
        JLabel chartTitle = new JLabel("Location Business Chart");
        JLabel overviewTitle = new JLabel("Location Overview");
        JLabel leastBusyTitle = new JLabel("Least Busy Locations");
        mainScreen.add(mainTitle, BorderLayout.NORTH);
        chartScreen.add(chartTitle, BorderLayout.NORTH);
        overviewScreen.add(overviewTitle, BorderLayout.NORTH);
        leastBusyScreen.add(leastBusyTitle, BorderLayout.NORTH);
        formatPanelTitle(mainTitle);
        formatPanelTitle(chartTitle);
        formatPanelTitle(overviewTitle);
        formatPanelTitle(leastBusyTitle);

        // all formating for the home screen
        // create new panel for holding the instructions as a border layout to hold instructions at top and buttons in the rest
        JPanel homeInstructionPanel = new JPanel(new BorderLayout(10,40));
        JPanel instructionCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homeInstructionPanel.add(instructionCenteringPanel, BorderLayout.NORTH);
        JTextArea homeInstructions = new JTextArea("Welcome to the FLEX analysis site. Please select your desired option below:\n - Chart --> select a vendor and day to receive a visual of it's business at different times in the day\n - Overview --> select a vendor to recieve a FLEX spending overview of that location\n - Least Busy --> Select a time and day and recieve your specified amount of locations with the least traffic");
        instructionCenteringPanel.add(homeInstructions);
        formatTextArea(homeInstructions);

        JPanel homeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        

        JButton goToChartButton = new JButton("Chart");
        JButton goToOverviewButton = new JButton("Overview");
        JButton goToLeastBusyButton = new JButton("Least Busy");

        homeButtonPanel.add(goToChartButton);
        homeButtonPanel.add(goToOverviewButton);
        homeButtonPanel.add(goToLeastBusyButton);
        

        formatHomeButton(goToChartButton);
        formatHomeButton(goToOverviewButton);
        formatHomeButton(goToLeastBusyButton);
        homeInstructionPanel.add(homeButtonPanel);
        mainScreen.add(homeInstructionPanel);

        // formatting for the chart screen excluding the return button
        JPanel chartInputPanel = new JPanel(new BorderLayout(10,40));
        JPanel chartCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel chartVendorInstructions = new JLabel("Vendor:");
        chartCenteringPanel.add(chartVendorInstructions);
        formatInstruction(chartVendorInstructions);

        // String[] locations = getAllLocation();
        // NEED FUNCTION FOR THIS
        String[] test = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> chartLocationDropdown = new JComboBox<>(test/*locations */);
        chartCenteringPanel.add(chartLocationDropdown);
        chartLocationDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        JLabel chartDayInstructions = new JLabel("Day:");
        chartCenteringPanel.add(chartDayInstructions);
        formatInstruction(chartDayInstructions);
        
        LocalDateTime current = LocalDateTime.now();
        
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        JComboBox<String> chartDayDropdown = new JComboBox<>(days);
        String currentDay = current.getDayOfWeek().toString();
        int currentHours = current.getHour();
        int currentMinutes = current.getMinute();
        String meridiem;
        if(currentHours >= 12){
            currentHours = currentHours % 12;
            meridiem = "PM";
        } else {
            meridiem = "AM";
        }
        currentDay = currentDay.charAt(0) + currentDay.substring(1).toLowerCase();
        chartDayDropdown.setSelectedItem(currentDay);
        chartCenteringPanel.add(chartDayDropdown);
        chartDayDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));


        JButton chartGenerateButton = new JButton("Generate");
        formatGenerateButton(chartGenerateButton);
        chartCenteringPanel.add(chartGenerateButton);
        
        chartInputPanel.add(chartCenteringPanel, BorderLayout.NORTH);
        chartScreen.add(chartInputPanel);

        // formatting for the overview screen excluding the return button
        JPanel overviewInputPanel = new JPanel(new BorderLayout(10,40));
        JPanel overviewCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JLabel overviewVendorInstructions = new JLabel("Vendor:"); 
        overviewCenteringPanel.add(overviewVendorInstructions);
        formatInstruction(overviewVendorInstructions);
        JComboBox<String> overviewLocationDropdown = new JComboBox<>(test/*locations */);
        overviewCenteringPanel.add(overviewLocationDropdown);
        overviewLocationDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        overviewInputPanel.add(overviewCenteringPanel, BorderLayout.NORTH);

        overviewScreen.add(overviewInputPanel);

        // formatting for the least busy screen excluding the return button
        JPanel leastBusyInputPanel = new JPanel(new BorderLayout(10,40));
        JPanel leastBusyCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,10));
        JPanel dayCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JPanel timeCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JPanel numLocationsCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));

        JLabel leastBusyDaysInstruction = new JLabel("Day:");
        formatInstruction(leastBusyDaysInstruction);
        JComboBox<String> leastBusyDayDropdown = new JComboBox<>(days);
        leastBusyDayDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));        
        JLabel timeInstruction = new JLabel("Time:");
        formatInstruction(timeInstruction);
        String[] hours = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        String[] minutes = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
        String[] meridiems = {"AM","PM"};
        JComboBox<String> hourDropdown = new JComboBox<>(hours);
        JLabel colon = new JLabel(":");
        formatInstruction(colon);
        JComboBox<String> minuteDropdown = new JComboBox<>(minutes);
        JComboBox<String> meridiemDropdown = new JComboBox<>(meridiems);
        JLabel numLocationsInstruction = new JLabel("Number of Locations:");
        formatInstruction(numLocationsInstruction);
        String[] numLocations = {"1","2","3","4","5"};
        JComboBox<String> numLocationsDropdown = new JComboBox<>(numLocations);
        JButton leastBusyGenerateButton = new JButton("Generate");
        formatGenerateButton(leastBusyGenerateButton);

        hourDropdown.setSelectedItem(String.valueOf(currentHours));
        minuteDropdown.setSelectedItem(String.valueOf(currentMinutes));
        meridiemDropdown.setSelectedItem(meridiem);

        hourDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        minuteDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        meridiemDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        numLocationsDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));


        dayCenteringPanel.add(leastBusyDaysInstruction);
        dayCenteringPanel.add(leastBusyDayDropdown);
        timeCenteringPanel.add(timeInstruction);
        timeCenteringPanel.add(hourDropdown);
        timeCenteringPanel.add(colon);
        timeCenteringPanel.add(minuteDropdown);
        timeCenteringPanel.add(meridiemDropdown);
        numLocationsCenteringPanel.add(numLocationsInstruction);
        numLocationsCenteringPanel.add(numLocationsDropdown);
        leastBusyCenteringPanel.add(dayCenteringPanel);
        leastBusyCenteringPanel.add(timeCenteringPanel);
        leastBusyCenteringPanel.add(numLocationsCenteringPanel);
        leastBusyCenteringPanel.add(leastBusyGenerateButton);
        leastBusyInputPanel.add(leastBusyCenteringPanel);
        leastBusyScreen.add(leastBusyInputPanel);


        // all formating for return buttons
        JPanel chartReturnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel overviewReturnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel leastBusyReturnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnFromChartButton = new JButton("Return");
        JButton returnFromOverviewButton = new JButton("Return");
        JButton returnFromLeastBusyButton = new JButton("Return");

        chartReturnPanel.add(returnFromChartButton);
        overviewReturnPanel.add(returnFromOverviewButton);
        leastBusyReturnPanel.add(returnFromLeastBusyButton);

        chartScreen.add(chartReturnPanel, BorderLayout.SOUTH);
        overviewScreen.add(overviewReturnPanel, BorderLayout.SOUTH);
        leastBusyScreen.add(leastBusyReturnPanel, BorderLayout.SOUTH);


        formatReturnButton(returnFromChartButton);
        formatReturnButton(returnFromOverviewButton);
        formatReturnButton(returnFromLeastBusyButton);



        // add functionality to buttons
        // if any of the buttons on the home screen are pressed, take the user to the correct corresponding screen
        goToChartButton.addActionListener(e -> cardLayout.show(cardPanel, "chartScreen"));
        goToOverviewButton.addActionListener(e -> cardLayout.show(cardPanel, "overviewScreen"));
        goToLeastBusyButton.addActionListener(e -> cardLayout.show(cardPanel, "leastBusyScreen"));
        // if the return button on any of the special screens are clicked, return to the home screen
        returnFromChartButton.addActionListener(e -> cardLayout.show(cardPanel, "mainScreen"));
        returnFromOverviewButton.addActionListener(e -> cardLayout.show(cardPanel, "mainScreen"));
        returnFromLeastBusyButton.addActionListener(e -> cardLayout.show(cardPanel, "mainScreen"));

        chartGenerateButton.addActionListener(e -> createHistogram((String) chartLocationDropdown.getSelectedItem(), (String) chartDayDropdown.getSelectedItem()));
        overviewLocationDropdown.addActionListener(e -> generateOverview((String) overviewLocationDropdown.getSelectedItem()));
        leastBusyGenerateButton.addActionListener(e -> generateLeastBusy((String) leastBusyDayDropdown.getSelectedItem(), (String) hourDropdown.getSelectedItem(), (String) minuteDropdown.getSelectedItem(), (String) meridiemDropdown.getSelectedItem(), Integer.parseInt((String) numLocationsDropdown.getSelectedItem())));


        frame.setVisible(true);
    }

    private void formatHomeButton(JButton button){
        Font buttonFont = new Font(Font.SERIF, Font.BOLD, 24);
        button.setFont(buttonFont);
        Dimension buttonSize = new Dimension(150,50);
        button.setPreferredSize(buttonSize);
        Insets buttonSpacing = new Insets(20,50,20,20);
        button.getInsets(buttonSpacing);
    }

    private void formatReturnButton(JButton button){
        Font buttonFont = new Font(Font.SERIF, Font.PLAIN, 12);
        button.setFont(buttonFont);
        Dimension buttonSize = new Dimension(40,15);
        button.setSize(buttonSize); 
    }

    private void formatGenerateButton(JButton button){
        Font buttonFont = new Font(Font.SERIF, Font.BOLD, 20);
        button.setFont(buttonFont);
        Dimension buttonSize = new Dimension(100,30);
        button.setSize(buttonSize);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    private void formatInstruction(JLabel label){
        Font instructionFont = new Font(Font.SERIF, Font.PLAIN, 20);
        label.setFont(instructionFont);
    }

    private void formatPanelTitle(JLabel title){
        Font titleFont = new Font(Font.SERIF, Font.BOLD, 20);
        title.setFont(titleFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void formatTextArea(JTextArea tp){
        tp.setBackground(UIManager.getColor("Panel.background"));
        tp.setFocusable(false);
    }

    private void createHistogram(String location, String day){
        System.out.println("create histogram for " + location + " on " + day);
    }

    private void generateOverview(String location){
        System.out.println("overview for " + location);
    }

    private void generateLeastBusy(String day, String hour, String minute, String meridiem, int numLocations){
        System.out.println(day + hour + minute + meridiem + numLocations);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                UserInterface  ui = new UserInterface();
                ui.initialize();
            }
        });
    }
}
