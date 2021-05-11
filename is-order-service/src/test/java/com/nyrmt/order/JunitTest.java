package com.nyrmt.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.LinkedHashSet;

@SpringBootTest
public class JunitTest {

    @Test
    public void run(){
        LinkedHashSet<String> objects = new LinkedHashSet<>();
        objects.add("read");
        objects.add("write");

        for (String s:objects) {
            System.out.println(s);
        }
    }
}
