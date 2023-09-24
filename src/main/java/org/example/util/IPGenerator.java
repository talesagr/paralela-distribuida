package org.example.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IPGenerator {
    private static List<String> generatedIPs = new ArrayList<>();
    private static int nextIPSegment = 1;

    public static String generateIP(){
        if(nextIPSegment <= 255){
            String ip = "192.168.0."+nextIPSegment;
            nextIPSegment++;
            generatedIPs.add(ip);
            return ip;
        } else {
            throw new RuntimeException("Server cannot handle more than 255 connections");
        }
    }


}
