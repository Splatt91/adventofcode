import day.thirteen.Comparison;
import day.thirteen.DataParser;
import day.thirteen.DistressSignal;
import day.thirteen.State;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class DistressSignalTest {

//  Packet data consists of lists and integers.
//  Each list starts with [, ends with ],
//  and contains zero or more comma-separated values (either integers or other lists).
//  When comparing two values, the first value is called left and the second value is called right.

    @Test
    public void SumOfIndicesOfInOrderPairs() throws IOException {
        DistressSignal distressSignal = new DistressSignal();
        int result = distressSignal.sumOfIndicesOfInOrderPairs(new DataParser(), "day-thirteen/packets.txt");
        assertEquals(5905, result);
    }

    @Test
    public void FindDecoderKey() throws IOException {
        DistressSignal distressSignal = new DistressSignal();
        int result = distressSignal.findDecoderKey(new DataParser(), "day-thirteen/packets.txt");
        assertEquals(21691, result);
    }

    public Object[] getParams() {
        return new Object[]{
                new Object[]{List.of(1, 1, 3, 1, 1),
                        List.of(1, 1, 5, 1, 1),
                        State.IN_ORDER},
                new Object[]{List.of(List.of(1), List.of(2, 3, 4)),
                        List.of(List.of(1), 4),
                        State.IN_ORDER},
                new Object[]{List.of(9),
                        List.of(List.of(8, 7, 6)),
                        State.NOT_IN_ORDER},
                new Object[]{List.of(List.of(4, 4), 4, 4),
                        List.of(List.of(4, 4), 4, 4, 4),
                        State.IN_ORDER},
                new Object[]{List.of(7, 7, 7, 7),
                        List.of(7, 7, 7), State.NOT_IN_ORDER},
                new Object[]{Collections.emptyList(),
                        List.of(3),
                        State.IN_ORDER},
                new Object[]{List.of(List.of(Collections.emptyList())),
                        List.of(Collections.emptyList()),
                        State.NOT_IN_ORDER},
                new Object[]{List.of(1, List.of(2, List.of(3, List.of(4, List.of(5, 6, 7)))), 8, 9),
                        List.of(1, List.of(2, List.of(3, List.of(4, List.of(5, 6, 0)))), 8, 9),
                        State.NOT_IN_ORDER},
        };
    }

    @Test
    @Parameters(method = "getParams")
    public void TestPairs(List<Object> left, List<Object> right, State expected) {
        DistressSignal distressSignal = new DistressSignal();
        State result = distressSignal.comparePairs(left, right);
        assertEquals(expected, result);
    }

//  If both values are integers, the lower integer should come first.
//  If the left integer is lower than the right integer, the inputs are in the right order.
    @Test
    public void CompareIntegers_LeftLowerThanRight_InOrder() {
        DistressSignal distressSignal = new DistressSignal();
        int left = 1;
        int right = 2;
        State result = distressSignal.compareInts(left, right);
        assertEquals(State.IN_ORDER, result);
    }

//  If the left integer is higher than the right integer, the inputs are not in the right order.
    @Test
    public void CompareIntegers_LeftHigherThanRight_NotInOrder() {
        DistressSignal distressSignal = new DistressSignal();
        int left = 3;
        int right = 1;
        State result = distressSignal.compareInts(left, right);
        assertEquals(State.NOT_IN_ORDER, result);
    }

//  Otherwise, the inputs are the same integer; continue checking the next part of the input.
    @Test
    public void CompareIntegers_LeftEqualToRight_SKIP() {
        DistressSignal distressSignal = new DistressSignal();
        int left = 1;
        int right = 1;
        State result = distressSignal.compareInts(left, right);
        assertEquals(State.SKIP, result);
    }

//  If both values are lists, compare the first value of each list, then the second value, and so on.

//  If the left list runs out of items first, the inputs are in the right order.
    @Test
    public void CompareLists_LeftListRunsOutOfItems_InOrder() {
        DistressSignal distressSignal = new DistressSignal();
        List<Object> left = List.of(1, 2, 3);
        List<Object> right = List.of(1, 2, 3, 4);
        State result = distressSignal.comparePairs(left, right);
        assertEquals(State.IN_ORDER, result);
    }

//  If the right list runs out of items first, the inputs are not in the right order.
    @Test
    public void CompareLists_RightListRunsOutOfItems_NotInOrder() {
        DistressSignal distressSignal = new DistressSignal();
        List<Object> left = List.of(1, 2, 3);
        List<Object> right = List.of(1, 2);
        State result = distressSignal.comparePairs(left, right);
        assertEquals(State.NOT_IN_ORDER, result);
    }

//  If the lists are the same length and no comparison makes a decision about the order,
//  continue checking the next part of the input.
    @Test
    public void CompareLists_ListsHaveSameLength_Skip() {
        DistressSignal distressSignal = new DistressSignal();
        List<Object> left = List.of(1, 2);
        List<Object> right = List.of(1, 2);
        State result = distressSignal.comparePairs(left, right);
        assertEquals(State.SKIP, result);
    }

//  If exactly one value is an integer,
//  convert the integer to a list which contains that integer as its only value, then retry the comparison.
//  For example, if comparing [0,0,0] and 2, convert the right value to [2] (a list containing 2);
//  the result is then found by instead comparing [0,0,0] and [2].

    @Test
    public void GetComparison_BothIntegers() {
        DistressSignal distressSignal = new DistressSignal();
        int left = 1;
        int right = 2;
        Comparison result = distressSignal.getComparison(left, right);
        assertEquals(Comparison.BOTH_INTEGERS, result);
    }

    @Test
    public void GetComparison_BothLists() {
        DistressSignal distressSignal = new DistressSignal();
        var left = List.of(1);
        var right = List.of(2);
        Comparison result = distressSignal.getComparison(left, right);
        assertEquals(Comparison.BOTH_LISTS, result);
    }

    @Test
    public void GetComparison_ExactlyOneIsAnInteger() {
        DistressSignal distressSignal = new DistressSignal();
        List<Integer> left = List.of(1, 2, 3);
        int right = 1;
        Comparison result = distressSignal.getComparison(left, right);
        assertEquals(Comparison.EXACTLY_ONE_IS_AN_INTEGER, result);
    }

    @Test
    public void ConvertIntegerToList() {
        DistressSignal distressSignal = new DistressSignal();
        int num = 6;
        List<Integer> result = distressSignal.convertIntegerToList(num);
        assertEquals(List.of(6), result);
    }

}
