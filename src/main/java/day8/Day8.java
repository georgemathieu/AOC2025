private final Set<Box> boxes = new HashSet<>();
private final Set<Block> closestMapping = new HashSet<>();
private final Set<Set<Box>> groups = new HashSet<>();

void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day8/input.txt");
    List<String> lines = Files.readAllLines(inputPath);

    int boxCount = 0;
    for (String line : lines) {
        String[] content = line.split(",");
        boxes.add(new Box(Integer.parseInt(content[0]), Integer.parseInt(content[1]), Integer.parseInt(content[2]), "" + boxCount));
        boxCount++;
    }

    for (Box box1 : boxes) {
        MinDistBox mdb = new MinDistBox(null, Double.MAX_VALUE);
        for (Box box2 : boxes) {
            if (box1.equals(box2)) {
                continue;
            }
            double distance = distance(box1, box2);
            if (distance < mdb.distance()) {
                mdb = new MinDistBox(box2, distance);
            }
        }
        closestMapping.add(new Block(box1, mdb));
    }

    List<Block> sortedBlocks = closestMapping.stream().sorted(Comparator.comparingDouble(p -> p.mdb().distance())).toList();

    int count = 0;
    for (Block entry : sortedBlocks) {
        boolean found = false;
        for (Set<Box> group : groups) {
            if (group.contains(entry.box())) {
                found = true;
                group.add(entry.mdb().box());
            } else if (group.contains(entry.mdb().box())) {
                found = true;
                group.add(entry.box());
            }
        }
        if (!found) {
            Set<Box> newGroup = new HashSet<>();
            newGroup.add(entry.box());
            newGroup.add(entry.mdb().box());
            groups.add(newGroup);
        }
        count++;

        if (count >= 10) {
            break;
        }
    }

    long result = groups.stream()
            .sorted((s1, s2) -> s2.size() - s1.size())
            .limit(3)
            .mapToLong(Set::size)
            .reduce(1L, (a, b) -> a * b);
    IO.println(result);
}

private double distance(Box b1, Box b2) {
    double dx = b2.x() - b1.x();
    double dy = b2.y() - b1.y();
    double dz = b2.z() - b1.z();
    return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
}

private record Block(Box box, MinDistBox mdb) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Block(Box box1, MinDistBox mdb1))) return false;
        return (Objects.equals(box, box1) && Objects.equals(mdb.box(), mdb1.box())) || (Objects.equals(box, mdb1.box()) && Objects.equals(mdb.box(), box1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(box, mdb);
    }
}

private record Box(int x, int y, int z, String id) {

}

private record MinDistBox(Box box, double distance) {

}