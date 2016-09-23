package edu.cwru.sail.imagelearning.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import edu.cwru.sail.imagelearning.Util.Util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDialog {
    private static final String PARENT_DIR = "..";
    private final String TAG = getClass().getName();
    private String[] fileList;
    private File currentPath;


    public interface FileSelectedListener {
        void fileSelected(File file);
    }

    public interface DirectorySelectedListener {
        void directorySelected(File directory);
    }

    private ListenerList<FileSelectedListener> fileListenerList = new ListenerList<FileSelectedListener>();
    private ListenerList<DirectorySelectedListener> dirListenerList = new ListenerList<DirectorySelectedListener>();
    private final Activity activity;
    private boolean selectDirectoryOption = true;
    private String fileEndsWith;

    /**
     * @param activity
     * @param initialPath
     */
    public FileDialog(Activity activity, File initialPath) {
        this(activity, initialPath, null);
    }

    public FileDialog(Activity activity, File initialPath, String fileEndsWith) {
        this.activity = activity;
        setFileEndsWith(fileEndsWith);
        if (!initialPath.exists()) initialPath = Environment.getExternalStorageDirectory();
        loadFileList(initialPath);
    }

    /**
     * @return file dialog
     */
    public Dialog createFileDialog(final ArrayList<String> image_list) {
        Dialog dialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getText(R.string.title_folderSelect_default) + "\n" + currentPath.getPath());
        if (selectDirectoryOption) {
            builder.setPositiveButton("Select directory", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    image_list.clear();
                    Log.d(TAG, currentPath.getPath());
                    fireDirectorySelectedEvent(currentPath);
                    for (int i = 0; i < fileList.length; i++) {
                        if (!fileList[i].contains(".."))
                            if ((new File(currentPath.getAbsoluteFile() + "/" + fileList[i])).isFile())
                                image_list.add(currentPath.getAbsoluteFile() + "/" + fileList[i]);
                    }
                    //是否要image_list不为空才能进行一下操作			re:这里允许image_list为空，因为后面的所有操作都会进行image_list的检查
                    ((ImageActivity) activity).setCsvDir(currentPath.getAbsolutePath() + "/" + Util.truncateFileName(currentPath.getAbsolutePath()) + ".csv");
                    boolean flag = ((ImageActivity) activity).CSV.readCSV(((ImageActivity) activity).gradingTable, ((ImageActivity) activity).getCsvDir());
                    if (!flag)
                        Toast.makeText(activity.getApplicationContext(), activity.getText(R.string.errMsg_noCSV), Toast.LENGTH_SHORT).show();
                    else {
                        ((ImageActivity) activity).gradingTable.mergeAndSortByDir();
                    }
                    ((ImageActivity) activity).setImg_counter(0);
                    ((ImageActivity) activity).changeImg();
                    ((ImageActivity) activity).updateSmileLevel();
                    ((ImageActivity) activity).updateButtonSelect();
                }
            });
        }

        builder.setItems(fileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String fileChosen = fileList[which];
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    loadFileList(chosenFile);
                    dialog.cancel();
                    dialog.dismiss();
                    showDialog(image_list);
                } else {
                    builder.show();
                    //fireFileSelectedEvent(chosenFile);
                }
            }
        });

        dialog = builder.show();
        return dialog;
    }


    public void addFileListener(FileSelectedListener listener) {
        fileListenerList.add(listener);
    }

    public void removeFileListener(FileSelectedListener listener) {
        fileListenerList.remove(listener);
    }

    public void setSelectDirectoryOption(boolean selectDirectoryOption) {
        this.selectDirectoryOption = selectDirectoryOption;
    }

    public void addDirectoryListener(DirectorySelectedListener listener) {
        dirListenerList.add(listener);
    }

    public void removeDirectoryListener(DirectorySelectedListener listener) {
        dirListenerList.remove(listener);
    }

    /**
     * Show file dialog
     */
    public void showDialog(ArrayList<String> image_list) {
        createFileDialog(image_list).show();
    }

    private void fireFileSelectedEvent(final File file) {

        fileListenerList.fireEvent(new edu.cwru.sail.imagelearning.Activity.ListenerList.FireHandler<FileSelectedListener>() {
            public void fireEvent(FileSelectedListener listener) {
                listener.fileSelected(file);
            }
        });
    }

    private void fireDirectorySelectedEvent(final File directory) {
        dirListenerList.fireEvent(new edu.cwru.sail.imagelearning.Activity.ListenerList.FireHandler<DirectorySelectedListener>() {
            public void fireEvent(DirectorySelectedListener listener) {
                listener.directorySelected(directory);
            }
        });
    }

    private void loadFileList(File path) {
        this.currentPath = path;
        List<String> r = new ArrayList<String>();
        if (path.exists()) {
            if (path.getParentFile() != null) r.add(PARENT_DIR);
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    if (!sel.canRead()) return false;
//                    if (selectDirectoryOption) return sel.isDirectory();
                    else {
                        boolean endsWith = fileEndsWith != null ? filename.toLowerCase().endsWith(fileEndsWith) : true;
                        return endsWith || sel.isDirectory();
                    }
                }
            };
            File[] fileList1 = path.listFiles(filter);
//            File[] fileList1 = path.listFiles();
            if (fileList1 != null)
                for (int i = 0; i < fileList1.length; i++) {
                    r.add(fileList1[i].getName());
                }
        }
        String[] temp = r.toArray(new String[]{});
        fileList = new String[temp.length];
        for (int i = 0; i < fileList.length; i++) {
            fileList[i] = temp[i];
        }
        Arrays.sort(fileList);
    }

    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) return currentPath.getParentFile();
        else return new File(currentPath, fileChosen);
    }

    private void setFileEndsWith(String fileEndsWith) {
        this.fileEndsWith = fileEndsWith != null ? fileEndsWith.toLowerCase() : fileEndsWith;
    }

}

class ListenerList<L> {
    private List<L> listenerList = new ArrayList<L>();

    public interface FireHandler<L> {
        void fireEvent(L listener);
    }

    public void add(L listener) {
        listenerList.add(listener);
    }

    public void fireEvent(FireHandler<L> fireHandler) {
        List<L> copy = new ArrayList<L>(listenerList);
        for (L l : copy) {
            fireHandler.fireEvent(l);
        }
    }

    public void remove(L listener) {
        listenerList.remove(listener);
    }

    public List<L> getListenerList() {
        return listenerList;
    }
}
