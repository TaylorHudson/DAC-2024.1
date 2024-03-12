package br.edu.ifpb.dac.util.reader;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("scanner")
public class ScannerDataReader implements DataReader {
    @Override
    public String read(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}
