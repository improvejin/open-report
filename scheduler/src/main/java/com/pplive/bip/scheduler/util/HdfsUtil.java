package com.pplive.bip.scheduler.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;

/**
 * Created by jiatingjin on 2018/1/11.
 */
public class HdfsUtil {


    public static Path save(String content, String path) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path p = new Path(path);

        Path dir = p.getParent();

        if (fs.exists(p) == false || fs.isDirectory(p) == false) {
            fs.mkdirs(dir);
        }

        FSDataOutputStream os =  fs.create(p);
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.flush();
        bw.close();
        osw.close();
        os.close();
        return p;
    }
}
