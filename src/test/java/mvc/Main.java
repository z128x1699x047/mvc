package mvc;

import java.util.*;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println(sort(line));
        }

    }

    private static String sort(String line) {
        Map<String,Integer> map = new HashMap<>();
        Map<Integer,String> map1 = new HashMap<>();
        for(int i=0;i<line.length();i++){
            String key = line.substring(i,i+1);
            if(map.keySet().contains(key)){
                map.put(key,map.get(key)+1);
            }else{
                map.put(key,1);
            }
        }
        for(String key:map.keySet()){
            map1.put(map.get(key),key);
        }
        List<Integer> list = new ArrayList<>();
        for(Integer key:map1.keySet()){
            list.add(key);
        }
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        return null;
    }
}
