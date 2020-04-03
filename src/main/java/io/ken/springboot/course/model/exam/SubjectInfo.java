package io.ken.springboot.course.model.exam;

/**
 * 考试科目
 * @author GouYudong
 * @create 2019-12-10 10:18
 **/
public class SubjectInfo {

    private String imgSrc; // 图片路径
    private String imgActiveSrc;  // 高亮的图片
    private String text;  // 名称
    private String isTest;  // 是否考核      1:是；0：否

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgActiveSrc() {
        return imgActiveSrc;
    }

    public void setImgActiveSrc(String imgActiveSrc) {
        this.imgActiveSrc = imgActiveSrc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIsTest() {
        return isTest;
    }

    public void setIsTest(String isTest) {
        this.isTest = isTest;
    }
}
