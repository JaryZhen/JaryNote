package com.note.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

  public static void main(String[] args) throws Exception {
    // set up the streaming execution environment
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    /*
     * Here, you can start creating your execution plan for Flink.
     *
     * Start with getting some data from the environment, like
     * 	env.readTextFile(textPath);
     *
     * then, transform the resulting DataStream<String> using operations
     * like
     * 	.filter()
     * 	.flatMap()
     * 	.join()
     * 	.coGroup()
     *
     * and many more.
     * Have a look at the programming guide for the Java API:
     *
     * http://flink.apache.org/docs/latest/apis/streaming/index.html
     *
     */

    // execute program
    env.execute("Flink Streaming Java API Skeleton");
  }
}
