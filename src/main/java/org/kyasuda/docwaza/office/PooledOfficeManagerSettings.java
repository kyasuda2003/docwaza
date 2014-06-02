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

class PooledOfficeManagerSettings extends ManagedOfficeProcessSettings {

    public static final long DEFAULT_TASK_EXECUTION_TIMEOUT = 120000L;

    public static final int DEFAULT_MAX_TASKS_PER_PROCESS = 200;

    private long taskExecutionTimeout = DEFAULT_TASK_EXECUTION_TIMEOUT;

    private int maxTasksPerProcess = DEFAULT_MAX_TASKS_PER_PROCESS;

    public PooledOfficeManagerSettings(UnoUrl unoUrl) {
        super(unoUrl);
    }

    public long getTaskExecutionTimeout() {
        return taskExecutionTimeout;
    }

    public void setTaskExecutionTimeout(long taskExecutionTimeout) {
        this.taskExecutionTimeout = taskExecutionTimeout;
    }

    public int getMaxTasksPerProcess() {
        return maxTasksPerProcess;
    }

    public void setMaxTasksPerProcess(int maxTasksPerProcess) {
        this.maxTasksPerProcess = maxTasksPerProcess;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\ntaskExecutionTimeout :" + taskExecutionTimeout);
        sb.append("\nmaxTasksPerProcess :" + maxTasksPerProcess);
        return sb.toString();
    }
}
