# Cloud_Assignment1
# YouTube Channel Analytics Using Hadoop MapReduce

## Project Overview
This project analyzes the YouTube Channels Dataset (575,609 rows, 27 columns, 170.72 MB) to calculate the top 50 channels by average subscriber count using Hadoop MapReduce. The solution is implemented in Java and leverages Hadoop's distributed processing capabilities.

## Prerequisites
- Java JDK 8 or higher
- Hadoop 3.3.0 installed locally
- Required libraries in classpath:
  - Apache Commons CSV (e.g., commons-csv-1.9.0.jar)
  - Apache POI (e.g., poi-5.2.3.jar)
  - Log4j-core (e.g., log4j-core-2.17.1.jar) to resolve logging errors

## Setup Instructions
1. Clone the repository: `git clone https://github.com/Jathusan19/Cloud_Assignment1`
2. Navigate to the project directory: `cd Cloud_Assignment1`
3. Compile the code: `javac -cp "hadoop-common-3.3.0.jar:hadoop-mapreduce-client-core-3.3.0.jar:commons-csv-1.9.0.jar:poi-5.2.3.jar:log4j-core-2.17.1.jar" -d target src/main/java/org/example/*.java`
4. Package the JAR: `jar cvf CloudAssignment-1.0-SNAPSHOT.jar -C target/ .`
5. Copy the dataset to HDFS: `hdfs dfs -put input/YouTubeDataset.csv /user/input`
6. Run the job: `hadoop jar CloudAssignment-1.0-SNAPSHOT.jar org.example.Main /user/input/YouTubeDataset.csv /user/output`
7. View results: `hdfs dfs -cat /user/output/reducer1_output.xlsx` or check the local file.

## Notes
- Replace file paths (e.g., /user/input) with your HDFS configuration.
- Ensure all JAR files are in the classpath and match the versions listed.
- The dataset sample (YouTubeDataset_sample.csv) and source code are included in the repository.
- Execution time was approximately 10 minutes on a local setup (adjust based on your system).

## Submission Files
- `README.md`: This file
- `Main.java` and `ViewsPerSubscriberReducer.java`: Source code
- `YouTubeDataset_sample.csv`: Dataset sample
- `CloudAssignment1.pdf`: Project report
- Screenshots: Evidence of Hadoop setup and execution
