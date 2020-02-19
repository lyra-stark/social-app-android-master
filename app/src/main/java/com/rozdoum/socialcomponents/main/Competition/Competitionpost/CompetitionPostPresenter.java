package com.rozdoum.socialcomponents.main.Competition.Competitionpost;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.rozdoum.socialcomponents.ApplicationHelper;
import com.rozdoum.socialcomponents.R;
import com.rozdoum.socialcomponents.main.post.BaseCreatePostPresenter;
import com.rozdoum.socialcomponents.main.post.createPost.CreatePostView;
import com.rozdoum.socialcomponents.managers.DatabaseHelper;
import com.rozdoum.socialcomponents.model.Post;

public class CompetitionPostPresenter extends BaseCreatePostPresenter<CreatePostView> {

    public CompetitionPostPresenter(Context context) {
        super(context);
    }
    private DatabaseHelper databaseHelper;
    @Override
    protected int getSaveFailMessage() {
        return R.string.error_fail_create_post;
    }

    public String generatePostId() {
        return databaseHelper
                .getDatabaseReference()
                .child(DatabaseHelper.POSTS_DB_KEY)
                .push()
                .getKey();
    }

    @Override
    protected void savePost(String title, String description) {
        ifViewAttached(view -> {
            view.showProgress(R.string.message_creating_post);
            Post post = new Post();
            post.setTitle(title);
            post.setDescription(description);
            post.setAuthorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            //postManager.createOrUpdatePostWithImage(view.getImageUri(), this, post);
            DatabaseHelper databaseHelper = ApplicationHelper.getDatabaseHelper();
            if (post.getId() == null) {
                post.setId(generatePostId());
            }
        });
    }

    @Override
    protected boolean isImageRequired() {
        return true;
    }
}
