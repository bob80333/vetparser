package com.erice.vetparser;

import java.util.Arrays;

public class Client {

    // 390 bytes long
    enum ClientType {
        Client, RefVet, Other, Hospital
    }

    // these are the only options in the original program
    enum Gender {
        He, She, They
    }

    public int id;
    public ClientType type;
    public String name;
    public String address1;
    public String address2;
    public String zipcode;
    public String city;
    public String state;
    public String phone1;
    public String phone2;
    public String phone3;
    public String comment;
    public Gender gender;
    public String unknown1;
    public int lastInDays;
    public String unknown2;
    public String status1;
    public String status2;
    public String status3;
    public String unknown3;

    public static Client parseClient(byte[] data) {
        Client client = new Client();
        client.id = ((data[0] & 0xFF) + ((data[1] & 0xFF) << 8));
        if (data[2] == 0x0) {
            client.type = ClientType.Client;
        } else if (data[2] == 0x1) {
            client.type = ClientType.RefVet;
        } else if (data[2] == 0x2) {
            client.type = ClientType.Other;
        } else {
            client.type = ClientType.Hospital;
        }

        // offsets are in excel file
        byte[] nameArr = Arrays.copyOfRange(data, 4, 4 + (data[3] & 0xFF));
        client.name = new String(nameArr);

        byte[] addr1Arr = Arrays.copyOfRange(data, 30, 30 + (data[29] & 0xFF));
        client.address1 = new String(addr1Arr);

        byte[] addr2Arr = Arrays.copyOfRange(data, 67, 67 + (data[66] & 0xFF));
        client.address2 = new String(addr2Arr);

        byte[] zipcode = Arrays.copyOfRange(data, 104, 109);
        client.zipcode = new String(zipcode);

        byte[] city = Arrays.copyOfRange(data, 110, 110 + (data[109] & 0xFF));
        client.city = new String(city);

        byte[] state = Arrays.copyOfRange(data, 126, 128);
        client.state = new String(state);

        byte[] phone = Arrays.copyOfRange(data, 129, 134);
        String phoneS = new String(phone);
        phone = Arrays.copyOfRange(data, 135, 143);
        phoneS += new String(phone);

        client.phone1 = phoneS;

        phone = Arrays.copyOfRange(data, 144, 149);
        phoneS = new String(phone);
        phone = Arrays.copyOfRange(data, 150, 158);
        phoneS += new String(phone);

        client.phone2 = phoneS;

        byte[] comments = Arrays.copyOfRange(data, 159, 159 + (data[158] & 0xFF));
        client.comment = new String(comments);

        byte gender = data[198];

        switch (gender) {
            case 0x0:
                client.gender = Gender.He;
                break;
            case 0x1:
                client.gender = Gender.She;
                break;
            case 0x2:
                client.gender = Gender.They;
                break;
        }

        byte[] unknown = Arrays.copyOfRange(data, 199, 218);
        client.unknown1 = new String(unknown);

        byte[] date = Arrays.copyOfRange(data, 219, 221);
        client.lastInDays = Utils.calculateDays(Utils.convertByteToInt(date));

        unknown = Arrays.copyOfRange(data, 221, 231);
        client.unknown2 = new String(unknown);

        byte[] status = Arrays.copyOfRange(data, 232, 246);
        client.status1 = new String(status);

        status = Arrays.copyOfRange(data, 247, 261);
        client.status2 = new String(status);

        status = Arrays.copyOfRange(data, 262, 276);
        client.status3 = new String(status);

        unknown = Arrays.copyOfRange(data, 276, 351);
        client.unknown3 = new String(unknown);

        phone = Arrays.copyOfRange(data, 352, 357);
        phoneS = new String(phone);
        phone = Arrays.copyOfRange(data, 358, 366);
        phoneS += new String(phone);

        client.phone3 = phoneS;

        return client;
    }

    @Override
    public String toString() {
        return id + "|" + type.toString() + "|" + name + "|" + address1 + "|" + address2 + "|" + zipcode + "|" + city
                + "|" + state + "|" + phone1 + "|" + phone2 + "|" + phone3 + "|" + comment + "|" + status1 + "|"
                + status2 + "|" + status3 + "|" + gender + "|" + lastInDays;
    }
}
