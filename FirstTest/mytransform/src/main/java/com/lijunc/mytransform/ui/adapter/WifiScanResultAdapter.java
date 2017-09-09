package com.lijunc.mytransform.ui.adapter;

import android.content.Context;
import android.net.sip.SipAudioCall;
import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.lijunc.mytransform.R;

import java.util.List;

/**
 * Created by lijunc on 2017/9/8.
 */

public class WifiScanResultAdapter extends RecyclerView.Adapter<WifiScanResultAdapter.ScanViewHolder>{

    private Context mContext;
    private List<ScanResult> mScanResultList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener ){
        this.mListener=listener;
    }
    public WifiScanResultAdapter(Context context, List<ScanResult> scanResultList) {
        mContext = context;
        mScanResultList = scanResultList;
    }



    @Override
    public ScanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.scan_result_item, parent, false);
        ScanViewHolder holder = new ScanViewHolder(inflate);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ScanViewHolder holder, int position) {
        ScanResult result = mScanResultList.get(position);

        holder.tv_name.setText(result.SSID.toString());
        holder.mac_name.setText(result.BSSID.toString());
        if(mListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position1 = holder.getLayoutPosition();
                    mListener.onItemClick(holder.itemView,position1);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return mScanResultList.size();
    }



    class ScanViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
         TextView mac_name;
        public ScanViewHolder(View itemView) {
            super(itemView);
            mac_name=(TextView)itemView.findViewById(R.id.mac_name);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);
        }
    }
}
