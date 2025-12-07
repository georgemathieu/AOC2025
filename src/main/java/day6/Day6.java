void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day6/input.txt");
    List<String> lines = Files.readAllLines(inputPath);

    int size = lines.getFirst().trim().split("\\s+").length;
    int fullSize = lines.getFirst().length();
    List<long[]> numbers = new ArrayList<>();
    int[] slotSize = new int[size];
    Operator[] operators = new Operator[size];
    int lineNb = 1;
    // Build arrays
    for (String line : lines) {
        boolean isLast = lineNb == lines.size();
        String[] content = line.trim().split("\\s+");
        long[] arr = new long[size];
        int cpt = 0;
        for (String nb : content) {
            if (!isLast) {
                arr[cpt] = Long.parseLong(nb);
            } else {
                operators[cpt] = Operator.toEnum(nb);
            }
            cpt++;
        }
        if (!isLast) {
            numbers.add(arr);
        }
        // Build size array
        if (isLast) {
            int sizeCpt = 0;
            int slotCount = 0;
            char[] lastLineArray = line.toCharArray();
            for (int i = 1; i < lastLineArray.length; i++) {
                if (lastLineArray[i] == ' ') {
                    sizeCpt++;
                } else {
                    slotSize[slotCount] = sizeCpt;
                    sizeCpt = 0;
                    slotCount++;
                }
            }
            slotSize[slotCount] = sizeCpt + 1; // last value
        }
        lineNb++;
    }

    // Consume arrays
    long sum = 0;
    for (int i = 0; i < size; i++) {
        Operator op = operators[i];
        if (op == null) throw new IllegalArgumentException();
        long currentValue = op.equals(Operator.PLUS) ? 0 : 1;
        for (long[] number : numbers) {
            currentValue = op.getFunction().apply(currentValue, number[i]);
        }
        sum += currentValue;
    }
    IO.println(sum);
    // P1 5524274308182

    long sumP2 = 0;
    int currentPosition = fullSize - 1;
    lines.removeLast(); // remove operators
    for (int i = slotSize.length - 1; i >= 0; i--) {
        int currentSize = slotSize[i];
        Operator op = operators[i];
        long currentValue = op.equals(Operator.PLUS) ? 0 : 1;
        // Build the current operation's numbers
        List<Long> operationNumbers = new ArrayList<>();
        for (int j = 0; j < currentSize ; j++) {
            StringBuilder sb = new StringBuilder();
            // Read a specific position for all lines
            for (String line : lines) {
                char c = line.charAt(currentPosition - j);
                if (c == ' ') continue;
                sb.append(c);
            }
            if (!sb.toString().isEmpty()) {
                operationNumbers.add(Long.parseLong(sb.toString()));
            }
        }
        for (Long nb : operationNumbers) {
            currentValue = op.getFunction().apply(currentValue, nb);
        }
        sumP2 += currentValue;
        currentPosition -= currentSize + 1;
    }
    IO.println(sumP2);
    // P2 8843673199391
}

private enum Operator {
    PLUS(Long::sum),
    MULT((a, b) -> a * b);

    private BiFunction<Long, Long, Long> apply;

    public BiFunction<Long, Long, Long> getFunction() {
        return apply;
    }

    Operator(BiFunction<Long, Long, Long> apply) {
        this.apply = apply;
    }

    public static Operator toEnum(String str) {
        return switch (str) {
            case "+" -> PLUS;
            case "*" -> MULT;
            default -> null;
        };
    }
}

