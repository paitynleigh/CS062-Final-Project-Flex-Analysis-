import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

/***
 * This class is a Graphical User Interface, representing our extra credit feature.
 * The TextFlex class initializes a new ui and calls the initialize method to pop
 * up a window containing buttons to perform our three features: view a histogram of
 * the average business of a vendor on a given day, see an overview of purchases at
 * each vendor for the first part of the semester, or find the least busy spots at a given time.
 */
public class UserInterface {
    Flex flexForUI;
    private JFrame frame;
    private JPanel cardPanel;
    private JPanel mainScreen;
    private JPanel chartScreen;
    private JPanel overviewScreen;
    private JPanel leastBusyScreen;
    
    public UserInterface(Flex flex){
        flexForUI = flex;
        initialize();
    }

    /**
     * Initializes a Graphical User Interface with buttons to perform each of the three 
     * features on click
     */
    public void initialize(){
        // instantiate JFrame with title FLEX, set exit, and set fixed size
        frame = new JFrame("FLEX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        frame.setResizable(false);
        
        // create panel card layout for easy switching between screens
        CardLayout cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        // create 4 main screens all with border layout for easy adding of title and instructions
        mainScreen = new JPanel(new BorderLayout(10,40));
        chartScreen = new JPanel(new BorderLayout(10,40));
        overviewScreen = new JPanel(new BorderLayout(10,40));
        leastBusyScreen = new JPanel(new BorderLayout(10,40));
        
        // add the card panel to the frame and all of the screen panels to the card panel
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
        JPanel chartCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        JPanel chartVendorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel chartDayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel histogramPanel = new JPanel(new BorderLayout(10,0));
        chartInputPanel.add(histogramPanel);
        

        JLabel chartVendorInstructions = new JLabel("Vendor:");
        chartVendorPanel.add(chartVendorInstructions);
        formatInstruction(chartVendorInstructions);


        // chart dropdown
        JComboBox<String> chartLocationDropdown = new JComboBox<>(flexForUI.getAllLocations());
        chartVendorPanel.add(chartLocationDropdown);
        chartLocationDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        JLabel chartDayInstructions = new JLabel("Day:");
        chartDayPanel.add(chartDayInstructions);
        formatInstruction(chartDayInstructions);
        
        LocalDateTime current = LocalDateTime.now();
        
        // dropdown to pick day of week 
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        JComboBox<String> chartDayDropdown = new JComboBox<>(days);
        String currentDay = current.getDayOfWeek().toString();
        int currentHours = current.getHour();
        int currentMinutes = current.getMinute();
        String meridiem;
        if(currentHours > 12){
            currentHours = currentHours % 12;
            meridiem = "PM";
        } else if (currentHours == 0){
            currentHours = 12;
            meridiem = "AM";
        } else {
            meridiem = "AM";
        }
        currentDay = currentDay.charAt(0) + currentDay.substring(1).toLowerCase();
        chartDayDropdown.setSelectedItem(currentDay);
        chartDayPanel.add(chartDayDropdown);
        chartDayDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        chartCenteringPanel.add(chartVendorPanel);
        chartCenteringPanel.add(chartDayPanel);

        // button to generate output
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

        // overview dropdown
        String[] locations = flexForUI.getAllLocations();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        model.addElement("Select"); 
        for (String loc : locations) {
            model.addElement(loc);
        }

        JComboBox<String> overviewLocationDropdown = new JComboBox<>(model);
        // default in dropdown is set to select
        overviewLocationDropdown.setSelectedIndex(0); 

        overviewCenteringPanel.add(overviewLocationDropdown);
        overviewLocationDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        overviewInputPanel.add(overviewCenteringPanel, BorderLayout.NORTH);

        JPanel overviewResponsePanel = new JPanel(new BorderLayout(10,5));
        overviewInputPanel.add(overviewResponsePanel);

        overviewScreen.add(overviewInputPanel);


        // Formatting for the least busy screen excluding the return button
        JPanel leastBusyInputPanel = new JPanel(new BorderLayout(10,10));
        JPanel leastBusyCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,10));
        leastBusyCenteringPanel.setPreferredSize(new Dimension(800,80));
        JPanel dayCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JPanel timeCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JPanel numLocationsCenteringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JPanel leastBusyOutputPanel = new JPanel(new BorderLayout(0,30));

