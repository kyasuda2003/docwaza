//
// Docwaza - Java OpenDocument Converter
// Copyright 2009 Art of Solving Ltd
// Copyright 2004-2009 Mirko Nasato
//
// Docwaza is free software: you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation, either version 3 of
// the License, or (at your option) any later version.
//
// Docwaza is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General
// Public License along with Docwaza.  If not, see
// <http://www.gnu.org/licenses/>.
//
// Contributors:
//     Yasuda Keisuke (kyasuda), Chia Chang (chiayear2003)
package org.kyasuda.docwaza.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class PlatformUtils {

    private static final Logger logger = Logger.getLogger(PlatformUtils.class.getName());

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    private static final String WINDOWS = "windows";

    private static final String MAC = "mac";

    private static final String LINUX = "linux";

    private static final String[] LINUX_OO_HOME_PATHS = {
            "/usr/lib/libreoffice", "/usr/lib/openoffice",
            "/usr/lib/openoffice.org", "/usr/lib/openoffice.org3","/opt/openoffice4",
            "/opt/openoffice.org3", "/opt/libreoffice", "/usr/lib/ooo" };

    private static final String[] MAC_OO_HOME_PATHS = {
            "/Applications/LibreOffice.app/Contents",
            "/Applications/OpenOffice.app/Contents" };

    private static final String[] WINDOWS_OO_HOME_PATHS = {
            System.getenv("ProgramFiles") + File.separator + "LibreOffice 3.9",
            System.getenv("ProgramFiles") + File.separator + "LibreOffice 3.8",
            System.getenv("ProgramFiles") + File.separator + "LibreOffice 3.7",
            System.getenv("ProgramFiles") + File.separator + "LibreOffice 3.6",
            System.getenv("ProgramFiles") + File.separator + "LibreOffice 3.5",
            System.getenv("ProgramFiles") + File.separator + "LibreOffice 3.4",
            System.getenv("ProgramFiles") + File.separator + "LibreOffice 3",
            System.getenv("ProgramFiles(x86)") + File.separator
                    + "LibreOffice 3.9",
            System.getenv("ProgramFiles(x86)") + File.separator
                    + "LibreOffice 3.8",
            System.getenv("ProgramFiles(x86)") + File.separator
                    + "LibreOffice 3.7",
            System.getenv("ProgramFiles(x86)") + File.separator
                    + "LibreOffice 3.6",
            System.getenv("ProgramFiles(x86)") + File.separator
                    + "LibreOffice 3.5",
            System.getenv("ProgramFiles(x86)") + File.separator
                    + "LibreOffice 3.4",
            System.getenv("ProgramFiles(x86)") + File.separator
                    + "LibreOffice 3",
            System.getenv("ProgramFiles") + File.separator + "OpenOffice.org 3",
            System.getenv("ProgramFiles(x86)") + File.separator
                    + "OpenOffice.org 3", };

    private static final String[] LINUX_OO_PROFILE_PATHS = {
            System.getProperty("user.home") + File.separator
                    + ".openoffice.org/4",
            System.getProperty("user.home") + File.separator + ".libreoffice/4" };

    private static final String[] MAC_OO_PROFILE_PATHS = {
            System.getProperty("user.home") + File.separator
                    + "Library/Application Support/OpenOffice.org/4",
            System.getProperty("user.home") + File.separator
                    + "Library/Application Support/LibreOffice.org/4" };

    private static final String[] WINDOWS_OO_PROFILE_PATHS = {
            System.getenv("APPDATA") + File.separator + "LibreOffice.org/3",
            System.getenv("APPDATA") + File.separator + "OpenOffice.org/3" };

    private static String officeProfileDir = null;

    private static String officeHome = null;

    private PlatformUtils() {
        throw new AssertionError("utility class must not be instantiated");
    }

    public static boolean isLinux() {
        return OS_NAME.startsWith(LINUX);
    }

    public static boolean isMac() {
        return OS_NAME.startsWith(MAC);
    }

    public static boolean isWindows() {
        return OS_NAME.startsWith(WINDOWS);
    }

    /**
     * Search for OpenOffice or LibreOffice on default paths.
     * 
     * @return path to Office home or an empty String if not found.
     */
    public static String findOfficeHome() {
        if (officeHome == null) {
            String[] homeList = new String[0];
            if (isLinux()) {
                homeList = LINUX_OO_HOME_PATHS;
            } else if (isMac()) {
                homeList = MAC_OO_HOME_PATHS;
            } else if (isWindows()) {
                homeList = WINDOWS_OO_HOME_PATHS;
            }
            officeHome = searchExistingfile(Arrays.asList(homeList));
        }
        return officeHome;
    }

    /**
     * Search for OpenOffice or LibreOffice user profile on default paths.
     * 
     * @return path to Office profile or an empty String if not found.
     */
    public static String findOfficeProfileDir() {
        if (officeProfileDir == null) {
            String[] profileDirList = new String[0];
            if (isLinux()) {
                profileDirList = LINUX_OO_PROFILE_PATHS;
            } else if (isMac()) {
                profileDirList = MAC_OO_PROFILE_PATHS;
            } else if (isWindows()) {
                profileDirList = WINDOWS_OO_PROFILE_PATHS;
            }
            officeProfileDir = searchExistingfile(Arrays.asList(profileDirList));
        }
        return officeProfileDir;
    }

    protected static String searchExistingfile(List<String> pathList) {
        for (String path : pathList) {
            if (new File(path).exists()) {
                logger.info("Jod will be using " + path);
                return path;
            }
        }
        return "";
    }

}
