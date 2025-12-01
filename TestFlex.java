public class TestFlex {
    public static void main(String[] args){
  Flex Flex = new Flex();

  Flex.loadCSV("Data/Stored_Value_Transaction_by_Customer__11_39_2025-10-17_11_40_52(Stored_Value_Transaction_by_Cus).csv");

  // Example: Print all transactions for Locations
  //System.out.println("Frequency Data");
  //System.out.println(Flex.getFreqData());

  // Example: Print number of transactions per each time Interval at all 
  // locations and Days
  System.out.println("Time/Interval Data");
  System.out.println(Flex.getTimeData());

  }
}   
