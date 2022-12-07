package day.seven;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Directories {

    private static final long TOTAL_DISK_SPACE = 70000000L;
    private static final long MIN_NEEDED_SPACE = 30000000L;

    public static class Tree<T> {
        public Node<T> root;

        public Tree(T rootData) {
            root = new Node<>(rootData);
        }

        public void addChild(Node<T> child) {
            root.children.add(child);
        }

        public static class Node<T> {
            public T data;
            public Node<T> parent;
            public List<Node<T>> children;
            public long size;

            public Node(T rootData) {
                this.data = rootData;
                this.children = new ArrayList<>();
                this.size = 0L;
            }
        }

        public record TreeData(String dirName, List<Long> files) {
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-seven/commands.txt");
        // Create directory tree
        Tree<Tree.TreeData> directoryTree = new Tree<>(new Tree.TreeData("/", new ArrayList<>()));
        Tree.Node<Tree.TreeData> node = directoryTree.root;
        String line = reader.readLine();
        while (line != null) {
            String[] inputs = line.split(" ");
            String command = inputs[1];
            if (node != null) {
                switch (command) {
                    case "cd" -> {
                        String dir = inputs[2];
                        switch (dir) {
                            case "/" -> {
                                while (node.parent != null) {
                                    node = node.parent;
                                }
                            }
                            case ".." -> {
                                node = node.parent;
                            }
                            default -> {
                                for (Tree.Node<Tree.TreeData> tempNode : node.children) {
                                    if (tempNode.data.dirName.compareToIgnoreCase(dir) == 0) {
                                        node = tempNode;
                                        break;
                                    }
                                }
                            }
                        }
                        line = reader.readLine();
                    }
                    case "ls" -> {
                        boolean isCommand;
                        while (true) {
                            line = reader.readLine();
                            if (line != null) {
                                inputs = line.split(" ");
                                isCommand = inputs[0].compareToIgnoreCase("$") == 0;
                                if (isCommand) {
                                    break;
                                }
                                boolean isDir = inputs[0].compareToIgnoreCase("dir") == 0;
                                if (isDir) {
                                    String dir = inputs[1];
                                    Tree.Node<Tree.TreeData> newNode = new Tree.Node<>(new Tree.TreeData(dir, new ArrayList<>()));
                                    newNode.parent = node;
                                    node.children.add(newNode);
                                } else {
                                    Long fileSize = Long.parseLong(inputs[0]);
                                    node.data.files.add(fileSize);
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        calculateTotalSumForEachNode(directoryTree);
        partOne(directoryTree);
        partTwo(directoryTree);
    }

    public static void partTwo(Tree<Tree.TreeData> directoryTree) {
        Queue<Tree.Node<Tree.TreeData>> queue = new LinkedList<>();
        queue.add(directoryTree.root);
        long minDiskSpaceToFree = Long.MAX_VALUE;
        long currentUnusedSpace = TOTAL_DISK_SPACE - directoryTree.root.size;
        while (!queue.isEmpty()) {
            Tree.Node<Tree.TreeData> directory = queue.poll();
            long bigEnoughDir = directory.size + currentUnusedSpace;
            if (bigEnoughDir >= MIN_NEEDED_SPACE) {
                if (minDiskSpaceToFree > directory.size) {
                    minDiskSpaceToFree = directory.size;
                }
            }
            queue.addAll(directory.children);
        }
        System.out.println(minDiskSpaceToFree);
    }

    public static void partOne(Tree<Tree.TreeData> directoryTree) {
        Queue<Tree.Node<Tree.TreeData>> queue = new LinkedList<>();
        queue.add(directoryTree.root);
        long allSize = 0;
        while (!queue.isEmpty()) {
            Tree.Node<Tree.TreeData> directory = queue.poll();
            if (directory.size <= 100000) {
                allSize += directory.size;
            }
            queue.addAll(directory.children);
        }
        System.out.println(allSize);
    }

    public static void calculateTotalSumForEachNode(Tree<Tree.TreeData> directoryTree) {
        Queue<Tree.Node<Tree.TreeData>> queue = new LinkedList<>();
        queue.add(directoryTree.root);
        while (!queue.isEmpty()) {
            Tree.Node<Tree.TreeData> directory = queue.poll();
            directory.size = DFS(directory);
            queue.addAll(directory.children);
        }
    }

    public static long DFSUtil(Tree.Node<Tree.TreeData> root, long size) {
        Iterator<Tree.Node<Tree.TreeData>> i = root.children.iterator();
        while (i.hasNext()) {
            Tree.Node<Tree.TreeData> child = i.next();
            size = DFSUtil(child, size);
        }
        return size + root.data.files.stream().mapToLong(Long::longValue).sum();
    }

    public static long DFS(Tree.Node<Tree.TreeData> root) {
        return DFSUtil(root, 0);
    }
}

