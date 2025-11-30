void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day1/input.txt");
    List<String> lines = Files.readAllLines(inputPath);
    IO.println(lines);
}