package com.kotikface;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String,String> map = new LinkedHashMap<>();
        List<String> list = new ArrayList<>();
        Map<String,Integer> hardest = new HashMap<>();
        if(args.length==2) {
            if (args[0].equals("-import"))
                importCard(map, list, hardest, args[1]);
        } else if(args.length==4){
            if(args[0].equals("-import"))
                importCard(map, list, hardest, args[1]);
            else if(args[2].equals("-import"))
                importCard(map, list, hardest, args[3]);
        }
        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            list.add("Input the action (add, remove, import, export, ask, exit):");
            String a = scanner.nextLine();
            list.add(a);
            if("exit".equals(a)){
                break;
            }
            switch (a) {
                case "add":
                    addCard(map,scanner, list, hardest);
                    break;
                case "remove":
                    removeCard(map,scanner, list, hardest);
                    break;
                case "import":
                    importCard(map,scanner, list, hardest);
                    break;
                case "export":
                    exportCard(map,scanner, list, hardest);
                    break;
                case "ask":
                    askCard(map,scanner, list, hardest);
                    break;
                case "log":
                    logCards(list,scanner);
                    break;
                case "hardest card":
                    hardestCard(hardest, list);
                    break;
                case "reset stats":
                    resetStats(list, hardest);
                default:
                    break;
            }
        }
        System.out.println("Bye bye!");
        list.add("Bye bye!");
        if(args.length==2) {
            if (args[0].equals("-export"))
                exportCard(map, list, hardest, args[1]);
            } else if(args.length==4){
            if(args[0].equals("-export"))
                exportCard(map, list, hardest, args[1]);
            else if(args[2].equals("-export"))
                exportCard(map, list, hardest, args[3]);
        }
    }


    private static void resetStats(List<String> list,Map<String, Integer> hardest) {
        for (String a : hardest.keySet()) {
            hardest.put(a,0);
        }
        System.out.println("Card statistics has been reset.");
        list.add("Card statistics has been reset.");
    }

    private static void hardestCard(Map<String,Integer> hardest, List<String> list) {
        int max=-9999999;
        String hard = " ";
        StringBuilder hardCards1= new StringBuilder();
        int count=0;
        for (String a:hardest.keySet()) {
            if(hardest.get(a)>max){
                max=hardest.get(a);
                hard=a;

            }
        }
        for (String a :hardest.keySet()) {
            if(hardest.get(a)==max){
                hardCards1.append("\"" + a + "\"" + ", ");
                count++;
            }
        }

        if(max<0)
            max=0;
        if(max==0){
            System.out.println("There are no cards with errors.");
            list.add("There are no cards with errors.");
        } else if (count==1){
            System.out.println("The hardest card is \""+  hard + "\". You have " +  max+ " errors answering it.");
            list.add("The hardest card is " + hardCards1.substring(0,hardCards1.length()-2)+ ". You have " +  max+ " errors answering it.");
        } else {

            System.out.println("The hardest card is " + hardCards1.substring(0,hardCards1.length()-2) + ". You have " +  max+ " errors answering it.");
            list.add("The hardest cards " + hardCards1.substring(0,hardCards1.length()-2)+ " with " +  max+ " mistakes.");
        }


    }

    private static void logCards(List<String> list, Scanner scanner) {
        System.out.println("File name:");
        list.add("File name:");
        String filename = scanner.nextLine();
        list.add(filename);
        try(PrintWriter print = new PrintWriter(new File(filename))) {
            for (String a : list) {
                print.println(a);
            }
            System.out.println("The log has been saved.");
            list.add("The log has been saved.");
        }
        catch (IOException e){
            System.out.println("The log has been saved.");
            list.add("The log has been saved.");
        }
    }

    public static void addCard(Map<String,String> map, Scanner scanner,List<String> list, Map<String, Integer> hardest){
        System.out.println("The card:");
        list.add("The card");
        String a = scanner.nextLine();
        if(!hardest.containsKey(a))
            hardest.put(a,0);
        list.add(a);
        if (map.containsKey(a)){
            System.out.println("The card \"" + a + "\" already exists.");
            list.add("The card \" "+ a + "\" already exists.\"");
            return;}
        System.out.println("The definition of the card:");
        list.add("The definition of the card:");
        String b = scanner.nextLine();
        list.add(b);
        if (map.containsValue(b)) {
            System.out.println("The definition \"" + b + "\" already exists.");
            list.add("\"The definition \"" + b + "\" already exists.\"");
            return;
        } else {
            map.put(a,b);
            System.out.println("The pair (\""+a+"\":\""+ b+"\") has been added.");
            list.add("The pair (\""+a+"\":\""+ b+"\") has been added.");
        }
    }
    public static void removeCard(Map<String,String> map, Scanner scanner, List<String> list, Map<String,Integer> hardest){
        System.out.println("The card:");
        list.add("The card:");
        String a = scanner.nextLine();
        list.add(a);
        if (map.containsKey(a)){
            map.remove(a);
            hardest.put(a,0);
            System.out.println("The card has been removed.");
            list.add("The card has been removed.");
        }else {
            System.out.println("Can't remove \""+a+"\": there is no such card.");
            list.add("Can't remove \""+a+"\": there is no such card.");
        }


    }
    public static void  importCard(Map<String,String> map, List<String> list, Map<String, Integer> hardest, String args){

        String filename = args;
        list.add(filename);
        try( Scanner scanner1 = new Scanner(new File(filename))) {
            int kol=0;
            while (true){
                if(scanner1.hasNextLine()){
                    String flash = scanner1.nextLine();
                    map.put(flash,scanner1.nextLine());
                    int err = Integer.parseInt(scanner1.nextLine());
                    hardest.put(flash,err);
                    kol++;
                    if(!hardest.containsKey(flash)){
                        hardest.put(flash,0);
                    }
                }
                else
                    break;

            }
            System.out.println(kol + " cards have been loaded.");
            list.add(kol + " cards have been loaded.");
        }
        catch (FileNotFoundException e){
            System.out.println("File not found.");
            list.add("File not found");
        }
    }
    public static void  importCard(Map<String,String> map, Scanner scanner, List<String> list, Map<String, Integer> hardest) {
        System.out.println("File name:");
        list.add("File name:");
        String filename = scanner.nextLine();
        list.add(filename);
        try( Scanner scanner1 = new Scanner(new File(filename))) {
            int kol=0;
            while (true){
                if(scanner1.hasNextLine()){
                    String flash = scanner1.nextLine();
                    map.put(flash,scanner1.nextLine());
                    int err = Integer.parseInt(scanner1.nextLine());
                    hardest.put(flash,err);
                    kol++;
                    if(!hardest.containsKey(flash)){
                        hardest.put(flash,0);
                    }
                }
                else
                    break;

            }
            System.out.println(kol + " cards have been loaded.");
            list.add(kol + " cards have been loaded.");
        }
        catch (FileNotFoundException e){
            System.out.println("File not found.");
            list.add("File not found");
        }

    }
    public static void exportCard(Map<String, String> map, List<String> list, Map<String,Integer> hardest, String args){

        int count=0;
        String filename = args;
        list.add(filename);
        try(FileWriter print = new FileWriter(new File(filename))) {

            for (String str:map.keySet()) {
                count++;
                print.write(str + "\n");
                print.write(map.get(str) + "\n");
                print.write(hardest.get(str) + "\n");
            }

            System.out.println(count + " cards have been saved.");
            list.add(count + " cards have been saved.");
        }
        catch (IOException e){
            System.out.println("0 cards have been saved.");
            list.add("0 cards have been saved.");
        }
    }
    public static void exportCard(Map<String, String> map, Scanner scanner, List<String> list, Map<String,Integer> hardest){
        System.out.println("File name:");
        list.add("File name:");
        int count=0;
        String filename = scanner.nextLine();
        list.add(filename);
        try(FileWriter print = new FileWriter(new File(filename))) {

            for (String str:map.keySet()) {
                count++;
                print.write(str + "\n");
                print.write(map.get(str) + "\n");
                print.write(hardest.get(str) + "\n");
            }

            System.out.println(count + " cards have been saved.");
            list.add(count + " cards have been saved.");
        }
        catch (IOException e){
            System.out.println("0 cards have been saved.");
            list.add("0 cards have been saved.");
        }

    }
    public static void askCard(Map<String,String> map, Scanner scanner, List<String> list, Map<String,Integer> hardest){
        System.out.println("How many times to ask? ");
        list.add("How many times to ask? ");

        Map<Integer, String> map1 = new HashMap<>();
        int key = 0;
        for (String a:map.keySet()) {
            key++;
            map1.put(key,a);
        }
        Map<String,String> map2 = new LinkedHashMap<>();
        for (var i :map.entrySet()) {
            map2.put(i.getValue(),i.getKey());
        }

        int a = Integer.parseInt(scanner.nextLine());
        list.add(String.valueOf(a));
        Random random = new Random();
        for (int i = 0; i <a ; i++) {
            int ask=random.nextInt(key)+1;
            System.out.println("Print the definition of " + "\""+ map1.get(ask) +"\":");
            list.add("Print the definition of " + "\""+ map1.get(ask) +"\":");
            String def = scanner.nextLine();
            list.add(def);
            if (map.get(map1.get(ask)).equals(def)) {
                System.out.println("Correct answer.");
                list.add("Correct answer.");
            }
            else if (!map.get(map1.get(ask)).equals(def) && map.containsValue(def)){
                System.out.println("Wrong answer. The correct one is \""+ map.get(map1.get(ask))+ "\", " +
                        "you've just written the definition of \"" +  map2.get(def) + "\".");
                hardest.put(map.get(map1.get(ask)), hardest.get(map1.get(ask))+1);
                list.add("Wrong answer. The correct one is \""+ map.get(map1.get(ask)) + "\", " +
                        "you've just written the definition of \"" +  map2.get(def) + "\".");
            }
            else{
                System.out.println("Wrong answer. The correct one is \""+ map.get(map1.get(ask)) + "\".");
                hardest.put(map1.get(ask),hardest.get(map1.get(ask))+1);
                list.add("Wrong answer. The correct one is \""+ map.get(map1.get(ask)) + "\".");
            }
        }

    }
}