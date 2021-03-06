//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.jminusminus.core;

import github.com.jminusminus.simplebdd.Test;

public class Fs_test extends Test {
    public static void main(String[] args) {
        Fs_test test = new Fs_test();
        test.run();
    }

    protected String tmp = "./fixtures/filesystem/tmp.txt";

    public void test_new_FileSystem() {
        this.should("return an instance of Fs");
        Fs fs = new Fs();
        this.assertEqual("github.com.jminusminus.core.Fs", fs.getClass().getName());
    }

    public void test_readdir() {
        this.should("return an array of files");
        String[] files = Fs.readdir("./fixtures/filesystem");
        java.util.Arrays.sort(files);
        this.assertEqual("execute.txt", files[0]);
    }

    public void test_readdir_bad_path() {
        this.should("return null as the directory doesn't exist");
        String[] files = Fs.readdir("/foo/bar");
        this.assertEqual(true, files == null);
    }

    public void test_readdirr() {
        this.should("return an array of files from all sub directories");
        String[] files = Fs.readdirr("./fixtures");
        java.util.Arrays.sort(files);
        this.assertEqual(13, files.length);
    }

    public void test_chown_bad_user() {
        this.should("return false as the chmod user is invalid");
        Fs.writeFile("./fixtures/filesystem/xchown.txt", "chown".getBytes());
        this.assertEqual(false, Fs.chown("./fixtures/filesystem/xchown.txt", "xxx"));
    }

    public void test_chown_bad_bad_file() {
        this.should("return false as the file doesn't exist");
        this.assertEqual(false, Fs.chown("/dev/null/foo", "root"));
    }

    public void test_chmod_bad() {
        this.should("return false as the chmod is invalid");
        this.assertEqual(false, Fs.chmod("./fixtures/filesystem/exists.txt", 888));
    }

    public void test_access_bad() {
        this.should("return false as the mode is invalid");
        this.assertEqual(false, Fs.access("./fixtures/filesystem/exists.txt", 888));
    }

    public void test_access_exists_true() {
        this.should("return true as the file exists");
        Fs.chmod("./fixtures/filesystem/exists.txt", Fs.F_OK);
        this.assertEqual(true, Fs.access("./fixtures/filesystem/exists.txt"));
    }

    public void test_access_exists_false() {
        this.should("return false as the file doesn't exists");
        this.assertEqual(false, Fs.access("./fixtures/filesystem/no.txt"));
    }

    public void test_access_execute_true() {
        this.should("return true as the file can execute");
        Fs.chmod("./fixtures/filesystem/execute.txt", Fs.X_OK);
        this.assertEqual(true, Fs.access("./fixtures/filesystem/execute.txt", Fs.X_OK));
    }

    public void test_access_execute_false() {
        this.should("return false as the file cannot execute");
        Fs.chmod("./fixtures/filesystem/read.txt", Fs.R_OK);
        this.assertEqual(false, Fs.access("./fixtures/filesystem/read.txt", Fs.X_OK));
    }

    public void test_access_write_true() {
        this.should("return true as the file can be written");
        Fs.chmod("./fixtures/filesystem/write.txt", Fs.W_OK);
        this.assertEqual(true, Fs.access("./fixtures/filesystem/write.txt", Fs.W_OK));
    }

    public void test_access_write_false() {
        this.should("return false as the file cannot be written");
        Fs.chmod("./fixtures/filesystem/execute.txt", Fs.X_OK);
        this.assertEqual(false, Fs.access("./fixtures/filesystem/execute.txt", Fs.W_OK));
    }

    public void test_access_write_execute_true() {
        this.should("return true as the file can be read and executed");
        Fs.chmod("./fixtures/filesystem/write_execute.txt", Fs.W_OK | Fs.X_OK);
        this.assertEqual(true, Fs.access("./fixtures/filesystem/write_execute.txt", Fs.W_OK | Fs.X_OK));
    }

    public void test_access_write_execute_false() {
        this.should("return false as the file cannot be read and executed");
        Fs.chmod("./fixtures/filesystem/write.txt", Fs.W_OK);
        this.assertEqual(false, Fs.access("./fixtures/filesystem/write.txt", Fs.W_OK | Fs.X_OK));
    }

    public void test_access_read_true() {
        this.should("return true as the file can be read");
        Fs.chmod("./fixtures/filesystem/read.txt", Fs.R_OK);
        this.assertEqual(true, Fs.access("./fixtures/filesystem/read.txt", Fs.R_OK));
    }

    public void test_access_read_false() {
        this.should("return false as the file cannot be read");
        this.assertEqual(false, Fs.access("./fixtures/filesystem/no.txt", Fs.R_OK));
    }

