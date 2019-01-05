package com.erice.vetparser;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(args[0]);

        FileReader reader = new FileReader(new File(args[0]));

        // skip first 394 bytes, empty in vss so....

        reader.skip(394); // skipping stuff

        ArrayList<char[]> clientschars = new ArrayList<>();

        // read all clients into array
        while(reader.ready()) {
            char[] cbuf = new char[390]; // clients 390 bytes
            reader.read(cbuf);
            clientschars.add(cbuf);
        }

        reader.close();

        // turn clients into objects

        ArrayList<Client> clients= new ArrayList<>();
        for (char[] client: clientschars) {
            clients.add(Client.parseClient(client));
        }


        // output file as csv
        String columns = "id, type, name, address1, address2, zipcode, city, state, phone1, phone2, phone3, comment, status1, status2, status3";

        File createdFile = new File(new File(args[0]).getParent() + "\\converted.csv");
        createdFile.createNewFile();

        FileWriter writer = new FileWriter(createdFile);
        writer.write(columns + "\n");
        writer.flush();

        for (Client client : clients) {
            writer.write(client.toString() + "\n");
            writer.flush();
        }

        writer.flush();
        writer.close();
    }
}
