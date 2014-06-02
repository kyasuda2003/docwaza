//
// Docwaza - Java OpenDocument Converter
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
package org.kyasuda.docwaza.office;

import java.io.File;

import org.kyasuda.docwaza.process.ProcessManager;
import org.kyasuda.docwaza.process.PureJavaProcessManager;

class ManagedOfficeProcessSettings {

    public static final long DEFAULT_RETRY_TIMEOUT = 30000L;

    public static final long DEFAULT_RETRY_INTERVAL = 250L;

    private final UnoUrl unoUrl;

    private File officeHome = OfficeUtils.getDefaultOfficeHome();

    private File templateProfileDir;

    private ProcessManager processManager = new PureJavaProcessManager();

    private long retryTimeout = DEFAULT_RETRY_TIMEOUT;

    private long retryInterval = DEFAULT_RETRY_INTERVAL;

    protected boolean useGnuStyleLongOptions = false;

    public ManagedOfficeProcessSettings(UnoUrl unoUrl) {
        this.unoUrl = unoUrl;
    }

    public UnoUrl getUnoUrl() {
        return unoUrl;
    }

    public File getOfficeHome() {
        return officeHome;
    }

    public void setOfficeHome(File officeHome) {
        this.officeHome = officeHome;
    }

    public File getTemplateProfileDir() {
        return templateProfileDir;
    }

    public void setTemplateProfileDir(File templateProfileDir) {
        this.templateProfileDir = templateProfileDir;
    }

    public ProcessManager getProcessManager() {
        return processManager;
    }

    public void setProcessManager(ProcessManager processManager) {
        this.processManager = processManager;
    }

    public long getRetryTimeout() {
        return retryTimeout;
    }

    public void setRetryTimeout(long retryTimeout) {
        this.retryTimeout = retryTimeout;
    }

    public long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval;
    }

    public boolean isUseGnuStyleLongOptions() {
        return useGnuStyleLongOptions;
    }

    public void setUseGnuStyleLongOptions(boolean useGnuStyleLongOptions) {
        this.useGnuStyleLongOptions = useGnuStyleLongOptions;
    }

}
