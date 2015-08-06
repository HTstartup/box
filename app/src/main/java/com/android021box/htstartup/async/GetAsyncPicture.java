package com.android021box.htstartup.async;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android021box.htstartup.R;
import com.android021box.htstartup.tool.BitmapCut;
import com.android021box.htstartup.tool.GetPhoto;

public class GetAsyncPicture {
    private Context context;

    public GetAsyncPicture(Context context) {
        this.context = context;
    }

    public void getImage(String path, ImageView imageView, final int size) {
        final GetPhoto getphoto = new GetPhoto();
        try {
            AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
            Drawable cachedImage = asyncImageLoader.loadDrawable(path,
                    imageView, null, new AsyncImageLoader.ImageCallback() {
                        @Override
                        public void imageLoaded(Drawable imageDrawable,
                                                ImageView imageView, ProgressBar progressbar) {
                            // TODO Auto-generated method stub
                            imageView.setImageDrawable(getphoto
                                    .getRoundedCornerDrawable(imageDrawable,
                                            size));
                        }
                    });
            if (cachedImage != null) {
                imageView.setImageDrawable(getphoto.getRoundedCornerDrawable(
                        cachedImage, size));
            } else {
                Resources res = context.getResources();
                Drawable drawable = res.getDrawable(R.drawable.usericon);
                imageView.setImageDrawable(getphoto.getRoundedCornerDrawable(
                        drawable, size));
            }
        } catch (Exception e) {
            Resources res = context.getResources();
            Drawable drawable = res.getDrawable(R.drawable.usericon);
            imageView.setImageDrawable(getphoto.getRoundedCornerDrawable(
                    drawable, size));
            Log.e("GetAsyncPicture", "error to get" + e.toString());
        }
    }

    public void getSquarePhoto(String path, ImageView imageView) {
        try {
            final BitmapCut picCut = new BitmapCut();
            AsyncPhotoLoader asyncphotoLoader = new AsyncPhotoLoader();
            Drawable cachedImage = asyncphotoLoader.loadDrawable(path,
                    imageView, new AsyncPhotoLoader.PhotoCallback() {
                        @Override
                        public void imageLoaded(Drawable imageDrawable,
                                                ImageView imageView) {
                            // TODO Auto-generated method stub
                            imageView.setImageBitmap(picCut.ImageCrop(
                                    ((BitmapDrawable) imageDrawable)
                                            .getBitmap(), false));
                        }
                    });
            if (cachedImage != null) {
                imageView.setImageBitmap(picCut.ImageCrop(
                        ((BitmapDrawable) cachedImage).getBitmap(), false));
            } else {
                imageView.setImageResource(R.drawable.phbg);
            }
        } catch (Exception e) {
            imageView.setImageResource(R.drawable.phbg);
            Log.e("GetAsyncPicture", "error to get" + e.toString());
        }
    }

    public void getSquareImage(final String path, ImageView imageView) {
        try {
            final BitmapCut picCut = new BitmapCut();
            Drawable cachedImage = null;
            AsyncImageLoader asyncimageLoader = new AsyncImageLoader();
            cachedImage = asyncimageLoader.loadDrawable(path, imageView,
                    null, new AsyncImageLoader.ImageCallback() {
                        @Override
                        public void imageLoaded(Drawable imageDrawable,
                                                ImageView imageView, ProgressBar progressbar) {
                            if (imageView.getTag() != null
                                    && imageView.getTag().equals(path)) {
                                imageView.setImageBitmap(picCut.ImageCrop(
                                        ((BitmapDrawable) imageDrawable)
                                                .getBitmap(), false));
                            }
                        }
                    });
            if (cachedImage != null) {
                if (imageView.getTag() != null
                        && imageView.getTag().equals(path)) {
                    imageView.setImageBitmap(picCut.ImageCrop(
                            ((BitmapDrawable) cachedImage).getBitmap(), false));
                }
            } else {
                imageView.setImageResource(R.drawable.phbg);
            }
        } catch (Exception e) {
            imageView.setImageResource(R.drawable.phbg);
            Log.e("GetAsyncPicture", "error to get" + e.toString());
        }
    }

