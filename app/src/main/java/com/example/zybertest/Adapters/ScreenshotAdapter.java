package com.example.zybertest.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zybertest.Models.GameScreenshot;
import com.example.zybertest.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotAdapter extends RecyclerView.Adapter<ScreenshotAdapter.ScreenshotViewHolder> {

    private final Context context;
    private List<GameScreenshot> screenshots = new ArrayList<>();

    public ScreenshotAdapter(Context context, List<GameScreenshot> screenshots) {
        this.context = context;
        setScreenshots(screenshots);
    }

    @NonNull
    @Override
    public ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_screenshot, parent, false);
        return new ScreenshotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotViewHolder holder, int position) {
        GameScreenshot screenshot = screenshots.get(position);

        // Загрузка изображения с обработкой ошибок
        Picasso.get()
                .load(screenshot.getImage_url())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.screenshotImage);

        // Обработка клика для открытия полноэкранного просмотра
        holder.screenshotImage.setOnClickListener(v -> {
            List<String> imageUrls = getImageUrls();
            showImagePagerDialog(imageUrls, position);
        });
    }

    @Override
    public int getItemCount() {
        return screenshots.size();
    }

    public void setScreenshots(List<GameScreenshot> newScreenshots) {
        screenshots.clear();
        for (GameScreenshot s : newScreenshots) {
            if (!s.iscover()) {
                screenshots.add(s);
            }
        }
        notifyDataSetChanged();
    }

    private List<String> getImageUrls() {
        List<String> urls = new ArrayList<>();
        for (GameScreenshot s : screenshots) {
            urls.add(s.getImage_url());
        }
        return urls;
    }

    private void showImagePagerDialog(List<String> imageUrls, int startPosition) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_image_pager);

        ViewPager2 viewPager = dialog.findViewById(R.id.image_view_pager);
        ImageButton closeButton = dialog.findViewById(R.id.close_button);

        // Настройка адаптера для ViewPager2
        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageUrls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(startPosition, false);

        // Закрытие диалога
        closeButton.setOnClickListener(v -> dialog.dismiss());

        // Показываем диалог
        dialog.show();
    }

    static class ScreenshotViewHolder extends RecyclerView.ViewHolder {
        PhotoView screenshotImage;

        public ScreenshotViewHolder(@NonNull View itemView) {
            super(itemView);
            screenshotImage = itemView.findViewById(R.id.screenshot_image);

            // Настройка параметров масштабирования для превью
            screenshotImage.setMaximumScale(3f);
            screenshotImage.setMediumScale(1.5f);
        }
    }

    private static class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {
        private final Context context;
        private final List<String> imageUrls;

        public ImagePagerAdapter(Context context, List<String> imageUrls) {
            this.context = context;
            this.imageUrls = imageUrls;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image_pager, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            String imageUrl = imageUrls.get(position);

            // Загрузка изображения в полноэкранном режиме
            Picasso.get()
                    .load(imageUrl)
                    .into(holder.photoView);

            // Дополнительные настройки PhotoView
            holder.photoView.setMaximumScale(8f);
            holder.photoView.setMediumScale(3f);
        }

        @Override
        public int getItemCount() {
            return imageUrls.size();
        }

        static class ImageViewHolder extends RecyclerView.ViewHolder {
            PhotoView photoView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                photoView = itemView.findViewById(R.id.photoView);

                // Включение масштабирования
                photoView.setZoomable(true);
            }
        }
    }
}