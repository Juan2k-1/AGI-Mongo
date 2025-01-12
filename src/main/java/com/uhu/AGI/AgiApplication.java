package com.uhu.AGI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.uhu.AGI")
public class AgiApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(AgiApplication.class, args);
    }

}