    public void getRectImage(final String path, ImageView imageView) {
        final BitmapCut picCut = new BitmapCut();
        try {
            Drawable cachedImage = null;
            AsyncImageLoader asyncimageLoader = new AsyncImageLoader();
            cachedImage = asyncimageLoader.loadDrawable(path, imageView, null,
                    new AsyncImageLoader.ImageCallback() {
                        @Override
                        public void imageLoaded(Drawable imageDrawable,
                                                ImageView imageView, ProgressBar progressbar) {
                            if (imageView.getTag() != null
                                    && imageView.getTag().equals(path)) {
                                imageView.setImageBitmap(picCut
                                        .ImageCropWithRect(((BitmapDrawable) imageDrawable)
                                                .getBitmap()));
                            }
                        }
                    });
            if (cachedImage != null) {
                if (imageView.getTag() != null
                        && imageView.getTag().equals(path)) {
                    imageView.setImageBitmap(picCut
                            .ImageCropWithRect(((BitmapDrawable) cachedImage)
                                    .getBitmap()));
                }
            } else {
                Resources res = context.getResources();
                NinePatchDrawable drawable = (NinePatchDrawable) res
                        .getDrawable(R.drawable.phbg);
                Bitmap bitmap = bitmapNinePatchDrawable(drawable);
                imageView.setImageBitmap(picCut.ImageCropWithRect(bitmap));
            }
        } catch (Exception e) {
            Resources res = context.getResources();
            NinePatchDrawable drawable = (NinePatchDrawable) res
                    .getDrawable(R.drawable.phbg);
            Bitmap bitmap = bitmapNinePatchDrawable(drawable);
            imageView.setImageBitmap(picCut.ImageCropWithRect(bitmap));
            Log.e("GetAsyncPicture", "error to get" + e.toString());
        }
    }


    public void getRoundPhoto(final String path, ImageView imageView) {
        final BitmapCut cut = new BitmapCut();
        if (path != null) {
            try {
                AsyncPhotoLoader asyncphotoLoader = new AsyncPhotoLoader();
                Drawable cachedImage = asyncphotoLoader.loadDrawable(path,
                        imageView, new AsyncPhotoLoader.PhotoCallback() {
                            @Override
                            public void imageLoaded(Drawable imageDrawable,
                                                    ImageView imageView) {
                                // TODO Auto-generated method stub
                                if (imageView.getTag() != null
                                        && imageView.getTag().equals(path)) {
                                    imageView.setImageBitmap(cut
                                            .toRoundBitmap(((BitmapDrawable) imageDrawable)
                                                    .getBitmap()));
                                }
                            }
                        });
                if (cachedImage != null) {
                    if (imageView.getTag() != null
                            && imageView.getTag().equals(path)) {
                        imageView.setImageBitmap(cut
                                .toRoundBitmap(((BitmapDrawable) cachedImage)
                                        .getBitmap()));
                    }
                } else {
                    Resources res = context.getResources();
                    Drawable drawable = res.getDrawable(R.drawable.usericon);
                    imageView.setImageBitmap(cut
                            .toRoundBitmap(((BitmapDrawable) cachedImage)
                                    .getBitmap()));
                }
            } catch (Exception e) {
                Resources res = context.getResources();
                Drawable drawable = res.getDrawable(R.drawable.usericon);
                imageView
                        .setImageBitmap(cut
                                .toRoundBitmap(((BitmapDrawable) drawable)
                                        .getBitmap()));
                Log.e("GetAsyncPicture", "error to get" + e.toString());
            }
        } else {
            Resources res = context.getResources();
            Drawable drawable = res.getDrawable(R.drawable.usericon);
            imageView.setImageBitmap(cut
                    .toRoundBitmap(((BitmapDrawable) drawable).getBitmap()));
        }
    }

    public void getRoundCornerPhoto(String path, ImageView imageView,
                                    final int size) {
        final GetPhoto getphoto = new GetPhoto();
        if (path != null) {
            try {
                AsyncPhotoLoader asyncphotoLoader = new AsyncPhotoLoader();
                Drawable cachedImage = asyncphotoLoader.loadDrawable(path,
                        imageView, new AsyncPhotoLoader.PhotoCallback() {
                            @Override
                            public void imageLoaded(Drawable imageDrawable,
                                                    ImageView imageView) {
                                // TODO Auto-generated method stub
                                imageView.setImageDrawable(getphoto
                                        .getRoundedCornerDrawable(
                                                imageDrawable, size));
                            }
                        });
                if (cachedImage != null) {
                    imageView.setImageDrawable(getphoto
                            .getRoundedCornerDrawable(cachedImage, size));
                } else {
                    Resources res = context.getResources();
                    Drawable drawable = res.getDrawable(R.drawable.usericon);
                    imageView.setImageDrawable(getphoto
                            .getRoundedCornerDrawable(drawable, size));
                }
            } catch (Exception e) {
                Resources res = context.getResources();
                Drawable drawable = res.getDrawable(R.drawable.usericon);
                imageView.setImageDrawable(getphoto.getRoundedCornerDrawable(
                        drawable, size));
                Log.e("GetAsyncPicture", "error to get" + e.toString());
            }
        } else {
            Resources res = context.getResources();
            Drawable drawable = res.getDrawable(R.drawable.usericon);
            imageView.setImageDrawable(getphoto.getRoundedCornerDrawable(
                    drawable, size));
        }
    }

    private Bitmap bitmapNinePatchDrawable(NinePatchDrawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
