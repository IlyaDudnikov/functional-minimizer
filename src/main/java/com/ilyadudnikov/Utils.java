package com.ilyadudnikov;

import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Utils {
    public static void readFilePoints(String fileName, List<IVector> points, List<Double> funcPoints) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int nPoints = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < nPoints; i++) {
                String[] str = br.readLine().split(" ");

                IVector tmp1 = new Vector();
                tmp1.add(Double.parseDouble(str[0]));

                points.add(tmp1);
                funcPoints.add(Double.parseDouble(str[1]));
            }
        } catch (Exception e) {
            System.out.println("The file could not be read:");
            System.out.println(e.getMessage());
        }
    }

    public static void readFileParams(String fileName, IVector initial, IVector minimum, IVector maximum) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String[] str = br.readLine().split(" ");
            for (String s : str) {
                initial.add(Double.parseDouble(s));
            }

            str = br.readLine().split(" ");
            for (String s : str) {
                minimum.add(Double.parseDouble(s));
            }

            str = br.readLine().split(" ");
            for (String s : str) {
                maximum.add(Double.parseDouble(s));
            }

        } catch (Exception e) {
            System.out.println("The file could not be read:");
            System.out.println(e.getMessage());
        }
    }
}
