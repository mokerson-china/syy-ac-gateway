package com.syy.ac.gateway.util;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.*;

public class FilePermissionUtils {
    public FilePermissionUtils() {
    }

    public static boolean isPosixFileSystem() {
        return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
    }

    public static void adjustWindowsFilePermission(Path path) {
        String userName = System.getProperty("user.name");

        UserPrincipal user;
        try {
            user = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(userName);
        } catch (IOException var9) {
            throw new RuntimeException("Could not get principle user", var9);
        }

        AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);
        AclEntryPermission[] perms = new AclEntryPermission[]{AclEntryPermission.READ_DATA, AclEntryPermission.READ_NAMED_ATTRS, AclEntryPermission.WRITE_DATA, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.READ_ACL, AclEntryPermission.DELETE, AclEntryPermission.APPEND_DATA, AclEntryPermission.WRITE_ATTRIBUTES, AclEntryPermission.WRITE_NAMED_ATTRS, AclEntryPermission.WRITE_ACL, AclEntryPermission.SYNCHRONIZE};
        Set<AclEntryPermission> permSet = EnumSet.noneOf(AclEntryPermission.class);
        Collections.addAll(permSet, perms);
        AclEntry entry = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(user).setPermissions(permSet).setFlags(AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT).build();

        try {
            view.setAcl(Collections.singletonList(entry));
        } catch (IOException var8) {
            throw new RuntimeException("Failed to set acl", var8);
        }
    }

    public static FileAttribute<List<AclEntry>> getWindowsFilePermission(Path path) {
        String userName = System.getProperty("user.name");
        UserPrincipal user = null;

        try {
            user = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(userName);
        } catch (IOException var7) {
            throw new RuntimeException("Could not get principle user", var7);
        }

        AclEntryPermission[] perms = new AclEntryPermission[]{AclEntryPermission.READ_DATA, AclEntryPermission.READ_NAMED_ATTRS, AclEntryPermission.WRITE_DATA, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.READ_ACL, AclEntryPermission.DELETE, AclEntryPermission.APPEND_DATA, AclEntryPermission.WRITE_ATTRIBUTES, AclEntryPermission.WRITE_NAMED_ATTRS, AclEntryPermission.WRITE_ACL, AclEntryPermission.SYNCHRONIZE};
        Set<AclEntryPermission> permSet = EnumSet.noneOf(AclEntryPermission.class);
        Collections.addAll(permSet, perms);
        final AclEntry entry = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(user).setPermissions(permSet).setFlags(AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT).build();
        FileAttribute<List<AclEntry>> aclAttr = new FileAttribute<List<AclEntry>>() {
            public String name() {
                return "acl:acl";
            }

            public List<AclEntry> value() {
                return Arrays.asList(entry);
            }
        };
        return aclAttr;
    }
}