import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;


public class Flex {
    private FrequencyData freqData;
    private TimeData timeData;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm");

    public Flex(){
        freqData = new FrequencyData();
        timeData = new TimeData();
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
            location = "the Cafe (Mudd)";
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
            !location.contains("Pitzer Bernard Cafe")) {

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
}

