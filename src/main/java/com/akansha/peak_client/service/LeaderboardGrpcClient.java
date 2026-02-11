package com.akansha.peak_client.service;

import com.akansha.peak.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class LeaderboardGrpcClient {
    private ManagedChannel channel;
    private LeaderboardServiceGrpc.LeaderboardServiceStub asyncStub;
    private StreamObserver<ClientEvent> requestObserver;

    @PostConstruct
    public void start(){
        //creates channel
        channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        // create async stub
        asyncStub = LeaderboardServiceGrpc.newStub(channel);

        //opens the bidi stream
        requestObserver = asyncStub.streamLeaderboard(new StreamObserver<ServerEvent>() {
            @Override
            public void onNext(ServerEvent serverEvent) {
                System.out.println("Received Server Event");
                System.out.println(serverEvent);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Stream error: "+ throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream Completed");
            }
        });
    }
    private void sendJoin(String userId){
        ClientEvent joinEvent = ClientEvent.newBuilder()
                .setJoin(
                        JoinLeaderboard.newBuilder()
                                .setUserId(userId)
                                .build()
                )
                .build();
        requestObserver.onNext(joinEvent);
    }
    public void updateScore(String userId, long score){
        ClientEvent scoreEvent = ClientEvent.newBuilder()
                .setScoreUpdate(
                        ScoreUpdate.newBuilder()
                                .setUserId(userId)
                                .setScore(score)
                                .build()
                )
                .build();
        requestObserver.onNext(scoreEvent);
    }

    @PreDestroy
    public void shutdown(){
        if(requestObserver != null){
            requestObserver.onCompleted();
        }
        if(channel != null){
            channel.shutdown();
        }
    }
}
