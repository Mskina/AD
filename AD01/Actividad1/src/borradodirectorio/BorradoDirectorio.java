/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package borradodirectorio;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

/**
 *
 * @author mskin
 */
public class BorradoDirectorio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO code application logic here

        // Método 2
        System.out.println("--Iniciando método 2--\n");
        File metodo2 = new File("./metodo2/test/probando/manolo");
        metodo2.mkdirs();

        Thread.sleep(2000);
        borrar(new File("./metodo2"));
        System.out.println("-- Finalizado método 2--\n");

        //Método 5
        System.out.println("--Iniciando método 5--\n");
        File metodo5 = new File("./metodo5/test/probando/manolo");
        metodo5.mkdirs();
        Path p5 = Paths.get("./metodo5");
        borrarDirectorioWalk(p5);
        System.out.println("-- Finalizado método 5--\n");

        // Método 6
        System.out.println("--Iniciando método 6--\n");
        File metodo6 = new File("./metodo6/test/probando/manolo");
        metodo6.mkdirs();
        Path p6 = Paths.get("./metodo6");
        borrarWalkStream(p6);
        System.out.println("-- Finalizado método 6--\n");
    }

    /**
     * Método 5 https://www.baeldung.com/java-delete-directory
     *
     * @param f
     * @return
     */
    static boolean borrar(File f) {
        // Creamos un array con los contenidos del File (directorio o archivo, por determinar)
        File[] contenidos = f.listFiles();
        // contenidos es 'null' si 'f' no es un directorio, por lo que no entra en el if y entra en el .delete
        if (contenidos != null) { // Si es un directorio, lo recorre eliminando
            for (File file : contenidos) {
                borrar(file);
            }
        }

        System.out.println("borrado: " + f.getAbsolutePath());
        return f.delete();
    }

    //Path pathToBeDeleted = Paths.get("./prueba");
    static void borrarDirectorioWalk(Path pathToBeDeleted) throws IOException {

        try {
            Files.walkFileTree(pathToBeDeleted, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    System.out.println("preVisitDirectory: he visitado " + dir.toString());
                    return super.preVisitDirectory(dir, attrs); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("visitFile: he visitado " + file.toString());
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.out.println("visitFileFailed: lo siento, he fallado en: " + file.toString());
                    return super.visitFileFailed(file, exc); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    System.out.println("postVisitDirectory: se acabó el paseo en " + dir.toString());
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            System.out.println("Oh no, excecpión " + ex);
        }

    }

    /**
     * Método 6 https://www.baeldung.com/java-delete-directory
     *
     * @param pathToBeDeleted
     * @throws IOException
     */
    static void borrarWalkStream(Path pathToBeDeleted) throws IOException {
        // PROPIO
        //        try {
        //            Files.walk(pathToBeDeleted)
        //                    .sorted(Comparator.reverseOrder()) //Ordena en orden inverso
        //                    .map(Path::toFile) // Mapea cada Path a un File
        //                    .forEach(File::delete); // Elimina cada File
        //            System.out.println("Borrado: "
        //                  + pathToBeDeleted.toString());
        //        } catch (IOException ex) {
        //            System.out.println("Oh no, excecpión " + ex);
        //        }

        // PROFESORA
        try {
            Files.walk(pathToBeDeleted)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(f -> {
                System.out.println("Borrando "
                        + f.getAbsolutePath());
                f.delete();
            });
        } catch (IOException ex) {
            System.out.println("Ha ocurrido una exception: " + ex);
        }
    }
}