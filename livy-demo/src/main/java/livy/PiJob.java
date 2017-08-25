package livy;

import com.cloudera.livy.Job;
import com.cloudera.livy.JobContext;
import com.cloudera.livy.LivyClient;
import com.cloudera.livy.LivyClientBuilder;
import org.apache.spark.api.java.function.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PiJob implements Job<Double>, Function<Integer, Integer>,
        Function2<Integer, Integer, Integer> {

    private final int samples;

    public PiJob(int samples) {
        this.samples = samples;
    }

    @Override
    public Double call(JobContext ctx) throws Exception {
        List<Integer> sampleList = new ArrayList<Integer>();
        for (int i = 0; i < samples; i++) {
            sampleList.add(i + 1);
        }
        return 4.0d * ctx.sc().parallelize(sampleList).map(this).reduce(this) / samples;
    }

    @Override
    public Integer call(Integer v1) {
        double x = Math.random();
        double y = Math.random();
        return (x*x + y*y < 1) ? 1 : 0;
    }

    @Override
    public Integer call(Integer v1, Integer v2) {
        return v1 + v2;
    }

    public static void main(String[] args) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        LivyClient client = new LivyClientBuilder()
                .setURI(new URI("http://172.24.4.110:8998"))
                .build();

        try {

            System.err.printf("Uploading %s to the Spark context...\n", "f");
            client.uploadJar(new File("data/dsapp.jar")).get();

            System.err.printf("Running PiJob with %d samples...\n", 1);
            double pi = client.submit(new PiJob(30)).get();

            System.out.println("Pi is roughly: " + pi);
        } finally {
            client.stop(true);
        }
    }
}