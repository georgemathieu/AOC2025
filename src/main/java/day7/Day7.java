import util.Coordinate;
import util.Direction;
import util.MapComponent;

private static final char START = 'S';
private static final char SPLITTER = '^';
private static final char BEAM = '|';
private static final char EMPTY = '.';

private static final Map<Coordinate, Long> cache = new HashMap<>();

void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day7/input.txt");
    List<String> lines = Files.readAllLines(inputPath);
    int nbColumns = lines.getFirst().length();
    int nbRows = lines.size();
    MapComponent mc = new MapComponent(nbColumns, lines.size());
    char[][] elements = mc.asChars(lines);

    IO.println(part1(nbRows, nbColumns, elements, mc)); // P1 1690
    clean(nbRows, nbColumns, elements);
    IO.println(part2(mc, new Coordinate(lines.getFirst().indexOf(START), 0), elements)); // P2 221371496188107
}

private static long part2(MapComponent mc, Coordinate start, char[][] elements) {
    Coordinate next = new Coordinate(start.x(), start.y()).apply(Direction.DOWN);
    if (cache.get(next) != null) {
        return cache.get(next);
    }

    if (!mc.isValid(next)) {
        return 1;
    }

    long count = 0;
    if (elements[next.y()][next.x()] == SPLITTER) {
        Coordinate splitLeft = next.apply(Direction.DOWN_LEFT);
        count += part2(mc, splitLeft, elements);

        Coordinate splitRight = next.apply(Direction.DOWN_RIGHT);
        count += part2(mc, splitRight, elements);
    } else {
        count += part2(mc, next, elements);
    }
    cache.put(next, count);
    return count;
}

private static int part1(int nbRows, int nbColumns, char[][] elements, MapComponent mc) {
    int count = 0;
    for (int y = 0; y < nbRows; y++) {
        for (int x = 0; x < nbColumns; x++) {
            char slot = elements[y][x];
            boolean goesDown = slot == START || slot == BEAM;
            Coordinate next = new Coordinate(x, y).apply(Direction.DOWN);
            if (!mc.isValid(next)) {
                continue;
            }

            if (goesDown && elements[next.y()][next.x()] == SPLITTER) {
                Coordinate splitLeft =  new Coordinate(x, y).apply(Direction.DOWN_LEFT);
                boolean splitOccured = false;
                if (elements[splitLeft.y()][splitLeft.x()] == EMPTY) {
                    elements[splitLeft.y()][splitLeft.x()] = BEAM;
                    splitOccured = true;
                }

                Coordinate splitRight =  new Coordinate(x, y).apply(Direction.DOWN_RIGHT);
                if (elements[splitRight.y()][splitRight.x()] == EMPTY) {
                    elements[splitRight.y()][splitRight.x()] = BEAM;
                    splitOccured = true;
                }

                if (splitOccured) {
                    count++;
                }
            } else if (goesDown && elements[next.y()][next.x()] == EMPTY) {
                elements[next.y()][next.x()] = BEAM;
            }
        }
    }
    return count;
}

private static void clean(int nbRows, int nbColumns, char[][] elements) {
    for (int y = 0; y < nbRows; y++) {
        for (int x = 0; x < nbColumns; x++) {
            if (elements[y][x] == BEAM) {
                elements[y][x] = EMPTY;
            }
        }
    }
}