package mvc;

import java.util.*;

public class Main1 {


    public static Set<Integer> GetLeastNumbers_Solution(ArrayList<Integer> input, int k) {
        Set<Integer> set = new HashSet<>();
        for(Integer ele:input){
            if(!set.contains(ele)){
                if(ele<=k){
                    set.add(ele) ;
                }
            }
        }
        return set;
    }



    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            int k = Integer.parseInt(in.nextLine());
            ArrayList input = spliteLine(line);
            Set<Integer> res = GetLeastNumbers_Solution(input, k);
            System.out.println(res);
        }

}

    private static ArrayList<Integer> spliteLine(String line) throws Exception {
        List<Integer> array = new ArrayList<Integer>();
        String[] split = line.split(",");
        try{
            for(int i=0;i<split.length;i++){
                array.add(Integer.parseInt(split[i]));
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return (ArrayList<Integer>) array;
    }
    }
