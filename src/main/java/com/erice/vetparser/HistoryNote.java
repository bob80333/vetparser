package com.erice.vetparser;

import java.util.Arrays;

public class HistoryNote {

    public int chunkNum;
    public int clientNum;
    public int petNum;
    public String date;
    public int unknown;
    public int id1;
    public int id2;
    public String text;

    public static HistoryNote parseHistoryNote(byte[] data) {
        HistoryNote historyNote = new HistoryNote();

        historyNote.chunkNum = data[0] & 0xFF;

        historyNote.clientNum = ((data[1] & 0xFF) + ((data[2] & 0xFF) << 8));

        historyNote.petNum = ((data[3] & 0xFF) + ((data[4] & 0xFF) << 8));

        byte[] dateOffset = Arrays.copyOfRange(data, 5, 7);
        historyNote.date = Utils.bytesToHex(dateOffset);

        historyNote.unknown = data[8] & 0xFF;

        historyNote.id1 = data[9] & 0xFF;

        historyNote.id2 = data[10] & 0xFF;

        byte[] textData = Arrays.copyOfRange(data, 12, 12 + (data[11] & 0xFF));
        historyNote.text = new String(textData);

        return historyNote;
    }

    @Override
    public String toString() {
        return chunkNum + "|" + clientNum + "|" + petNum + "|" + date + "|" + unknown + "|" + id1 + "|" + id2 + "|" + text;
    }
}
