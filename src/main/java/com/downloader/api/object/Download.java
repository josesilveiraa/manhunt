package com.downloader.api.object;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

@ToString @EqualsAndHashCode @Accessors(chain = true)
public class Download {


    @Getter @Setter private URL fileUri;
    @Getter @Setter private File outputDir;
    @Getter @Setter private String fileName;

    @Getter @Setter(AccessLevel.PRIVATE) private boolean finished;

    @Getter @Setter(AccessLevel.PRIVATE) private long fileSize;

    @Getter @Setter(AccessLevel.PRIVATE) private int progress;

    @Getter @Setter(AccessLevel.PRIVATE) private int swingProgress;

    @Setter @EqualsAndHashCode.Exclude
    private Consumer<Download> onFinish;

    @Setter @EqualsAndHashCode.Exclude
    private Consumer<Download> onStart;

    @Setter @EqualsAndHashCode.Exclude
    private Consumer<Integer> onPercentChange;

    @Setter @EqualsAndHashCode.Exclude
    private Consumer<Integer> onSwingPercentChange;

    @Setter @EqualsAndHashCode.Exclude
    private Consumer<Exception> onError;

    @SneakyThrows
    public Download(String fileUri, String fileName, File outputDir) {
        this.fileUri = new URL(fileUri);
        this.fileName = fileName;
        this.outputDir = outputDir;
    }

    public Download(URL fileUri, String fileName, File outputDir) {
        this.fileUri = fileUri;
        this.fileName = fileName;
        this.outputDir = outputDir;
    }

    @SneakyThrows
    public Download(String fileUri, String fileName) {
        this.fileUri = new URL(fileUri);
        this.fileName = fileName;
        this.outputDir = new File(""); // Stores the jar where the JVM was invoked
    }

    public void start() {
        setFinished(false);

        Runnable thread = () -> {
            try {

                URL url = this.fileUri;
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                long fileSize = connection.getContentLength();

                setFileSize(fileSize);

                BufferedInputStream inStream = new BufferedInputStream(connection.getInputStream());
                FileOutputStream fileOutputStream = new FileOutputStream(getOutputDir() + getFileName());
                BufferedOutputStream outStream = new BufferedOutputStream(fileOutputStream, 1024);

                byte[] data = new byte[1024];
                long downloaded = 0;

                // Accept the onStart consumer.
                if(onStart != null) {
                    onStart.accept(this);
                }

                int x;

                while((x = inStream.read(data, 0, 1024)) >= 0) {
                    downloaded += x;

                    final int currentProgress = (int) ((((double)downloaded) / ((double)fileSize)) * 100d);
                    final int currentSwingProgress = (int) ((((double)downloaded) / ((double)fileSize)) * 100000d);

                    setProgress(currentProgress);
                    setSwingProgress(currentSwingProgress);

                    if(onPercentChange != null) {
                        onPercentChange.accept(progress);
                    }

                    if(onSwingPercentChange != null) {
                        onSwingPercentChange.accept(currentSwingProgress);
                    }

                    outStream.write(data, 0, x);
                }
                outStream.close();
                inStream.close();

                // Set the finished status to true and accept the onFinish consumer.
                setFinished(true);

                if(onFinish != null) {
                    onFinish.accept(this);
                }



            } catch (IOException e) {
                onError.accept(e);
            }
        };

        new Thread(thread).start();
    }
}