        // Least Busy Days feature dropdown selection for day, time, number of locations
        JLabel leastBusyDaysInstruction = new JLabel("Day:");
        formatInstruction(leastBusyDaysInstruction);
        JComboBox<String> leastBusyDayDropdown = new JComboBox<>(days);
        leastBusyDayDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));        
        JLabel timeInstruction = new JLabel("Time:");
        formatInstruction(timeInstruction);
        String[] hours = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        String[] minutes = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
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

        // set default of least busy days dropdown to current day
        leastBusyDayDropdown.setSelectedItem(currentDay);
        hourDropdown.setSelectedItem(String.valueOf(currentHours));
        if(currentMinutes < 10){
            minuteDropdown.setSelectedItem("0" + String.valueOf(currentMinutes));
        } else {
            minuteDropdown.setSelectedItem(String.valueOf(currentMinutes));
        }
        meridiemDropdown.setSelectedItem(meridiem);

        hourDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        minuteDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        meridiemDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        numLocationsDropdown.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

        // add all features to the panels
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
        leastBusyInputPanel.add(leastBusyCenteringPanel, BorderLayout.NORTH);
        leastBusyInputPanel.add(leastBusyOutputPanel);
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

        chartGenerateButton.addActionListener(e -> createHistogram((String) chartLocationDropdown.getSelectedItem(), (String) chartDayDropdown.getSelectedItem(), histogramPanel, flexForUI.getTimeData().transactionsOnTime((String) chartLocationDropdown.getSelectedItem(), (String) chartDayDropdown.getSelectedItem())));
        overviewLocationDropdown.addActionListener(e -> generateOverview((String) overviewLocationDropdown.getSelectedItem(), overviewResponsePanel));
        leastBusyGenerateButton.addActionListener(e -> generateLeastBusy((String) leastBusyDayDropdown.getSelectedItem(), (String) hourDropdown.getSelectedItem(), (String) minuteDropdown.getSelectedItem(), (String) meridiemDropdown.getSelectedItem(), Integer.parseInt((String) numLocationsDropdown.getSelectedItem()), leastBusyOutputPanel));


        frame.setVisible(true);
    }

    /**
     * Reformats the home button so that all buttons are uniform
     * @param button home button to reformat
     */
    private void formatHomeButton(JButton button){
        Font buttonFont = new Font(Font.SERIF, Font.BOLD, 24);
        button.setFont(buttonFont);
        Dimension buttonSize = new Dimension(150,50);
        button.setPreferredSize(buttonSize);
        Insets buttonSpacing = new Insets(20,50,20,20);
        button.getInsets(buttonSpacing);
    }

    /***
     * Reformats the return button so that all buttons are uniform
     * @param button return button to reformat
     */
    private void formatReturnButton(JButton button){
        Font buttonFont = new Font(Font.SERIF, Font.PLAIN, 12);
        button.setFont(buttonFont);
        Dimension buttonSize = new Dimension(40,15);
        button.setSize(buttonSize); 
    }

    /***
     * Reformats the generate button so that all buttons are uniform
     * @param button generate button to reformat
     */
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

    /***
     * Change font on instruction label
     * @param label 
     */
    private void formatInstruction(JLabel label){
        Font instructionFont = new Font(Font.SERIF, Font.PLAIN, 20);
        label.setFont(instructionFont);
    }

    /**
     * Format title of each panel
     * @param title
     */
    private void formatPanelTitle(JLabel title){
        Font titleFont = new Font(Font.SERIF, Font.BOLD, 20);
        title.setFont(titleFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
    }


    /**
     * Format text area
     * @param ta
     */
    private void formatTextArea(JTextArea ta){
        ta.setBackground(UIManager.getColor("Panel.background"));
        ta.setFocusable(false);
    }

    /**
     * Formats text pane with given text
     * @param tp
     * @param text
     */
    private void formatTextPane(JTextPane tp, String text){
        tp.setText(text);
        tp.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        tp.setBackground(UIManager.getColor("Panel.background"));
        StyledDocument tpDoc = tp.getStyledDocument();
        SimpleAttributeSet format = new SimpleAttributeSet();
        StyleConstants.setAlignment(format, StyleConstants.ALIGN_CENTER);
        tpDoc.setParagraphAttributes(0, tpDoc.getLength(), format, false);
    }

    /***
     * Creates the histogram for the location business chart feature.
     * @param location selected in location business chart dropdown
     * @param day location selected in location business chart dropdown
     * @param histogramContainer a histogram JPanel
     * @param transactionData array of integer transaction frequencies
     */
    private void createHistogram(String location, String day, JPanel histogramContainer, int[] transactionData){
        System.out.println("create histogram for " + location + " on " + day);
        histogramContainer.removeAll();
        histogramContainer.revalidate();
        histogramContainer.repaint();
        JPanel histogramSideLabelPanel = new JPanel(new BorderLayout(0,0));
        JPanel maxPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        JPanel minPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        JPanel histogramSideLabelCushion = new JPanel();
        JPanel histogramBottomLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // JPanel histogramCenterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        JPanel histogramCenterPanel = new JPanel(new BorderLayout());
        //histogramSideLabelPanel.setBackground(Color.GREEN);
        //histogramBottomLabelPanel.setBackground(Color.GREEN);
        // histogramSideLabelCushion.setBackground(Color.ORANGE);
        //histogramCenterPanel.setBackground(Color.RED);
        // code that generates a HashMap of 15 minute-intervals (repped as Strings) as key to
        /*
        test using times intervals repped as Strings and random frequencies
        */
        int[] data = transactionData;
        String timeOverview = flexForUI.getLocationHours().getHours(location, day).toString();
        // find maximum frequency (range)
        int maxFrequency = 0;
        for(int i = 0; i < data.length; i ++){
            if(data[i] > maxFrequency){
                maxFrequency = data[i];
            }
        }
        // label maximum frequency and format
        JLabel max = new JLabel("" + maxFrequency);
        max.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        JLabel min = new JLabel("0");
        min.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        maxPanel.add(max);
        minPanel.add(min);
        histogramSideLabelPanel.add(maxPanel, BorderLayout.NORTH);
        histogramSideLabelPanel.add(minPanel, BorderLayout.SOUTH);

        JLabel histogramMessage = new JLabel("Displays number of transactions per 15 minute interval at " + location + " on " + day + "s from " + timeOverview);
        histogramMessage.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        histogramBottomLabelPanel.add(histogramMessage);
        int maxHeight = histogramContainer.getHeight() - 30;
        int maxWidth = histogramContainer.getWidth() - 50;
        int scalingConstant = maxHeight/maxFrequency;
        int barWidth = maxWidth/data.length;
        
        // separate bar panel
        JPanel barPanel = new JPanel(new GridLayout(1, data.length)); 
        // x-axis label panel
        JPanel xAxisLabelPanel = new JPanel();
        xAxisLabelPanel.setLayout(new GridLayout(1, data.length));
        xAxisLabelPanel.setPreferredSize(new Dimension(0, 20));

        // add a bar for each 15 minute interval frequency
        for(int i = 0; i < data.length; i ++){
            JPanel overallPanel = new JPanel(new BorderLayout(0,5));

            //JPanel label = new JPanel(new BorderLayout(0,5))

            overallPanel.setPreferredSize(new Dimension(barWidth,maxHeight));
            overallPanel.setMinimumSize(overallPanel.getPreferredSize());
            JPanel cushionForBorderLeft = new JPanel();
            JPanel cushionForBorderRight = new JPanel();
            cushionForBorderLeft.setPreferredSize(new Dimension(1,maxHeight));
            cushionForBorderRight.setPreferredSize(new Dimension(1,maxHeight));

            overallPanel.add(cushionForBorderLeft, BorderLayout.WEST);
            overallPanel.add(cushionForBorderRight, BorderLayout.EAST);
            //JLabel timeInterval = new JLabel(testTimes[i]);
            //overallPanel.add(timeInterval, BorderLayout.SOUTH);
            //JPanel barPanel = new JPanel(new BorderLayout(0,10));
            //overallPanel.add(barPanel);

            JPanel histogramBar = new JPanel();
            histogramBar.setPreferredSize(new Dimension(barWidth,data[i]*scalingConstant));
            histogramBar.setMaximumSize(histogramBar.getPreferredSize());
            histogramBar.setBackground(Color.BLUE);
            overallPanel.add(histogramBar, BorderLayout.SOUTH);

            histogramBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            barPanel.add(overallPanel);
            // histogramCenterPanel.add(overallPanel);

            // x-axis label:
            JLabel timeLabel = null;

            LocalTime[] hoursOpen = flexForUI.getLocationHours().getHours(location, day).getHours();
            LocalTime firstOpen = hoursOpen[0];
            LocalTime firstClose = hoursOpen[1];
            LocalTime secondOpen = null;
            LocalTime secondClose = null;
            int lengthOpen = firstClose.getHour() - firstOpen.getHour();
            int hourTracker = lengthOpen;

            // case where more than one open/close
            if (hoursOpen.length > 2) {
                secondOpen = hoursOpen[2];
                secondClose = hoursOpen[3];
            }

            if (lengthOpen > 0) {
                if (i % 4 == 0 && firstOpen.getMinute() == 0) {
                    // opens at hour case
                    int hour = firstOpen.getHour() + i / 4;
                    timeLabel = new JLabel(String.valueOf(hour),SwingConstants.CENTER);
                    timeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
                    timeLabel.setPreferredSize(new Dimension(barWidth, 20));
    
                    lengthOpen--;
                    
                } else if ((i - 2) % 4 == 0 && firstOpen.getMinute() == 30) {
                    // opens between hour case
                    int hour = firstOpen.getHour() + 1 + (i-2) / 4;
                    timeLabel = new JLabel(String.valueOf(hour),SwingConstants.CENTER);

                    timeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
                    timeLabel.setPreferredSize(new Dimension(barWidth, 20));
                    lengthOpen--;
                }
                else {
                    timeLabel = new JLabel("");
                }
            } else {
                if(secondOpen != null) {
                    
                    if (i % 4 == 0 && secondOpen.getMinute() == 0) {
                        lengthOpen = secondClose.getHour() - secondOpen.getHour();
                        // opens at hour case
                        int hour = secondOpen.getHour() + i / 4 - hourTracker;
                        
                        timeLabel = new JLabel(String.valueOf(hour), SwingConstants.CENTER);
                        timeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
                        timeLabel.setPreferredSize(new Dimension(barWidth, 20));

                        
                    } else if ((i - 2) % 4 == 0 && secondOpen.getMinute() == 30) {
                        lengthOpen = hoursOpen[3].getHour() - secondOpen.getHour();
                        // opens between hour case
                        int hour = secondOpen.getHour() + 1 + (i-2) / 4 - hourTracker;
                        
                        timeLabel = new JLabel(String.valueOf(hour), SwingConstants.CENTER);
                        timeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
                        timeLabel.setPreferredSize(new Dimension(barWidth, 20));

                    }
                    else {
                        timeLabel = new JLabel("");
                    }
                }
            }
            

            //timeLabel.setPreferredSize(new Dimension(barWidth, 20));  // force enough space
            //timeLabel.setMaximumSize(new Dimension(barWidth, 20));

            xAxisLabelPanel.add(timeLabel);

        }


        // add both the bars and labels to center panel
        histogramCenterPanel.add(barPanel, BorderLayout.CENTER);
        histogramCenterPanel.add(xAxisLabelPanel, BorderLayout.SOUTH);

        histogramContainer.add(histogramSideLabelPanel, BorderLayout.WEST);
        histogramContainer.add(histogramSideLabelCushion, BorderLayout.EAST);
        histogramContainer.add(histogramBottomLabelPanel, BorderLayout.NORTH);
        histogramContainer.add(histogramCenterPanel, BorderLayout.CENTER);  
        
        
    }

    private void generateOverview(String location, JPanel overviewContainer){
        System.out.println("overview for " + location);
        overviewContainer.removeAll();
        overviewContainer.revalidate();
        overviewContainer.repaint();

        Overview o = new Overview(flexForUI.getTimeData(), flexForUI.getFreqData());

        String leastBusy = correctOverview(o.getLeastBusy(), location);
        String mostBusy = correctOverview(o.getMostBusy(), location);
        String schoolTransAmount = o.getTransactionAmountbySchool();
        String locationTransAmount = correctOverview(o.getTransactionAmountbyLocation(), location);
        String schoolTotalAmount = o.getTotalAmountbySchool();
        String locationTotalAmount = correctOverview(o.getTotalAmountbyLocation(), location);


        JLabel overviewHeader = new JLabel("Overview for " + location + ":");
        overviewHeader.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        JTextPane overviewResponse = new JTextPane();
        formatTextPane(overviewResponse, leastBusy + "\n" + mostBusy + "\n" + locationTransAmount + "\n" + locationTotalAmount);

        JTextPane schoolResponse = new JTextPane();
        formatTextPane(schoolResponse, schoolTransAmount + "\n" + schoolTotalAmount);

        JPanel schoolResponsePanel = new JPanel(new BorderLayout(5,5));
        JLabel schoolHeader = new JLabel("The following are stats based on school, regardless of location:");
        schoolHeader.setFont(new Font(Font.SERIF, Font.BOLD, 18));

        schoolResponsePanel.add(schoolHeader, BorderLayout.NORTH);
        schoolResponsePanel.add(schoolResponse);
        overviewContainer.add(overviewHeader, BorderLayout.NORTH);
        overviewContainer.add(schoolResponsePanel, BorderLayout.SOUTH);
        overviewContainer.add(overviewResponse);

    }

    private String correctOverview(String allLocationData, String location){
        String[] eachEntry = allLocationData.split("\n");
        String correctMessage = eachEntry[0];
        for (String singleLocationData : eachEntry){
            String name = singleLocationData.substring(0, singleLocationData.indexOf(":"));
            if(name.equals(location)){
                correctMessage += singleLocationData;
            }
        }
        return correctMessage;
    }




    private void generateLeastBusy(String day, String hour, String minute, String meridiem, int numLocations, JPanel outputPanel){
        System.out.println(day + hour + minute + meridiem + numLocations);
        outputPanel.removeAll();
        outputPanel.revalidate();
        outputPanel.repaint();
        //"Least busy (" + TimeData.formatDay(day) + " " + startInterval + " - " + endInterval + "): " +

        int hourNoMeridiem = Integer.parseInt(hour);
        if(meridiem.equals("PM") && hourNoMeridiem != 12){
            hourNoMeridiem += 12;
        } else if (meridiem.equals("AM") && hourNoMeridiem == 12){
            hourNoMeridiem -= 12;
        }

        String message = LeastBusySpots.findLeastBusy(flexForUI.getTimeData(), flexForUI.getLocationHours(), day, String.valueOf(hourNoMeridiem),minute, numLocations);


        JPanel leastBusyOutputHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20, 5));
        JPanel leastBusyOutputResponsePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20, 5));

        JLabel leastBusyOutputHeader = new JLabel("The following are the least busy open locations for " + hour + ":" + minute + " " + meridiem + " on " + day + ":");
        formatInstruction(leastBusyOutputHeader);

        JTextArea leastBusyOutput = new JTextArea(message);
        leastBusyOutput.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        formatTextArea(leastBusyOutput);

        leastBusyOutputHeaderPanel.add(leastBusyOutputHeader);
        leastBusyOutputResponsePanel.add(leastBusyOutput);
        outputPanel.add(leastBusyOutputHeaderPanel, BorderLayout.NORTH);
        outputPanel.add(leastBusyOutputResponsePanel);

    }

}
