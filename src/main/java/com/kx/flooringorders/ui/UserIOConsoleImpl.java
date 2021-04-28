/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author khuxi
 */
public class UserIOConsoleImpl implements FlooringOrdersUserIO{

    private ArrayList<Integer> quizScores = new ArrayList<>();
    private double scoreAverage;

    private Scanner sc = new Scanner(System.in);

    public void print(String message) {
        System.out.println(message);
    }

    
    public void print(String message, BigDecimal c) {
        message += c.toString();
        System.out.println(message);
    };

    
    public double readDouble(String prompt) {
        print(prompt);
        String temp = sc.nextLine();
        double x;
        boolean isTrue = false;
        while (!isTrue) {
            try {
                x = Double.parseDouble(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Numbers only beyond this point, enter a double");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        x = Double.parseDouble(temp);
        return x;
    }

    ;
        
    public double readDouble(String prompt, double min, double max) {
        print(prompt);
        String temp = sc.nextLine();
        double x;
        boolean isTrue = false;
        while (!isTrue) {
            try {
                x = Double.parseDouble(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                print("Error: Numbers only beyond this point, enter a double between 1 and 11");
                temp = sc.nextLine();
                isTrue = false;
                continue;
            }
            if (x < min || x > max) {
                print("Error: Numbers only beyond this point, enter a double between 1 and 11");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        x = Double.parseDouble(temp);
        return x;
    }

    ;

    public float readFloat(String prompt) {
        print(prompt);
        String temp = sc.nextLine();
        float x;
        boolean isTrue = false;
        while (!isTrue) {
            try {
                x = Float.parseFloat(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Numbers only beyond this point, enter a float");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        x = Float.parseFloat(temp);
        return x;
    }

    ;

    public float readFloat(String prompt, float min, float max) {
        print(prompt);
        String temp = sc.nextLine();
        float x;
        boolean isTrue = false;
        while (!isTrue) {
            try {
                x = Float.parseFloat(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                print("Error: Numbers only beyond this point, enter a float between 1 and 11");
                temp = sc.nextLine();
                isTrue = false;
                continue;
            }
            if (x < min || x > max) {
                print("Error: Numbers only beyond this point, enter a float between 1 and 11");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        x = Float.parseFloat(temp);
        return x;
    }

    ;

    public int readInt(String prompt) {
        print(prompt);
        String temp = sc.nextLine();
        boolean isTrue = false;
        int x;
        while (!isTrue) {
            try {
                x = Integer.parseInt(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Numbers only beyond this point, enter a int");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        x = Integer.parseInt(temp);
        return x;
    }

        public String readLocalDate(String promptY, String promptM, String promptD) {
        print(promptY);
        String localDate = "";
        String temp = sc.nextLine();
        String[] strings = new String[3];
        boolean isTrue = false;
        int x;
        int year;
        while (!isTrue) {
            try {
                x = Integer.parseInt(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Numbers only beyond this point, enter a int");
                temp = sc.nextLine();
                isTrue = false;
                continue;
            }
            if (x < 1 || x > 2020) {
                print("Error: Enter a valid year between 1 and 2020");
                temp = sc.nextLine();
                isTrue = false;
                continue;
            }
            for (int i = 0; temp.length() < 4; i++) {
                temp = "0" + temp;
            }
        }
        year = Integer.parseInt(temp);
        strings[2] =temp;

        print(promptM);
        temp = sc.nextLine();
        isTrue = false;
        while (!isTrue) {
            try {
                x = Integer.parseInt(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                print("Error: Numbers only beyond this point, enter a number [1-12]");
                temp = sc.nextLine();
                isTrue = false;
                continue;
            }
            if (x < 1 || x > 12) {
                print("Error: Enter a valid Month number");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        x = Integer.parseInt(temp);
        temp += "/";
        strings[0] = temp;

        print(promptD);
        temp = sc.nextLine();
        isTrue = false;
        int[] monthDays = new int[12];
        monthDays[0] = 31;
        if (year % 4 == 0) {
            monthDays[1] = 29;
        } else {
            monthDays[1] = 28;
        }

        monthDays[2] = 31;
        monthDays[3] = 30;
        monthDays[4] = 31;
        monthDays[5] = 30;
        monthDays[6] = 31;
        monthDays[7] = 31;
        monthDays[8] = 30;
        monthDays[9] = 31;
        monthDays[10] = 30;
        monthDays[11] = 31;
        int m = x;
        while (!isTrue) {
            try {
                x = Integer.parseInt(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Numbers only beyond this point, enter a int");
                temp = sc.nextLine();
                isTrue = false;
                continue;
            }
            int y = monthDays[m - 1];
            if (x < 1 || x > monthDays[m - 1]) {
                print("Error: Enter a valid day for your month");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        temp += "/";
        strings[1] = temp;
        for(String s : strings){
            localDate += s;
        }
        return localDate;
    }

    public int readInt(String prompt, int min, int max) {
        print(prompt);
        String temp = sc.nextLine();
        boolean isTrue = false;
        int x;
        while (!isTrue) {
            try {
                x = Integer.parseInt(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                print("Error: Numbers only beyond this point, enter a int between 1 and 8");
                temp = sc.nextLine();
                isTrue = false;
                continue;
            }
            if (x < min || x > max) {
                print("Error: Numbers only beyond this point, enter a int between 1 and 8");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        x = Integer.parseInt(temp);
        return x;
    }

    ;

    public long readLong(String prompt) {
        print(prompt);
        String temp = sc.nextLine();
        long x;
        boolean isTrue = false;
        while (!isTrue) {
            try {
                x = Long.parseLong(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Numbers only beyond this point, enter a long");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        x = Long.parseLong(temp);
        return x;
    }

    ;

    public long readLong(String prompt, long min, long max) {
        print(prompt);
        String temp = sc.nextLine();
        long x;
        boolean isTrue = false;
        while (!isTrue) {
            try {
                x = Long.parseLong(temp);
                isTrue = true;
            } catch (NumberFormatException e) {
                print("Error: Numbers only beyond this point, enter a long between 1 and 11");
                temp = sc.nextLine();
                isTrue = false;
                continue;
            }
            if (x < min || x > max) {
                print("Error: Numbers only beyond this point, enter a long between 1 and 11");
                temp = sc.nextLine();
                isTrue = false;
            }
        }
        x = Long.parseLong(temp);
        return x;
    }

    ;

    public String readString(String prompt) {
        print(prompt);
        String s;
        s = sc.nextLine();
            if (s.contains(",")) {
                s = s.replace(",", "");
            }
        return s;
    }

    ;
    
    public String readStringBDM(String prompt) {
        print(prompt);
        String s;
        s = sc.nextLine();
        boolean isTrue = false;
        double x;
        int y;
        while (!isTrue) {
            if (s.equals("")) {
                s = ".00";
                isTrue = true;
            } else {
                try {
                    x = Double.parseDouble(s);
                    isTrue = true;
                } catch (NumberFormatException e) {
                    print("Error: Must enter Area(SQR FT) greater than zero.");
                    s = sc.nextLine();
                    isTrue = false;
                    continue;
                }
                if (x <= 0) {
                    print("Error: Must enter Area(SQR FT) greater than zero.");
                    s = sc.nextLine();
                    isTrue = false;
                    continue;
                }

                try {
                    y = Integer.parseInt(s);
                    s += ".00";
                } catch (NumberFormatException e) {
                    String[] strArray = s.split("\\.");
                    if (strArray[1].length() != 2) {
                        print("Please enter Area in acceptable format, Whole or Two-Decimals.");
                        s = sc.nextLine();
                        isTrue = false;
                        continue;
                    }
                }
            }
        }
        return s;
    }

    ;
    
    public String readStringRating(String prompt) {
        print(prompt);
        String s;
        s = sc.nextLine();
        while ((s.replaceAll("\\*", "")).length() > 0 || (s.length() > 5) || (s.length() < 1)) {
            print("Error: Use any number of " + "* " + "between 1 and 5 for user rating");
            s = sc.nextLine();
        }
        return s;
    }
;
}
