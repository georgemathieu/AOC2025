import util.Coordinate;
import util.Direction;
import util.MapComponent;

final char ROLL = '@';
final char EMPTY = '.';

void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day4/input.txt");
    List<String> lines = Files.readAllLines(inputPath);
    MapComponent mc = new MapComponent(lines.getFirst().length(), lines.size());
    char[][] chars = mc.asChars(lines);
    int totalSum = 0;
    while (true) {
        int localSum = 0;
        Set<Coordinate> removedRolls = new HashSet<>();
        for (int y = 0; y < mc.nbRows(); y++) {
            for (int x = 0; x < mc.nbColumns(); x++) {
                Coordinate c = new Coordinate(x, y);
                if (chars[y][x] == ROLL && isAccessible(mc, chars, c)) {
                    localSum++;
                    removedRolls.add(c);
                }
            }
        }
        for (Coordinate removedRoll : removedRolls) {
            chars[removedRoll.y()][removedRoll.x()] = EMPTY;
            totalSum++;
        }
        if (localSum == 0) {
            break;
        }
    }
    IO.println(totalSum);
    // P1 1508
    // P2 8538
}

boolean isAccessible(MapComponent mc, char[][] map, Coordinate coord) {
    int sum = 0;
    for (Direction vector : Direction.values()) {
        Coordinate next = coord.apply(vector);
        if (mc.isValid(next) && map[next.y()][next.x()] == ROLL) {
            sum++;
        }
    }
    return sum < 4;
}