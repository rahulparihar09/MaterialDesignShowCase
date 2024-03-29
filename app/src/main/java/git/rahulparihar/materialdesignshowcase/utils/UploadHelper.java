package git.rahulparihar.materialdesignshowcase.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.nispok.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import git.rahulparihar.materialdesignshowcase.R;
import git.rahulparihar.materialdesignshowcase.model.AppInfo;

public class UploadHelper {
    private static UploadHelper instance = null;


    private UploadHelper(AppCompatActivity act, List<AppInfo> applicationList) {
        this.act = act;

        if (applicationList != null) {
            this.applicationList = applicationList;
        } else {
            this.applicationList = new ArrayList<AppInfo>();
        }
    }

    private AppCompatActivity act;
    private List<AppInfo> applicationList = new ArrayList<AppInfo>();

    public static UploadHelper getInstance(AppCompatActivity act, List<AppInfo> applicationList) {
        if (instance == null) {
            instance = new UploadHelper(act, applicationList);
        } else if (act != null) {
            instance.act = act;
        }
        return instance;
    }

    public void destroy() {
        this.act = null;
    }

    public UploadComponentInfoTask upload(AppInfo appInfo) {
        UploadComponentInfoTask ucit = new UploadComponentInfoTask();
        ucit.execute(appInfo);
        return ucit;
    }

    public UploadComponentInfoTask uploadAll() {
        UploadComponentInfoTask ucit = new UploadComponentInfoTask();
        ucit.execute();
        return ucit;
    }

    public class UploadComponentInfoTask extends AsyncTask<AppInfo, Integer, Boolean> {
        ProgressDialog mProgressDialog;

        public boolean isRunning = false;

        public void showProgress(Activity act) {
            mProgressDialog = new ProgressDialog(act);
            mProgressDialog.setTitle(R.string.dialog_uploading);
            mProgressDialog.setMessage(act.getString(R.string.dialog_processinganduploading));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMax(applicationList.size());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            mProgressDialog.show();
        }

        @Override
        protected void onPreExecute() {
            if (!Network.isAvailiable(act)) {
                this.cancel(true);
                Snackbar.with(act).text(act.getString(R.string.dialog_nointernet)).show(act);
            } else {
                showProgress(act);
            }

            isRunning = true;

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(AppInfo... params) {
            boolean updateRequired = false;
            if (params == null || params.length == 0) {
                mProgressDialog.setMax(applicationList.size());

                int i = 0;
                for (AppInfo ai : applicationList) {
                    updateRequired = postData();
                    publishProgress(i);
                    if (updateRequired) {
                        break;
                    }
                    i++;
                }
            } else if (params.length == 1) {
                updateRequired = postData();
                publishProgress(applicationList.size());
            }

            return updateRequired;
        }

        @Override
        protected void onPostExecute(Boolean updateRequired) {
            isRunning = false;

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            super.onPostExecute(updateRequired);
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values.length > 0 && mProgressDialog != null) {
                mProgressDialog.setProgress(values[0]);
            }
            super.onProgressUpdate(values);
        }
    }

    public boolean postData() {
        try {
            Thread.sleep(100);
        } catch (Exception ex) {

        }
        return false;
    }
}
