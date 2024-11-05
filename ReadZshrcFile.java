private static Map<String, String> getVariablesFromZshrc() throws IOException {
        Path zshrcPath = Paths.get(System.getProperty("user.home"), ".zshrc");
        Map<String, String> envVars = new HashMap<>();
        try (Stream<String> lines = Files.lines(zshrcPath)) {
            lines.filter(line -> line.startsWith("export "))
                    .forEach(line -> {
                        String[] parts = line.substring(7).split("=", 2);
                        if (parts.length == 2) {
                            envVars.put(parts[0].trim(), parts[1].replace("\"", "").trim());
                        }
                    });
        }
        return envVars;
    }
