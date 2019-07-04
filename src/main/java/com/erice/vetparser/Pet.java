package com.erice.vetparser;

import javax.rmi.CORBA.Util;
import java.util.Arrays;

public class Pet {

    // 291 bytes long

    // this is the program's options
    enum Gender {
        Male, Female, MaleNeutered, FemaleSpayed, Unknown
    }

    enum Species {
        Canine, Feline, Equine, Bovine, Avian, Other
    }

    public int ownerId;
    public int petId;
    public String name;
    public String breed;
    public String birthdayOffset;
    public String color;
    public Species species;
    public Gender gender;
    public String comment;
    public String firstVisitOffset;
    public String lastVisitOffset;
    public String active;
    public String weight; // hex string, somehow both pounds and kilograms are stored in 12 bytes
    public String refVet1;
    public String refVet2;
    public String unknown1;
    public int primaryDoctorId;
    public String unknown2;

    public static Pet parsePet(byte[] data) {
        Pet pet = new Pet();

        pet.ownerId = ((data[0] & 0xFF) + ((data[1] & 0xFF) << 8));
        pet.petId = ((data[2] & 0xFF) + ((data[3] & 0xFF) << 8));


        byte[] nameArr = Arrays.copyOfRange(data, 5, 5 + (data[4] & 0xFF));
        pet.name = new String(nameArr);

        byte species = data[19];

        switch (species) {
            case 0x0:
                pet.species = Species.Canine;
                break;
            case 0x1:
                pet.species = Species.Feline;
                break;
            case 0x2:
                pet.species = Species.Equine;
                break;
            case 0x3:
                pet.species = Species.Bovine;
                break;
            case 0x4:
                pet.species = Species.Avian;
                break;
            case 0x5:
                pet.species = Species.Other;
                break;

        }

        byte[] breedArr = Arrays.copyOfRange(data, 21, 21 + (data[20] & 0xFF));
        pet.breed = new String(breedArr);

        byte[] bdayOffset = Arrays.copyOfRange(data, 33, 35);
        pet.birthdayOffset = Utils.bytesToHex(bdayOffset);


        byte[] colorArr = Arrays.copyOfRange(data, 36, 36 + (data[35] & 0xFF));
        pet.color = new String(colorArr);

        byte gender = data[50];

        switch (gender) {
            case 0x0:
                pet.gender = Gender.Male;
                break;
            case 0x1:
                pet.gender = Gender.Female;
                break;
            case 0x2:
                pet.gender = Gender.MaleNeutered;
                break;
            case 0x3:
                pet.gender = Gender.FemaleSpayed;
                break;
            case 0x4:
                pet.gender = Gender.Unknown;
                break;
        }

        pet.active = data[42] > 0 ? "Yes" : "No";

        byte[] firstVisitOffset = Arrays.copyOfRange(data, 60, 62);
        pet.firstVisitOffset = Utils.bytesToHex(firstVisitOffset);

        byte[] refVet1Arr = Arrays.copyOfRange(data, 66, 66 + (data[65] & 0xFF));
        pet.refVet1 = new String(refVet1Arr);

        byte[] refVet2Arr = Arrays.copyOfRange(data, 92, 92 + (data[91] & 0xFF));
        pet.refVet2 = new String(refVet2Arr);

        byte[] commentArr = Arrays.copyOfRange(data, 118, 118 + (data[117] & 0xFF));
        pet.comment = new String(commentArr);

        byte[] weightData = Arrays.copyOfRange(data, 173, 185);
        pet.weight = Utils.bytesToHex(weightData);

        byte[] lastVisitOffset = Arrays.copyOfRange(data, 210, 212);
        pet.lastVisitOffset = Utils.bytesToHex(lastVisitOffset);


        return pet;
    }

    @Override
    public String toString() {
        // ownerId petId name species gender active breed color birthdayOffset comment refVet1 refVet2 primaryDoctorId firstVisitOffset lastVisitOffset weight
        return
                ownerId + "|" + petId + "|" + name + "|" + species + "|" + gender + "|" + active + "|" + breed + "|" +
                        color + "|" + birthdayOffset + "|" + comment + "|" + refVet1 + "|" + refVet2 + "|" +
                        primaryDoctorId + "|" + firstVisitOffset + "|" + lastVisitOffset + "|" + weight;
    }
}