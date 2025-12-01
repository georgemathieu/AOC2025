void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day1/input.txt");
    List<String> lines = Files.readAllLines(inputPath);

    int pos = 50;
    int count = 0;
    for (String line : lines) {
        boolean isLeft = line.charAt(0) == 'L';
        int value = Integer.parseInt(line.substring(1));
        //pos -= value;
        for (int i = 0; i < value; i++) {
            pos = isLeft ? pos - 1 : pos + 1;
            pos %= 100;
            if (pos == 0) count++;
        }
        // pos += value;
        pos %= 100;
    }
    IO.println(count);
    // 6671
}