package ru.momentum.finstrument;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.momentum.finstrument.core.RController;

@SpringBootApplication
public class FinstrumentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinstrumentApplication.class, args);
	}


//	public static void main(String[] args){
//		RController RController = new RController();
//		RController.compute();
//	}
}
