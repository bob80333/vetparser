package com.erice.vetparser;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ArgumentParserException {
        ArgumentParser parser = ArgumentParsers.newFor("vetparser").build()
                .description("Parse vet files into csv");

        parser.addArgument("--clientfile")
                .dest("client")
                .type(String.class)
                .help("filepath to the client file");
        parser.addArgument("--petfile")
                .dest("pet")
                .type(String.class)
                .help("filepath to the pet file");
        parser.addArgument("--historyfile")
                .dest("history")
                .type(String.class)
                .help("filepath to the history/notes file");

        Namespace res = parser.parseArgs(args);

        if (res.get("client") != null) {
            File clientFile = new File((String) res.get("client"));
            FileReader reader = new FileReader(clientFile);

            // skip first 394 bytes, empty in vss so....

            reader.skip(394); // skipping stuff

            ArrayList<char[]> clientsData = new ArrayList<>();

            // read all clients into array
            while (reader.ready()) {
                char[] clientData = new char[390]; // clients 390 bytes
                reader.read(clientData);
                clientsData.add(clientData);
            }

            reader.close();

            // turn clients into objects, parsing the data

            ArrayList<Client> clients = new ArrayList<>();
            for (char[] client : clientsData) {
                clients.add(Client.parseClient(client));
            }


            // output file as csv
            String columns = "id, type, name, address1, address2, zipcode, city, state, phone1, phone2, phone3, comment, status1, status2, status3";

            File createdFile = new File(clientFile.getParent() + "/converted_client.csv");
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

        if (res.get("pet") != null) {
            File petFile = new File((String) res.get("pet"));
            FileInputStream reader = new FileInputStream(petFile);


            // skip first 295 bytes

            System.out.println(reader.skip(295));

            ArrayList<byte[]> petsData = new ArrayList<>();

            // read all pet data into array
            while (reader.available() > 0) {
                byte[] petData = new byte[291]; // pets 291 bytes
                reader.read(petData);
                petsData.add(petData);
            }

            reader.close();

            // turn pets into objects, parsing the data

            ArrayList<Pet> pets = new ArrayList<>();
            for (byte[] pet : petsData) {
                pets.add(Pet.parsePet(pet));
            }

            // output file as csv

            String columns = "ownerId|petId|name|breed|color|comment|refVet1|refVet2|primaryDoctorId|lastVisit|age|" +
                    "DOB|firstVisit|weight|sex|active";

            File createdFile = new File(petFile.getParent() + "/converted_pet.csv");
            createdFile.createNewFile();

            FileWriter writer = new FileWriter(createdFile);
            writer.write(columns + "\n");
            writer.flush();

            for (Pet pet : pets) {
                writer.write(pet.toString() + "\n");
                writer.flush();
            }

            writer.flush();
            writer.close();


        }

        if (res.get("history") != null) {
            File petFile = new File((String) res.get("history"));
            FileInputStream reader = new FileInputStream(petFile);


            // skip first 86 bytes

            System.out.println(reader.skip(86));

            ArrayList<byte[]> historiesData = new ArrayList<>();

            // read all pet data into array
            while (reader.available() > 0) {
                byte[] historyData = new byte[82]; // pets 82 bytes
                reader.read(historyData);
                historiesData.add(historyData);
            }

            reader.close();

            // turn history and notes into objects, parsing the data

            ArrayList<HistoryNote> historyNotes = new ArrayList<>();
            for (byte[] historyNote : historiesData) {
                historyNotes.add(HistoryNote.parseHistoryNote(historyNote));
            }

            // Dedup pet data:

            // petid, First HistoryNote
            HashMap<Integer, HistoryNote> map = new HashMap<>();

            for (HistoryNote historyNote : historyNotes) {
                if (map.containsKey(historyNote.petNum)) {
                    HistoryNote firstNote = map.get(historyNote.petNum);
                    if (historyNote.id1 != firstNote.id1 || historyNote.id2 != firstNote.id2) {
                        firstNote.text += "[" + firstNote.id1 + "-" + firstNote.id2 + "] ~ ";
                        firstNote.id1 = historyNote.id1;
                        firstNote.id2 = historyNote.id2;
                    }
                    firstNote.text += historyNote.text;
                } else {
                    map.put(historyNote.petNum, historyNote);
                }
            }

            // get hashmap historynotes

            historyNotes = new ArrayList<>(map.values());

            // output file as csv

            String columns = "chunkId|clientId|petId|date|unknown|id1|id2|text";

            File createdFile = new File(petFile.getParent() + "/converted_histories_notes.csv");
            createdFile.createNewFile();

            FileWriter writer = new FileWriter(createdFile);
            writer.write(columns + "\n");
            writer.flush();

            for (HistoryNote historyNote : historyNotes) {
                writer.write(historyNote.toString() + "\n");
                writer.flush();
            }

            writer.flush();
            writer.close();


        }


    }
}
