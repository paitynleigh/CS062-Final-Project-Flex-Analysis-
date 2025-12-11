import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.io.*;
import java.util.Arrays;


public class Flex {
    private FrequencyData freqData;
    private TimeData timeData;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm");
    private HashSet<String> totalLocations;

    public Flex(){
        freqData = new FrequencyData();
        timeData = new TimeData();
        totalLocations = new HashSet<String>();
    }

    public void rowParse(String line){
        //Split cols in csv
        String[] cols = line.split(",");

        //Parse each row into column objects
        String date = cols[0].trim();
        String location = cols[1].trim();
        double amount = Double.parseDouble(cols[3].trim());
        String school = cols[8].trim();

        //Format Date Correctly
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        // Clean up names
        // Sagehen?
        if (location.equals("the Cafe")) {
            location = "The Cafe (Mudd)";
        } if (location.equals("Sagehen")) {
            location = "Cafe 47";
        }


        // Filter for only locations of interest: no dining halls, enterprise, data with not enough entries
        // To-do: filter for how many entries need to be in the dataset for us to include?
        if (!location.contains("Enterprise Svcs") &&
            !location.contains("Malott Commons") &&
            !location.contains("Collins Dining Hall") &&
            !location.contains("Hoch - Shanahan") &&
            !location.contains("McConnell") &&
            !location.contains("Frank") &&
            !location.contains("Oldenborg") &&
            !location.contains("Frary") &&
            !location.contains("Pitzer Bernard Cafe") &&
            !location.contains("Scripps Store")) {

            // add location (only is new because HashSet doesn't allow for duplicate values) into the HashSet
            totalLocations.add(location);

            //Create Transaction
            Transaction t = new Transaction(dateTime, amount, location, school);

            //--- Frequency/School/Location (Flex Overview)---//
            freqData.add(t);

            //--- Day-Time Analysis (Feature 1)
            timeData.add(t);

        }

        
    }

    public void loadCSV(String filename){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;

            //read first line 
            br.readLine();

            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    rowParse(line);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /* 
    * Getter Methods for data
    */

    public FrequencyData getFreqData(){
        return freqData;
    }
    public TimeData getTimeData(){
        return timeData;
    }

    public HashSet<String> getHashSet(){
        return totalLocations;
    }
    public String[] getAllLocations(){
        String[] arrayLocations = new String[totalLocations.size()];
        int counter = 0;
        for(String location : totalLocations){
            System.out.println(location);
            arrayLocations[counter] = location;
            counter++;
        }
        return arrayLocations;
    }

    public static void main(String[] args) {
        // tester
        Flex flex = new Flex();
        flex.loadCSV("Data/Stored_Value_Transaction_by_Customer__11_39_2025-10-17_11_40_52(Stored_Value_Transaction_by_Cus).csv");
        System.out.println(flex.getHashSet().toString());
        System.out.println(Arrays.toString(flex.getAllLocations()));
    }
}

