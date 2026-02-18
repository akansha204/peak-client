package com.akansha.peak_client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.akansha.peak_client.service.LeaderboardGrpcClient;

@SpringBootApplication
public class PeakClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeakClientApplication.class, args);
	}
	@Bean
	public CommandLineRunner test (LeaderboardGrpcClient grpcClient){
		return args -> {
//			Thread.sleep(2000);
//			grpcClient.Join("gooner");
//			Thread.sleep(1000);
//			grpcClient.Join("alpha-male");
			grpcClient.updateScore("alpha-male", 100);
			Thread.sleep(1000);
			grpcClient.updateScore("gooner", 500);
		};
	}

}
