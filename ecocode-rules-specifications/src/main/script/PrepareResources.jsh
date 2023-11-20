//usr/bin/env jshell -v "$@" "$0"; exit $?

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.DEBUG;
import static java.util.Optional.empty;
import static java.util.Optional.of;

    class PrepareResources implements Runnable {
        private static final System.Logger LOGGER = System.getLogger("PrepareResources");

        private final Path sourceDir;
        private final Path targetDir;

        public static void main(String... args) throws Exception {
            new PrepareResources(
                    Path.of(Objects.requireNonNull(System.getProperty("sourceDir"), "system property: sourceDir")),
                    Path.of(Objects.requireNonNull(System.getProperty("targetDir"), "system property: targetDir"))
            ).run();
        }

        PrepareResources(Path sourceDir, Path targetDir) {
            this.sourceDir = sourceDir;
            this.targetDir = targetDir;
        }

        @Override
        public void run() {
            getResourcesToCopy().forEach(rule -> {
                mergeAndCopyFile(rule.metadata, rule.specificMetadata, rule.getMetadataTargetPath(targetDir));
                copyFile(rule.htmlDescription, rule.getHtmlDescriptionTargetPath(targetDir));
            });
        }

        private List<Rule> getResourcesToCopy() {
            try (Stream<Path> stream = Files.walk(sourceDir)) {
                return stream
                        .filter(Files::isRegularFile)
                        .map(Rule::createFromHtmlDescription)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        private void mergeAndCopyFile(Path source, Path merge, Path target) {
            if (Files.isRegularFile(merge)) {
                LOGGER.log(DEBUG, "Merge: {0} and {1} -> {2}", source, merge, target);
                try (
                    FileReader sourceReader = new FileReader(source.toFile());
                    FileReader mergeReader = new FileReader(merge.toFile())
                ) {
                    JSONParser parser = new JSONParser();
                    JSONObject sourceJson = (JSONObject) parser.parse(sourceReader);
                    JSONObject mergeJson = (JSONObject) parser.parse(mergeReader);

                    mergeJsonObjects(sourceJson, mergeJson);

                    Files.createDirectories(target.getParent());
                    Files.write(target, sourceJson.toString().getBytes());
                } catch (IOException | ParseException e) {
                    throw new RuntimeException("cannot process source " + source, e);
                }
            } else {
                copyFile(source, target);
            }
        }

        private void mergeJsonObjects(JSONObject object1, JSONObject object2) {
            object2.forEach((key, value) -> {
                Object element = object1.get(key);
                if (element instanceof JSONObject && value instanceof JSONObject) {
                    mergeJsonObjects((JSONObject) element, (JSONObject) value);
                } else {
                    object1.put(key, value);
                }
            });
        }

        private void copyFile(Path source, Path target) {
            LOGGER.log(DEBUG, "Copy: {0} -> {1}", source, target);
            try {
                Files.createDirectories(target.getParent());
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }


        private static class Rule {
            /**
             * Resources to include
             */
            private static final Pattern TARGET_RESOURCES = Pattern.compile("^.*/(?<ruleKey>EC\\d+)/(?<language>[^/]*)/.*\\.html$");

            static Optional<Rule> createFromHtmlDescription(Path htmlDescription) {
                final Matcher matcher = TARGET_RESOURCES.matcher(htmlDescription.toString().replace('\\', '/'));
                if (!matcher.find()) {
                    return empty();
                }
                final String ruleKey = matcher.group("ruleKey");
                final Path metadata = htmlDescription.getParent().getParent().resolve(ruleKey + ".json");
                final Path specificMetadata = htmlDescription.getParent().resolve(ruleKey + ".json");

                if (!Files.isRegularFile(htmlDescription) || !Files.isRegularFile(metadata)) {
                    return empty();
                }

                return of(new Rule(
                        matcher.group("language"),
                        htmlDescription,
                        metadata,
                        specificMetadata
                ));
            }

            private final String language;
            private final Path htmlDescription;
            private final Path metadata;
            private final Path specificMetadata;

            Rule(String language, Path htmlDescription, Path metadata, Path specificMetadata) {
                this.language = language;
                this.htmlDescription = htmlDescription;
                this.metadata = metadata;
                this.specificMetadata = specificMetadata;
            }

            Path getHtmlDescriptionTargetPath(Path targetDir) {
                return targetDir.resolve(language).resolve(htmlDescription.getFileName());
            }

            Path getMetadataTargetPath(Path targetDir) {
                return targetDir.resolve(language).resolve(metadata.getFileName());
            }
        }
    }

    PrepareResources.main();

    // @formatter:off
/exit
