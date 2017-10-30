package note.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import jary.grpc.study.dto.PersonList;
import jary.grpc.study.dto.PingRequest;
import jary.grpc.study.dto.PingResponse;
import jary.grpc.study.dto.QueryParameter;
import jary.grpc.study.service.DemoServiceGrpc;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jary on 2017/10/30 0030.
 */
public class DemoServiceClient {
    private final ManagedChannel channel;
    private final DemoServiceGrpc.DemoServiceBlockingStub blockingStub;

    public DemoServiceClient(String host, int port) {
       // channel = NettyChannelBuilder.forAddress(host, port).negotiationType(NegotiationType.PLAINTEXT).build();
        channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true)
                .build();
        blockingStub = DemoServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void ping(String name) {
        try {
            System.out.println("Will try to ping " + name + " ...");
            PingRequest request = PingRequest.newBuilder().setIn(name).build();
            PingResponse response = blockingStub.ping(request);
            System.out.println("ping: " + response.getOut());
        } catch (RuntimeException e) {
            System.out.println("RPC failed:" + e.getMessage());
            return;
        }
    }

    public void getPersonList(QueryParameter parameter) {
        try {
            //System.out.println("Will try to getPersonList " + parameter + " ...");
            PersonList response = blockingStub.getPersonList(parameter);
            System.out.println("items count: " + response.getItemsCount());
//            for (Person p : response.getItemsList()) {
//                System.out.println(p);
//            }
        } catch (RuntimeException e) {
            System.out.println("RPC failed:" + e.getMessage());
            return;
        }
    }


    public static void main(String[] args) throws Exception {
        DemoServiceClient client = new DemoServiceClient("localhost", 50051);
        try {
            client.ping("a");

            int max = 100;
            Long start = System.currentTimeMillis();

            for (int i = 0; i < max; i++) {
                client.getPersonList(getParameter());
            }
            Long end = System.currentTimeMillis();
            Long elapse = end - start;
            int perform = Double.valueOf(max / (elapse / 1000d)).intValue();

            System.out.print("rgpc " + max + " 次NettyServer调用，耗时：" + elapse + "毫秒，平均" + perform + "次/秒");
        } finally {
            client.shutdown();
        }
    }

    private static QueryParameter getParameter() {
        return QueryParameter.newBuilder().setAgeStart(5).setAgeEnd(50).build();
    }
}
