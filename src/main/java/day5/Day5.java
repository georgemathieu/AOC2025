void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day5/input.txt");
    List<String> lines = Files.readAllLines(inputPath);
    boolean isRange = true;
    int count = 0;
    ArrayList<Range> ranges = new ArrayList<>();

    // Create ranges
    for (String line : lines) {
        if (line.trim().isEmpty()) {
            isRange = false;
            continue;
        }
        if (isRange) {
            String[] content = line.split("-");
            ranges.add(new Range(Long.parseLong(content[0]), Long.parseLong(content[1])));
        } else {
            for (Range range : ranges) {
                if (range.contains(Long.parseLong(line))) {
                    count++;
                    break;
                }
            }
        }
    }

    // Merge ranges into sequences
    Collections.sort(ranges);
    for (int i = 0; i < ranges.size(); i++) {
        Range current = ranges.get(i);
        if (i + 1 == ranges.size()){
            break;
        }
        Range next = ranges.get(i + 1);
        if (next.start <= current.end + 1) {
            ranges.set(i, new Range(current.start, Math.max(current.end, next.end)));
            ranges.remove(i + 1);
            i--;
        }
    }

    long countTotal = 0;
    for (Range range : ranges) {
        countTotal += range.end - range.start + 1;
    }

    IO.println("P1 : " + count);
    IO.println("Total : " + countTotal);
    // P1 598
    // P2 360341832208407
}

private record Range(long start, long end) implements Comparable<Range> {
    public boolean contains(long value) {
        return value >= start && value <= end;
    }

    @Override
    public int compareTo(Range o) {
        return Long.compare(this.start, o.start);
    }
}