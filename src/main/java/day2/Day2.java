void main() throws IOException {
    Path inputPath = Paths.get("src/main/java/day2/input.txt");
    List<String> lines = Files.readAllLines(inputPath);
    String[] ranges = lines.getFirst().split(",");
    long sum = 0;
    for (String range : ranges) {
        String[] content = range.split("-");
        String startCode = content[0];
        String endCode = content[1];
        long startValue = Long.parseLong(startCode);
        long endValue = Long.parseLong(endCode);
        for (long i = startValue; i <= endValue; i++) {
            String code = Long.toString(i);
            StringBuilder acc = new StringBuilder();
            int size = 0;
            for (char c : code.toCharArray()) {
                if (size >= code.length() / 2) break;
                acc.append(c);
                String regex = "^(" + acc +  ")+$";
                Matcher m = Pattern.compile(regex).matcher(code);
                if (m.find()) {
                    sum += i;
                    break;
                }
                size ++;
            }
            /*if (!isValid("" + i)) {
                sum += i;
            }*/
        }
    }
    IO.println(sum);
    // P1 54234399924
    // P2 70187097315
}

boolean isValid(String code) {
    if (code.length() % 2 != 0) {
        return true;
    }
    return !code.substring(0, code.length() / 2).equals(code.substring(code.length() / 2));
}