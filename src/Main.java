import gameProgress.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {


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
                    String[] lastPath = s.split("/");

                    ZipEntry entry = new ZipEntry(lastPath[lastPath.length - 1]);
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

        File saveDir = new File("Games/savegames");

        GameProgress player1 = new GameProgress(82,3, 32, 54.2);
        GameProgress player2 = new GameProgress(76,1, 15, 10.4);
        GameProgress player3 = new GameProgress(100,9, 80, 102.7);

        saveGame(player1, "Games/savegames/save1.dat");
        saveGame(player2, "Games/savegames/save2.dat");
        saveGame(player3, "Games/savegames/save3.dat");

        zipFiles("Games/savegames/zip.zip",
                "Games/savegames/save1.dat",
                "Games/savegames/save2.dat",
                "Games/savegames/save3.dat");

        for (File item : saveDir.listFiles()){
            if (!item.getName().equals("zip.zip")) item.delete();
        }
    }
}
