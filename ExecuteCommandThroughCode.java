    private static List<String> executeCommand(String nameSpace) throws IOException, InterruptedException {
        String command = String.format("abc --namespace def-%s", nameSpace);
        Process listProcess = Runtime.getRuntime().exec(command);
        List<String> names = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(listProcess.getInputStream()))) {
            reader.lines()
                    .filter(line -> line.startsWith("someFilterValue"))
                    .map(line -> line.split("\\s+")[0].trim())
                    .forEach(names::add);
        }

        listProcess.waitFor();
        return names;
    }
