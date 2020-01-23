package com.arrayLeader.clases;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public Solution(int k, int m, int[] a){
        List<Integer> intList = Arrays.stream(a).boxed().collect(Collectors.toList());
        System.out.println("Initial Array: " + intList);
        List<Integer> leadersList = new LinkedList<>();
        List<List<Integer>> segments = getSegments(k, m, intList);
        IntStream.rangeClosed(0, segments.size() - 1)
                .forEach(
                        x -> {
                            int leader = getLeader(segments.get(x));
                            if(!leadersList.contains(leader))
                                leadersList.add(leader);
                                System.out.println("Segment: " + segments.get(x) + " -> Leader: " + leader);
                        }
                );
        System.out.println("Leaders: " + leadersList.stream().sorted().collect(Collectors.toList()));
    }

    public List<List<Integer>> getSegments(int k, int m, List<Integer> intList){//obtiene segmentos validos dado un array
        List<List<Integer>> listOfSegments = new LinkedList<>(new LinkedList<>());
        IntStream.rangeClosed(0, intList.size() - k)
                .forEach(
                    x -> {
                        List<Integer> actual =  intList.subList(x, x + k);//sublista del elemento x hasta x + k
                        actual = actual.stream()
                                .filter(y -> y <= m) //valores del segmento <= m
                                .map(y -> y + 1) //aumento valores del segmento +1
                                .collect(Collectors.toList());
                        if(actual.size() == k)//si tamaño segmento == k tras filtrado
                            listOfSegments.add(actual);

                    }
                );
        return listOfSegments;
    }

    public int getLeader(List<Integer> segment){//obtiene lider del segmento
        Map<Integer, Long> mapOcurrences = getOcurrences(segment);
        Map.Entry<Integer, Long> maxEntry = Collections.max(mapOcurrences.entrySet(), Map.Entry.comparingByValue());//obtiene el valor con mayor ocurrencias del segmento
        return maxEntry.getKey();
    }

    public Map<Integer, Long> getOcurrences(List<Integer> segment){//obtiene el numero de ocurrencias de cada elemento del segmento
        return segment.stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
    }
}
