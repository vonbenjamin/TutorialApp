package com.example.jiamoufang.tutorialapp.adapter.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.base.BaseViewHolder;
import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;
import com.example.jiamoufang.tutorialapp.factory.ImageLoaderFactory;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import cn.bmob.newim.bean.BmobIMLocationMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by jiamoufang on 2017/12/22.
 */

public class ReceiveLocationHolder extends BaseViewHolder {

    @Bind(R.id.iv_avatar)
    protected ImageView iv_avatar;

    @Bind(R.id.tv_time)
    protected TextView tv_time;

    @Bind(R.id.tv_location)
    protected TextView tv_location;

    public ReceiveLocationHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_chat_received_location,onRecyclerViewListener);
    }

    @Override
    public void bindData(Object o) {
        BmobIMMessage msg = (BmobIMMessage)o;
        //用户信息的获取必须在buildFromDB之前，否则会报错'Entity is detached from DAO context'
        final BmobIMUserInfo info = msg.getBmobIMUserInfo();
        //加载头像
        ImageLoaderFactory.getLoader(mContext).loadAvatar(iv_avatar,info != null ? info.getAvatar() : null, R.mipmap.icon_message_press);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(msg.getCreateTime());
        tv_time.setText(time);
        //
        final BmobIMLocationMessage message = BmobIMLocationMessage.buildFromDB(msg);
        tv_location.setText(message.getAddress());
        //
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("经度：" + message.getLongitude() + ",维度：" + message.getLatitude());
                if(onRecyclerViewListener!=null){
                    onRecyclerViewListener.onItemClick(getAdapterPosition());
                }
            }
        });
        tv_location.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onRecyclerViewListener != null) {
                    onRecyclerViewListener.onItemLongClick(getAdapterPosition());
                }
                return true;
            }
        });

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("点击"+info.getName()+"头像");
            }
        });
    }

    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
