package org.answer.common.util.zip;

/**
 * <p>
 * </p>
 *
 * @author Answer.AI.L
 * @version 1.0
 * @date 2019-09-02
 */
public class TarUtilsApp {

    public static void main(String[] args) throws Exception {
        String srcFile = "/home/util/tar/pack.tar";
        TarUtils.unPack(srcFile);
        //output: /home/util/tar/


        String srcDir = "/home/util/tar/logs";
        TarUtils.pack(srcDir);
        // output: /home/util/tar/logs.tar
    }

}