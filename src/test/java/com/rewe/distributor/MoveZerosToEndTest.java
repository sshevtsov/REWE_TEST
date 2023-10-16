package com.rewe.distributor;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//You are given a list of integers.
//Your task is to create a method that shifts all zeros
//in the list to the end while maintaining the order of the non-zero elements.
public class MoveZerosToEndTest {


    @Test
    public void moveZerosToEndTest() {
        List<Integer> inputList1 = new ArrayList<>(List.of(0, 1, 0, 3, 12));
        List<Integer> expected1 = new ArrayList<>(List.of(1, 3, 12, 0, 0));
        moveZerosToEnd(inputList1);
        assertEquals(expected1, inputList1);
        System.out.println(inputList1);

        List<Integer> inputList2 = new ArrayList<>(List.of(0, 0, 0, 3, 0));
        List<Integer> expected2 = new ArrayList<>(List.of(3, 0, 0, 0, 0));
        moveZerosToEnd(inputList2);
        assertEquals(expected2, inputList2);
        System.out.println(inputList2);

        List<Integer> inputList3 = new ArrayList<>(List.of(0, 0, 0, 3, 12));
        List<Integer> expected3 = new ArrayList<>(List.of(3, 12, 0, 0, 0));
        moveZerosToEnd(inputList3);
        assertEquals(expected3, inputList3);
        System.out.println(inputList3);

        List<Integer> inputList4 = new ArrayList<>(List.of(1, 3, 0, 0, 0));
        List<Integer> expected4 = new ArrayList<>(List.of(1, 3, 0, 0, 0));
        moveZerosToEnd(inputList4);
        assertEquals(expected4, inputList4);
        System.out.println(inputList4);

        List<Integer> inputList5 = new ArrayList<>(List.of(1 , 0, 0, 0, 9));
        List<Integer> expected5 = new ArrayList<>(List.of(1, 9, 0, 0, 0));
        moveZerosToEnd(inputList5);
        assertEquals(expected5, inputList5);
        System.out.println(inputList5);
    }

    private static void moveZerosToEnd(List<Integer> inputList) {
        int zeroIndex = 0;
        for (int i = 0; i < inputList.size(); i++) {
            int current = inputList.get(i);
            if (current != 0) {
                Integer zero = inputList.get(zeroIndex);
                inputList.set(zeroIndex, current);
                inputList.set(i, zero);
                zeroIndex++;
            }
        }

    }
}