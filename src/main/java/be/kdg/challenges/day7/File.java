package be.kdg.challenges.day7;

public class File implements FileOrDirectory, Comparable<File> {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int compareTo(File o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return String.format("- %s (file, size=%d)", getName(), getSize());
    }
}