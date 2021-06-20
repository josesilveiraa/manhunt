package com.downloader.api.object;
import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

@ToString @EqualsAndHashCode @Accessors(chain = true)
public final class Download {

    @Getter @Setter @NotNull
    private URL fileUri;

    @Getter @Setter @NotNull
    private File outputDir;

    @Getter @Setter @NotNull
    private String fileName;

    @Getter @Setter(AccessLevel.PRIVATE)
    private boolean finished;

    @Getter @Setter(AccessLevel.PRIVATE)
    private long fileSize;

    @Getter @Setter(AccessLevel.PRIVATE)
    private int progress;

    @Getter @Setter(AccessLevel.PRIVATE)
    private int swingProgress;

    @Setter @EqualsAndHashCode.Exclude @Nullable
    private Consumer<Download> onFinish;

    @Setter @EqualsAndHashCode.Exclude @Nullable
    private Consumer<Download> onStart;

    @Setter @EqualsAndHashCode.Exclude @Nullable
    private Consumer<Integer> onPercentChange;

    @Setter @EqualsAndHashCode.Exclude @Nullable
    private Consumer<Integer> onSwingPercentChange;

    @Setter @EqualsAndHashCode.Exclude @Nullable
    private Consumer<Exception> onError;

    @SneakyThrows
    public Download(@NotNull String fileUri, @NotNull String fileName, @NotNull File outputDir) {
        this.fileUri = new URL(fileUri);
        this.fileName = fileName;
        this.outputDir = outputDir;
    }

    public Download(@NotNull URL fileUri, @NotNull String fileName, @NotNull File outputDir) {
        this.fileUri = fileUri;
        this.fileName = fileName;
        this.outputDir = outputDir;
    }

    @SneakyThrows
    public Download(@NotNull String fileUri, @NotNull String fileName) {
        this.fileUri = new URL(fileUri);
        this.fileName = fileName;
        this.outputDir = new File("");
    }

    public Download(@NotNull URL fileUri, @NotNull String fileName) {
        this.fileUri = fileUri;
        this.fileName = fileName;
        this.outputDir = new File("");
    }

    public final void start() {
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

                final byte[] data = new byte[1024];
                long downloaded = 0;

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

                setFinished(true);

                if(onFinish != null) {
                    onFinish.accept(this);
                }

            } catch (IOException e) {
                if(onError != null) {
                    onError.accept(e);
                }
            }
        };

        new Thread(thread).start();
    }
}
