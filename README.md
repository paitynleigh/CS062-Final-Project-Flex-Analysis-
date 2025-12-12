# CS062-Final-Project-Flex-Analysis-

This software is intended to be used to explore activity trends at on-campus dining and grab and go snack options at the Claremont Colleges, based on flex transaction data. Through a Graphical User Interface, a user will be able to access three features that can help them determine where the best location is to get a meal on a given day or time. 

The first feature, Chart, allows users to select a vendor and day of the week and a histogram with the number of transactions per 15 minute interval on that day populates when the "Generate" button is clicked:

![Feature 1 Screenshot](chart.png)

Note that if no new chart is generated for a particular selection, this means that the selected vendor is closed for the entirety of the selected day.

The second feature, Overview, allows users to select a food vendor and access a summary which includes the least busy day on average,
most busy day on average, average transaction price, total retail over 6 weeks, as well as the average transaction price and total dollar
amount spent per school:

![Feature 2 Screenshot](overview.png)

Finally, the third feature, Least Busy, allows users to choose a specific day and time (with a default of the current time), and generates the selected number of least busy locations in the time interval of the given time on the given day:

![Feature 3 Screenshot](least.png)

## Running the Code
In order to interact with the features of the project, users should run the TestFlex class, which contains the main method. This will initiate the GUI, which can be navigated using labeled buttons for the features.

## API Instructions
Instructions on how to call public methods
That is, you are now writing an API in your README. This could be the same thing as your JavaDocs: What is the method called? What are its inputs and outputs? What is a verbal description of what it does? Don’t forget about constructors!
For each of your public methods, also include usage examples. What do expected inputs and outputs look like in practice? For example, a usage example for add(x,y) would be add(2, 3) = 5.

notes:


"Chart" feature: if nothing new generates, it means the vendor you selected is closed on the selected day.