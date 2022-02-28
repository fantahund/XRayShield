package com.trophonix.xrayshield;

import org.bukkit.Bukkit;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logs {

  private File parentFile;
  private SimpleDateFormat fileNameFormat;
private final XRayShield plugin;

  private String currentFileName;
  private List<String> messages = new ArrayList<>();

  public Logs(File parentFile, String fileNameFormat, XRayShield plugin) {
    this.parentFile = parentFile;
    this.fileNameFormat = new SimpleDateFormat(fileNameFormat);
    this.plugin = plugin;
  }

  public void push(String message) {
    checkDate();
    messages.add(message);
  }

  public void save() {
    if (!messages.isEmpty()) {
      try {
        File logfolder = new File(plugin.getDataFolder() + "/logs");
        System.out.println(logfolder.getAbsolutePath());
        if (!logfolder.exists()){
          logfolder.mkdirs();
        }


        File file = new File(parentFile, currentFileName);
        FileWriter fileWriter = new FileWriter(file, true);

        if (!file.exists()) {
          file.getParentFile().mkdirs();
          file.createNewFile();
        }
        for (String message : messages) fileWriter.write(message + "\n");
        fileWriter.close();
        messages.clear();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void checkDate() {
    String fileName = fileNameFormat.format(new Date());
    if (currentFileName == null || !currentFileName.equals(fileName)) {
      save();
      currentFileName = fileName;
    }
  }

}
