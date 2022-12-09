package day.nine;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Rope {

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static boolean isNeighbour(Point head, Point tail) {
            return Math.abs(head.x - tail.x) <= 1 && Math.abs(head.y - tail.y) <= 1;
        }
    }

    public static void main(String[] args) throws IOException {
        // Part one
        solve(2);
        // Part two
        solve(10);
    }

    public static void solve(int numberOfPoints) throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-nine/moves.txt");
        Set<String> marked = new HashSet<>();
        marked.add("0,0");
        Point[] points = new Point[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            points[i] = new Point(0, 0);
        }
        for (String line; (line = reader.readLine()) != null;) {
            fillMatrix(marked, line, points);
        }
        System.out.println(marked.size());
    }

    public static void fillMatrix(Set<String> marked,
                                  String line,
                                  Point[] points) {
        String[] inputs = line.split(" ");
        String direction = inputs[0];
        int numberOfMoves = Integer.parseInt(inputs[1]);

        Point dir = switch (direction) {
            case "R" -> new Point(1,0);
            case "L" -> new Point(-1,0);
            case "U" -> new Point(0,1);
            case "D" -> new Point(0,-1);
            default -> throw new RuntimeException();
        };

        for (int i = 0; i < numberOfMoves; i++) {
            // Update head
            points[0].x += dir.x;
            points[0].y += dir.y;

            // Update body
            for (int ptr = 1; ptr < points.length; ptr++) {
                if (Point.isNeighbour(points[ptr - 1], points[ptr])) {
                    continue;
                }

                Point[] offsets;
                if (points[ptr].x != points[ptr-1].x
                        && points[ptr].y != points[ptr-1].y) {
                    // Possible diagonal offsets
                    offsets = new Point[] {
                            new Point(1, 1),
                            new Point(1, -1),
                            new Point(-1, 1),
                            new Point(-1, -1)
                    };
                } else {
                    // Possible straight offsets
                    offsets = new Point[] {
                            new Point(1, 0),
                            new Point(-1, 0),
                            new Point(0, 1),
                            new Point(0, -1)
                    };
                }

                for (int d = 0; d < offsets.length; d++) {
                    Point n = new Point(points[ptr].x + offsets[d].x,
                            points[ptr].y + offsets[d].y);
                    if (Point.isNeighbour(n, points[ptr-1])) {
                        // Use this position
                        points[ptr].x = n.x;
                        points[ptr].y = n.y;
                        break;
                    }
                }
            }
            marked.add(points[points.length - 1].x + "," + points[points.length - 1].y);
        }
    }
}
