package com.pplive.bip.scheduler.oozie.ActionBuilder;

import com.pplive.bip.exception.EngineNotSupportException;
import com.pplive.bip.report.Engine;
import com.pplive.bip.report.ResultImporter;
import com.pplive.bip.scheduler.oozie.workflow.Sqoop;
import com.pplive.bip.util.PathUtil;
import com.pplive.bip.util.SystemConfig;

import java.util.List;

/**
 * Created by jiatingjin on 2018/1/10.
 */
public class SqoopActionBuilder {
    private String jobTracker="${jobTracker}";
    private String nameNode="${nameNode}";


    private ResultImporter importer;

    public SqoopActionBuilder(ResultImporter importer) {
        this.importer = importer;
    }

    public Sqoop build() {
        //todo 如果目的地是hive的话啥都不做
        Sqoop s = new Sqoop();
        s.setJobTracker(jobTracker);
        s.setNameNode(nameNode);
        List<String> args = s.getArg();
        args.add("export");
        args.add("--connect");
        args.add(getConnectJdbcUri(importer.getStorePlace()));
        Pair pair = getPasswordFile(importer.getStorePlace());
        args.add("--username");
        args.add(pair.userName);
        args.add("--password-file");
        args.add(pair.pwdFile);
        args.add("--export-dir");
        args.add(importer.getInputDir());
        args.add("--table");
        args.add(importer.getTable());
        args.add("--update-mode");
        args.add("allowinsert");
        args.add("--update-key");
        args.add("dt");
        return s;
    }

    public String getConnectJdbcUri(Engine storage) {
        String jdbcUri;
        switch (storage) {
            case MYSQL:
                jdbcUri = SystemConfig.getProperty("report.result.mysql.jdbc");
                break;
            case SQLSERVER:
                jdbcUri = SystemConfig.getProperty("report.result.sqlserver.jdbc");
                break;
            default:
                jdbcUri = null;
        }
        return jdbcUri;
    }

    public Pair getPasswordFile(Engine storage) {
        String pwdDir = SystemConfig.getProperty("report.result.pwd.dir");
        String pwdFile;
        String user;
        switch (storage) {
            case MYSQL:
                pwdFile = PathUtil.combine(pwdDir, "mysql.pwd");
                user = SystemConfig.getProperty("report.result.mysql.user");
                break;
            case SQLSERVER:
                pwdFile = PathUtil.combine(pwdDir, "sqlserver.pwd");
                user = SystemConfig.getProperty("report.result.sqlserver.user");
                break;
            default:
               throw new EngineNotSupportException(storage.toString());
        }
        return new Pair(user, pwdFile);
    }

    private class Pair{
        String userName;
        String pwdFile;

        public Pair(String userName, String pwdFile) {
            this.userName = userName;
            this.pwdFile = pwdFile;
        }
    }
}
