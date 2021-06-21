package org.josesilveiraa.manhunt.update;

import com.downloader.api.object.Download;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.log.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public final class UpdateChecker {

    private final Main plugin;
    private final String version;
    private final JsonObject mainObj;

    public UpdateChecker(@NotNull Main plugin) throws IOException {
        this.plugin = plugin;
        this.version = "v" + this.plugin.getDescription().getVersion();
        URL url = new URL("https://api.github.com/repos/Josesilveiraa/manhunt/releases/latest");
        JsonParser jsonParser = new JsonParser();
        URLConnection urlConnection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String response = reader.lines().collect(Collectors.joining("\n"));
        this.mainObj = jsonParser.parse(response).getAsJsonObject();
    }

    public final void check() throws MalformedURLException {
        String latestVersion = this.mainObj.get("tag_name").getAsString();

        if(!this.version.equals(latestVersion)) {
            LogManager.log("A new version is available (" + latestVersion + "). The plugin will download it now.", LogLevel.INFO);
            downloadLatestUpdate();
        } else {
            LogManager.log("You're using the latest Manhunt version (" + this.version + ").", LogLevel.INFO);
        }
    }

    public final void downloadLatestUpdate() throws MalformedURLException {
        String artifact = this.mainObj.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        URL downloadUrl = new URL("https://github.com/Josesilveiraa/manhunt/releases/latest/download/" + artifact);

        File f = new File(this.plugin.getDataFolder(), "update");

        if(!f.exists()) {
            if(f.mkdir()) {
                LogManager.log("Update directory created.", LogLevel.INFO);
            }
        }

        Download download = new Download(downloadUrl, "/update/" + artifact, this.plugin.getDataFolder())
                .setOnError(d -> LogManager.log("Couldn't check for new updates.", LogLevel.ERROR))
                .setOnFinish(d -> LogManager.log("Update downloaded successfully! You can locate it in the Manhunt directory.", LogLevel.INFO));

        download.start();
    }

}
