package me.cl.lingxi.common.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import me.cl.lingxi.R;
import me.cl.lingxi.adapter.FeedAdapter;
import me.cl.lingxi.adapter.FeedPhotoAdapter;
import me.cl.lingxi.common.config.Constants;
import me.cl.lingxi.common.glide.GlideApp;
import me.cl.lingxi.entity.Like;

/**
 * author : Bafs
 * e-mail : bafs.jy@live.com
 * time   : 2018/05/29
 * desc   : 内容管理
 * version: 1.0
 */
public class ContentUtil {

    /**
     * 设置用户头像，相对路径
     */
    public static void loadUserAvatar(ImageView imageView, String avatar) {
        String url = Constants.IMG_URL + avatar;
        GlideApp.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.img_user)
                .error(R.drawable.img_user)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    /**
     * 加载动态图片
     */
    public static void loadFeedImage(ImageView imageView, String url) {
        url = Constants.IMG_URL + url;
        GlideApp.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载图片
     */
    public static void loadImage(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 设置与我相关提示
     */
    public static void setMoreBadge(TextView textView) {
        Context context = textView.getContext();
        Drawable mineItemAt = context.getResources().getDrawable(R.drawable.ic_eit);
        Drawable mineItemRight = context.getResources().getDrawable(R.drawable.ic_more);
        Drawable mineBadgeRight = context.getResources().getDrawable(R.drawable.ic_more_badge);
        mineItemAt.setBounds(0, 0, mineItemAt.getIntrinsicWidth(), mineItemAt.getIntrinsicHeight());
        mineItemRight.setBounds(0, 0, mineItemRight.getIntrinsicWidth(), mineItemRight.getIntrinsicHeight());
        mineBadgeRight.setBounds(0, 0, mineBadgeRight.getIntrinsicWidth(), mineBadgeRight.getIntrinsicHeight());
        if (Constants.isRead) {
            textView.setCompoundDrawables(mineItemAt, null, mineItemRight, null);
        } else {
            textView.setCompoundDrawables(mineItemAt, null, mineBadgeRight, null);
        }
    }

    /**
     * 设置喜欢面板
     */
    public static void setLikePeople(TextView likePeople, TextView likeNum, LinearLayout likeWindow, List<Like> likeList) {
        int num = likeList == null ? 0 :likeList.size();
        likeNum.setText(String.valueOf(num));
        String likeStr = Utils.getLikeStr(likeList);
        switch (num) {
            case 0:
                likeNum.setText("赞");
                likeWindow.setVisibility(View.GONE);
                break;
            case 1:
            case 2:
            case 3:
                likeWindow.setVisibility(View.VISIBLE);
                likeStr = likeStr + "觉得很赞";
                break;
            default:
                likeWindow.setVisibility(View.VISIBLE);
                likeStr = likeStr + "等" + num + "人觉得很赞";
                break;
        }
        likePeople.setText(Utils.colorFormat(likeStr));
    }

    /**
     * 设置动态图片适配器
     */
    public static void setFeedPhotoAdapter(RecyclerView recyclerView, final List<String> photos, final FeedAdapter.OnItemListener mOnItemListener) {
        FeedPhotoAdapter adapter = new FeedPhotoAdapter(photos);
        adapter.setOnItemClickListener(new FeedPhotoAdapter.OnItemClickListener() {
            @Override
            public void onPhotoClick(int position) {
                // 新对象接防止拼接后影响原来的url
                List<String> urls = new ArrayList<>(photos);
                int size = urls.size();
                // 拼接url
                for (int i = 0; i < size; i++) {
                    String photo = urls.get(i);
                    photo = Constants.IMG_URL + photo;
                    urls.set(i, photo);
                }
                if (mOnItemListener != null) mOnItemListener.onPhotoClick((ArrayList<String>) urls, position);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}