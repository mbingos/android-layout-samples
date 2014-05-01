package org.lucasr.layoutsamples.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.lucasr.layoutsamples.adapter.Tweet;
import org.lucasr.layoutsamples.adapter.TweetPresenter;
import org.lucasr.layoutsamples.app.R;
import org.lucasr.layoutsamples.util.ImageUtils;

import java.util.EnumMap;
import java.util.EnumSet;

public class TweetCompositeView extends RelativeLayout implements TweetPresenter {
    private final ImageView mProfileImage;
    private final TextView mAuthorText;
    private final TextView mMessageText;
    private final ImageView mPostImage;
    private final EnumMap<Action, ImageView> mActionIcons;

    public TweetCompositeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TweetCompositeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.tweet_composite_view, this, true);
        mProfileImage = (ImageView) findViewById(R.id.profile_image);
        mAuthorText = (TextView) findViewById(R.id.author_text);
        mMessageText = (TextView) findViewById(R.id.message_text);
        mPostImage = (ImageView) findViewById(R.id.post_image);

        mActionIcons = new EnumMap(Action.class);
        for (Action action : Action.values()) {
            final ImageView icon;
            switch (action) {
                case REPLY:
                    icon = (ImageView) findViewById(R.id.reply_action);
                    break;

                case RETWEET:
                    icon = (ImageView) findViewById(R.id.retweet_action);
                    break;

                case FAVOURITE:
                    icon = (ImageView) findViewById(R.id.favourite_action);
                    break;

                default:
                    throw new IllegalArgumentException("Unrecognized tweet action");
            }

            mActionIcons.put(action, icon);
        }
    }

    @Override
    public void update(Tweet tweet, EnumSet<UpdateFlags> flags) {
        mMessageText.setText(Html.fromHtml(tweet.getMessage()));
        mAuthorText.setText(tweet.getAuthorName());

        final Context context = getContext();
        ImageUtils.loadImage(context, mProfileImage, tweet.getProfileImageUrl(), flags);

        final boolean hasPostImage = !TextUtils.isEmpty(tweet.getPostImageUrl());
        mPostImage.setVisibility(hasPostImage ? View.VISIBLE : View.GONE);
        if (hasPostImage) {
            ImageUtils.loadImage(context, mPostImage, tweet.getPostImageUrl(), flags);
        }
    }
}
