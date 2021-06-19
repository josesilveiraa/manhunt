package org.josesilveiraa.manhunt.update;

import com.downloader.api.object.Download;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import org.bukkit.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.log.LogManager;
import org.josesilveiraa.manhunt.log.LogType;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.stream.Collectors;

public final class UpdateChecker {

    private final Main plugin;
    private final String version;
    private final URL url;

    @SneakyThrows
    public UpdateChecker(@NotNull Main plugin) {
        this.plugin = plugin;
        this.version = "v" + this.plugin.getDescription().getVersion();
        this.url = new URL("https://api.github.com/repos/Josesilveiraa/manhunt/releases/latest");
    }

    @SneakyThrows
    public final void check() {
        URLConnection connection = this.url.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String resp = reader.lines().collect(Collectors.joining("\n"));

        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(resp).getAsJsonObject();
        String latestVersion = obj.get("tag_name").getAsString();

        if(!this.version.equals(latestVersion)) {
            LogManager.log("A new version is available! The plugin will download it now.", LogType.INFO);
            downloadLatestUpdate();
        } else {
            LogManager.log("You're using the latest Manhunt version.", LogType.INFO);
        }
    }

    @SneakyThrows
    public final void downloadLatestUpdate() {
        URLConnection connection = this.url.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String resp = reader.lines().collect(Collectors.joining("\n"));
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(resp).getAsJsonObject();

        String artifact = obj.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        String downloadUrl = "https://github.com/Josesilveiraa/manhunt/releases/latest/download/" + artifact;

        Download download = new Download(downloadUrl, "/update/" + artifact, this.plugin.getDataFolder())
                .setOnFinish((d -> this.plugin.getLogger().log(Level.INFO, "Downloaded " + artifact + " successfully.")))
                .setOnError(Throwable::printStackTrace)
                .setOnFinish((d) -> {
                    LogManager.log("Update downloaded successfully! You can locate it in the Manhunt directory.", LogType.INFO);
                });
        download.start();
    }

}
