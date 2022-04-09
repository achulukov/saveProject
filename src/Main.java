import gameProgress.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {

    private static String slash = File.separator;
    private static String gamesSave = "Games" + slash + "savegames" + slash;

    private static void saveGame(GameProgress progress, String path) {
        try(FileOutputStream fosProgress  = new FileOutputStream(path);
            ObjectOutputStream oosProgress = new ObjectOutputStream(fosProgress)) {

            oosProgress.writeObject(progress);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void zipFiles(String pathZIP, String ... pathFile) {
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZIP))) {

            for (String s : pathFile) {

                try (FileInputStream fis = new FileInputStream(s)) {

                    int lastIndexSlash = s.lastIndexOf(slash);


                    ZipEntry entry = new ZipEntry(s.substring(lastIndexSlash + 1));
                    zout.putNextEntry(entry);

                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);

                    zout.write(buffer);

                    zout.closeEntry();

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    };

    public static void main(String[] args) {

        File saveDir = new File("Games" + slash + "savegames");

        GameProgress player1 = new GameProgress(82,3, 32, 54.2);
        GameProgress player2 = new GameProgress(76,1, 15, 10.4);
        GameProgress player3 = new GameProgress(100,9, 80, 102.7);

        saveGame(player1, gamesSave + "save1.dat");
        saveGame(player2, gamesSave + "save2.dat");
        saveGame(player3, gamesSave + "save3.dat");

        zipFiles(gamesSave + "zip.zip",
                gamesSave + "save1.dat",
                gamesSave + "save2.dat",
                gamesSave + "save3.dat");

        for (File item : saveDir.listFiles()){
            if (!item.getName().equals("zip.zip")) item.delete();
        }
    }
}
