package com.sfurors.tictactoe;

import com.sfurors.tictactoe.controller.GameController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TictactoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TictactoeApplication.class, args);
	}

}
