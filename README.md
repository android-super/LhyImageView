# LhyImageView
自定义圆形，圆角，带边框ImageView

自定义属性有以下几种：\
circle, round_all,\
round_top_left,round_top_right, round_bottom_left, round_bottom_right,\
round_top_both, round_left_both, round_bottom_both,round_right_both,\
round_top_left_other, round_top_right_other,round_bottom_left_other, round_bottom_right_other

边框颜色设置 crop_border_color\
边框宽度设置 crop_border_width\
边框是否显示 crop_border_is_show\
圆角大小设置 crop_radius

默认图片格式是 center_crop

如:
<com.lhy.view.LhyRoundImageView\
        &&&&&&&&android:id="@+id/lhyRoundImageView"\
        &&&&&&&&android:layout_width="200dp"\
        &&&&&&&&android:layout_height="100dp"\
        &&&&&&&&app:crop_border_color="#858784"\
        &&&&&&&&app:crop_border_is_show="true"\
        &&&&&&&&app:crop_border_width="3dp"\
        &&&&&&&&app:crop_radius="15dp"\
        &&&&&&&&app:crop_type="round_top_right_other" />