    public void test_access_read_execute_true() {
        this.should("return true as the file can be read and executed");
        Fs.chmod("./fixtures/filesystem/read_execute.txt", Fs.R_OK | Fs.X_OK);
        this.assertEqual(true, Fs.access("./fixtures/filesystem/read_execute.txt", Fs.R_OK | Fs.X_OK));
    }

    public void test_access_read_execute_false() {
        this.should("return false as the file cannot be read and executed");
        Fs.chmod("./fixtures/filesystem/read_write.txt", Fs.R_OK | Fs.W_OK);
        this.assertEqual(false, Fs.access("./fixtures/filesystem/read_write.txt", Fs.R_OK | Fs.X_OK));
    }

    public void test_access_read_write_true() {
        this.should("return true as the file can be read and written");
        Fs.chmod("./fixtures/filesystem/read_write.txt", Fs.R_OK | Fs.W_OK);
        this.assertEqual(true, Fs.access("./fixtures/filesystem/read_write.txt", Fs.R_OK | Fs.W_OK));
    }

    public void test_access_read_write_false() {
        this.should("return false as the file cannot be read and written");
        Fs.chmod("./fixtures/filesystem/read_execute.txt", Fs.R_OK | Fs.X_OK);
        this.assertEqual(false, Fs.access("./fixtures/filesystem/read_execute.txt", Fs.R_OK | Fs.W_OK));
    }

    public void test_access_read_write_execute_true() {
        this.should("return true as the file can be read, written and executed");
        Fs.chmod("./fixtures/filesystem/read_write_execute.txt", Fs.R_OK | Fs.W_OK | Fs.X_OK);
        this.assertEqual(true, Fs.access("./fixtures/filesystem/read_write_execute.txt", Fs.R_OK | Fs.W_OK | Fs.X_OK));
    }

    public void test_access_read_write_execute_false() {
        this.should("return false as the file cannot be read, written and executed");
        Fs.chmod("./fixtures/filesystem/read_write.txt", Fs.R_OK | Fs.W_OK);
        this.assertEqual(false, Fs.access("./fixtures/filesystem/read_write.txt", Fs.R_OK | Fs.W_OK | Fs.X_OK));
    }

    public void test_appendFile_new() {
        this.should("create a new file and write the bytes to it");
        this.assertEqual(true, Fs.appendFile(this.tmp, "test".getBytes()));
        Fs.unlink(this.tmp);
    }

    public void test_appendFile_existing() {
        this.should("append bytes to an existing file");
        Fs.appendFile(this.tmp, "test".getBytes());
        this.assertEqual(true, Fs.appendFile(this.tmp, "test".getBytes()));
        Fs.unlink(this.tmp);
    }

    public void test_appendFile_string() {
        this.should("create a new file and append the string to it");
        this.assertEqual(true, Fs.appendFile(this.tmp, "test"));
        Fs.unlink(this.tmp);
    }

    public void test_appendFile_string_encoding() {
        this.should("create a new file and append the string as utf8");
        this.assertEqual(true, Fs.appendFile(this.tmp, "test", "utf8"));
        Fs.unlink(this.tmp);
    }

    public void test_appendFile_string_bad_encoding() {
        this.should("create a new file and append the string as utf8");
        this.assertEqual(false, Fs.appendFile(this.tmp, "test", "xxx"));
        Fs.unlink(this.tmp);
    }

    public void test_appendFile_dev_null() {
        this.should("return false as file cannot be created");
        this.assertEqual(false, Fs.appendFile("/dev/null/foo", "test".getBytes()));
    }

    public void test_unlink() {
        this.should("create a file and then unlink it");
        Fs.appendFile(this.tmp, "test".getBytes());
        this.assertEqual(true, Fs.unlink(this.tmp));
    }

    public void test_unlink_no_file() {
        this.should("return true even if the file doesn't exist");
        this.assertEqual(false, Fs.unlink(this.tmp));
    }

    public void test_stat_file() {
        this.should("return true from file");
        Stat s = Fs.stat("./fixtures/filesystem/read_write_execute.txt");
        this.assertEqual(false, s.dir);
        this.assertEqual(true, s.file);
    }

    public void test_stat_dir() {
        this.should("return true from dir");
        Stat s = Fs.stat("./fixtures/filesystem");
        this.assertEqual(true, s.dir);
        this.assertEqual(false, s.file);
    }

    public void test_readFile() {
        this.should("return the content of the file");
        byte[] b = Fs.readFile("./fixtures/filesystem/read.txt");
        this.assertEqual("read", new String(b));
    }

    public void test_readFile_null() {
        this.should("return the content of the file");
        this.assertEqual(true, Fs.readFile("/dev/null/foo") == null);
    }

