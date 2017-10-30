package note.grpc;


import io.grpc.stub.StreamObserver;
import jary.grpc.study.dto.*;
import jary.grpc.study.service.DemoServiceGrpc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jary on 2017/10/27 0027.
 */
public class DemoServiceImpl extends DemoServiceGrpc.DemoServiceImplBase {
    @Override
    public void ping(PingRequest pingRequest, StreamObserver<PingResponse> streamObserver) {
        super.ping(pingRequest, streamObserver);
        PingResponse reply = PingResponse.newBuilder().setOut("pong => " + pingRequest.getIn()).build();
        streamObserver.onNext(reply);
        streamObserver.onCompleted();

    }

    @Override
    public void getPersonList(QueryParameter queryParameter, StreamObserver<PersonList> responseObserver) {
        super.getPersonList(queryParameter, responseObserver);
        System.out.println(queryParameter.getAgeStart() + "-" + queryParameter.getAgeEnd());

        PersonList.Builder personListBuilder = PersonList.newBuilder();
        Person.Builder builder = Person.newBuilder();
        List<Person> list = new ArrayList<Person>();
        for (short i = 0; i < 10; i++) {
            list.add(builder.setAge(i).setChildrenCount(i).setName("test" + i).setSex(true).build());
        }
        personListBuilder.addAllItems(list);
        responseObserver.onNext(personListBuilder.build());
        responseObserver.onCompleted();
    }
}
