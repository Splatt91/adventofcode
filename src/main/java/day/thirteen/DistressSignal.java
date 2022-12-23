package day.thirteen;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DistressSignal {

    public State compareInts(int left, int right) {
        return State.convertToState(Integer.compare(left, right));
    }

    public State comparePairs(List<Object> left, List<Object> right) {
        int sizeL = left.size();
        int sizeR = right.size();
        int size = Math.min(sizeL, sizeR);
        for (int i = 0; i < size; i++) {
            Object l = left.get(i);
            Object r = right.get(i);
            State result = State.SKIP;
            switch (getComparison(l, r)) {
                case BOTH_INTEGERS -> {
                    result = compareInts((int) l, (int) r);
                }
                case BOTH_LISTS -> {
                    result = comparePairs((List) l, (List) r);
                }
                case EXACTLY_ONE_IS_AN_INTEGER -> {
                    if (l instanceof Integer) {
                        l = convertIntegerToList((int) l);
                    } else if (r instanceof  Integer) {
                        r = convertIntegerToList((int) r);
                    }
                    result = comparePairs((List) l, (List) r);
                }
            }
            if (result != State.SKIP) {
                return result;
            }
        }
        return compareInts(sizeL, sizeR);
    }

    public List<Integer> convertIntegerToList(int num) {
        return List.of(num);
    }

    public Comparison getComparison(Object left, Object right) {
        if (areBothIntegers(left, right)) {
            return Comparison.BOTH_INTEGERS;
        }
        if (areBothLists(left, right)) {
            return Comparison.BOTH_LISTS;
        }
        if (isExactlyOneValueAnInteger(left, right)) {
            return Comparison.EXACTLY_ONE_IS_AN_INTEGER;
        }
        throw new ComparisonException();
    }

    private boolean areBothIntegers(Object left, Object right) {
        return (left instanceof Integer) && (right instanceof Integer);
    }

    private boolean areBothLists(Object left, Object right) {
        return (left instanceof List) && (right instanceof List);
    }

    private boolean isExactlyOneValueAnInteger(Object left, Object right) {
        boolean isLeftInteger = left instanceof Integer;
        boolean isRightInteger = right instanceof Integer;
        return (!isLeftInteger && isRightInteger) || (isLeftInteger && !isRightInteger);
    }

    public int sumOfIndicesOfInOrderPairs(DataParser dataParser, String filePath) throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader(filePath);
        int sum = 0;
        int indice = 1;
        while (true) {
            List<Object> left = dataParser.parsePacket(reader.readLine());
            List<Object> right = dataParser.parsePacket(reader.readLine());
            if (comparePairs(left, right) == State.IN_ORDER) {
                sum += indice;
            }

            if (reader.readLine() == null) {
                break;
            }
            indice++;
        }
        return sum;
    }

    public int findDecoderKey(DataParser dataParser, String filePath) throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader(filePath);
        List<List<Object>> list = new LinkedList<>();
        while (true) {
            list.add(dataParser.parsePacket(reader.readLine()));
            list.add(dataParser.parsePacket(reader.readLine()));
            if (reader.readLine() == null) {
                break;
            }
        }
        List<Object> dividerPacket1 = List.of(List.of(2));
        List<Object> dividerPacket2 = List.of(List.of(6));
        list.add(dividerPacket1);
        list.add(dividerPacket2);
        list.sort((left, right) -> comparePairs(left, right).getValue());
        return (list.indexOf(dividerPacket1) + 1) * (list.indexOf(dividerPacket2) + 1);
    }
}