    public void test_writeFile() {
        this.should("return true after the file is written");
        Fs.chmod("./fixtures/filesystem/write.txt", Fs.R_OK | Fs.W_OK);
        this.assertEqual(true, Fs.writeFile("./fixtures/filesystem/write.txt", "write foo".getBytes()));
        byte[] b = Fs.readFile("./fixtures/filesystem/write.txt");
        this.assertEqual("write foo", new String(b));
    }

    public void test_writeFile_string() {
        this.should("create a new file and write the string to it");
        this.assertEqual(true, Fs.writeFile(this.tmp, "test"));
        Fs.unlink(this.tmp);
    }

    public void test_writeFile_string_encoding() {
        this.should("create a new file and write the string as utf8");
        this.assertEqual(true, Fs.writeFile(this.tmp, "test", "utf8"));
        Fs.unlink(this.tmp);
    }

    public void test_writeFile_string_bad_encoding() {
        this.should("create a new file and write the string as utf8");
        this.assertEqual(false, Fs.writeFile(this.tmp, "test", "xxx"));
        Fs.unlink(this.tmp);
    }

    public void test_writeFile_null() {
        this.should("return false as the file content be written");
        this.assertEqual(false, Fs.writeFile("/dev/null/foo", "write foo".getBytes()));
    }

    public void test_mkdir() {
        this.should("create the directory");
        boolean b = Fs.mkdir("./fixtures/filesystem/newDir");
        this.assertEqual(true, b);
        Fs.rmdir("./fixtures/filesystem/newDir");
    }

    public void test_mkdir_null() {
        this.should("return false as the directory cannot be made");
        this.assertEqual(false, Fs.mkdir("/dev/null/foo"));
    }

    public void test_mkdirs() {
        this.should("create the directories");
        boolean b = Fs.mkdirs("./fixtures/filesystem/newDir/newSubDir");
        this.assertEqual(true, b);
        Fs.rmdir("./fixtures/filesystem/newDir/newSubDir");
        Fs.rmdir("./fixtures/filesystem/newDir");
    }

    public void test_mkdirs_null() {
        this.should("return false as the directories cannot be made");
        this.assertEqual(false, Fs.mkdirs("/dev/null/foo/bar"));
    }

    public void test_truncate() {
        this.should("truncate the file to 10 bytes");
        Fs.writeFile("./fixtures/filesystem/truncate.txt", "12345678901234567890".getBytes());
        this.assertEqual((long)20, Fs.stat("./fixtures/filesystem/truncate.txt").size);
        Fs.truncate("./fixtures/filesystem/truncate.txt", 10);
        this.assertEqual((long)10, Fs.stat("./fixtures/filesystem/truncate.txt").size);
        Fs.unlink("./fixtures/filesystem/truncate.txt");
    }

    public void test_truncate_null() {
        this.should("return false as truncate cannot be called");
        this.assertEqual(false, Fs.truncate("/dev/null/foo", 5));
    }

    public void test_stat_null() {
        this.should("return false as stat cannot be called");
        this.assertEqual(true, Fs.stat("/dev/null/foo") == null);
    }

    public void test_rename_same() {
        this.should("return true as the file renamed it's self");
        this.assertEqual(true, Fs.rename("./fixtures/filesystem/rename.txt", "./fixtures/filesystem/rename.txt"));
    }

    public void test_rename_different() {
        this.should("return true as the file is renamed");
        this.assertEqual(true, Fs.rename("./fixtures/filesystem/rename.txt", "./fixtures/filesystem/rename1.txt"));
        this.assertEqual(true, Fs.rename("./fixtures/filesystem/rename1.txt", "./fixtures/filesystem/rename.txt"));
    }

    public void test_rename_null() {
        this.should("return false as the file cannot be renamed");
        this.assertEqual(false, Fs.rename("/dev/null/foo", "/dev/null/bar"));
    }

    public void test_mtime() {
        this.should("set the new modified time on the file");
        Fs.mtime("./fixtures/filesystem/modified.txt", (long)0);
        this.assertEqual(true, Fs.mtime("./fixtures/filesystem/modified.txt", (long)123451000));
        this.assertEqual((long)123451000, Fs.stat("./fixtures/filesystem/modified.txt").modify);
    }

    public void test_mtime_null() {
        this.should("return false as the file doesn't exist");
        this.assertEqual(false, Fs.mtime("/dev/null/foo", (long)123451000));
    }

    public void test_rmdirr() {
        this.should("remove directory and sub folders");
        Fs.mkdirs("./fixtures/tmp/foo/bar");
        Fs.writeFile("./fixtures/tmp/foo/tmp.txt", "bar");
        this.assertEqual(true, Fs.rmdirr("./fixtures/tmp"));
    }
}
