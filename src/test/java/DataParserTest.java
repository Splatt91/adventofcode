import day.thirteen.DataParser;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class DataParserTest {

    public Object[] getParams() {
        return  new Object[] {
                new Object[] {"[1,1,3,1,1]", List.of(1, 1, 3, 1, 1),
                        "[1,1,5,1,1]", List.of(1, 1, 5, 1, 1)},
                new Object[] {"[[1],[2,3,4]]", List.of(List.of(1), List.of(2, 3, 4)),
                        "[[1],4]", List.of(List.of(1), 4)},
                new Object[] {"[9]", List.of(9),
                        "[[8,7,6]]", List.of(List.of(8, 7, 6))},
                new Object[] {"[[4,4],4,4]", List.of(List.of(4, 4), 4, 4),
                        "[[4,4],4,4,4]", List.of(List.of(4, 4), 4, 4, 4)},
                new Object[] {"[7,7,7,7]", List.of(7, 7, 7, 7),
                        "[7,7,7]", List.of(7, 7, 7)},
                new Object[] {"[]", Collections.emptyList(), "[3]", List.of(3)},
                new Object[] {"[[[]]]", List.of(List.of(Collections.emptyList())),
                        "[[]]", List.of(Collections.emptyList())},
                new Object[] {"[1,[2,[3,[4,[5,6,7]]]],8,9]",
                        List.of(1, List.of(2, List.of(3, List.of(4, List.of(5, 6, 7)))), 8, 9),
                        "[1,[2,[3,[4,[5,6,0]]]],8,9]",
                        List.of(1, List.of(2, List.of(3, List.of(4, List.of(5, 6, 0)))), 8, 9)}
        };
    }

    @Test
    @Parameters(method = "getParams")
    public void TestPairs(String strLeft, List<Object> left, String strRight, List<Object> right) {
        DataParser dataParser = new DataParser();
        List<Object> resultL = dataParser.parsePacket(strLeft);
        List<Object> resultR = dataParser.parsePacket(strRight);
        assertEquals(left, resultL);
        assertEquals(right, resultR);
    }
}
