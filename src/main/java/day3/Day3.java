void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day3/input.txt");
    List<String> lines = Files.readAllLines(inputPath);
    int nbJolt = 12;
    long sum = 0;
    for (String line : lines) {
        String res = getJolts(line.toCharArray(), 0, nbJolt - 1, new StringBuffer());
        sum += Long.parseLong(res);
    }
    IO.println(sum);
    // P1 16973
    // P2 168027167146027
}

private String getJolts(char[] chars, int startIndex, int remainingLoops, StringBuffer acc) {
    if (remainingLoops == -1) {
        return acc.toString();
    }
    int max = 0, maxIndex = 0;
    for (int i = startIndex; i < chars.length - remainingLoops; i++) {
        int nb = Character.getNumericValue(chars[i]);
        if (nb > max) {
            max = nb;
            maxIndex = i;
        }
    }
    return getJolts(chars, maxIndex + 1, remainingLoops - 1, acc.append(max));
}
