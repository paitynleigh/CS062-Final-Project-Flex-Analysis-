import javax.swing.SwingUtilities;

/**
 * This is the class used for running the main program and UI
 * @authors Jalen DeLoney, Paityn Richardson, Maren Rusk, and Nate Wehner
 */
public class TestFlex {
    public static void main(String[] args){
        // create new Flex object
        Flex flex = new Flex();

        // load data
        flex.loadCSV("Data/Stored_Value_Transaction_by_Customer__11_39_2025-10-17_11_40_52(Stored_Value_Transaction_by_Cus).csv");
        flex.loadLocationHours("Data/LocationHoursData");

        // run the UI using the flex object with given data
        SwingUtilities.invokeLater(() -> {
            UserInterface  ui = new UserInterface(flex);
            ui.initialize();
        });
    }
}   
