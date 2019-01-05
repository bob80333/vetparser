package com.erice.vetparser;

import java.util.Arrays;

public class Client {

    // 390 bytes long
    enum ClientType {
        Client, RefVet, Other, Hospital
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
    public String unknown1;
    public String status1;
    public String status2;
    public String status3;
    public String unknown2;

    public static Client parseClient(char[] chars) {
        Client client = new Client();
        client.id = (chars[0] + (chars[1] << 2));
        if (chars[2] == 0x0) {
            client.type = ClientType.Client;
        } else if (chars[2] == 0x1) {
            client.type = ClientType.RefVet;
        } else if (chars[2] == 0x2) {
            client.type = ClientType.Other;
        } else {
            client.type = ClientType.Hospital;
        }

        // offsets are in excel file
        char[] nameArr = Arrays.copyOfRange(chars, 4, 4 + chars[3]);
        client.name = new String(nameArr);

        char[] addr1Arr = Arrays.copyOfRange(chars, 30, 30 + chars[29]);
        client.address1 = new String(addr1Arr);

        char[] addr2Arr = Arrays.copyOfRange(chars, 67, 67 + chars[66]);
        client.address2 = new String(addr2Arr);

        char[] zipcode = Arrays.copyOfRange(chars, 104, 109);
        client.zipcode = new String(zipcode);

        char[] city = Arrays.copyOfRange(chars, 110, 110 + chars[109]);
        client.city = new String(city);

        char[] state = Arrays.copyOfRange(chars, 126, 128);
        client.state = new String(state);

        char[] phone = Arrays.copyOfRange(chars, 129, 134);
        String phoneS = new String(phone);
        phone = Arrays.copyOfRange(chars, 135, 143);
        phoneS += new String(phone);

        client.phone1 = phoneS;

        phone = Arrays.copyOfRange(chars, 144, 149);
        phoneS = new String(phone);
        phone = Arrays.copyOfRange(chars, 150, 158);
        phoneS += new String(phone);

        client.phone2 = phoneS;

        char[] comments = Arrays.copyOfRange(chars, 159, 159 + chars[158]);
        client.comment = new String(comments);

        char[] unknown = Arrays.copyOfRange(chars, 198, 231);
        client.unknown1 = new String(unknown);

        char[] status = Arrays.copyOfRange(chars, 232, 246);
        client.status1 = new String(status);

        status = Arrays.copyOfRange(chars, 247, 261);
        client.status2 = new String(status);

        status = Arrays.copyOfRange(chars, 262, 276);
        client.status3 = new String(status);

        unknown = Arrays.copyOfRange(chars, 276, 351);
        client.unknown2 = new String(unknown);

        phone = Arrays.copyOfRange(chars, 352, 357);
        phoneS = new String(phone);
        phone = Arrays.copyOfRange(chars, 358, 366);
        phoneS += new String(phone);

        client.phone3 = phoneS;

        return client;
    }

    @Override
    public String toString() {
        return id + "," + type.toString() + "," + name + "," + address1 + "," + address2 + "," + zipcode + "," + city
                + "," + state + "," + phone1 + "," + phone2 + "," + phone3 + "," + comment + "," + status1 + ","
                + status2 + "," + status3;
    }
}
