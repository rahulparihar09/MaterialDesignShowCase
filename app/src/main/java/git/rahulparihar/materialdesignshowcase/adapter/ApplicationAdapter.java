package git.rahulparihar.materialdesignshowcase.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import git.rahulparihar.materialdesignshowcase.R;
import git.rahulparihar.materialdesignshowcase.RecyclerSampleActivity;
import git.rahulparihar.materialdesignshowcase.model.AppInfo;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private List<AppInfo> applications;
    private int rowLayout;
    private Activity mAct;

    public ApplicationAdapter(List<AppInfo> applications, int rowLayout, Activity act) {
        this.applications = applications;
        this.rowLayout = rowLayout;
        this.mAct = act;
    }


    public void clearApplications() {
        int size = this.applications.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                applications.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void addApplications(List<AppInfo> applications) {
        this.applications.addAll(applications);
        this.notifyItemRangeInserted(0, applications.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final AppInfo appInfo = applications.get(i);
        viewHolder.name.setText(appInfo.getName());
        viewHolder.image.setImageDrawable(appInfo.getIcon());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecyclerSampleActivity) mAct).animateActivity(appInfo, viewHolder.image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applications == null ? 0 : applications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.appName);
            image = (ImageView) itemView.findViewById(R.id.appIcon);
        }

    }
}
