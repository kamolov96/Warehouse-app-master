package uz.pdp.warehouseapp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class Test {
    public static void main(String[] args) {
       Duration duration=Duration.between(LocalDateTime.now().plusMinutes(6),LocalDateTime.now().plusMinutes(5));
        System.out.println(duration.getSeconds());
    }
}
