void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day2/input.txt");
    List<String> lines = Files.readAllLines(inputPath);

    String[] ranges = lines.getFirst().split(",");

    long count = 0;
    for (String range : ranges) {

        String[] content = range.split("-");
        String startCode = content[0];
        long startValue = Long.parseLong(startCode);
        String startTruncatedCode = startCode.substring(0, startCode.length() / 2);
        int startTruncatedValue = Integer.parseInt(startTruncatedCode);

        String endCode = content[1];
        long endValue = Long.parseLong(endCode);
        String endTruncatedCode = endCode.substring(0, endCode.length() / 2);
        int endTruncatedValue = Integer.parseInt(endTruncatedCode);

        for (int i = startTruncatedValue; i < endTruncatedValue; i++) {

        }
    }


}

record Range(int start, String startCode, int end, String endCode) { }

boolean isValid(String code) {
    if (code.length() % 2 != 0) {
        return true;
    }

    return !code.substring(0, code.length() / 2).equals(code.substring(code.length() / 2));
}