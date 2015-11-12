package com.example.daniel.qrcodecreator.adapters;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.qrcodecreator.MyWifiProperties;
import com.example.daniel.qrcodecreator.R;
import com.example.daniel.qrcodecreator.fragments.WifiListFragment;

import java.util.List;

/**
 * Created by Daniel on 12/11/2015.
 */

public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ViewHolder> {


    private List<MyWifiProperties> data;
    private WifiListFragment fragment;

    public WifiListAdapter(List<MyWifiProperties> data,WifiListFragment fragment) {
        this.data = data;
        this.fragment = fragment;
    }

    @Override
    public WifiListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wifi_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(WifiListAdapter.ViewHolder holder, int position) {
        MyWifiProperties myWifi = data.get(position);
        // holder.text_view.setText(data.);
        holder.bindWifi(myWifi);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public MyWifiProperties myWifiProperties;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (myWifiProperties != null) {
                        Log.i("Item chosen", "List result : " + myWifiProperties);
                        if (myWifiProperties.getType().equals("nopass")) {
                            fragment.showNoPasswordDialog(myWifiProperties);
                        }else
                            fragment.showPasswordDialog(myWifiProperties);

                    }
                        //Show this wifi dialog with this wifi properties
                    //and change fragment after dialog
                       // mainActivity.changeFragment(VideoClipDetailFragment.newInstance(videoClip), false);

                }
            });
            title = (TextView) itemView.findViewById(R.id.wifi_name);
        }

        public void bindWifi(MyWifiProperties myWifiProperties) {

            this.myWifiProperties = myWifiProperties;
            title.setText(myWifiProperties.getSsid());
            String dbg = "";
        }

    }
}

