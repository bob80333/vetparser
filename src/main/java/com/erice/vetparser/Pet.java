package com.erice.vetparser;

import java.util.Arrays;

public class Pet {

    // 291 bytes long

    public int ownerId;
    public int petId;
    public String name;
    public String breed;
    public String color;
    public String comment;
    public String refVet1;
    public String refVet2;
    public String unknown1;
    public int primaryDoctorId;
    public String unknown2;

    public static Pet parsePet(byte[] data) {
        Pet pet = new Pet();

        pet.ownerId = ((data[0] & 0xFF) + ((data[1] & 0xFF) << 8));
        pet.petId = ((data[2] & 0xFF) + ((data[3] & 0xFF) << 8));


        byte[] nameArr = Arrays.copyOfRange(data, 5, 5 + data[4]);
        pet.name = new String(nameArr);

        byte[] breedArr = Arrays.copyOfRange(data, 21, 21 + data[20]);
        pet.breed = new String(breedArr);

        byte[] colorArr = Arrays.copyOfRange(data, 36, 36 + data[35]);
        pet.color = new String(colorArr);

        byte[] refVet1Arr = Arrays.copyOfRange(data, 66, 66 + data[65]);
        pet.refVet1 = new String(refVet1Arr);

        byte[] refVet2Arr = Arrays.copyOfRange(data, 92, 92 + data[91]);
        pet.refVet2 = new String(refVet2Arr);

        byte[] commentArr = Arrays.copyOfRange(data, 118, 118 + data[117]);
        pet.comment = new String(commentArr);


        return pet;
    }

    @Override
    public String toString() {
        return
                ownerId + "|" + petId + "|" + name + "|" + breed + "|" + color + "|" + comment + "|" + refVet1 +
                        "|" + refVet2 + "|" + unknown1 + "|" + primaryDoctorId + "|" + unknown2;
    }
}