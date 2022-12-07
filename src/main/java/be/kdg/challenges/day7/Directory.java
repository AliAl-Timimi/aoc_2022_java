package be.kdg.challenges.day7;

import java.util.List;

public class Directory implements FileOrDirectory, Comparable<Directory> {
    private Directory parent;
    private String name;
    private List<FileOrDirectory> children;

    public Directory(String name, List<FileOrDirectory> files, Directory parent) {
        this.name = name;
        this.children = files;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        int total = 0;
        for (FileOrDirectory file : children) {
            total += file.getSize();
        }
        return total;
    }

    public List<FileOrDirectory> getChildren() {
        return children;
    }

    public void setChildren(List<FileOrDirectory> children) {
        this.children = children;
    }

    public Directory getParent() {
        return parent;
    }

    public void addFileOrDirectory(FileOrDirectory fileOrDirectory) {
        children.add(fileOrDirectory);
    }

    @Override
    public int compareTo(Directory o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return String.format("- %s (dir)", getName());
    }
}
